package com.poker.poker;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Screen;
import javafx.util.Duration;

public class Jogador {
    private int pontos = 0;
    private String nome;
    protected  List<Card> mao = new ArrayList<>();
    private int aces =0;
    public Jogador(String nome) {
        this.nome = nome;
        this.pontos =20000;
    }

    public void adicionarCarta(Card carta) {
        mao.add(carta);
        if (carta.getFace().equals("Ace")) {
            pontos += 11;
            aces++;
        } else if (carta.getFace().equals("2")) {
            pontos += 2;
        } else if (carta.getFace().equals("3")) {
            pontos += 3;
        } else if (carta.getFace().equals("4")) {
            pontos += 4;
        } else if (carta.getFace().equals("5")) {
            pontos += 5;
        } else if (carta.getFace().equals("6")) {
            pontos += 6;
        } else if (carta.getFace().equals("7")) {
            pontos += 7;
        } else if (carta.getFace().equals("8")) {
            pontos += 8;
        } else if (carta.getFace().equals("9")) {
            pontos += 9;
        } else {
            pontos += 10;
        }

        while (pontos > 21 && aces > 0) {
            pontos -= 10;
            aces--;
        }
    }


    public void mostrarMao() {
        System.out.println(nome + " tem as cartas:");
        for (Card c : mao) {
            System.out.println("  " + c);
        }
        System.out.println("Total de pontos: " + pontos);
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





    public void Hit(Card carta) {
        adicionarCarta(carta);
    }

    public void resetMao() {
        mao.clear();
        pontos = 0;
    }

    public void fistHand(Card carta1, Card carta2) {
        adicionarCarta(carta1);
        adicionarCarta(carta2);
    }

    protected List<Card> getMao() {
        return mao;
    }
    public int getPontos() {
        return pontos;
    }
    public String getNome() {
        return nome;
    }
}

