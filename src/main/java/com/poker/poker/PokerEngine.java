    package com.poker.poker;


    import javafx.animation.AnimationTimer;
    import javafx.animation.KeyFrame;
    import javafx.animation.PauseTransition;
    import javafx.animation.Timeline;
    import javafx.scene.control.Button;
    import javafx.scene.control.ProgressBar;
    import javafx.scene.layout.HBox;
    import javafx.scene.layout.Pane;
    import javafx.util.Duration;

    import java.util.Arrays;
    import java.util.stream.Stream;

    public class PokerEngine {


        private DeckOfCards baralho;
        private final Mesa mesa = new Mesa();


        // ======== Jogadores ========
        private final Jogador jogador;
        private final JogadorBot jbot1;
        private final JogadorBot jbot2;
        private final JogadorBot jbot3;

        private Pane bot2Pane;
        private Pane bot3Pane;
        private Pane mesaPane;

        // ======== Botões de fichas ========
        private Button btnDez;
        private Button btnVinteCinco;
        private Button btnCinquenta;
        private Button btnCem;
        private Button btnQuinhentos;
        private Button call;
        private Button fold;


        private HBox cartas;
        private HBox cartasBot1;
        private HBox cartasBot2;
        private HBox cartasMesa;
        private HBox cartasbot3;
        private HBox bts;
        private  HBox hboxFichas;


        private ProgressBar barraTempo;


        private int pote = 0;
        private  int apostaAtual = 100;

        public  String[] indiceGame = {"jbot1","jogador","jbot3","jbot2"};
        public  String[] indiceRoda = {"jbot1","jogador","jbot3","jbot2"};
        private int auxAtual = 0;
        private String atual = indiceRoda[auxAtual];
        private int ativo = indiceGame.length;
        private int ativosRodada = indiceRoda.length;
        private boolean rodada = true;
        private boolean rodadaEmAndamento = false;






        // ======== Construtor ========
        public PokerEngine(Jogador jogador, JogadorBot jbot1, JogadorBot jbot2, JogadorBot jbot3,
                           Pane player, Pane bot1, Pane bot2, Pane bot3, Pane mesa,
                           Button dez, Button vinteCinco, Button cinquenta, Button cem, Button quinhentos, HBox cartas, HBox cartasBot1
                           , HBox cartasBot2, HBox cartasBot3, HBox cartasMesa, Button call, Button fold , HBox bts, HBox hboxFichas, ProgressBar barraTempo) {

            // Jogadores
            this.jogador = jogador;
            this.jbot1 = jbot1;
            this.jbot2 = jbot2;
            this.jbot3 = jbot3;

            // Panes
            // ======== Panes da interface ========
            this.bot2Pane = bot2;
            this.bot3Pane = bot3;
            this.mesaPane = mesa;

            // Botões de fichas
            this.btnDez = dez;
            this.btnVinteCinco = vinteCinco;
            this.btnCinquenta = cinquenta;
            this.btnCem = cem;
            this.btnQuinhentos = quinhentos;
            this.call=call;
            this.fold=fold;


            this.cartas=cartas;
            this.cartasBot1=cartasBot1;
            this.cartasBot2=cartasBot2;
            this.cartasbot3= cartasBot3;
            this.cartasMesa=cartasMesa;
            this.bts = bts;
            this.hboxFichas=hboxFichas;


            this.barraTempo = barraTempo;

            iniciarJogo();
        }

        // ======== Métodos de inicialização ========
        public void iniciarJogo() {
            iniciarRodada();
        }

        public void iniciarRodada() {
            resetaMesa();
            limparMaos();
            distribuirCartasIniciais();
            iniciaTurno(); // Renomeado para gerenciar a sequência
        }


        public void iniciaTurno(){
            if (rodadaEmAndamento) return; // evita múltiplas chamadas
            rodadaEmAndamento = true;

            turno(() ->
                    mostrarFlop(() ->
                            turno(() ->
                                    mostrarTurn(() ->
                                            turno(() ->
                                                    mostrarRiver(() ->
                                                            turno(() -> {
                                                                determinarVencedor();
                                                                rodadaEmAndamento = false;
                                                            })
                                                    )
                                            )
                                    )
                            )
                    )
            );






        }

        public void turno(Runnable onFinish){
            // Zera apostas antes de começar o turno
            jogador.setApostaRodada(0);
            jbot1.setApostaRodada(0);
            jbot2.setApostaRodada(0);
            jbot3.setApostaRodada(0);

            indiceRoda = indiceGame;

            Jogador primeiro = proximoJogador();
            aposta(primeiro, onFinish);
        }



        public void resetaMesa() {
                rodada = true;
                apostaAtual = 100;
                pote = 0;
                jogadorAtivo(jogador);
                jogadorAtivo(jbot1);
                jogadorAtivo(jbot2);
                jogadorAtivo(jbot3);

        }

        public void limparMaos() {
                cartas.getChildren().clear();
                cartasMesa.getChildren().clear();
                cartasbot3.getChildren().clear();
                cartasBot2.getChildren().clear();
                cartasBot1.getChildren().clear();
                jogador.resetMao();
                jbot3.resetMao();
                jbot2.resetMao();
                jbot1.resetMao();
                mesa.resetMao();
        }




        public void distribuirCartasIniciais() {
            baralho = new DeckOfCards();
            baralho.shuffle();

            jbot3.fistHand(baralho.dealCard(),baralho.dealCard());
            jbot3.mostrarMao(cartasbot3);
            jogador.fistHand(baralho.dealCard(),baralho.dealCard());
            jogador.mostrarMao(cartas);
            jbot2.fistHand(baralho.dealCard(),baralho.dealCard());
            jbot2.mostrarMao(cartasBot2);
            jbot1.fistHand(baralho.dealCard(),baralho.dealCard());
            jbot1.mostrarMao(cartasBot1);


        }






        public void mostrarFlop(Runnable onFinish) {
            mesa.addCard(baralho.dealCard());
            mesa.addCard(baralho.dealCard());
            mesa.addCard(baralho.dealCard());
            mesa.mostrarMao(cartasMesa);
            onFinish.run();


        }
        public void mostrarTurn(Runnable onFinish) {
            mesa.addCard(baralho.dealCard());
            mesa.mostrarUltimacarta(cartasMesa);
            onFinish.run();


        }
        public void mostrarRiver(Runnable onFinish) {
            mesa.addCard(baralho.dealCard());
            mesa.addCard(baralho.dealCard());
            mesa.mostrarUltimacarta(cartasMesa);
            onFinish.run();
        }







        public Jogador proximoJogador() {

            // evita dividir por zero / acesso inválido
            if (indiceRoda == null || indiceRoda.length == 0) {
                return null;
            }

            // garante que auxAtual esteja sempre dentro dos limites
            auxAtual = auxAtual % indiceRoda.length;

            // pega o nome atual e avança o ponteiro circularmente
            atual = indiceRoda[auxAtual];
            auxAtual = (auxAtual + 1) % indiceRoda.length;

            if (atual.equals(jogador.getNome())) {
                return jogador;
            }
            if (atual.equals(jbot1.getNome())) {
                return jbot1;
            }
            if (atual.equals(jbot2.getNome())) {
                return jbot2;
            }
            if (atual.equals(jbot3.getNome())) {
                return jbot3;
            }

            return null;
        }

        private int raise = 0;

        public void aposta(Jogador a,Runnable onFinish) {


            if (a instanceof JogadorBot) {
                JogadorBot bot = (JogadorBot) a;

                // Calcula quanto falta para igualar a aposta atual
                int diferenca = apostaAtual - bot.getApostaRodada();

                // Retira as fichas do bot e atualiza os valores
                bot.retiraFichas(diferenca);
                bot.setApostaRodada(apostaAtual);
                pote += diferenca;

                // Exibe no console para testar
                System.out.println(bot.getNome() + " deu call de " + diferenca + " fichas.");

                // Continua o jogo (passa para o próximo jogador)
                encerrarApostaDoJogador(onFinish);
            } else {
                PauseTransition tempo = new PauseTransition(Duration.seconds(25));

                barraTempo.setProgress(0); // começa vazia
                final long[] inicio = new long[1];

                AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long agora) {
                        double decorrido = (System.nanoTime() - inicio[0]) / 1_000_000_000.0;
                        double progresso = decorrido / tempo.getDuration().toSeconds();
                        barraTempo.setProgress(Math.min(progresso, 1.0));
                    }
                };

                inicio[0] = System.nanoTime();
                timer.start();

                tempo.setOnFinished(e -> {

                    if(raise!=0) {
                        jogador.setFold(true);
                        timer.stop();
                        barraTempo.setProgress(1.0);
                        encerrarApostaDoJogador(onFinish);
                    }
                    timer.stop();
                    barraTempo.setProgress(1.0);
                    encerrarApostaDoJogador(onFinish);
                });
                tempo.play();

                bts.setDisable(false);
                hboxFichas.setDisable(false);

                call.setOnAction(e -> {
                    int diferenca = apostaAtual - jogador.getApostaRodada();
                    jogador.retiraFichas(diferenca);
                    jogador.setApostaRodada(apostaAtual);
                    pote += diferenca;
                    tempo.stop();
                    timer.stop();
                    barraTempo.setProgress(1.0);
                    encerrarApostaDoJogador(onFinish);
                });

                fold.setOnAction(e -> {
                    jogador.setFold(true);
                    tempo.stop();
                    jogador.resetMao();
                    cartas.getChildren().clear();
                    timer.stop();
                    barraTempo.setProgress(1.0);
                    encerrarApostaDoJogador(onFinish);
                });



                btnDez.setOnAction(e->{
                    raise++;
                    realizarRaise(10);
                });
                btnVinteCinco.setOnAction(e->{
                    raise++;
                    realizarRaise(25);
                });
                btnCinquenta.setOnAction(e->{
                    raise++;
                    realizarRaise(50);
                });
                btnCem.setOnAction(e->{
                    raise++;
                    realizarRaise(100);
                });
                btnQuinhentos.setOnAction(e->{
                    raise++;
                    realizarRaise(500);
                });

            }
        }








        private void realizarRaise(int valor) {
            int novoValor = apostaAtual + valor;
            int diferenca = novoValor - jogador.getApostaRodada();
            apostaAtual = novoValor;
            jogador.retiraFichas(diferenca);
            jogador.setApostaRodada(novoValor);
            pote += diferenca;


        }

        public void encerrarApostaDoJogador(Runnable onFinish) {
            bts.setDisable(true);
            hboxFichas.setDisable(true);

            if (todosApostaram() || contarJogadoresAtivos() == 1) {
                System.out.println("Rodada de apostas encerrada.");

                // reseta apostas individuais
                jogador.setApostaRodada(0);
                jbot1.setApostaRodada(0);
                jbot2.setApostaRodada(0);
                jbot3.setApostaRodada(0);

                onFinish.run(); // segue para a próxima etapa
                return;
            }

            // próximo jogador
            Jogador proximo = proximoJogador();
            aposta(proximo, onFinish);
        }







        public int contarJogadoresAtivos() {
            int count = 0;
            if (jogador.getAtivo() && !jogador.getFold()) count++;
            if (jbot1.getAtivo() && !jbot1.getFold()) count++;
            if (jbot2.getAtivo() && !jbot2.getFold()) count++;
            if (jbot3.getAtivo() && !jbot3.getFold()) count++;
            return count;
        }


        public boolean todosApostaram() {
            int jogadoresAtivos = (int) Stream.of(jogador, jbot1, jbot2, jbot3)
                    .filter(j -> j.getAtivo() && !j.getFold())
                    .count();

            int jogadoresVerificados = (int) Stream.of(jogador, jbot1, jbot2, jbot3)
                    .filter(j -> j.getAtivo() && !j.getFold() && j.getApostaRodada() == apostaAtual)
                    .count();

            return jogadoresAtivos > 0 && jogadoresVerificados == jogadoresAtivos;
        }






        public void removerJogadorSemFichas(Jogador a) {
            if (a.getAtivo()) {
                a.setAtivo(false);

                // converte o vetor em lista temporária
                indiceGame = Arrays.stream(indiceGame)
                        .filter(nome -> !a.getNome().equals(nome)) // remove o nome do jogador
                        .toArray(String[]::new); // volta pra vetor
            }

        }
        public void jogadorAtivo(Jogador a) {

            if (a.getFichas() == 0) {
                removerJogadorSemFichas(a);
            }

        }




        // ======== Métodos de lógica de vitória ========
        public Jogador showdown() {
            jbot3.mostraMaoVirada(cartasbot3);
            jbot2.mostraMaoVirada(cartasBot2);
            jbot1.mostraMaoVirada(cartasBot1);

            PauseTransition delay = new PauseTransition(Duration.seconds(7));

            delay.setOnFinished(e->{
                return ;
            });


            return null;
        }

        public void determinarVencedor() {


            distribuirPote(showdown());

        }


        public void distribuirPote(Jogador a) {
            a.ganhaFichas(pote);
            rodada = false;
            limparMaos();
            resetaMesa();
            iniciarRodada();
        }



    }