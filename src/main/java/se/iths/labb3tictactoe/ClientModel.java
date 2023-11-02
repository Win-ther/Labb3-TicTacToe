package se.iths.labb3tictactoe;

import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.util.Objects;

public class ClientModel {
    private final StringProperty winnerText = new SimpleStringProperty();
    private turnOrder turn = turnOrder.PLAYER_2;
    private int turnTotal;
    private boolean isGameOver;
    private final Player player1, player2;
    public final Image image1, image2;
    private final ObjectProperty<Image> left, right, startImage;
    public final Client client;

    public ClientModel() {
        winnerText.setValue("TIC TAC TOE");
        turnTotal = 0;
        isGameOver = false;

        //Setting up players
        player1 = new Player(new SimpleStringProperty("Player1"), new SimpleIntegerProperty(0), new SimpleStringProperty("X"));
        player2 = new Player(new SimpleStringProperty("Player2"), new SimpleIntegerProperty(0), new SimpleStringProperty("0"));

        //For gifs
        image1 = new Image(Objects.requireNonNull(getClass().getResource("images/skeleton-dancing.gif")).toExternalForm());
        image2 = new Image(Objects.requireNonNull(getClass().getResource("images/StartSkeleton.gif")).toExternalForm());
        left = new SimpleObjectProperty<>(image1);
        right = new SimpleObjectProperty<>(image1);
        startImage = new SimpleObjectProperty<>(image2);

        //Client
        client = new Client("192.168.0.100");
        Thread.ofVirtual().start(client::startRunning);
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

    public Image getLeft() {
        return left.get();
    }

    public Image getRight() {
        return right.get();
    }

    public Image getStartImage() {
        return startImage.get();
    }

    public String getPlayer1Name() {
        return player1.name().get();
    }

    public String getPlayer2Name() {
        return player2.name().get();
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
