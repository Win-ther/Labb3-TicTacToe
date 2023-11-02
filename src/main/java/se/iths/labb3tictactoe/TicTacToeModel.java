package se.iths.labb3tictactoe;

import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import static se.iths.labb3tictactoe.TicTacToeModel.multiPlayerStatus.*;
import static se.iths.labb3tictactoe.TicTacToeModel.turnOrder.PLAYER_1;
import static se.iths.labb3tictactoe.TicTacToeModel.turnOrder.PLAYER_2;

public class TicTacToeModel {
    private final StringProperty winnerText = new SimpleStringProperty();
    private turnOrder playerTurn = PLAYER_1;
    private int turnTotal;
    private boolean gameOver;
    private final Player player1, player2;
    public Image image1, image2;
    private ObjectProperty<Image> left, right, startImage;
    private multiPlayerStatus currentStatus = VS_CPU;
    public Server server;
    private final String[] board = {
            "", "", "",
            "", "", "",
            "", "", ""};

    public TicTacToeModel() {
        winnerText.setValue("TIC TAC TOE");
        turnTotal = 0;
        gameOver = false;

        //Setting up players
        player1 = new Player(new SimpleStringProperty("CPU"), new SimpleIntegerProperty(0), new SimpleStringProperty("X"));
        player2 = new Player(new SimpleStringProperty("Player"), new SimpleIntegerProperty(0), new SimpleStringProperty("0"));

        //For gifs
        image1 = new Image(Objects.requireNonNull(getClass().getResource("images/skeleton-dancing.gif")).toExternalForm());
        image2 = new Image(Objects.requireNonNull(getClass().getResource("images/StartSkeleton.gif")).toExternalForm());
        left = new SimpleObjectProperty<>(image1);
        right = new SimpleObjectProperty<>(image1);
        startImage = new SimpleObjectProperty<>(image2);

        //Server - resten av servern startar när VS_LAN aktiveras
        server = new Server();
    }
    /**
     * Below constructor is for tests only.
     * The pictures that are in the real constructor does not work with the tests.
     * <p>
     * The only difference is that the pictures does not get initialized in the testConstructor.
     * <p>
     * Also omitted server as it is not needed for these tests.
     * **/
    public TicTacToeModel(boolean forTest){
        winnerText.setValue("TIC TAC TOE");
        turnTotal = 0;
        gameOver = false;

        //Setting up players
        player1 = new Player(new SimpleStringProperty("CPU"), new SimpleIntegerProperty(0), new SimpleStringProperty("X"));
        player2 = new Player(new SimpleStringProperty("Player"), new SimpleIntegerProperty(0), new SimpleStringProperty("0"));
    }

    public void setSymbol(int index) {
        if (playerTurn == PLAYER_1) {
            board[index] = player1.symbol().get();
            changeTurn(PLAYER_2);
        } else {
            board[index] = player2.symbol().get();
            changeTurn(PLAYER_1);
        }
        turnTotal++;
        gameOver();
    }

    public int cpuTurn() {
        Random random = new Random();
        int buttonNumber;
        while (true) {
            buttonNumber = random.nextInt(9);
            if (usableButton(buttonNumber, board)) {
                setSymbol(buttonNumber);
                break;
            }
        }
        return buttonNumber;
    }

    private void changeTurn(turnOrder PLAYER) {
        playerTurn = PLAYER;
    }
    public void gameOver() {
        String[] winningLines = returnArrayWithAllPossibleWins(board);
        String winningLine = getTheWinningLine(winningLines, player1, player2);

        if (checkIfPlayerSymbolsMatchTheWinningLine(winningLine, player1)) {
            winningPlayer(player1);
            sendGameOverStatusToClientIfVsLan("P1");
        } else if (checkIfPlayerSymbolsMatchTheWinningLine(winningLine, player2)) {
            winningPlayer(player2);
            sendGameOverStatusToClientIfVsLan("P2");
        } else if (turnTotal > 8) {
            setWinnerText("Draw");
            setGameOver(true);
            sendGameOverStatusToClientIfVsLan("D");
        }
    }

    private void sendGameOverStatusToClientIfVsLan(String status) {
        if (currentStatus == VS_LAN)
            sendGameOverToClient(status);
    }

