package com.poker.poker;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    boolean musicaLigada = true;

    @Override
    public void start(Stage stage){




        AudioClip musica = new AudioClip(
                Objects.requireNonNull(getClass().getResource("/sounds/trilhaSonora.mp3"))
                        .toExternalForm()
        );
        musica.setCycleCount(AudioClip.INDEFINITE);
        musica.setVolume(0.1);
        musica.play();


        Image imgSomLigado = new Image(
                Objects.requireNonNull(getClass().getResource("/images/musica.png")).toExternalForm()
        );
        Image imgSomDesligado = new Image(
                Objects.requireNonNull(getClass().getResource("/images/musica-nao.png")).toExternalForm()
        );

        ImageView iconMusic = new ImageView(imgSomDesligado);
        iconMusic.setFitWidth(50);
        iconMusic.setFitHeight(50);

        Button btnMusica = new Button("", iconMusic);
        btnMusica.getStyleClass().add("botao-musica");
        btnMusica.relocate(300, 700);


        btnMusica.setOnAction(e -> {
            if (musicaLigada) {
                musica.stop();
                iconMusic.setImage(imgSomLigado);
            } else {
                musica.play();
                iconMusic.setImage(imgSomDesligado);
            }
            musicaLigada = !musicaLigada;
        });


        Pane fundo = new Pane();
        fundo.getStyleClass().add("fundo");


        Button jogar = new Button("Jogar");
        jogar.getStyleClass().add("botao");
        Button sair = new Button("Sair");
        sair.getStyleClass().add("botao");

        VBox botoes = new VBox(20, jogar, sair);
        botoes.relocate(300,400);

        fundo.getChildren().addAll(botoes, btnMusica);

        Scene scene = new Scene(fundo, 1536,864);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("PUCPoker");
        stage.show();



        jogar.setOnAction(e -> {
           Game jg = new Game(stage);

        });
        sair.setOnAction(e -> {
            stage.close();
        });
    }

}
