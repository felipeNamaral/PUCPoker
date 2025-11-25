package com.poker.poker;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import java.util.List;
import java.util.stream.Collectors;

public class JogadorBot extends Jogador {

    Dotenv dotenv = Dotenv.load();
    Client client = Client.builder().apiKey(dotenv.get("API_KAY")).build();


    public JogadorBot(String nome){
        super(nome);
    }

    public String sendIA(String gamestatus){

        try {

            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.5-flash",
                    gamestatus,
                    null
            );
            System.out.println("IA respondeu: " + response.text());
            return response.text().trim().toLowerCase();

        } catch (com.google.genai.errors.ServerException se) {
            System.err.println("Erro de servidor: " + se.getMessage());

            return "call";
        } catch (Exception e) {
            e.printStackTrace();
            return "call";
        }
    }


    @Override
    public void mostrarMao(HBox container) {
        container.getChildren().clear(); // limpa cartas antigas


        double mesaX = 0;
        double mesaY = -500;

        // delay baseado no som de shuffle
        double duracaoSom = 6; // segundos
        PauseTransition delay = new PauseTransition(Duration.seconds(duracaoSom));

        AudioClip dealcard = new AudioClip(getClass().getResource("/sounds/dealcard.mp3").toExternalForm());

        delay.setOnFinished(event -> {
            for (int i = 0; i < mao.size(); i++) {
                Card c = mao.get(i);

                ImageView cartaView = new ImageView(
                        new Image(getClass().getResource("/images/cartas/back.png")
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



    public void mostraMaoVirada(HBox container){


        container.getChildren().clear();

        for (int i = 0; i < mao.size(); i++) {
            Card c = mao.get(i);

            ImageView cartaView = new ImageView(
                    new Image(getClass().getResource("/images/cartas/" + c.getSuit() + "_" + c.getFace() + ".png")
                            .toExternalForm())
            );

            cartaView.setFitWidth(80);
            cartaView.setFitHeight(120);
            container.getChildren().add(cartaView);
        }



    }



    public void aposta(Runnable onFinish){
        onFinish.run();
    }

}



