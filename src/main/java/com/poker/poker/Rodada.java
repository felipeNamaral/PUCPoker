package com.poker.poker;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.List;

public class Rodada {
    private final PokerEngine engine;
    private final List<Jogador> jogadoresAtivos;
    private final Mesa mesa;
    private int etapa =0 ;
    private final GerenciarAposta gerenciadorApostas;

    public Rodada(PokerEngine engine,List<Jogador> jogadoresAtivos,Mesa mesa, GerenciarAposta gerenciadorApostas ){
        this.engine = engine;
        this.jogadoresAtivos = jogadoresAtivos;
        this.mesa = mesa;
        this.gerenciadorApostas = gerenciadorApostas;
    }


    public void iniciar(){

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
        engine.mesa.resetaPote();
        engine.getUi().desativarpotes();
        PauseTransition pause = new PauseTransition(Duration.seconds(4));
        pause.setOnFinished(e->{
            engine.finalizarRodada();
        });
        pause.play();
    }



    public  void showdownDireto(Jogador jogadorVencedorDireto){
        // quando 3 dao fold e sobra 1,dai ele ganha direto sem mostar as cartas

        jogadoresAtivos.get(0).ganhaFichas(mesa.getPote());
        engine.mesa.resetaPote();
        engine.getUi().desativarpotes();
        PauseTransition pause = new PauseTransition(Duration.seconds(4));
        pause.setOnFinished(e->{
            engine.finalizarRodada();
        });
        pause.play();
    }
}




