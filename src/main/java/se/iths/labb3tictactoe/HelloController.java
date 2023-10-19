package se.iths.labb3tictactoe;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

public class HelloController {
    //Controller hanterar event, klickar på skit
    //Modellen ansvaring för informationen och regler om informationen.
    //Controller ska där med hjälp av event uppdatera informationen i Modellen.
    //ändra i model endast och bind vyns fält med dem i Model.
    //Går att binda dem även i fxml-filen, se welcomeText;
    //Använd Canvas istället för buttons kanske; Kan se bättre ut.
    //Kontrollera klick med koordinater, MouseEvent mouseEvent
    //ToDO: Grid ~Done~, logic for clicking squares ~Done~, turnorder logic, GameOver Logic, AI Logic.
    @FXML
    private GridPane grid;
    @FXML
    private Label winnerText;
    private Model model = new Model();
    private GraphicsContext gc;
    @FXML
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, restartButton;

    private List<Button> buttons;

    @FXML
    public void restart(ActionEvent event){
        model.reset(buttons);
    }

    @FXML
    private void onButtonClick(ActionEvent event) {
        model.setSymbol((Button) event.getSource());
        model.gameOver(buttons);
    }

    public Model getModel() {
        return model;
    }

    public void initialize() {
        buttons = Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9);
    }
}