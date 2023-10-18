package se.iths.labb3tictactoe;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;

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

    public void setSymbol(Canvas canvas) {
        if (turn.get() == 0){
        }
    }

    public int getTurn() {
        return turn.get();
    }

    public IntegerProperty turnProperty() {
        return turn;
    }

    public void changeTurn() {
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
    public void resetWinnerText() {
        this.winnerText.set("TIC TAC TOE");
    }
}
