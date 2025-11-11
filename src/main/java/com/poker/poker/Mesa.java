package com.poker.poker;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Mesa {
    protected List<Card> mao = new ArrayList<>();
    private int pote = 0;



    public void addCard(Card card){
        mao.add(card);
    }

    public void mostrarMao(HBox container) {
        double mesaX = 0;
        double mesaY = 0;

        AudioClip dealcard = new AudioClip(Objects.requireNonNull(getClass().getResource("/sounds/dealcard.mp3")).toExternalForm());

        for (int i = 0; i < mao.size(); i++) {
            Card c = mao.get(i);

            ImageView cartaView = new ImageView(
                    new Image(Objects.requireNonNull(getClass().getResource("/images/cartas/" + c.getSuit() + "_" + c.getFace() + ".png"))
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

            // animação de surgir (mais rápida)
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.15), cartaView);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

            ScaleTransition scaleUp = new ScaleTransition(Duration.seconds(0.15), cartaView);
            scaleUp.setFromX(0);
            scaleUp.setFromY(0);
            scaleUp.setToX(1);
            scaleUp.setToY(1);

            // animação de mover do centro para o container
            TranslateTransition mover = new TranslateTransition(Duration.seconds(0.25), cartaView);
            mover.setToX(0);
            mover.setToY(0);

            ParallelTransition aparecer = new ParallelTransition(fadeIn, scaleUp);
            SequentialTransition animacao = new SequentialTransition(aparecer, mover);

            // intervalo curto entre as cartas (efeito de distribuição suave)
            animacao.setDelay(Duration.seconds(i * 0.15));

            // toca o som levemente defasado
            PauseTransition somDelay = new PauseTransition(Duration.seconds(i * 0.15));
            somDelay.setOnFinished(e -> dealcard.play());
            somDelay.play();

            animacao.play();
        }
    }



    public void mostrarUltimacarta(HBox container) {
        double mesaX = 0;
        double mesaY = 0;

        AudioClip dealcard = new AudioClip(Objects.requireNonNull(getClass().getResource("/sounds/dealcard.mp3")).toExternalForm());

        Card c = mao.getLast();

        ImageView cartaView = new ImageView(
                new Image(Objects.requireNonNull(getClass().getResource("/images/cartas/" + c.getSuit() + "_" + c.getFace() + ".png"))
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

        // animação de surgir (mais rápida)
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.15), cartaView);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        ScaleTransition scaleUp = new ScaleTransition(Duration.seconds(0.15), cartaView);
        scaleUp.setFromX(0);
        scaleUp.setFromY(0);
        scaleUp.setToX(1);
        scaleUp.setToY(1);

        // animação de mover (um pouco mais curta)
        TranslateTransition mover = new TranslateTransition(Duration.seconds(0.25), cartaView);
        mover.setToX(0);
        mover.setToY(0);

        // combina animações
        ParallelTransition aparecer = new ParallelTransition(fadeIn, scaleUp);
        SequentialTransition animacao = new SequentialTransition(aparecer, mover);

        // delay leve só para dar ritmo entre cartas (opcional)
        animacao.setDelay(Duration.seconds(0.1));

        dealcard.play();
        animacao.play();
    }

    public void resetMao() {
        mao.clear();

    }


    public int getPote() {
        return pote;
    }

    public void addPote(int pote) {
        this.pote += pote;
    }

     public void resetaPote(){
         this.pote = 0;
    }
}
