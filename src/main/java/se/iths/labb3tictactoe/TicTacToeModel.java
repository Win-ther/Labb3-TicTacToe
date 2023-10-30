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
    private StringProperty winnerText = new SimpleStringProperty();
    private turnOrder turn = PLAYER_1;
    private int turnTotal;
    private boolean isGameOver;
    private Player player1, player2;
    public Image image1, image2;
    private ObjectProperty<Image> left, right, startImage;
    private multiPlayerStatus currentStatus = VS_CPU;
    Server server;
    private String[] board = {
            "", "", "",
            "", "", "",
            "", "", ""};

    //Todo: Move points, name and symbols to Player objects, clean up this garbage code
    public TicTacToeModel() {
        winnerText.setValue("TIC TAC TOE");
        turnTotal = 0;
        isGameOver = false;

        //Setting up players
        player1 = new Player(new SimpleStringProperty("CPU"), new SimpleIntegerProperty(0), new SimpleStringProperty("X"));
        player2 = new Player(new SimpleStringProperty("Player"), new SimpleIntegerProperty(0), new SimpleStringProperty("0"));

        //For gifs
        image1 = new Image(Objects.requireNonNull(getClass().getResource("images/skeleton-dancing.gif")).toExternalForm());
        image2 = new Image(Objects.requireNonNull(getClass().getResource("images/StartSkeleton.gif")).toExternalForm());
        left = new SimpleObjectProperty<>(image1);
        right = new SimpleObjectProperty<>(image1);
        startImage = new SimpleObjectProperty<>(image2);

        //Server
        server = new Server();

        //Todo: Flytta detta till egen metod för t.ex skickning av information när knapp klickas på.

        //Todo: Make Gameover static? Fix tests for game so that it uses gameOver-method
    }
    /**
     * Below constructor is for tests as you can se by the boolean parameter.
     * That is because the pictures that are in the real constructor does not work with the tests.
     * <p>
     * The only difference is that the pictures does not get initialized in the testConstructor
     * **/
    public TicTacToeModel(boolean forTest){
        winnerText.setValue("TIC TAC TOE");
        turnTotal = 0;
        isGameOver = false;

        //Setting up players
        player1 = new Player(new SimpleStringProperty("CPU"), new SimpleIntegerProperty(0), new SimpleStringProperty("X"));
        player2 = new Player(new SimpleStringProperty("Player"), new SimpleIntegerProperty(0), new SimpleStringProperty("0"));
    }

    public void setSymbol(int index) {
        if (turn == PLAYER_1) {
            board[index] = player1.symbol().get();
            turn = PLAYER_2;
        } else {
            board[index] = player2.symbol().get();
            turn = PLAYER_1;
        }
        turnTotal++;
        gameOver();
    }

    public turnOrder getTurn() {
        return turn;
    }

    public void setTurn(turnOrder turn) {
        this.turn = turn;
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
    public void gameOver() {
        String[] winningLines = getWinningLines(board);
        String winningLine = getTheWinningLine(winningLines, player1, player2);

        if (playerWins(winningLine, player1)) {
            winningPlayer(player1);
        } else if (playerWins(winningLine, player2)) {
            winningPlayer(player2);
        } else if (turnTotal > 8) {
            setWinnerText("Draw");
            setGameOver(true);
        }
    }
    private void winningPlayer(Player player) {
        setWinnerText(player.name().get() + " Won!");
        givePoints(player);
        setGameOver(true);
    }

    public static String getTheWinningLine(String[] winningLines, Player player1, Player player2) {
        return Arrays.stream(winningLines).filter(w -> w.equals(getPlayerSymbolWinningLine(player1)) || w.equals(getPlayerSymbolWinningLine(player2))).findFirst().orElse("");
    }

    public static String[] getWinningLines(String[] board) {
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

    public static boolean playerWins(String winningLine, Player player) {
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
        this.turnTotal = 0;
        this.isGameOver = false;
        Arrays.fill(board, "");
        turn = PLAYER_1;
    }
    public int getTurnTotal() {
        return turnTotal;
    }

    public boolean getIsGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
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

    public Image getImage1() {
        return image1;
    }

    public Image getImage2() {
        return image2;
    }

    public Image getLeft() {
        return left.get();
    }

    public ObjectProperty<Image> leftProperty() {
        return left;
    }

    public Image getRight() {
        return right.get();
    }

    public ObjectProperty<Image> rightProperty() {
        return right;
    }

    public Image getStartImage() {
        return startImage.get();
    }

    public ObjectProperty<Image> startImageProperty() {
        return startImage;
    }

    public void resetPoints() {
        player1.points().set(0);
        player2.points().set(0);
    }

    public multiPlayerStatus getCurrentStatus() {
        return currentStatus;
    }

    public Player getCurrentPlayer() {
        return turn == PLAYER_1 ? player1 : player2;
    }

    public void setCurrentStatus(multiPlayerStatus currentStatus) {
        this.currentStatus = currentStatus;
        startOrCloseServer(currentStatus);
    }

    private void startOrCloseServer(multiPlayerStatus currentStatus) {
        if (currentStatus == VS_LAN && !server.isUp())
            Thread.ofPlatform().start(() -> server.startRunning());
        else if (currentStatus != VS_LAN && server.isUp()) {
            server.closeCrap();
        }
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

    public boolean usableButton(int index, String[] buttonText) {
        return buttonText[index].isEmpty();
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void player2LanTurn(int indexOfBoard) {
        //Todo: Implement network gaming
        int indexFromPlayer2 = server.whilePlaying(indexOfBoard);
        setSymbol(indexFromPlayer2);
    }

    public String[] getBoard() {
        return board;
    }

    public enum multiPlayerStatus {VS_CPU, VS_LOCAL, VS_LAN}

    public enum turnOrder {PLAYER_1, PLAYER_2}
}
