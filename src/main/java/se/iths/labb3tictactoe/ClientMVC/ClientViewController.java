package se.iths.labb3tictactoe.ClientMVC;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import se.iths.labb3tictactoe.TicTacToeModel;

import java.util.Arrays;
import java.util.List;

public class ClientViewController {
    private TicTacToeModel model = new TicTacToeModel();
    public Button button1, button2, button3, button4, button5, button6, button7, button8, button9;
    private List<Button> buttons;
    public void onButtonClick(ActionEvent event) {
        model.setSymbol((Button) event.getSource());
        model.gameOver(buttons);
    }
    public void initialize() {
        buttons = Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9);
        buttons.forEach(e -> e.setFocusTraversable(false));
    }

    public TicTacToeModel getModel() {
        return model;
    }
}
