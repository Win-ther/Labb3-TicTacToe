package se.iths.labb3tictactoe;

import javafx.beans.property.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.List;

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
    //Todo: Move points, name and symbols to Player objects, clean up this garbage code
    public TicTacToeModel() {
        winnerText.setValue("TIC TAC TOE");
        turnTotal = 0;
        isGameOver = false;

        //Setting up players
        player1 = new Player(new SimpleStringProperty("CPU"),new SimpleIntegerProperty(0),new SimpleStringProperty("X"));
        player2 = new Player(new SimpleStringProperty("Player"),new SimpleIntegerProperty(0),new SimpleStringProperty("0"));

        //For gifs
        image1 = new Image(getClass().getResource("images/skeleton-dancing.gif").toExternalForm());
        image2 = new Image(getClass().getResource("images/StartSkeleton.gif").toExternalForm());
        left = new SimpleObjectProperty<>(image1);
        right = new SimpleObjectProperty<>(image1);
        startImage = new SimpleObjectProperty<>(image2);
    }


    public void setSymbol(Button button) {
        if (turn == PLAYER_1) {
            button.setText(player1.symbol().get());
            turn = PLAYER_2;
        } else {
            button.setText(player2.symbol().get());
            turn = PLAYER_1;
        }
        button.setDisable(true);
        turnTotal++;
    }

    public turnOrder getXoTurn() {
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

    public void gameOver(List<Button> buttons) {
        String[] winningLines = {
                buttons.get(0).getText() + buttons.get(1).getText() + buttons.get(2).getText(),
                buttons.get(3).getText() + buttons.get(4).getText() + buttons.get(5).getText(),
                buttons.get(6).getText() + buttons.get(7).getText() + buttons.get(8).getText(),
                buttons.get(0).getText() + buttons.get(3).getText() + buttons.get(6).getText(),
                buttons.get(1).getText() + buttons.get(4).getText() + buttons.get(7).getText(),
                buttons.get(2).getText() + buttons.get(5).getText() + buttons.get(8).getText(),
                buttons.get(0).getText() + buttons.get(4).getText() + buttons.get(8).getText(),
                buttons.get(2).getText() + buttons.get(4).getText() + buttons.get(6).getText()
        };
        String winningLine = Arrays.stream(winningLines).filter(w -> w.equals(getPlayer1WinningLine()) || w.equals(getPlayer2WinningLine())).findFirst().orElse("");
        if (winningLine.equals(getPlayer1WinningLine())) {
            setWinnerText(player1.name().get()+" Won!");
            disableButtons(buttons);
            givePoints(player1);
        } else if (winningLine.equals(getPlayer2WinningLine())) {
            setWinnerText(player2.name().get()+" Won!");
            disableButtons(buttons);
            givePoints(player2);
        } else if (turnTotal > 8) {
            setWinnerText("Draw");
            disableButtons(buttons);
        }
    }

    private String getPlayer2WinningLine() {
        return player2.symbol().get() + player2.symbol().get() + player2.symbol().get();
    }

    private String getPlayer1WinningLine() {
        return player1.symbol().get() + player1.symbol().get() + player1.symbol().get();
    }

    private void givePoints(Player player) {
        player.points().set(player.points().get()+1);
    }

    private void disableButtons(List<Button> buttons) {
        buttons.forEach(e -> e.setDisable(true));
        this.isGameOver = true;
    }


    public void reset(List<Button> buttons) {
        this.winnerText.set("TIC TAC TOE");
        buttons.forEach(this::resetButton);
        this.turnTotal = 0;
        this.isGameOver = false;
    }

    private void resetButton(Button button) {
        button.setText("");
        button.setDisable(false);
        turn = PLAYER_1;
    }

    public int getTurnTotal() {
        return turnTotal;
    }

    public boolean isGameOver() {
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

    public void resetPoints(){
        player1.points().set(0);
        player2.points().set(0);
    }
    public multiPlayerStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(multiPlayerStatus currentStatus) {
        this.currentStatus = currentStatus;
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

    public enum multiPlayerStatus {VS_CPU, VS_LOCAL, VS_LAN}
    public enum turnOrder{PLAYER_1, PLAYER_2}
}
