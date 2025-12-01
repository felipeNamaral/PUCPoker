package com.poker.poker;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaFinal {

    public Scene criarTelaFinal(Stage stage, boolean venceu) {

        Pane fim = new Pane();
        fim.getStyleClass().add("fim");
        String texto = venceu ? "VOCÊ GANHOU!" : "VOCÊ PERDEU!";
        Label txt = new Label(texto);
        txt.getStyleClass().add("txt");

        Button novo = new Button("Jogar Novamente");
        Button sair = new Button("Sair");

        novo.getStyleClass().add("botaoFim");
        sair.getStyleClass().add("botaoFim");

        VBox botoesTexto = new VBox(25);
        botoesTexto.getChildren().addAll(txt, novo, sair);
        botoesTexto.relocate(518,200);
        botoesTexto.setPrefSize(500,550);
        botoesTexto.getStyleClass().add("vboxFim");

        fim.getChildren().add(botoesTexto);

        novo.setOnAction(e -> {
            new Game(stage);
        });

        sair.setOnAction(e -> {
            stage.close();
        });

        Scene cena = new Scene(fim, 1536, 864);
        cena.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        return cena;
    }
}