package se.iths.labb3tictactoe;

import javafx.beans.property.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.List;

public class Model {
    private StringProperty winnerText = new SimpleStringProperty();
    private IntegerProperty xoTurn = new SimpleIntegerProperty();
    private int turnTotal;
    private boolean isGameOver;
    private IntegerProperty playerPoints = new SimpleIntegerProperty();
    private IntegerProperty cpuPoints = new SimpleIntegerProperty();
    public Image image1;
    public Image image2;
    private ObjectProperty<Image> left;
    private ObjectProperty<Image> right;
    private ObjectProperty<Image> startImage;

    public Model() {
        winnerText.setValue("TIC TAC TOE");
        xoTurn.setValue(0);
        turnTotal = 0;
        isGameOver = false;
        cpuPoints.set(0);
        playerPoints.set(0);
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
        }else if(turnTotal > 8){
            setWinnerText("Draw");
            disableButtons(buttons);
        }
    }

    private void givePoints() {
        String method_name = Thread.currentThread().getStackTrace()[3].getMethodName();
        if (method_name.equals("onButtonClick"))
            playerPoints.set(playerPoints.get()+1);
        else
            cpuPoints.set(cpuPoints.get()+1);
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

    public int getPlayerPoints() {
        return playerPoints.get();
    }

    public IntegerProperty playerPointsProperty() {
        return playerPoints;
    }

    public int getCpuPoints() {
        return cpuPoints.get();
    }

    public IntegerProperty cpuPointsProperty() {
        return cpuPoints;
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
}
