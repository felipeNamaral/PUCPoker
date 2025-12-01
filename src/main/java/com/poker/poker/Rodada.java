    package com.poker.poker;

    import javafx.animation.PauseTransition;
    import javafx.util.Duration;

    import java.util.ArrayList;
    import java.util.List;

    public class Rodada {
        private final PokerEngine engine;
        private final List<Jogador> jogadoresAtivos;
        private final Mesa mesa;
        private final GerenciarAposta gerenciadorApostas;

        public Rodada(PokerEngine engine,List<Jogador> jogadoresAtivos,Mesa mesa, GerenciarAposta gerenciadorApostas ){
            this.engine = engine;
            this.jogadoresAtivos = new ArrayList<>(jogadoresAtivos);
            this.mesa = mesa;
            this.gerenciadorApostas = gerenciadorApostas;
        }


        public void iniciar() {

            Jogador bb = jogadoresAtivos.get(jogadoresAtivos.size() - 2);
            int valorBB = 50;
            if (bb.getFichas() >= valorBB) {
                bb.apostar(valorBB);
                engine.mesa.addPote(valorBB);
            } else {
                bb.apostar(bb.getFichas());
                bb.setAllin(true);
                engine.mesa.addPote(bb.getFichas());
            }
            engine.getUi().mostrarPoteBot(bb.getNome());


            Jogador sb = jogadoresAtivos.get(jogadoresAtivos.size() - 1);
            int valorSB = 100;
            if (sb.getFichas() >= valorSB) {
                sb.apostar(valorSB);
                engine.mesa.addPote(valorSB);
            } else {
                sb.apostar(sb.getFichas());
                sb.setAllin(true);
                engine.mesa.addPote(sb.getFichas());
            }
            engine.getUi().mostrarPoteBot(sb.getNome());

            preFlop();
        }


        // ====== PRÉ-FLOP ======
        private void preFlop() {
            System.out.println("Etapa: Pré-Flop");
            mesa.setEtapa("Pre-Flop");
            gerenciadorApostas.iniciarApostas(jogadoresAtivos, () -> flop());

        }

        // ====== FLOP ======
        private void flop() {

            if(jogadoresAtivos.size()==1){
                showdownDireto(jogadoresAtivos.get(0));
                return;
            }
            System.out.println("Etapa: Flop");
            mesa.setEtapa("Flop");
            engine.adicionarCartasMesa();
            engine.adicionarCartasMesa();
            engine.adicionarCartasMesa();
            engine.getUi().atualizarCartasMesa(mesa, true);
            gerenciadorApostas.iniciarApostas(jogadoresAtivos, () -> turn());
        }

        // ====== TURN ======
        private void turn() {
            if(jogadoresAtivos.size()==1){
                showdownDireto(jogadoresAtivos.get(0));
                return;
            }
            System.out.println("Etapa: Turn");
            mesa.setEtapa("Turn");
            engine.adicionarCartasMesa();
            engine.getUi().atualizarCartasMesa(mesa, false);
            gerenciadorApostas.iniciarApostas(jogadoresAtivos, () -> river());
        }

        // ====== RIVER ======
        private void river() {
            if(jogadoresAtivos.size()==1){
                showdownDireto(jogadoresAtivos.get(0));
                return;
            }
            System.out.println("Etapa: River");
            mesa.setEtapa("River");
            engine.adicionarCartasMesa();
            engine.getUi().atualizarCartasMesa(mesa, false);
            gerenciadorApostas.iniciarApostas(jogadoresAtivos, () -> showdown());
        }

        // ====== SHOWDOWN ======
        private void showdown() {
            //

            engine.getUi().atualizaCartaVirada(engine.jbot1);
            engine.getUi().atualizaCartaVirada(engine.jbot2);
            engine.getUi().atualizaCartaVirada(engine.jbot3);

            for (Jogador aux : jogadoresAtivos) {
                aux.setMaoFinal(mesa.getMao());
            }


            Jogador vencedor = PokerHandEvaluator.avaliarVencedor(jogadoresAtivos);

            System.out.println(vencedor.getNome());
            System.out.println("pote:"+engine.mesa.getPote());

            vencedor.ganhaFichas(mesa.getPote());
            if (vencedor.isAllin() && vencedor.getFichas() > 0) {
                vencedor.setAllin(false);
            }

            engine.mesa.resetaPote();
            engine.getUi().desativarpotes();
            engine.getUi().SoudVitoriaPlay();
            engine.getUi().bordaDoGanhador(vencedor.getNome());

            PauseTransition pause = new PauseTransition(Duration.seconds(4));
            pause.setOnFinished(e->{
                engine.finalizarRodada();
            });
            pause.play();
        }



        public  void showdownDireto(Jogador jogadorVencedorDireto){
            // quando 3 dao fold e sobra 1,dai ele ganha direto sem mostar as cartas


            jogadoresAtivos.get(0).ganhaFichas(mesa.getPote());
            if (jogadoresAtivos.get(0).isAllin() && jogadoresAtivos.get(0).getFichas() > 0) {
                jogadoresAtivos.get(0).setAllin(false);
            }

            engine.mesa.resetaPote();
            engine.getUi().desativarpotes();
            engine.getUi().SoudVitoriaPlay();
            engine.getUi().bordaDoGanhador(jogadoresAtivos.get(0).getNome());
            PauseTransition pause = new PauseTransition(Duration.seconds(6));
            pause.setOnFinished(e->{
                engine.finalizarRodada();
            });
            pause.play();
        }
    }




