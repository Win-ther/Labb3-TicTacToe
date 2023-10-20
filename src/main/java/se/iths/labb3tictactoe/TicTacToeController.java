package se.iths.labb3tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TicTacToeController {
    //Controller hanterar event, klickar på skit
    //Modellen ansvarig för informationen och regler om informationen.
    //Controller ska där med hjälp av event uppdatera informationen i Modellen.
    //ändra i model endast och bind vyns fält med dem i Model.
    //Går att binda dem även i fxml-filen, se welcomeText;
    //Använd Canvas istället för buttons kanske; Kan se bättre ut.
    //Kontrollera klick med koordinater, MouseEvent mouseEvent
    //ToDO: Grid ~Done~, logic for clicking squares ~Done~, turnorder logic~Done~, GameOver Logic~Done~, AI Logic ~Done~.

    public RadioMenuItem vsCPU, vsPlayerOnPC, vsPlayerLAN;
    private TicTacToeModel model = new TicTacToeModel();
    @FXML
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, restartButton, playButton;
    private List<Button> buttons;
    @FXML
    private GridPane playArea;
    @FXML
    private Text tictictic, tactactac, toetoetoe;
    @FXML
    private ImageView leftSkeleton, rightSkeleton, startSkeleton;


    @FXML
    public void restart(ActionEvent event) {
        model.reset(buttons);
        cpuStart();
    }

    @FXML
    private void onButtonClick(ActionEvent event) {
        model.setSymbol((Button) event.getSource());
        model.gameOver(buttons);
        if (!model.isGameOver()) {
            switch (model.getCurrentStatus()) {
                case VS_CPU -> cpuTurn();
                case VS_LOCAL -> player2LocalTurn();
            }
        }
    }

    private void player2LocalTurn() {
        //ToDo: Fix so that 2 players on same pc can play, bind CPU points to a variable in Model so that it can change text.
    }

    public TicTacToeModel getModel() {
        return model;
    }


    public void cpuTurn() {
        Random random = new Random();
        int buttonNumber;
        while (true) {
            buttonNumber = random.nextInt(9);
            if (usableButton(buttonNumber)) {
                model.setSymbol(buttons.get(buttonNumber));
                model.gameOver(buttons);
                break;
            }
        }
    }

    private boolean usableButton(int index) {
        return !buttons.get(index).isDisabled();
    }

    public void initialize() {
        buttons = Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9);
        buttons.forEach(e -> e.setFocusTraversable(false));
    }

    public void startGame() {
        setPlayAreaVisible();
        cpuStart();
    }

    private void setPlayAreaVisible() {
        playButton.setVisible(false);
        playArea.setVisible(true);
        tictictic.setVisible(true);
        tactactac.setVisible(true);
        toetoetoe.setVisible(true);
        leftSkeleton.setVisible(true);
        rightSkeleton.setVisible(true);
        startSkeleton.setVisible(false);
    }

    public void restartGame() {
        model.resetPlayerPoints();
        model.resetCpuPoints();
        playButton.setVisible(true);
        playArea.setVisible(false);
        tictictic.setVisible(false);
        tactactac.setVisible(false);
        toetoetoe.setVisible(false);
        leftSkeleton.setVisible(false);
        rightSkeleton.setVisible(false);
        startSkeleton.setVisible(true);
    }

    private void cpuStart() {
        if ((int) Math.floor(Math.random() * 2) == 0)
            cpuTurn();
    }

    public void exit() {
        TicTacToeApplication.exitWindow();
    }

    public void setVsLAN(ActionEvent event) {
    }

    public void setVsLocal(ActionEvent event) {
    }

    public void setVsCPU(ActionEvent event) {
    }
}