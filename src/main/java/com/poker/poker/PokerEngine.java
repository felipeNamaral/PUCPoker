    package com.poker.poker;


    import javafx.animation.PauseTransition;
    import javafx.util.Duration;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;

    public class PokerEngine {
        protected final Jogador jogador;
        protected final JogadorBot jbot1;
        protected final JogadorBot jbot2;
        protected final JogadorBot jbot3;
        protected final Mesa mesa;
        private  DeckOfCards baralho;
        private final InterfaceController ui;
        protected final List<Jogador> jogadores = new ArrayList<>();

        public PokerEngine(Jogador jogador, JogadorBot jbot1, JogadorBot jbot2, JogadorBot jbot3, InterfaceController ui)
        {
            this.jogador = jogador;
            this.jbot1 = jbot1;
            this.jbot2 = jbot2;
            this.jbot3 = jbot3;
            this.ui = ui;
            jogadores.clear();
            jogadores.addAll(Arrays.asList(jbot2, jogador, jbot3, jbot1));

            this.mesa = new Mesa();
            this.baralho = new DeckOfCards();
            iniciaPartida();

        }

        public  void iniciaPartida() {


            ui.limpaMesa();
            jogador.resetMao();
            jbot1.resetMao();
            jbot2.resetMao();
            jbot3.resetMao();
            mesa.resetMao();
            mesa.resetaPote();
            baralho = new DeckOfCards();
            baralho.shuffle();
            novaRodada();
        }

        public  void novaRodada(){
            distribuiCartas(() -> {

                GerenciarAposta gerenciarAposta = new GerenciarAposta(this);
                Rodada rodada = new Rodada(this, jogadores, mesa, gerenciarAposta);
                rodada.iniciar();
            });
        }


        public  void finalizarRodada(){
            jbot2.resetApostaRodada();
            jbot3.resetApostaRodada();
            jbot1.resetApostaRodada();
            jogador.resetApostaRodada();




            Jogador aux = jogadores.remove(0);
            jogadores.add(aux);


            iniciaPartida();



        }

        public  void encerraPartida(){

        }


        private void distribuiCartas(Runnable onFinish){


                jogador.fistHand(baralho.dealCard(),baralho.dealCard());
                jbot1.fistHand(baralho.dealCard(),baralho.dealCard());
                jbot2.fistHand(baralho.dealCard(),baralho.dealCard());
                jbot3.fistHand(baralho.dealCard(),baralho.dealCard());



                ui.atualizarCartasJogador(jogador);
                ui.atualizarCartasJogador(jbot1);
                ui.atualizarCartasJogador(jbot2);
                ui.atualizarCartasJogador(jbot3);

                PauseTransition pause = new PauseTransition(Duration.seconds(10));
                pause.setOnFinished(e->{
                    onFinish.run();
                });
                pause.play();

        }

        public void adicionarCartasMesa(){
            mesa.addCard(baralho.dealCard());
        }


        public InterfaceController getUi() {
            return ui;
        }
    }