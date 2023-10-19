package se.iths.labb3tictactoe;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

import java.util.List;

public class Model {
    private StringProperty winnerText = new SimpleStringProperty();
    private IntegerProperty turn = new SimpleIntegerProperty();
    private String[][] grid;

    public Model() {
        winnerText.setValue("TIC TAC TOE");
        turn.setValue(0);
        grid = new String[3][3];
    }

    public String[][] getGrid() {
        return grid;
    }

    public void setSymbol(Button button) {
        if (turn.get() == 0){
            button.setText("X");
            button.setDisable(true);
            nextTurn();
        }else{
            button.setText("0");
            button.setDisable(true);
            nextTurn();
        }
    }

    public int getTurn() {
        return turn.get();
    }

    public IntegerProperty turnProperty() {
        return turn;
    }

    public void nextTurn() {
        if (turn.get() == 0)
            turn.set(1);
        else
            turn.set(0);
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
        String winningLine = "";
        for (int i = 0; i < 8; i++) {
            winningLine = switch (i){
                case 0 -> buttons.get(0).getText() + buttons.get(1).getText() + buttons.get(2).getText();
                case 1 -> buttons.get(3).getText() + buttons.get(4).getText() + buttons.get(5).getText();
                case 2 -> buttons.get(6).getText() + buttons.get(7).getText() + buttons.get(8).getText();
                case 3 -> buttons.get(0).getText() + buttons.get(3).getText() + buttons.get(6).getText();
                case 4 -> buttons.get(1).getText() + buttons.get(4).getText() + buttons.get(7).getText();
                case 5 -> buttons.get(2).getText() + buttons.get(5).getText() + buttons.get(8).getText();
                case 6 -> buttons.get(0).getText() + buttons.get(4).getText() + buttons.get(8).getText();
                case 7 -> buttons.get(2).getText() + buttons.get(4).getText() + buttons.get(6).getText();
                default -> "";
            };
            if (winningLine.equals("XXX")){
                setWinnerText("X Won!");
                buttons.forEach(e -> e.setDisable(true));
            } else if (winningLine.equals("000")) {
                setWinnerText("0 Won!");
                buttons.forEach(e -> e.setDisable(true));
            }
        }
    }

    public void reset(List<Button> buttons) {
        this.winnerText.set("TIC TAC TOE");
        buttons.forEach(this::resetButton);
    }

    private void resetButton(Button e) {
        e.setText("");
        e.setDisable(false);
        this.turn.set(0);
    }
}
