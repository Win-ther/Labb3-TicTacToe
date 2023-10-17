package se.iths.labb3tictactoe;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController {
    //Controller hanterar event, klickar på skit
    //Modellen ansvaring för informationen och regler om informationen.
    //Controller ska där med hjälp av event uppdatera informationen i Modellen.
    //ändra i model endast och bind vyns fält med dem i Model.
    //Går att binda dem även i fxml-filen, se welcomeText;
    //Använd Canvas istället för buttons kanske; Kan se bättre ut.
    //Kontrollera klick med koordinater, MouseEvent mouseEvent
    //ToDO: Grid, logic for clicking squares, turnorder logic, GameOver Logic, AI Logic.
    @FXML
    private GridPane grid;
    @FXML
    private Text winnerText;
    private int turn = 0;

    public Label welcomeText;
    private Model model = new Model();

    @FXML
    protected void onButtonClick(MouseEvent mouseEvent) {
        if (mouseEvent.getX() < grid.getWidth())
            grid.add(new Text("X"), 0, 0);
    }

    public Model getModel() {
        return model;
    }

    public void initialize() {
        welcomeText.textProperty().bind(model.textProperty());
    }
}