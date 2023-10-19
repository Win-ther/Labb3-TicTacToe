package se.iths.labb3tictactoe;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

import java.util.Arrays;
import java.util.List;

public class Model {
    private StringProperty winnerText = new SimpleStringProperty();
    private IntegerProperty xoTurn = new SimpleIntegerProperty();
    private int turnTotal;
    private boolean isGameOver;


    public Model() {
        winnerText.setValue("TIC TAC TOE");
        xoTurn.setValue(0);
        turnTotal = 0;
        isGameOver = false;
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
        } else if (winningLine.equals("000")) {
            setWinnerText("0 Won!");
            disableButtons(buttons);
        }else if(turnTotal > 8){
            setWinnerText("Draw");
            disableButtons(buttons);
        }
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

    public void setTurnTotal(int turnTotal) {
        this.turnTotal = turnTotal;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
}
