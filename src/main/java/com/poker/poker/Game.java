package com.poker.poker;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.scene.media.AudioClip;

import java.util.Objects;


public class Game {
    private final Jogador jogador = new Jogador("jogador");
    private final JogadorBot jbot1 = new JogadorBot("jbot1");
    private final JogadorBot jbot2 = new JogadorBot("jbot2");
    private final JogadorBot jbot3 = new JogadorBot("jbot3");


    public  Game(Stage stage){
        startGame(stage);
    }

    private void startGame(Stage stage){
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();



        Pane jogoLayout = new Pane();

        Image big = new Image(Objects.requireNonNull(getClass().getResource("/images/fichas/big_blind.png")).toExternalForm());
        Image small = new Image(Objects.requireNonNull(getClass().getResource("/images/fichas/small_blind.png")).toExternalForm());



        Pane player = new Pane();
        player.setPrefSize(400, 250);
        player.relocate(screenBounds.getWidth()-600,screenBounds.getHeight()-300);
        Image playerAvatar = new Image(Objects.requireNonNull(getClass().getResource("/images/usuario.png")).toExternalForm());
        ImageView avatarView = new ImageView(playerAvatar);
        avatarView.setFitWidth(150);
        avatarView.setFitHeight(150);
        StackPane avatarContainer = new StackPane(avatarView);
        avatarContainer.setPrefSize(150, 150); // tamanho do avatar container
        avatarContainer.relocate(230, 27);



        HBox cartas = new HBox(20);
        cartas.setPrefSize(100,200);
        cartas.relocate(10,60);
        Label fichas = new Label();
        fichas.textProperty().bind(jogador.fichasProperty().asString("Fichas: %d"));
        fichas.getStyleClass().add("fichas");
        fichas.relocate(70,250);
        HBox bts = new HBox(20);
        Button call = new Button("Call");
        Button fold = new Button("Fold");
        Button apostar = new Button("Apostar");
        call.getStyleClass().add("botao2");
        fold.getStyleClass().add("botao2");
        apostar.getStyleClass().add("botao2");
        bts.getChildren().addAll(call,fold,apostar);
        bts.relocate(550,800);
        bts.setDisable(true);
        ProgressBar barraTempo =new ProgressBar(0);
        player.getChildren().add(barraTempo);
        barraTempo.relocate(240,180);
        barraTempo.setPrefSize(100,10);
        barraTempo.setStyle("-fx-accent: #00ff00; -fx-control-inner-background: #222;");
        barraTempo.getStyleClass().add("progress-bar");


        Image monte= new Image(Objects.requireNonNull(getClass().getResource("/images/monteFichas.png")).toExternalForm());

        VBox poteJogador  = new VBox();
        poteJogador.getStyleClass().add("poteJogador");
        ImageView montefichasview = new ImageView(monte);
        montefichasview.setFitWidth(50);   // opcional
        montefichasview.setPreserveRatio(true);
        Label potedoJogador = new Label();
        potedoJogador.textProperty().bind(jogador.getApostaRodada().asString("%d"));
        poteJogador.relocate(screenBounds.getWidth()-500,screenBounds.getHeight()-350);
        poteJogador.getChildren().addAll(montefichasview,potedoJogador);







        AudioClip somHover = new AudioClip(Objects.requireNonNull(getClass().getResource("/sounds/hoverButton.mp3")).toExternalForm()) ;


        // Botão 10
        Image img10 = new Image(Objects.requireNonNull(getClass().getResource("/images/fichas/dez.png")).toExternalForm());
        ImageView iv10 = new ImageView(img10);
        iv10.setFitWidth(50);
        iv10.setFitHeight(50);
        Button dez = new Button("", iv10);
        dez.setContentDisplay(ContentDisplay.TOP);
        dez.setOnMouseEntered(e ->{

            somHover.play();

        });

        // Botão 25
        Image img25 = new Image(Objects.requireNonNull(getClass().getResource("/images/fichas/vinte_cinco.png")).toExternalForm());
        ImageView iv25 = new ImageView(img25);
        iv25.setFitWidth(50);
        iv25.setFitHeight(50);
        Button vinteCinco = new Button("", iv25);
        vinteCinco.setContentDisplay(ContentDisplay.TOP);
        vinteCinco.setOnMouseEntered(e ->{

            somHover.play();
        });

        // Botão 50
        Image img50 = new Image(Objects.requireNonNull(getClass().getResource("/images/fichas/cinquenta.png")).toExternalForm());
        ImageView iv50 = new ImageView(img50);
        iv50.setFitWidth(50);
        iv50.setFitHeight(50);
        Button cinquenta = new Button("", iv50);
        cinquenta.setContentDisplay(ContentDisplay.TOP);
        cinquenta.setOnMouseEntered(e ->{

            somHover.play();
        });

        // Botão 100
        Image img100 = new Image(Objects.requireNonNull(getClass().getResource("/images/fichas/cem.png")).toExternalForm());
        ImageView iv100 = new ImageView(img100);
        iv100.setFitWidth(50);
        iv100.setFitHeight(50);
        Button cem = new Button("", iv100);
        cem.setContentDisplay(ContentDisplay.TOP);
        cem.setOnMouseEntered(e ->{

            somHover.play();
        });

        // Botão 500
        Image img500 = new Image(Objects.requireNonNull(getClass().getResource("/images/fichas/quinhentos.png")).toExternalForm());
        ImageView iv500 = new ImageView(img500);
        iv500.setFitWidth(50);
        iv500.setFitHeight(50);
        Button quinhentos = new Button("", iv500);
        quinhentos.setContentDisplay(ContentDisplay.TOP);
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
        hboxFichas.setDisable(true);


        player.getChildren().addAll(avatarContainer,cartas,hboxFichas,fichas);





        Pane bot1 = new Pane();
        bot1.setPrefSize(250, 300);
        bot1.relocate(15,screenBounds.getHeight()-650);
        Image bot1Avatar = new Image(Objects.requireNonNull(getClass().getResource("/images/avatar-main2.png")).toExternalForm());
        ImageView avatarViewbot1 = new ImageView(bot1Avatar);
        avatarViewbot1.setFitWidth(150);
        avatarViewbot1.setFitHeight(150);
        StackPane avatarContainerBot1 = new StackPane(avatarViewbot1);
        avatarContainerBot1.setPrefSize(150,150);
        avatarContainerBot1.relocate(10,10);




        HBox cartasBot1 = new HBox(20);
        cartasBot1.setPrefSize(100,200);
        cartasBot1.relocate(50,160);
        Label bot1fichas = new Label();
        bot1fichas.textProperty().bind(jbot1.fichasProperty().asString("Fichas: %d"));
        bot1fichas.getStyleClass().add("fichas");
        bot1fichas.relocate(60,300);
        bot1.getChildren().addAll(avatarViewbot1,cartasBot1,bot1fichas);

        VBox potebot1  = new VBox();
        potebot1.getStyleClass().add("poteJogador");
        ImageView montefichasviewbot1 = new ImageView(monte);
        montefichasviewbot1.setFitWidth(50);   // opcional
        montefichasviewbot1.setPreserveRatio(true);
        Label potedobot1 = new Label();
        potedobot1.textProperty().bind(jbot1.getApostaRodada().asString("%d"));
        potebot1.relocate(350,screenBounds.getHeight()-450);
        potebot1.getChildren().addAll(montefichasviewbot1,potedobot1);









        Pane bot2 = new Pane();
        bot2.setPrefSize(250, 300);
        bot2.relocate(screenBounds.getWidth()-275,screenBounds.getHeight()-650);
        Image bot2Avatar = new Image(Objects.requireNonNull(getClass().getResource("/images/avatarw.png")).toExternalForm());
        ImageView avatarViewbot2 = new ImageView(bot2Avatar);
        avatarViewbot2.setFitWidth(150);
        avatarViewbot2.setFitHeight(150);
        StackPane avatarContainerBot2 = new StackPane(avatarViewbot2);
        avatarContainerBot2.setPrefSize(150,150);
        avatarContainerBot2.relocate(90,10);





        HBox cartasBot2 = new HBox(20);
        cartasBot2.setPrefSize(100,200);
        cartasBot2.relocate(35,160);
        Label bot2fichas = new Label();
        bot2fichas.textProperty().bind(jbot2.fichasProperty().asString("Fichas: %d"));
        bot2fichas.getStyleClass().add("fichas");
        bot2fichas.relocate(60,300);
        bot2.getChildren().addAll(avatarContainerBot2,cartasBot2,bot2fichas);
        VBox potebot2  = new VBox();
        potebot2.getStyleClass().add("poteJogador");
        ImageView montefichasviewbot2 = new ImageView(monte);
        montefichasviewbot2.setFitWidth(50);   // opcional
        montefichasviewbot2.setPreserveRatio(true);
        Label potedobot2 = new Label();
        potedobot2.textProperty().bind(jbot2.getApostaRodada().asString("%d"));
        potebot2.relocate(screenBounds.getWidth()-350,screenBounds.getHeight()-450);
        potebot2.getChildren().addAll(montefichasviewbot2,potedobot2);










        Pane bot3 = new Pane();
        bot3.setPrefSize(400, 250);
        bot3.relocate(screenBounds.getWidth()-1300,screenBounds.getHeight()-300);
        Image playerAvatarbot3 = new Image(Objects.requireNonNull(getClass().getResource("/images/avatar-main.png")).toExternalForm());
        ImageView avatarViewbot3 = new ImageView(playerAvatarbot3);
        avatarViewbot3.setFitWidth(150);
        avatarViewbot3.setFitHeight(150);


        StackPane avatarContainerBot3 = new StackPane(avatarViewbot3);
        avatarContainerBot3.setPrefSize(150,150);
        avatarContainerBot3.relocate(10,40);



        HBox cartasbot3 = new HBox(20);
        cartasbot3.setPrefSize(100,200);
        cartasbot3.relocate(175,60);
        Label bot3fichas = new Label();
        bot3fichas.textProperty().bind(jbot3.fichasProperty().asString("Fichas: %d"));
        bot3fichas.getStyleClass().add("fichas");
        bot3fichas.relocate(70,200);
        bot3.getChildren().addAll(avatarContainerBot3,cartasbot3,bot3fichas);
        VBox potebot3  = new VBox();
        ImageView montefichasviewbot3 = new ImageView(monte);
        montefichasviewbot3.setFitWidth(50);   // opcional
        montefichasviewbot3.setPreserveRatio(true);
        potebot3.getStyleClass().add("poteJogador");
        Label potedobot3 = new Label();
        potedobot3.textProperty().bind(jbot3.getApostaRodada().asString("%d"));
        potebot3.relocate(screenBounds.getWidth()-1100,screenBounds.getHeight()-350);
        potebot3.getChildren().addAll(montefichasviewbot3,potedobot3);







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


        poteJogador.setVisible(false);
        potebot2.setVisible(false);
        potebot1.setVisible(false);
        potebot3.setVisible(false);





        jogoLayout.getChildren().addAll(player,bot3,bot1,bot2,mesa,bts,poteJogador,potebot1,potebot2,potebot3);


        //Instancia InterfaceController
        InterfaceController ui = new InterfaceController(
                cartas, cartasMesa, cartasBot1, cartasBot2,cartasbot3, call, fold,apostar,
                dez, vinteCinco, cinquenta, cem, quinhentos,
                bts, hboxFichas, barraTempo,poteJogador,potebot1,potebot2,potebot3,avatarContainer, avatarContainerBot1, avatarContainerBot2, avatarContainerBot3
        );


        PokerEngine engine = new PokerEngine(
                jogador, jbot1, jbot2, jbot3 , ui
        );









        Scene jogoScene = new Scene(jogoLayout,1536,864);
        jogoScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());
        stage.setScene(jogoScene);
        stage.setResizable(false);
        stage.show();



    }




}
