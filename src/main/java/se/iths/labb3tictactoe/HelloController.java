package se.iths.labb3tictactoe;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    //Använd Canvas istället för buttons kanske; Kan se bättre ut.
    //Kontrollera klick med koordinater, MouseEvent mouseEvent
    //ToDO: Grid, logic for clicking squares, turnorder logic, GameOver Logic, AI Logic.
    @FXML
    Canvas canvas;
    @FXML
    private Text winnerText;
    @FXML
    private Text text1;
    private int turn = 0;

    @FXML
    protected void onButtonClick(MouseEvent mouseEvent) {
        if (mouseEvent.getX() < canvas.getWidth()/3 && mouseEvent.getY() < canvas.getHeight()/3)
            text1.setText("X");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}