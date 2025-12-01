    package com.poker.poker;

    import javafx.animation.PauseTransition;
    import javafx.util.Duration;

    import java.util.List;
    import java.util.Objects;

    public class GerenciarAposta {

        private final PokerEngine engine;
        private int indiceAtual;
        private int apostaTemp = 0;
        private int maiorAposta=100 ;
        private boolean interromperApostas = false;
        private String ultimoARaise;


        public GerenciarAposta(PokerEngine engine) {
            this.engine = engine;
            this.indiceAtual = 0;
        }



        public void iniciarApostas(List<Jogador> jogadores, Runnable proximaEtapa) {
            System.out.println("🪙 Iniciando rodada de apostas...");

            this.indiceAtual = 0;
            this.apostaTemp = 0;
            engine.getUi().bordaDesativadaTodos();
            ultimoARaise = null;
            realizarAposta(jogadores, proximaEtapa);
        }

        private void realizarAposta(List<Jogador> jogadores, Runnable proximaEtapa) {
            if (interromperApostas) return;


            //AllWin
            if (todosAllin(jogadores)) {
                proximaEtapa.run();
                return;
            }


            if (indiceAtual >= jogadores.size()) {

                // jog da rodada atual antes de resetar:
                Jogador inicioRodada = jogadores.get(0);

                indiceAtual = 0;

                if (todosApostasValidas(jogadores) ||
                        voltouAoUltimoRaise(inicioRodada.getNome())) {

                    proximaEtapa.run();
                    return;
                }
            }





            Jogador atual = jogadores.get(indiceAtual); // pega o jogador da lista começa no bot2,jogador,bot3,bot1, se um der fold tem que tirar ele da lista (da rodada)

            if (atual instanceof JogadorBot) {

                    engine.getUi().bordaAtiva(atual.getNome());

                    if(atual.getFichas()==0){
                        indiceAtual++;
                        engine.getUi().bordaDesativada(atual.getNome());
                        realizarAposta(jogadores, proximaEtapa);
                        return;
                    }

                    PauseTransition pause = new PauseTransition(Duration.seconds(2));
                    pause.setOnFinished(ev -> {


                        CriaJson criaJson = new CriaJson((JogadorBot) atual, engine.mesa, maiorAposta);

                        String resposta = ((JogadorBot) atual).sendIA(criaJson.criar());

                        System.out.println(atual.getNome() + ":" + resposta);

                        if (resposta.equals("fold") && atual.getApostaRodada().get() == maiorAposta) {
                            resposta = "call";
                        }

                        switch (resposta) {

                            case "call": {

                                int diff = maiorAposta - atual.getApostaRodada().get();

                                if (diff > 0) {
                                    if (diff >= atual.getFichas()) {
                                        diff = atual.getFichas();
                                        atual.setAllin(true);
                                    }

                                    atual.apostar(diff);
                                    engine.mesa.addPote(diff);
                                    engine.getUi().mostrarPoteBot(atual.getNome());

                                } else {
                                    // diff == 0
                                    engine.getUi().SoudCheckPlay();
                                }

                                if (atual.getFichas() == 0) {
                                    atual.setAllin(true);
                                }

                                indiceAtual++;
                                engine.getUi().bordaDesativada(atual.getNome());
                                realizarAposta(jogadores, proximaEtapa);
                                return;
                            }


                            case "fold": {
                                atual.setFold(true);
                                jogadores.remove(indiceAtual);
                                atual.resetMao();
                                engine.getUi().SoudFoldPlay();
                                engine.getUi().limpaMaoFold(atual.getNome());
                                if (jogadores.size() == 1) {
                                    interromperApostas = true;
                                    proximaEtapa.run();
                                    return;
                                }
                                if (indiceAtual >= jogadores.size()) {
                                    indiceAtual = 0;
                                }
                                engine.getUi().bordaDesativada(atual.getNome());
                                realizarAposta(jogadores, proximaEtapa);
                                return;
                            }


                            default: {
                                int valor = Integer.parseInt(resposta);

                                if (valor >= atual.getFichas()) {
                                    valor = atual.getFichas();
                                    atual.setAllin(true);
                                }


                                atual.apostar(valor);
                                engine.mesa.addPote(valor);

                                if (atual.getFichas() == 0) {
                                    atual.setAllin(true);
                                }


                                if (atual.getApostaRodada().get() > maiorAposta){
                                    maiorAposta = atual.getApostaRodada().get();
                                    ultimoARaise = atual.getNome();
                                 }
                                engine.getUi().mostrarPoteBot(atual.getNome());
                                indiceAtual++;
                                engine.getUi().bordaDesativada(atual.getNome());
                                realizarAposta(jogadores, proximaEtapa);
                                return;
                            }
                        }
                    });

                    pause.play();

            }

            else {
                // é o jogador real

                engine.getUi().bordaAtiva("jogador");


                if(atual.getFichas()==0){
                    indiceAtual++;
                    engine.getUi().bordaDesativada(atual.getNome());
                    realizarAposta(jogadores, proximaEtapa);
                    return;
                }


                engine.getUi().habilitarBotoes(true); // libera os botoes dele

                engine.getUi().configurarBotoes(atual, jogadores, this, proximaEtapa); // lugar que fica os botoes

                //inicia barra de tempo se passar 25 sec e nao apostou ficha ele da fold se nao aposta a qnt de ficha que ele colocou
                engine.getUi().iniciarBarraTempo(()->{
                     jogadorFold(atual,jogadores,proximaEtapa);
                });





            }
        }


        public void jogadorApostou(Jogador jogador, int valor, List<Jogador> jogadores, Runnable proximaEtapa) {
            // função vem do botao apostar apos passar por confirmar aposta mas passa valor  =0 entao nao apsota duas vezes , qui vem do call tbm qu ese tiver ficha da call senao dele aposta 0 ta no allwin


            jogador.apostar(valor);
            engine.mesa.addPote(valor);
            engine.getUi().pararBarraTempo();
            engine.getUi().habilitarBotoes(false);

            indiceAtual++;

            if (jogador.getApostaRodada().get() > maiorAposta) {
                maiorAposta = jogador.getApostaRodada().get();
                ultimoARaise = jogador.getNome();
            }

            engine.getUi().bordaDesativada("jogador");
            realizarAposta(jogadores, proximaEtapa);
        }

        public void jogadorFold(Jogador jogador, List<Jogador> jogadores, Runnable proximaEtapa) {

            // jogador deu fold, deliga os botoes , retira ele da lista e vai para o proximo

            jogador.setFold(true);

            engine.getUi().pararBarraTempo();

            // Desabilita os botões
            engine.getUi().habilitarBotoes(false);
            engine.getUi().limpaMaoFold("jogador");
            jogadores.remove(indiceAtual);
            if(jogadores.size()==1){
                interromperApostas = true;
                proximaEtapa.run();
                return;
            }
            if (indiceAtual >= jogadores.size()) {
                indiceAtual = 0;
            }
            engine.getUi().bordaDesativada("jogador");
            realizarAposta(jogadores, proximaEtapa);
        }



        public void adicionarFicha(Jogador jogador, int valor) {
            // quando clica nas fichas vem para essa função que adiciona no pote temporario

            if (jogador.getFichas() >= valor) {
                apostaTemp += valor;
                jogador.apostar(valor);
                engine.mesa.addPote(valor);
                System.out.println(jogador.getNome() + " adicionou " + valor + " à aposta. Total: " + apostaTemp);

            } else {
                System.out.println("⚠ " + jogador.getNome() + " não tem fichas suficientes!");
            }
        }

        public void confirmarAposta(Jogador jogador, List<Jogador> jogadores, Runnable proximaEtapa) {

           // quando clica no boatao apostar ele realiza a aposta


            if(apostaTemp<maiorAposta){
                engine.getUi().getCall().fire();
                return;
            }

            System.out.println(jogador.getNome() + " confirmou aposta de " + apostaTemp);
            apostaTemp = 0; // zera para a próxima rodada
            //botao desabilita
            engine.getUi().habilitarBotoes(false);

            //passa para jogador apostou
            jogadorApostou(jogador,apostaTemp,jogadores,proximaEtapa);
        }



        public int getMaiorAposta() {
            return maiorAposta;
        }


        private boolean todosApostasValidas(List<Jogador> jogadores) {
            int n = jogadores.size();

            // CASO ESPECIAL DE 2 JOGADORES
            if (n == 2) {
                Jogador j1 = jogadores.get(0);
                Jogador j2 = jogadores.get(1);

                // Se j1 está all-in, só j2 precisa cobrir a maior aposta
                if (j1.isAllin() && !j2.getFold()) {
                    return j2.getApostaRodada().get() >= maiorAposta;
                }

                // Se j2 está all-in, só j1 precisa cobrir a maior aposta
                if (j2.isAllin() && !j1.getFold()) {
                    return j1.getApostaRodada().get() >= maiorAposta;
                }

                // Caso normal: ninguém em all-in
                return j1.getApostaRodada().get() >= maiorAposta &&
                        j2.getApostaRodada().get() >= maiorAposta;
            }

            // CASO GERAL (3+ jogadores)
            for (Jogador j : jogadores) {

                if (j.isAllin()) continue; // já não aposta mais

                if (j.getApostaRodada().get() < maiorAposta)
                    return false;
            }

            return true;
        }
        private boolean voltouAoUltimoRaise(String nome) {
            return Objects.equals(nome, ultimoARaise);
        }


            private  boolean todosAllin(List<Jogador> jogadores){
                for (Jogador j : jogadores) {
                    if(!j.isAllin())
                    {
                        return false;
                    }
                }
                return true;
            }

    }
