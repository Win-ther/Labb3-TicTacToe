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
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HelloController {
    //Controller hanterar event, klickar på skit
    //Modellen ansvarig för informationen och regler om informationen.
    //Controller ska där med hjälp av event uppdatera informationen i Modellen.
    //ändra i model endast och bind vyns fält med dem i Model.
    //Går att binda dem även i fxml-filen, se welcomeText;
    //Använd Canvas istället för buttons kanske; Kan se bättre ut.
    //Kontrollera klick med koordinater, MouseEvent mouseEvent
    //ToDO: Grid ~Done~, logic for clicking squares ~Done~, turnorder logic~Done~, GameOver Logic~Done~, AI Logic.
    private Model model = new Model();
    @FXML
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, restartButton,playButton;
    private List<Button> buttons;
    @FXML
    private GridPane playArea;
    @FXML
    private Text tictictic,tactactac,toetoetoe;

    @FXML
    public void restart(ActionEvent event){
        model.reset(buttons);
        cpuTurn();
    }

    @FXML
    private void onButtonClick(ActionEvent event) {
        model.setSymbol((Button) event.getSource());
        model.gameOver(buttons);
        if (!model.isGameOver())
            cpuTurn();
    }

    public Model getModel() {
        return model;
    }

    public void cpuTurn(){
        Random random = new Random();
        int buttonNumber;
        while (true){
            buttonNumber = random.nextInt(9);
            if (usableButton(buttonNumber)){
                model.setSymbol(buttons.get(buttonNumber));
                model.gameOver(buttons);
                break;
            }
        }
    }
    private boolean usableButton(int index){
        return !buttons.get(index).isDisabled();
    }
    public void initialize() {
        buttons = Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9);
        buttons.forEach(e -> e.setFocusTraversable(false));
        playArea.setVisible(false);
        tictictic.setVisible(false);
        tactactac.setVisible(false);
        toetoetoe.setVisible(false);
    }

    public void startGame(ActionEvent event) {
        playButton.setVisible(false);
        playArea.setVisible(true);
        tictictic.setVisible(true);
        tactactac.setVisible(true);
        toetoetoe.setVisible(true);
        cpuTurn();
    }
}