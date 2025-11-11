package com.poker.poker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage stage){
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();


        Pane fundo = new Pane();
        fundo.getStyleClass().add("fundo");
        Label titulo = new Label("PUCPOKER ");


        titulo.getStyleClass().add("titulo");
        titulo.layoutXProperty().bind(fundo.widthProperty().multiply(0.1));
        titulo.layoutYProperty().bind(fundo.heightProperty().multiply(0.25));


        Button jogar = new Button("Jogar");
        jogar.getStyleClass().add("botao");
        Button sair = new Button("Sair");
        sair.getStyleClass().add("botao");

        VBox botoes=new VBox(20,jogar,sair);
        botoes.layoutXProperty().bind(fundo.widthProperty().multiply(0.09));
        botoes.layoutYProperty().bind(fundo.heightProperty().multiply(0.45));

        fundo.getChildren().addAll(titulo,botoes);


        Scene scene = new Scene(fundo,screenBounds.getWidth(),screenBounds.getHeight());
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Poker FX");
        stage.show();


        jogar.setOnAction(e ->{

            Game jg = new Game(stage);





        });




    }
}

