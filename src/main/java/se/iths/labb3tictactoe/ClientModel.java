package se.iths.labb3tictactoe;

import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.util.Objects;

public class ClientModel {
    private StringProperty winnerText = new SimpleStringProperty();
    private turnOrder turn = turnOrder.PLAYER_2;
    private int turnTotal;
    private boolean isGameOver;
    private Player player1, player2;
    public Image image1, image2;
    private ObjectProperty<Image> left, right, startImage;
    public Client client;


    //Todo: Move points, name and symbols to Player objects, clean up this garbage code
    public ClientModel() {
        winnerText.setValue("TIC TAC TOE");
        turnTotal = 0;
        isGameOver = false;

        //Setting up players
        player1 = new Player(new SimpleStringProperty("Player 1"), new SimpleIntegerProperty(0), new SimpleStringProperty("X"));
        player2 = new Player(new SimpleStringProperty("Player 2"), new SimpleIntegerProperty(0), new SimpleStringProperty("0"));

        //For gifs
        image1 = new Image(Objects.requireNonNull(getClass().getResource("images/skeleton-dancing.gif")).toExternalForm());
        image2 = new Image(Objects.requireNonNull(getClass().getResource("images/StartSkeleton.gif")).toExternalForm());
        left = new SimpleObjectProperty<>(image1);
        right = new SimpleObjectProperty<>(image1);
        startImage = new SimpleObjectProperty<>(image2);

        //Client
        client = new Client("192.168.0.100");
        Thread.ofVirtual().start(() -> client.startRunning());
        //Todo: Flytta detta till egen metod för t.ex skickning av information när knapp klickas på.

        //Todo: Make Gameover static? Fix tests for game so that it uses gameOver-method
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

    public static boolean playerWins(String winningLine, Player player) {
        return winningLine.equals(getPlayerSymbolWinningLine(player));
    }

    private static String getPlayerSymbolWinningLine(Player player) {
        return player.symbol().get() + player.symbol().get() + player.symbol().get();
    }

    public void givePoints(Player player) {
        player.points().set(player.points().get() + 1);
    }

    public void reset() {
        this.winnerText.set("TIC TAC TOE");
        resetTurnTotal();
        this.isGameOver = false;
        turn = turnOrder.PLAYER_1;
    }

    public void resetTurnTotal() {
        this.turnTotal = 0;
    }

    public int getTurnTotal() {
        return turnTotal;
    }

    public void nextTurn() {
        turnTotal++;
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

    public Player getCurrentPlayer() {
        return turn == turnOrder.PLAYER_1 ? player1 : player2;
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

    public boolean usableButton(int index, String[] buttonText) {
        return buttonText[index].isEmpty();
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void sendIndexClickedToServer(int index) {
        Thread.ofVirtual().start(() -> client.sendSymbolIndex(index));
    }

    public enum turnOrder {PLAYER_1, PLAYER_2}
}
