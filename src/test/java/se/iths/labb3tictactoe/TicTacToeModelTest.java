package se.iths.labb3tictactoe;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TicTacToeModelTest {

    String[] buttonsText = {"", "", "", "", "", "", "", "", ""};
    Player player1 = new Player(new SimpleStringProperty("TestPlayer"), new SimpleIntegerProperty(0), new SimpleStringProperty("X"));
    Player player2 = new Player(new SimpleStringProperty("TestPlayer2"), new SimpleIntegerProperty(0), new SimpleStringProperty("0"));

    @Test
    @DisplayName("All buttons are able to be clicked and should return true")
    void allButtonsAreAbleToBeClickedAndShouldReturnTrue() {
        assertThat(TicTacToeModel.usableButton(0, buttonsText)).isTrue();
        assertThat(TicTacToeModel.usableButton(1, buttonsText)).isTrue();
        assertThat(TicTacToeModel.usableButton(2, buttonsText)).isTrue();
        assertThat(TicTacToeModel.usableButton(3, buttonsText)).isTrue();
        assertThat(TicTacToeModel.usableButton(4, buttonsText)).isTrue();
        assertThat(TicTacToeModel.usableButton(5, buttonsText)).isTrue();
        assertThat(TicTacToeModel.usableButton(6, buttonsText)).isTrue();
        assertThat(TicTacToeModel.usableButton(7, buttonsText)).isTrue();
        assertThat(TicTacToeModel.usableButton(8, buttonsText)).isTrue();
    }
    @Test
    @DisplayName("If a button has text it is not be able to be clicked and should return false")
    void ifAButtonHasTextItShouldReturnFalse() {
        buttonsText[0] = "X";
        buttonsText[8] = "0";

        assertThat(TicTacToeModel.usableButton(0, buttonsText)).isFalse();
        assertThat(TicTacToeModel.usableButton(8, buttonsText)).isFalse();
    }

    @Test
    @DisplayName("Should return false if the winningLine is not equal to the player-objects winningLine")
    void shouldReturnFalseIfTheWinningLineIsNotEqualToThePlayerObjectsWinningLine(){
        String[] winningLines = TicTacToeModel.getWinningLines(buttonsText);
        String winningLine = TicTacToeModel.getTheWinningLine(winningLines, player1, player2);

        assertThat(TicTacToeModel.playerWins(winningLine, player1)).isFalse();
        assertThat(TicTacToeModel.playerWins(winningLine, player2)).isFalse();
    }
    @Test
    @DisplayName("Should return false if player1 symbol in winningLine is compared to player2 winningLine")
    void shouldReturnFalseIfPlayer1SymbolInWinningLineIsComparedToPlayer2WinningLine(){
        buttonsText[0] = player1.symbol().get();
        buttonsText[1] = player1.symbol().get();
        buttonsText[2] = player1.symbol().get();

        String[] winningLines = TicTacToeModel.getWinningLines(buttonsText);
        String winningLine = TicTacToeModel.getTheWinningLine(winningLines, player1, player2);

        assertThat(TicTacToeModel.playerWins(winningLine, player2)).isFalse();
    }
    @Test
    @DisplayName("Should return true if winningLine is equal to the player-objects winningLine Horizontal")
    void shouldReturnTrueIfWinningLineIsEqualToThePlayerObjectsWinningLineHorizontal(){
        buttonsText[0] = player1.symbol().get();
        buttonsText[1] = player1.symbol().get();
        buttonsText[2] = player1.symbol().get();

        String[] winningLines = TicTacToeModel.getWinningLines(buttonsText);
        String winningLine = TicTacToeModel.getTheWinningLine(winningLines, player1, player2);

        assertThat(TicTacToeModel.playerWins(winningLine, player1)).isTrue();
    }
    @Test
    @DisplayName("Should return true if winningLine is equal to the player-objects winningLine Vertical")
    void shouldReturnTrueIfWinningLineIsEqualToThePlayerObjectsWinningLineVertical(){
        buttonsText[0] = player1.symbol().get();
        buttonsText[3] = player1.symbol().get();
        buttonsText[6] = player1.symbol().get();

        String[] winningLines = TicTacToeModel.getWinningLines(buttonsText);
        String winningLine = TicTacToeModel.getTheWinningLine(winningLines, player1, player2);

        assertThat(TicTacToeModel.playerWins(winningLine, player1)).isTrue();
    }
    @Test
    @DisplayName("Should return true if winningLine is equal to the player-objects winningLine Diagonal square 1-5-9")
    void shouldReturnTrueIfWinningLineIsEqualToThePlayerObjectsWinningLineDiagonalSquare159(){
        buttonsText[0] = player1.symbol().get();
        buttonsText[4] = player1.symbol().get();
        buttonsText[8] = player1.symbol().get();

        String[] winningLines = TicTacToeModel.getWinningLines(buttonsText);
        String winningLine = TicTacToeModel.getTheWinningLine(winningLines, player1, player2);

        assertThat(TicTacToeModel.playerWins(winningLine, player1)).isTrue();
    }
    @Test
    @DisplayName("Should return true if winningLine is equal to the player-objects winningLine Diagonal square 3-5-7")
    void shouldReturnTrueIfWinningLineIsEqualToThePlayerObjectsWinningLineDiagonalSquare357(){
        buttonsText[2] = player2.symbol().get();
        buttonsText[4] = player2.symbol().get();
        buttonsText[6] = player2.symbol().get();

        String[] winningLines = TicTacToeModel.getWinningLines(buttonsText);
        String winningLine = TicTacToeModel.getTheWinningLine(winningLines, player1, player2);

        assertThat(TicTacToeModel.playerWins(winningLine, player2)).isTrue();
    }
}