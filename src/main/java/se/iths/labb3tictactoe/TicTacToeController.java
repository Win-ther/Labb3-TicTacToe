package se.iths.labb3tictactoe;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.List;

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
    private static List<Button> buttons;
    @FXML
    private GridPane playArea;
    @FXML
    private Text tictictic, tactactac, toetoetoe;
    @FXML
    private ImageView leftSkeleton, rightSkeleton, startSkeleton;
    @FXML
    public void restartButtonClick(ActionEvent event) {
        restart();
        findCurrentMode();
    }
    private void restart(){
        model.reset(buttons);
    }
    @FXML
    private void onButtonClick(ActionEvent event) {
        model.setSymbol((Button) event.getSource());
        model.gameOver(buttons);
        findCurrentMode();
    }

    private void findCurrentMode() {
        if (!model.isGameOver()) {
            switch (model.getCurrentStatus()) {
                case VS_CPU -> model.cpuTurn(buttons);
                case VS_LAN -> model.player2LanTurn(buttons);
            }
        }
    }

    public void initialize() {
        buttons = Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9);
        buttons.forEach(e -> e.setFocusTraversable(false));
    }

    public void startGame() {
        setPlayAreaVisible();
        findCurrentMode();
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

    public void mainMenu() {
        model.resetPoints();
        model.reset(buttons);
        playButton.setVisible(true);
        playArea.setVisible(false);
        tictictic.setVisible(false);
        tactactac.setVisible(false);
        toetoetoe.setVisible(false);
        leftSkeleton.setVisible(false);
        rightSkeleton.setVisible(false);
        startSkeleton.setVisible(true);
    }
    public void exit() {
        TicTacToeApplication.exitWindow();
    }

    public void setVsLAN() {
        model.setCurrentStatus(TicTacToeModel.multiPlayerStatus.VS_LAN);
        mainMenu();
        restart();
        setPlayerNames();
        vsPlayerLAN.setDisable(true);
        vsPlayerOnPC.setDisable(false);
        vsCPU.setDisable(false);
    }

    public void setVsLocal() {
        model.setCurrentStatus(TicTacToeModel.multiPlayerStatus.VS_LOCAL);
        mainMenu();
        restart();
        setPlayerNames();
        vsPlayerLAN.setDisable(false);
        vsPlayerOnPC.setDisable(true);
        vsCPU.setDisable(false);
    }
    public void setPlayerNames(){
        String[] pNames = AlertBoxNames.display("Set players", "Set player names:");
        model.setPlayer1Name(pNames[0]);
        model.setPlayer2Name(pNames[1]);
    }
    public void setVsCPU() {
        model.setCurrentStatus(TicTacToeModel.multiPlayerStatus.VS_CPU);
        mainMenu();
        model.setPlayer1Name("CPU");
        model.setPlayer2Name("Player");
        restart();
        vsPlayerLAN.setDisable(false);
        vsPlayerOnPC.setDisable(false);
        vsCPU.setDisable(true);
    }
    public TicTacToeModel getModel() {
        return model;
    }
}