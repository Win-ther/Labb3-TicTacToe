package se.iths.labb3tictactoe;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
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
    private Label winnerText;
    private Model model = new Model();
    private GraphicsContext gc;
    @FXML
    private Canvas canvas1, canvas2, canvas3, canvas4, canvas5, canvas6, canvas7, canvas8, canvas9;
    @FXML
    protected void onButtonClick(MouseEvent mouseEvent) {
        if(mouseEvent.)

    }

    public Model getModel() {
        return model;
    }

    public void initialize() {
        winnerText.textProperty().bind(model.textProperty());
        List<Canvas> canvasList = List.of(canvas1, canvas2, canvas3, canvas4, canvas5, canvas6, canvas7, canvas8, canvas9);
        //canvasList.forEach(e -> primeSquares);
    }

    private void setSquare(Canvas canvas){
        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.strokeLine(canvas.getWidth(), 0, 0, canvas.getHeight());


    }
    private void setOval(Canvas canvas){
        gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeOval(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}