    private void winningPlayer(Player player) {
        setWinnerText(player.name().get() + " Won!");
        givePoints(player);
        setGameOver(true);
    }

    public static String getTheWinningLine(String[] winningLines, Player player1, Player player2) {
        return Arrays.stream(winningLines).filter(w -> w.equals(getPlayerSymbolWinningLine(player1)) || w.equals(getPlayerSymbolWinningLine(player2))).findFirst().orElse("");
    }

    public static String[] returnArrayWithAllPossibleWins(String[] board) {
        return new String[]{
                board[0] + board[1] + board[2],
                board[3] + board[4] + board[5],
                board[6] + board[7] + board[8],
                board[0] + board[3] + board[6],
                board[1] + board[4] + board[7],
                board[2] + board[5] + board[8],
                board[0] + board[4] + board[8],
                board[2] + board[4] + board[6]
        };
    }

    public static boolean checkIfPlayerSymbolsMatchTheWinningLine(String winningLine, Player player) {
        return winningLine.equals(getPlayerSymbolWinningLine(player));
    }

    private static String getPlayerSymbolWinningLine(Player player) {
        return player.symbol().get() + player.symbol().get() + player.symbol().get();
    }

    private void givePoints(Player player) {
        player.points().set(player.points().get() + 1);
    }

    public void reset() {
        this.winnerText.set("TIC TAC TOE");
        this.gameOver = false;
        Arrays.fill(board, "");
        playerTurn = PLAYER_1;
        if (currentStatus == VS_LAN && turnTotal!=0)
            sendGameOverToClient("R");
        this.turnTotal = 0;
    }

    public boolean usableButton(int index, String[] buttonText) {
        return buttonText[index].isEmpty();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void resetPoints() {
        player1.points().set(0);
        player2.points().set(0);
    }

    public int getPlayer2Points() {
        return player2.points().get();
    }

    public IntegerProperty player2PointsProperty() {
        return player2.points();
    }

    public int getPlayer1Points() {
        return player1.points().get();
    }

    public IntegerProperty player1PointsProperty() {
        return player1.points();
    }

    public Image getLeft() {
        return left.get();
    }

    public Image getRight() {
        return right.get();
    }

    public Image getStartImage() {
        return startImage.get();
    }

    public Player getCurrentPlayer() {
        return playerTurn == PLAYER_1 ? player1 : player2;
    }

    public String getPlayer1Name() {
        return player1.name().get();
    }

    public StringProperty player1NameProperty() {
        return player1.name();
    }

    public void setPlayer1Name(String name) {
        player1.name().set(name);
    }

    public String getPlayer2Name() {
        return player2.name().get();
    }

    public StringProperty player2NameProperty() {
        return player2.name();
    }

    public void setPlayer2Name(String name) {
        this.player2.name().set(name);
    }

    public String[] getBoard() {
        return board;
    }

    public Player getPlayer1() {
        return player1;
    }
    public Player getPlayer2() {
        return player2;
    }

    public String getWinnerText() {
        return winnerText.get();
    }

    public StringProperty winnerTextProperty() {
        return winnerText;
    }

    public void setWinnerText(String winnerText) {
        this.winnerText.set(winnerText);
    }

    public void sendIndexClickedToClient(int index) {
        Thread.ofVirtual().start(() -> server.sendSymbolIndex(index));
    }

    private void sendGameOverToClient(String player1WinPlayer2WinDrawOrReset) {
        Thread.ofVirtual().start(() -> server.sendGameOver(player1WinPlayer2WinDrawOrReset));
    }

    public multiPlayerStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(multiPlayerStatus currentStatus) {
        this.currentStatus = currentStatus;
        startOrCloseServer(currentStatus);
    }

    private void startOrCloseServer(multiPlayerStatus currentStatus) {

        if (currentStatus == VS_LAN && !server.isUp()) {
            Thread.ofVirtual().start(() -> server.startRunning());
        } else if (currentStatus != VS_LAN && server.isUp()) {
            server.closeCrap();
        }
    }

    public enum multiPlayerStatus {VS_CPU, VS_LOCAL, VS_LAN;}

    public enum turnOrder {PLAYER_1, PLAYER_2;}
}
