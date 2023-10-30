package se.iths.labb3tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TicTacToeController {
    //ToDO: Grid ~Done~, logic for clicking squares ~Done~, turnorder logic~Done~, GameOver Logic~Done~, AI Logic ~Done~.

    public RadioMenuItem vsCPU, vsPlayerOnPC, vsPlayerLAN;
    private final TicTacToeModel model = new TicTacToeModel();
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
    public void restartButtonClick() {
        restart();
        checkIfVsCpu();
    }
    private void restart(){
        model.reset();
        buttons.forEach(this::resetButton);
    }
    private void resetButton(Button button) {
        button.setText("");
        button.setDisable(false);
    }

    public void onButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        clickedButton.setText(model.getCurrentPlayer().symbol().get());

        model.setSymbol(buttons.indexOf(clickedButton));
        disableButton(clickedButton);
        disableButtonsIfGameOver();
        if (model.getCurrentStatus() == TicTacToeModel.multiPlayerStatus.VS_LAN)
            player2ButtonGottaBeClicked(clickedButton);
        else
            checkIfVsCpu();
    }

    private void player2ButtonGottaBeClicked(Button clickedButton) {
        int indexFromPlayer2 = model.player2LanTurn(buttons.indexOf(clickedButton));
        buttons.get(indexFromPlayer2).setText(model.getCurrentPlayer().symbol().get());
        model.setSymbol(indexFromPlayer2);
        buttons.get(indexFromPlayer2).setDisable(true);
    }

    private void disableButtonsIfGameOver() {
        if(model.getIsGameOver())
            disableButtons();
    }

    private void disableButtons() {
        buttons.forEach(this::disableButton);
        model.setGameOver(true);
    }

    private void disableButton(Button button){
        button.setDisable(true);
    }
    private void checkIfVsCpu() {
        if (!model.getIsGameOver()) {
            if (model.getCurrentStatus() == TicTacToeModel.multiPlayerStatus.VS_CPU) {
                cpuTurn();
            }
        }
    }

    private void cpuTurn() {
        int buttonNr = model.cpuTurn();
        buttons.get(buttonNr).setText(model.getPlayer1().symbol().get());
        disableButton(buttons.get(buttonNr));
        disableButtonsIfGameOver();
    }

    public void initialize() {
        buttons = Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9);
        buttons.forEach(button -> button.setFocusTraversable(false));
    }

    public void startGame() {
        setPlayAreaVisible();
        checkIfVsCpu();
    }

    private void setPlayAreaVisible() {
        playButton.setVisible(false);
        showPlayArea(true);
        startSkeleton.setVisible(false);
    }

    public void mainMenu() {
        model.resetPoints();
        model.reset();
        buttons.forEach(this::resetButton);
        playButton.setVisible(true);
        showPlayArea(false);
        startSkeleton.setVisible(true);
    }

    private void showPlayArea(boolean value) {
        playArea.setVisible(value);
        tictictic.setVisible(value);
        tactactac.setVisible(value);
        toetoetoe.setVisible(value);
        leftSkeleton.setVisible(value);
        rightSkeleton.setVisible(value);
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