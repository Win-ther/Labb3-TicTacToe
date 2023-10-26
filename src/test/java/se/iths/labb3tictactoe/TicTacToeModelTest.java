package se.iths.labb3tictactoe;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TicTacToeModelTest {

    String[] buttons = {"", "", "", "", "", "", "", "", ""};
    Player player1 = new Player(new SimpleStringProperty("TestPlayer"), new SimpleIntegerProperty(0), new SimpleStringProperty("X"));
    Player player2 = new Player(new SimpleStringProperty("TestPlayer2"), new SimpleIntegerProperty(0), new SimpleStringProperty("0"));


    @Test
    @DisplayName("All buttons are able to be clicked and should return true")
    void allButtonsAreAbleToBeClickedAndShouldReturnTrue() {
        assertThat(TicTacToeModel.usableButton(0, buttons)).isTrue();
        assertThat(TicTacToeModel.usableButton(1, buttons)).isTrue();
        assertThat(TicTacToeModel.usableButton(2, buttons)).isTrue();
        assertThat(TicTacToeModel.usableButton(3, buttons)).isTrue();
        assertThat(TicTacToeModel.usableButton(4, buttons)).isTrue();
        assertThat(TicTacToeModel.usableButton(5, buttons)).isTrue();
        assertThat(TicTacToeModel.usableButton(6, buttons)).isTrue();
        assertThat(TicTacToeModel.usableButton(7, buttons)).isTrue();
        assertThat(TicTacToeModel.usableButton(8, buttons)).isTrue();
    }

    @Test
    @DisplayName("If a button has text it is not be able to be clicked and should return false")
    void ifAButtonHasTextItShouldReturnFalse() {
        buttons[0] = "X";
        buttons[8] = "0";

        assertThat(TicTacToeModel.usableButton(0, buttons)).isFalse();
        assertThat(TicTacToeModel.usableButton(8, buttons)).isFalse();
    }

    @Test
    @DisplayName("Should return false if the winningLine is not equal to the player-objects winningLine")
    void shouldReturnFalseIfTheWinningLineIsNotEqualToThePlayerObjectsWinningLine(){
        String[] winningLines = TicTacToeModel.getWinningLines(buttons);
        String winningLine = TicTacToeModel.getTheWinningLine(winningLines, player1, player2);

        assertThat(TicTacToeModel.playerWins(winningLine, player1)).isFalse();
        assertThat(TicTacToeModel.playerWins(winningLine, player2)).isFalse();
    }
    @Test
    @DisplayName("Should return false if player1 symbol in winningLine is compared to player2 winningLine")
    void shouldReturnFalseIfPlayer1SymbolInWinningLineIsComparedToPlayer2WinningLine(){
        buttons[0] = player1.symbol().get();
        buttons[1] = player1.symbol().get();
        buttons[2] = player1.symbol().get();

        String[] winningLines = TicTacToeModel.getWinningLines(buttons);
        String winningLine = TicTacToeModel.getTheWinningLine(winningLines, player1, player2);

        assertThat(TicTacToeModel.playerWins(winningLine, player2)).isFalse();
    }
    @Test
    @DisplayName("Should return true if winningLine is equal to the player-objects winningLine Horizontal")
    void shouldReturnTrueIfWinningLineIsEqualToThePlayerObjectsWinningLineHorizontal(){
        buttons[0] = player1.symbol().get();
        buttons[1] = player1.symbol().get();
        buttons[2] = player1.symbol().get();

        String[] winningLines = TicTacToeModel.getWinningLines(buttons);
        String winningLine = TicTacToeModel.getTheWinningLine(winningLines, player1, player2);

        assertThat(TicTacToeModel.playerWins(winningLine, player1)).isTrue();
    }
    @Test
    @DisplayName("Should return true if winningLine is equal to the player-objects winningLine Vertical")
    void shouldReturnTrueIfWinningLineIsEqualToThePlayerObjectsWinningLineVertical(){
        buttons[0] = player1.symbol().get();
        buttons[3] = player1.symbol().get();
        buttons[6] = player1.symbol().get();

        String[] winningLines = TicTacToeModel.getWinningLines(buttons);
        String winningLine = TicTacToeModel.getTheWinningLine(winningLines, player1, player2);

        assertThat(TicTacToeModel.playerWins(winningLine, player1)).isTrue();
    }
    @Test
    @DisplayName("Should return true if winningLine is equal to the player-objects winningLine Diagonal square 1-5-9")
    void shouldReturnTrueIfWinningLineIsEqualToThePlayerObjectsWinningLineDiagonalSquare159(){
        buttons[0] = player1.symbol().get();
        buttons[4] = player1.symbol().get();
        buttons[8] = player1.symbol().get();

        String[] winningLines = TicTacToeModel.getWinningLines(buttons);
        String winningLine = TicTacToeModel.getTheWinningLine(winningLines, player1, player2);

        assertThat(TicTacToeModel.playerWins(winningLine, player1)).isTrue();
    }
    @Test
    @DisplayName("Should return true if winningLine is equal to the player-objects winningLine Diagonal square 3-5-7")
    void shouldReturnTrueIfWinningLineIsEqualToThePlayerObjectsWinningLineDiagonalSquare357(){
        buttons[2] = player2.symbol().get();
        buttons[4] = player2.symbol().get();
        buttons[6] = player2.symbol().get();

        String[] winningLines = TicTacToeModel.getWinningLines(buttons);
        String winningLine = TicTacToeModel.getTheWinningLine(winningLines, player1, player2);

        assertThat(TicTacToeModel.playerWins(winningLine, player2)).isTrue();
    }



}