package com.poker.poker;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;

import java.util.List;
import java.util.Objects;


public class InterfaceController {

    private final HBox cartasJogador;
    private final HBox cartasMesa;
    private final HBox cartasBot1, cartasBot2, cartasBot3;
    private final Button call, fold, dez, vinteCinco, cinquenta, cem, quinhentos,apostar;
    private final HBox bts, hboxFichas;
    private  final ProgressBar barraTempo;
    private AnimationTimer barraTimer;
    AudioClip dropChips = new AudioClip(Objects.requireNonNull(getClass().getResource("/sounds/drop_Chips.mp3")).toExternalForm()) ;
    AudioClip checkSoud = new AudioClip(Objects.requireNonNull(getClass().getResource("/sounds/Bater.mp3")).toExternalForm()) ;
    AudioClip foldSoud = new AudioClip(Objects.requireNonNull(getClass().getResource("/sounds/fold.mp3")).toExternalForm()) ;
    AudioClip winSound = new AudioClip(Objects.requireNonNull(getClass().getResource("/sounds/vitoria.mp3")).toExternalForm()) ;
    private final VBox poteJogador,potebot1,potebot2,potebot3;

    public InterfaceController(HBox cartasJogador, HBox cartasMesa, HBox cartasBot1, HBox cartasBot2, HBox cartasBot3, Button call, Button fold, Button apostar, Button dez, Button vinteCinco, Button cinquenta, Button cem, Button quinhentos,
                               HBox bts, HBox hboxFichas, ProgressBar barraTempo, VBox poteJogador,VBox potebot1,VBox potebot2,VBox potebot3) {
        this.cartasJogador = cartasJogador;
        this.cartasMesa = cartasMesa;
        this.cartasBot1 = cartasBot1;
        this.cartasBot2 = cartasBot2;
        this.cartasBot3 = cartasBot3;
        this.call = call;
        this.fold = fold;
        this.dez = dez;
        this.vinteCinco = vinteCinco;
        this.cinquenta = cinquenta;
        this.cem = cem;
        this.quinhentos = quinhentos;
        this.bts = bts;
        this.hboxFichas = hboxFichas;
        this.barraTempo = barraTempo;
        this.apostar=apostar;
        this.poteJogador=poteJogador;
        this.potebot1=potebot1;
        this.potebot2=potebot2;
        this.potebot3=potebot3;
    }

    public void atualizarCartasJogador(Jogador jogador) {


            switch (jogador.getNome()){
                case "jbot1"->jogador.mostrarMao(cartasBot1);
                case "jbot2"->jogador.mostrarMao(cartasBot2);
                case "jbot3"->jogador.mostrarMao(cartasBot3);
                case "jogador"->jogador.mostrarMao(cartasJogador);
            }
    }

    public void atualizaCartaVirada(JogadorBot jogador){
        switch (jogador.getNome()){
            case "jbot1"->jogador.mostraMaoVirada(cartasBot1);
            case "jbot2"->jogador.mostraMaoVirada(cartasBot2);
            case "jbot3"->jogador.mostraMaoVirada(cartasBot3);
        }
    }



    public void atualizarCartasMesa(Mesa mesa, boolean flop) {
       if(flop){
           mesa.mostrarMao(cartasMesa);
       }else
       {
           mesa.mostrarUltimacarta(cartasMesa);
       }
    }


    public void habilitarBotoes(boolean habilitar) {
        bts.setDisable(!habilitar);
        hboxFichas.setDisable(!habilitar);
    }



    public void limpaMesa(){
        cartasMesa.getChildren().clear();
        cartasBot1.getChildren().clear();
        cartasBot2.getChildren().clear();
        cartasBot3.getChildren().clear();
        cartasJogador.getChildren().clear();
    }


