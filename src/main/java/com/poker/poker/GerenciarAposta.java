package com.poker.poker;

import java.util.List;

public class GerenciarAposta {

    private final PokerEngine engine;
    private int indiceAtual;
    private int apostaTemp = 0;
    private int maiorAposta ;

    public GerenciarAposta(PokerEngine engine) {
        this.engine = engine;
        this.indiceAtual = 0;
    }



    public void iniciarApostas(List<Jogador> jogadores, Runnable proximaEtapa) {
        System.out.println("🪙 Iniciando rodada de apostas...");

        this.indiceAtual = 0;
        this.apostaTemp = 0;
        this.maiorAposta = 100;
        engine.mesa.resetaPote();
        realizarAposta(jogadores, proximaEtapa);
    }

    private void realizarAposta(List<Jogador> jogadores, Runnable proximaEtapa) {
        if (indiceAtual >= jogadores.size()) {
            indiceAtual = 0;

            if(todosApostasValidas(jogadores)){
                proximaEtapa.run();  // retorna para rodada
                return;
            }

        }

        Jogador atual = jogadores.get(indiceAtual); // pega o jogador da lista começa no bot2,jogador,bot3,bot1, se um der fold tem que tirar ele da lista (da rodada)

        if (atual instanceof JogadorBot) {

            // esta dando call para teste mas aqui vai chamr função que manda pra ia responder

            atual.apostar(maiorAposta);// aposta maior aposta
            engine.mesa.addPote(maiorAposta);

            engine.getUi().mostrarPoteBot(atual.getNome()); //função pra mostrar o pote


            System.out.println(atual.getNome()+"apostou"+maiorAposta);
            indiceAtual++;  //passa para o proximo jogador


            realizarAposta(jogadores, proximaEtapa);

        }

        else {
            // é o jogador real


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


        jogador.apostar(valor); // jogador aposta o valor
        engine.mesa.addPote(valor);

        engine.getUi().habilitarBotoes(false);

        indiceAtual++;

        if (jogador.getApostaRodada().get() > maiorAposta) {
            maiorAposta = jogador.getApostaRodada().get();
        }

        realizarAposta(jogadores, proximaEtapa);
    }

    public void jogadorFold(Jogador jogador, List<Jogador> jogadores, Runnable proximaEtapa) {

        // jogador deu fold, deliga os botoes , retira ele da lista e vai para o proximo

        jogador.setFold(true);

        // Desabilita os botões
        engine.getUi().habilitarBotoes(false);

        jogadores.remove(indiceAtual);

        realizarAposta(jogadores, proximaEtapa);
    }



    public void adicionarFicha(Jogador jogador, int valor) {
        // quando clica nas fichas vem para essa função que adiciona no pote temporario

        if (jogador.getFichas() >= valor) {
            apostaTemp += valor;
            jogador.apostar(valor);
            System.out.println(jogador.getNome() + " adicionou " + valor + " à aposta. Total: " + apostaTemp);

        } else {
            System.out.println("⚠ " + jogador.getNome() + " não tem fichas suficientes!");
        }
    }

    public void confirmarAposta(Jogador jogador, List<Jogador> jogadores, Runnable proximaEtapa) {

       // quando clica no boatao apostar ele realiza a aposta



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
        for (Jogador j : jogadores) {
            if (!j.getFold()) {
                if (j.getApostaRodada().get() < maiorAposta) {

                    return false;
                }
            }
        }
        return true;
    }

}
