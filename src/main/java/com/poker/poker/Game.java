package com.poker.poker;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Game {




    public  Game(Stage stage){
        startGame(stage);
    }

    private void startGame(Stage stage){
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();



        Pane jogoLayout = new Pane();

        Pane player = new Pane();
        player.setPrefSize(200, 100);
        player.relocate(screenBounds.getWidth()/2-100,screenBounds.getHeight()-200);


        Pane bot1 = new Pane();
        bot1.setPrefSize(200, 100);
        bot1.relocate(10,screenBounds.getHeight()-500);


        Pane bot2 = new Pane();
        bot2.setPrefSize(200, 100);
        bot2.relocate(screenBounds.getWidth()-300,screenBounds.getHeight()-500);


        Pane mesa = new Pane();
        mesa.setPrefSize(400, 200);
        mesa.relocate(screenBounds.getWidth()/2-100,screenBounds.getHeight()/2);













        jogoLayout.getStyleClass().add("jogoLayout");
        player.getStyleClass().add("player");
        bot1.getStyleClass().add("bot1");
        bot2.getStyleClass().add("bot2");
        mesa.getStyleClass().add("mesa");






        jogoLayout.getChildren().addAll(player,bot1,bot2,mesa);



        Scene jogoScene = new Scene(jogoLayout,screenBounds.getWidth(),screenBounds.getHeight());
        jogoScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(jogoScene);
        stage.setFullScreen(true);
        stage.show();



    }




}
