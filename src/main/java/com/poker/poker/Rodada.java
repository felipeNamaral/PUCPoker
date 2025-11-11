package com.poker.poker;

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
        etapa = 0;
        preFlop();
    }


    // ====== PRÉ-FLOP ======
    private void preFlop() {
        System.out.println("Etapa: Pré-Flop");

        gerenciadorApostas.iniciarApostas(jogadoresAtivos, () -> flop());

    }

    // ====== FLOP ======
    private void flop() {
        System.out.println("Etapa: Flop");

        engine.adicionarCartasMesa();
        engine.adicionarCartasMesa();
        engine.adicionarCartasMesa();
        engine.getUi().atualizarCartasMesa(mesa, true);


        gerenciadorApostas.iniciarApostas(jogadoresAtivos, () -> turn());
    }

    // ====== TURN ======
    private void turn() {
        System.out.println("Etapa: Turn");
        engine.adicionarCartasMesa();
        engine.getUi().atualizarCartasMesa(mesa, false);
        gerenciadorApostas.iniciarApostas(jogadoresAtivos, () -> river());
    }

    // ====== RIVER ======
    private void river() {
        System.out.println("Etapa: River");
        engine.adicionarCartasMesa();
        engine.getUi().atualizarCartasMesa(mesa, false);
        gerenciadorApostas.iniciarApostas(jogadoresAtivos, () -> showdown());
    }

    // ====== SHOWDOWN ======
    private void showdown() {
        // o jogador sempre ganha so apra teste

        engine.getUi().atualizaCartaVirada(engine.jbot1);
        engine.getUi().atualizaCartaVirada(engine.jbot2);
        engine.getUi().atualizaCartaVirada(engine.jbot3);

        engine.getUi().desativarpotes();
        engine.jogador.ganhaFichas(engine.mesa.getPote());


        engine.finalizarRodada();
    }
}




