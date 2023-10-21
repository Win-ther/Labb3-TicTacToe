package se.iths.labb3tictactoe;

import javafx.beans.property.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.List;

import static se.iths.labb3tictactoe.TicTacToeModel.multiPlayerStatus.*;

public class TicTacToeModel {
    private StringProperty winnerText = new SimpleStringProperty();
    private IntegerProperty xoTurn = new SimpleIntegerProperty();
    private int turnTotal;
    private boolean isGameOver;

    private Player player1, player2;
    public Image image1, image2;
    private ObjectProperty<Image> left, right, startImage;
    private multiPlayerStatus currentStatus = VS_CPU;
    //Todo: Move points, name and symbols to Player objects, clean up this garbage code
    public TicTacToeModel() {
        winnerText.setValue("TIC TAC TOE");
        xoTurn.setValue(0);
        turnTotal = 0;
        isGameOver = false;

        //Setting up players
        player1.name().set("CPU:");
        player2.name().set("Player:");
        player1.points().set(0);
        player2.points().set(0);
        player1.symbol().set("X");
        player2.symbol().set("0");
        //For gifs
        image1 = new Image(getClass().getResource("images/skeleton-dancing.gif").toExternalForm());
        image2 = new Image(getClass().getResource("images/StartSkeleton.gif").toExternalForm());
        left = new SimpleObjectProperty<>(image1);
        right = new SimpleObjectProperty<>(image1);
        startImage = new SimpleObjectProperty<>(image2);
    }


    public void setSymbol(Button button) {
        if (xoTurn.get() == 0) {
            button.setText("X");
        } else {
            button.setText("0");
        }
        button.setDisable(true);
        nextTurn();
    }

    public int getXoTurn() {
        return xoTurn.get();
    }

    public IntegerProperty xoTurnProperty() {
        return xoTurn;
    }

    public void nextTurn() {
        if (xoTurn.get() == 0)
            xoTurn.set(1);
        else
            xoTurn.set(0);
        turnTotal++;
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
        String winningLine = Arrays.stream(winningLines).filter(w -> w.equals("XXX") || w.equals("000")).findFirst().orElse("");
        if (winningLine.equals("XXX")) {
            setWinnerText("X Won!");
            disableButtons(buttons);
            givePoints();
        } else if (winningLine.equals("000")) {
            setWinnerText("0 Won!");
            disableButtons(buttons);
            givePoints();
        } else if (turnTotal > 8) {
            setWinnerText("Draw");
            disableButtons(buttons);
        }
    }

    private void givePoints() {

        if (xoTurn.get() == 0)
            player2Points.set(player2Points.get() + 1);
        else
            player1Points.set(player1Points.get() + 1);
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
        this.xoTurn.set(0);
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
        return player2Points.get();
    }

    public IntegerProperty player2PointsProperty() {
        return player2Points;
    }

    public int getPlayer1Points() {
        return player1Points.get();
    }

    public IntegerProperty player1PointsProperty() {
        return player1Points;
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

    public void resetPlayer1Points() {
        this.player1Points.set(0);
    }

    public void resetPlayer2Points() {
        this.player2Points.set(0);
    }

    public multiPlayerStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(multiPlayerStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getPlayer1() {
        return player1.get();
    }

    public StringProperty player1Property() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1.set(player1);
    }

    public String getPlayer2() {
        return player2.get();
    }

    public StringProperty player2Property() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2.set(player2);
    }

    public enum multiPlayerStatus {VS_CPU, VS_LOCAL, VS_LAN}
    public enum turnOrder{PLAYER_1, PLAYER_2}
}
