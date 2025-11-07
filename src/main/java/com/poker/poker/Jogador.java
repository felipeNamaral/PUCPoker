package com.poker.poker;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Screen;
import javafx.util.Duration;

public class Jogador {
    private final SimpleIntegerProperty fichas = new SimpleIntegerProperty(20000);
    private final String nome;
    protected  List<Card> mao = new ArrayList<>();
    private boolean ativo = true;
    private boolean fold = false;
    private int apostaRodada = 0;



    public Jogador(String nome) {
        this.nome = nome;
    }



    public void mostrarMao(HBox container) {
        container.getChildren().clear(); // limpa cartas antigas
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        double mesaX = 0;
        double mesaY = -screenBounds.getWidth()/2;

        // delay baseado no som de shuffle
        double duracaoSom = 6; // segundos
        PauseTransition delay = new PauseTransition(Duration.seconds(duracaoSom));

        AudioClip dealcard = new AudioClip(getClass().getResource("/sounds/dealcard.mp3").toExternalForm());

        delay.setOnFinished(event -> {
            for (int i = 0; i < mao.size(); i++) {
                Card c = mao.get(i);

                ImageView cartaView = new ImageView(
                        new Image(getClass().getResource("/images/cartas/" + c.getSuit() + "_" + c.getFace() + ".png")
                                .toExternalForm())
                );

                cartaView.setFitWidth(80);
                cartaView.setFitHeight(120);

                // começa no centro da mesa
                cartaView.setTranslateX(mesaX);
                cartaView.setTranslateY(mesaY);

                // começa invisível e pequena
                cartaView.setOpacity(0);
                cartaView.setScaleX(0);
                cartaView.setScaleY(0);


                container.getChildren().add(cartaView);

                // animação de surgir
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.3), cartaView);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);

                ScaleTransition scaleUp = new ScaleTransition(Duration.seconds(0.3), cartaView);
                scaleUp.setFromX(0);
                scaleUp.setFromY(0);
                scaleUp.setToX(1);
                scaleUp.setToY(1);

                // animação de aparecer
                ParallelTransition aparecer = new ParallelTransition(fadeIn, scaleUp);

                // animação de mover do centro da mesa para o HBox (0,0 relativo ao container)
                TranslateTransition mover = new TranslateTransition(Duration.seconds(0.5), cartaView);
                mover.setToX(0);
                mover.setToY(0);

                SequentialTransition animacao = new SequentialTransition(aparecer, mover);

                // delay entre cartas para efeito de distribuição
                animacao.setDelay(Duration.seconds(i * 0.3));

                dealcard.play();
                animacao.play();
            }
        });

        delay.play();
    }





    public void fistHand(Card carta1, Card carta2) {
        mao.add(carta1);
        mao.add(carta2);
    }


    protected List<Card> getMao() {
        return mao;
    }

    // ==== Métodos de aposta ====
    public void apostarDez() {
        fichas.set(fichas.get() - 10);
    }

    public void apostarVinteCinco() {
        fichas.set(fichas.get() - 25);
    }

    public void apostarCinquenta() {
        fichas.set(fichas.get() - 50);
    }

    public void apostarCem() {
        fichas.set(fichas.get() - 100);
    }

    public void apostarQuinhentos() {
        fichas.set(fichas.get() - 500);
    }

    // ==== Métodos de manipulação de fichas ====
    public int getFichas() {
        return fichas.get();
    }

    public void setFichas(int valor) {
        fichas.set(valor);
    }

    public void ganhaFichas(int valor) {
        fichas.set(fichas.get() + valor);
    }

    public void retiraFichas(int valor) {
        // Corrigido: antes o if estava invertido
        if (fichas.get() >= valor) {
            fichas.set(fichas.get() - valor);
        }
    }

    // ==== Getter da property (necessário pro binding no JavaFX) ====
    public SimpleIntegerProperty fichasProperty() {
        return fichas;
    }



    public String getNome() {
        return nome;
    }


    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean getAtivo() {
        return ativo;
    }


    public boolean getFold() {
        return fold;
    }

    public void setFold(boolean fold) {
        this.fold = fold;
    }

    public int getApostaRodada() {
        return apostaRodada;
    }

    public void setApostaRodada(int apostaRodada) {
        this.apostaRodada = apostaRodada;
    }

    public void resetApostaRodada(){
        this.apostaRodada=0;
    }

    public void resetMao() {
        mao.clear();

    }
}

