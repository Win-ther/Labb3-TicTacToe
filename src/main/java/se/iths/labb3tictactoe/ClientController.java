package se.iths.labb3tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;

public class ClientController {
    //ToDO: Grid ~Done~, logic for clicking squares ~Done~, turnorder logic~Done~, GameOver Logic~Done~, AI Logic ~Done~.
    private final ClientModel model = new ClientModel();
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

        model.setSymbolPlayer2(buttons.indexOf(clickedButton));
        disableButton(clickedButton);
        model.gameOver();
        disableButtonsIfGameOver();
        //Todo: Fix play over lan
        model.player2LanTurn(buttons.indexOf(clickedButton));
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

    public void initialize() {
        buttons = Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9);
        buttons.forEach(button -> button.setFocusTraversable(false));
    }

    public void startGame() {
        setPlayAreaVisible();
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

    public ClientModel getModel() {
        return model;
    }
}