    public void iniciarBarraTempo(Runnable acaoAoTerminar) {


        if (barraTimer != null) barraTimer.stop();
        barraTempo.setProgress(0);
        final long duracaoNano = (long)(25 * 1_000_000_000L);
        final long inicio = System.nanoTime();
        barraTimer = new AnimationTimer() {
            @Override
            public void handle(long agora) {
                long decorrido = agora - inicio;
                double progresso = (double) decorrido / duracaoNano;
                barraTempo.setProgress(Math.min(progresso, 1.0));
                if (decorrido >= duracaoNano) {
                    barraTempo.setProgress(1.0);
                    acaoAoTerminar.run();
                    stop();
                }
            }
        };
        barraTimer.start();
    }
    public void pararBarraTempo() {
        if (barraTimer != null) {
            barraTimer.stop();
        }
    }


    public void configurarBotoes(Jogador jogador, List<Jogador> jogadores, GerenciarAposta gerenciarAposta, Runnable proximaEtapa) {



        dez.setOnAction(e ->{
            mostrarPoteJogador();
            gerenciarAposta.adicionarFicha(jogador, 10);
        });
        vinteCinco.setOnAction(e ->
        {
            mostrarPoteJogador();
            gerenciarAposta.adicionarFicha(jogador, 25);
        });
        cinquenta.setOnAction(e -> {
            mostrarPoteJogador();
            gerenciarAposta.adicionarFicha(jogador, 50);
        });
        cem.setOnAction(e ->
        {
            mostrarPoteJogador();
            gerenciarAposta.adicionarFicha(jogador, 100);
        });
        quinhentos.setOnAction(e ->
        {
            mostrarPoteJogador();
            gerenciarAposta.adicionarFicha(jogador, 500);
        });

            fold.setOnAction(e -> {SoudFoldPlay();gerenciarAposta.jogadorFold(jogador, jogadores, proximaEtapa);});
        apostar.setOnAction(e -> gerenciarAposta.confirmarAposta(jogador, jogadores, proximaEtapa));
        call.setOnAction(e -> {

            pararBarraTempo();

            int valorCall = gerenciarAposta.getMaiorAposta() - jogador.getApostaRodada().get();

            if (valorCall <= 0) {
                // Jogador pode dar check
                checkSoud.play();
                System.out.println(jogador.getNome() + " deu CHECK.");
                gerenciarAposta.jogadorApostou(jogador, 0, jogadores, proximaEtapa);

            } else if (jogador.getFichas() >= valorCall) {
                // Jogador paga o call normal
                mostrarPoteJogador();
                System.out.println(jogador.getNome() + " deu CALL de " + valorCall + " fichas.");
                gerenciarAposta.jogadorApostou(jogador, valorCall, jogadores, proximaEtapa);

            } else {
                // Jogador não tem fichas suficientes
                checkSoud.play();
                System.out.println(jogador.getNome() + " está ALL-IN com " + jogador.getFichas() + " fichas.");
                gerenciarAposta.jogadorApostou(jogador, jogador.getFichas(), jogadores, proximaEtapa);
            }
        });
    }

    public void SoudCheckPlay(){
        checkSoud.play();
    }

    public void SoudFoldPlay(){
        foldSoud.play();
    }

    public void SoudVitoriaPlay(){
        winSound.play();
    }



    public void mostrarPoteBot(String nome){
        dropChips.play();
        switch (nome) {
            case "jbot1":
                potebot1.setVisible(true);
                break;
            case "jbot2":
                potebot2.setVisible(true);
                break;
            case "jbot3":
                potebot3.setVisible(true);
                break;
        }
    }


    public void mostrarPoteJogador(){
        dropChips.play();
        poteJogador.setVisible(true);
    }

    public void desativarpotes(){
        poteJogador.setVisible(false);
        potebot2.setVisible(false);
        potebot1.setVisible(false);
        potebot3.setVisible(false);
    }



    public void limpaMaoFold(String nome){
        switch (nome){
            case "jbot1":
                cartasBot1.getChildren().clear();
                break;
            case "jbot2":
                cartasBot2.getChildren().clear();
                break;
            case "jbot3":
                cartasBot3.getChildren().clear();
                break;
            case "jogador":
                cartasJogador.getChildren().clear();
                break;
        }
    }
}