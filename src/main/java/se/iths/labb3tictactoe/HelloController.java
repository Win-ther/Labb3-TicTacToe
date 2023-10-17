package se.iths.labb3tictactoe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class HelloController {
    //Använd Canvas istället för buttons kanske; Kan se bättre ut.

    @FXML
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9;
    @FXML
    private Text winnerText;

    private int turn = 0;

    @FXML
    protected void onButtonClick() {

    }
}