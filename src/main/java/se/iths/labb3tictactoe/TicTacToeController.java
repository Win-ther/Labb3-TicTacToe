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

public class TicTacToeController {
    public RadioMenuItem vsCPU, vsPlayerOnPC, vsPlayerLAN;
    private static final TicTacToeModel model = new TicTacToeModel();
    @FXML
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9, playButton;
    private static List<Button> buttons;
    @FXML
    private GridPane playArea;
    @FXML
    private Text tictictic, tactactac, toetoetoe;
    @FXML
    private ImageView leftSkeleton, rightSkeleton, startSkeleton;

    public void initialize() {
        buttons = Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9);
        buttons.forEach(button -> button.setFocusTraversable(false));
    }

    public void onButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        clickedButton.setText(model.getCurrentPlayer().symbol().get());
        disableButton(clickedButton);

        int index = buttons.indexOf(clickedButton);

        ivVsLanThenSendIndexToClient(index);
        model.setSymbol(index);
        ifVsCpuThenCpuTurn();
        disableButtonsIfGameOver();

        if (model.getCurrentStatus() == TicTacToeModel.multiPlayerStatus.VS_LAN)
            buttons.forEach(b -> b.setDisable(true));
    }

    public static void addingClientIndexToBoard(int index) {
        buttons.get(index).setText("0");
        buttons.get(index).setDisable(true);
        model.setSymbol(index);
        if (!model.getIsGameOver())
            buttons.stream().filter(b -> b.getText().isEmpty()).forEach(b -> b.setDisable(false));
    }

    private void ivVsLanThenSendIndexToClient(int index) {
        if (model.getCurrentStatus() == TicTacToeModel.multiPlayerStatus.VS_LAN) {
            model.sendIndexClickedToClient(index);
        }
    }


    private void ifVsCpuThenCpuTurn() {
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

    public void startGame() {
        setPlayAreaVisible();
        ifVsCpuThenCpuTurn();
    }

    public void mainMenu() {
        model.resetPoints();
        model.reset();
        buttons.forEach(this::resetButton);
        playButton.setVisible(true);
        showPlayArea(false);
        startSkeleton.setVisible(true);
    }

    private void setPlayAreaVisible() {
        playButton.setVisible(false);
        showPlayArea(true);
        startSkeleton.setVisible(false);
    }

    private void showPlayArea(boolean value) {
        playArea.setVisible(value);
        tictictic.setVisible(value);
        tactactac.setVisible(value);
        toetoetoe.setVisible(value);
        leftSkeleton.setVisible(value);
        rightSkeleton.setVisible(value);
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

    public void setPlayerNames() {
        String[] pNames = AlertBoxNames.display("Set players", "Set player names:");
        model.setPlayer1Name(pNames[0]);
        model.setPlayer2Name(pNames[1]);
    }

    @FXML
    public void restartButtonClick() {
        restart();
        ifVsCpuThenCpuTurn();
    }

    private void restart() {
        model.reset();
        buttons.forEach(this::resetButton);
    }

    private void resetButton(Button button) {
        button.setText("");
        button.setDisable(false);
    }

    private void disableButtonsIfGameOver() {
        if (model.getIsGameOver())
            disableButtons();
    }

    private void disableButtons() {
        buttons.forEach(this::disableButton);
        model.setGameOver(true);
    }

    private void disableButton(Button button) {
        button.setDisable(true);
    }

    public void exit() {
        TicTacToeApplication.exitWindow();
    }

    public TicTacToeModel getModel() {
        return model;
    }
}