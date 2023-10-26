package se.iths.labb3tictactoe;

import javafx.beans.property.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static se.iths.labb3tictactoe.TicTacToeModel.multiPlayerStatus.*;
import static se.iths.labb3tictactoe.TicTacToeModel.turnOrder.PLAYER_1;
import static se.iths.labb3tictactoe.TicTacToeModel.turnOrder.PLAYER_2;

public class TicTacToeModel {
    private StringProperty winnerText = new SimpleStringProperty();
    private turnOrder turn = PLAYER_1;
    private int turnTotal;
    private boolean isGameOver;
    private Player player1, player2;
    public Image image1, image2;
    private ObjectProperty<Image> left, right, startImage;
    private multiPlayerStatus currentStatus = VS_CPU;

    //Todo: Move points, name and symbols to Player objects, clean up this garbage code
    public TicTacToeModel() {
        winnerText.setValue("TIC TAC TOE");
        turnTotal = 0;
        isGameOver = false;

        //Setting up players
        player1 = new Player(new SimpleStringProperty("CPU"), new SimpleIntegerProperty(0), new SimpleStringProperty("X"));
        player2 = new Player(new SimpleStringProperty("Player"), new SimpleIntegerProperty(0), new SimpleStringProperty("0"));

        //For gifs
        image1 = new Image(getClass().getResource("images/skeleton-dancing.gif").toExternalForm());
        image2 = new Image(getClass().getResource("images/StartSkeleton.gif").toExternalForm());
        left = new SimpleObjectProperty<>(image1);
        right = new SimpleObjectProperty<>(image1);
        startImage = new SimpleObjectProperty<>(image2);

        //Server


        //Todo: Flytta detta till egen metod för t.ex skickning av information när knapp klickas på.
    }

    public void setSymbol(Button button) {
        if (turn == PLAYER_1) {
            button.setText(player1.symbol().get());
            turn = PLAYER_2;
        } else {
            button.setText(player2.symbol().get());
            turn = PLAYER_1;
        }
        button.setDisable(true);
        turnTotal++;
    }

    public turnOrder getTurn() {
        return turn;
    }

    public void setTurn(turnOrder turn) {
        this.turn = turn;
    }

    public String getWinnerText() {
        return winnerText.get();
    }

    public StringProperty winnerTextProperty() {
        return winnerText;
    }

    public void setWinnerText(String winnerText) {
        this.winnerText.set(winnerText);
    }

    private String[] buttonsToText(List<Button> buttons) {
        String[] temp = new String[9];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = buttons.get(i).getText();
        }
        return temp;
    }

    public void gameOver(List<Button> buttons) {
        String[] buttonsText = buttonsToText(buttons);
        String[] winningLines = getWinningLines(buttonsText);
        String winningLine = getTheWinningLine(winningLines, player1, player2);

        if (playerWins(winningLine, player1)) {
            winningPlayer(player1, buttons);
        } else if (playerWins(winningLine, player2)) {
            winningPlayer(player2, buttons);
        } else if (turnTotal > 8) {
            setWinnerText("Draw");
            disableButtons(buttons);
        }
    }

    private void winningPlayer(Player player, List<Button> buttons) {
        setWinnerText(player.name().get() + " Won!");
        disableButtons(buttons);
        givePoints(player);
    }

    public static String getTheWinningLine(String[] winningLines, Player player1, Player player2) {
        return Arrays.stream(winningLines).filter(w -> w.equals(getPlayerWinningLine(player1)) ||w.equals(getPlayerWinningLine(player2))).findFirst().orElse("");
    }

    public static String[] getWinningLines(String[] buttonsText) {
        return new String[]{
                buttonsText[0] + buttonsText[1] + buttonsText[2],
                buttonsText[3] + buttonsText[4] + buttonsText[5],
                buttonsText[6] + buttonsText[7] + buttonsText[8],
                buttonsText[0] + buttonsText[3] + buttonsText[6],
                buttonsText[1] + buttonsText[4] + buttonsText[7],
                buttonsText[2] + buttonsText[5] + buttonsText[8],
                buttonsText[0] + buttonsText[4] + buttonsText[8],
                buttonsText[2] + buttonsText[4] + buttonsText[6]
        };
    }

    public static boolean playerWins(String winningLine, Player player) {
        return winningLine.equals(getPlayerWinningLine(player));
    }

    private static String getPlayerWinningLine(Player player) {
        return player.symbol().get() + player.symbol().get() + player.symbol().get();
    }

    private void givePoints(Player player) {
        player.points().set(player.points().get() + 1);
    }

    private void disableButtons(List<Button> buttons) {
        buttons.forEach(e -> e.setDisable(true));
        this.isGameOver = true;
    }


    public void reset(List<Button> buttons) {
        this.winnerText.set("TIC TAC TOE");
        buttons.forEach(this::resetButton);
        this.turnTotal = 0;
        this.isGameOver = false;
    }

    private void resetButton(Button button) {
        button.setText("");
        button.setDisable(false);
        turn = PLAYER_1;
    }

    public int getTurnTotal() {
        return turnTotal;
    }

    public boolean getIsGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public int getPlayer2Points() {
        return player2.points().get();
    }

    public IntegerProperty player2PointsProperty() {
        return player2.points();
    }

    public int getPlayer1Points() {
        return player1.points().get();
    }

    public IntegerProperty player1PointsProperty() {
        return player1.points();
    }

    public Image getImage1() {
        return image1;
    }

    public Image getImage2() {
        return image2;
    }

    public Image getLeft() {
        return left.get();
    }

    public ObjectProperty<Image> leftProperty() {
        return left;
    }

    public Image getRight() {
        return right.get();
    }

    public ObjectProperty<Image> rightProperty() {
        return right;
    }

    public Image getStartImage() {
        return startImage.get();
    }

    public ObjectProperty<Image> startImageProperty() {
        return startImage;
    }

    public void resetPoints() {
        player1.points().set(0);
        player2.points().set(0);
    }

    public multiPlayerStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(multiPlayerStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getPlayer1Name() {
        return player1.name().get();
    }

    public StringProperty player1NameProperty() {
        return player1.name();
    }

    public void setPlayer1Name(String name) {
        player1.name().set(name);
    }

    public String getPlayer2Name() {
        return player2.name().get();
    }

    public StringProperty player2NameProperty() {
        return player2.name();
    }

    public void setPlayer2Name(String name) {
        this.player2.name().set(name);
    }

    public void cpuTurn(List<Button> buttons) {
        Random random = new Random();
        int buttonNumber;
        String[] buttonText = buttonsToText(buttons);
        while (true) {
            buttonNumber = random.nextInt(9);
            if (usableButton(buttonNumber, buttonText)) {
                setSymbol(buttons.get(buttonNumber));
                gameOver(buttons);
                break;
            }
        }
    }

    public static boolean usableButton(int index, String[] buttonText) {
        return buttonText[index].isEmpty();
    }

    public void player2LanTurn(List<Button> buttons) {

    }

    public enum multiPlayerStatus {VS_CPU, VS_LOCAL, VS_LAN}

    public enum turnOrder {PLAYER_1, PLAYER_2}
}
