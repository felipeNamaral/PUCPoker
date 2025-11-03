package com.poker.poker;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.scene.media.AudioClip;


public class Game {
    private Jogador jogador = new Jogador("Jogador");
    private  JogadorBot jbot1 = new JogadorBot();
    private  JogadorBot jbot2 = new JogadorBot();
    private  JogadorBot jbot3 = new JogadorBot();


    public  Game(Stage stage){
        startGame(stage);
    }

    private void startGame(Stage stage){
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();



        Pane jogoLayout = new Pane();

        Pane player = new Pane();
        player.setPrefSize(400, 250);
        player.relocate(screenBounds.getWidth()-600,screenBounds.getHeight()-300);
        Image playerAvatar = new Image(getClass().getResource("/images/usuario.png").toExternalForm());
        ImageView avatarView = new ImageView(playerAvatar);
        avatarView.setFitWidth(150);
        avatarView.setFitHeight(150);
        avatarView.relocate(230,40);
        HBox cartas = new HBox(20);
        cartas.setPrefSize(100,200);
        cartas.relocate(10,60);
        Label fichas = new Label("Fichas:"+jogador.getPontos());
        fichas.getStyleClass().add("fichas");
        fichas.relocate(70,250);


        AudioClip somHover = new AudioClip(getClass().getResource("/sounds/hoverButton.mp3").toExternalForm()) ;


        // Botão 10
        Image img10 = new Image(getClass().getResource("/images/fichas/dez.png").toExternalForm());
        ImageView iv10 = new ImageView(img10);
        iv10.setFitWidth(50);
        iv10.setFitHeight(50);
        Button dez = new Button("", iv10);
        dez.setContentDisplay(javafx.scene.control.ContentDisplay.TOP);
        dez.setOnMouseEntered(e ->{

            somHover.play();

        });

        // Botão 25
        Image img25 = new Image(getClass().getResource("/images/fichas/vinte_cinco.png").toExternalForm());
        ImageView iv25 = new ImageView(img25);
        iv25.setFitWidth(50);
        iv25.setFitHeight(50);
        Button vinteCinco = new Button("", iv25);
        vinteCinco.setContentDisplay(javafx.scene.control.ContentDisplay.TOP);
        vinteCinco.setOnMouseEntered(e ->{

            somHover.play();
        });

        // Botão 50
        Image img50 = new Image(getClass().getResource("/images/fichas/cinquenta.png").toExternalForm());
        ImageView iv50 = new ImageView(img50);
        iv50.setFitWidth(50);
        iv50.setFitHeight(50);
        Button cinquenta = new Button("", iv50);
        cinquenta.setContentDisplay(javafx.scene.control.ContentDisplay.TOP);
        cinquenta.setOnMouseEntered(e ->{

            somHover.play();
        });

        // Botão 100
        Image img100 = new Image(getClass().getResource("/images/fichas/cem.png").toExternalForm());
        ImageView iv100 = new ImageView(img100);
        iv100.setFitWidth(50);
        iv100.setFitHeight(50);
        Button cem = new Button("", iv100);
        cem.setContentDisplay(javafx.scene.control.ContentDisplay.TOP);
        cem.setOnMouseEntered(e ->{

            somHover.play();
        });

        // Botão 500
        Image img500 = new Image(getClass().getResource("/images/fichas/quinhentos.png").toExternalForm());
        ImageView iv500 = new ImageView(img500);
        iv500.setFitWidth(50);
        iv500.setFitHeight(50);
        Button quinhentos = new Button("", iv500);
        quinhentos.setContentDisplay(javafx.scene.control.ContentDisplay.TOP);
        quinhentos.setOnMouseEntered(e ->{

            somHover.play();
        });



        dez.getStyleClass().add("fichabot");
        vinteCinco.getStyleClass().add("fichabot");
        cinquenta.getStyleClass().add("fichabot");
        cem.getStyleClass().add("fichabot");
        quinhentos.getStyleClass().add("fichabot");



        HBox hboxFichas = new HBox(5, dez, vinteCinco, cinquenta, cem, quinhentos);
        hboxFichas.setAlignment(Pos.CENTER);
        hboxFichas.relocate(80,180);



        player.getChildren().addAll(avatarView,cartas,hboxFichas,fichas);





        Pane bot1 = new Pane();
        bot1.setPrefSize(250, 300);
        bot1.relocate(15,screenBounds.getHeight()-650);
        Image bot1Avatar = new Image(getClass().getResource("/images/avatar-main2.png").toExternalForm());
        ImageView avatarViewbot1 = new ImageView(bot1Avatar);
        avatarViewbot1.setFitWidth(150);
        avatarViewbot1.setFitHeight(150);
        avatarViewbot1.relocate(10,10);
        HBox cartasBot1 = new HBox(20);
        cartasBot1.setPrefSize(100,200);
        cartasBot1.relocate(50,160);
        Label bot1fichas = new Label("Fichas:"+jbot1.getPontos());
        bot1fichas.getStyleClass().add("fichas");
        bot1fichas.relocate(60,300);
        bot1.getChildren().addAll(avatarViewbot1,cartasBot1,bot1fichas);


        Pane bot2 = new Pane();
        bot2.setPrefSize(250, 300);
        bot2.relocate(screenBounds.getWidth()-275,screenBounds.getHeight()-650);
        Image bot2Avatar = new Image(getClass().getResource("/images/avatarw.png").toExternalForm());
        ImageView avatarViewbot2 = new ImageView(bot2Avatar);
        avatarViewbot2.setFitWidth(150);
        avatarViewbot2.setFitHeight(150);
        avatarViewbot2.relocate(90,10);
        HBox cartasBot2 = new HBox(20);
        cartasBot2.setPrefSize(100,200);
        cartasBot2.relocate(35,160);
        Label bot2fichas = new Label("Fichas:"+jbot2.getPontos());
        bot2fichas.getStyleClass().add("fichas");
        bot2fichas.relocate(60,300);
        bot2.getChildren().addAll(avatarViewbot2,cartasBot2,bot2fichas);



        Pane bot3 = new Pane();
        bot3.setPrefSize(400, 250);
        bot3.relocate(screenBounds.getWidth()-1300,screenBounds.getHeight()-300);
        Image playerAvatarbot3 = new Image(getClass().getResource("/images/avatar-main.png").toExternalForm());
        ImageView avatarViewbot3 = new ImageView(playerAvatarbot3);
        avatarViewbot3.setFitWidth(150);
        avatarViewbot3.setFitHeight(150);
        avatarViewbot3.relocate(10,40);
        HBox cartasbot3 = new HBox(20);
        cartasbot3.setPrefSize(100,200);
        cartasbot3.relocate(175,60);
        Label bot3fichas = new Label("Fichas:"+jbot3.getPontos());
        bot3fichas.getStyleClass().add("fichas");
        bot3fichas.relocate(70,200);
        bot3.getChildren().addAll(avatarViewbot3,cartasbot3,bot3fichas);








        Pane mesa = new Pane();
        mesa.setPrefSize(600, 200);
        mesa.relocate(screenBounds.getWidth()/2-300,screenBounds.getHeight()/2-95);
        HBox cartasMesa = new HBox(15);
        cartasMesa.setPrefSize(100,200);
        cartasMesa.relocate(72,40);
        mesa.getChildren().add(cartasMesa);



        jogoLayout.getStyleClass().add("jogoLayout");
        bot3.getStyleClass().add("player");
        bot1.getStyleClass().add("bot1");
        bot2.getStyleClass().add("bot2");
        bot3.getStyleClass().add("bot3");
        mesa.getStyleClass().add("mesa");






        jogoLayout.getChildren().addAll(player,bot3,bot1,bot2,mesa);


        PokerEngine engine = new PokerEngine(
                jogador,jbot1,jbot2,jbot3,player,bot1,bot2,bot3,mesa,dez,vinteCinco,cinquenta,cem,quinhentos
                ,cartas,cartasBot1,cartasBot2,cartasbot3, cartasMesa
        );



        Scene jogoScene = new Scene(jogoLayout,screenBounds.getWidth(),screenBounds.getHeight());
        jogoScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(jogoScene);
        stage.setResizable(false);
        stage.show();



    }




}
