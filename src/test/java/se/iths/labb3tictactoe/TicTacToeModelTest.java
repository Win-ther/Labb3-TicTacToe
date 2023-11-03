package se.iths.labb3tictactoe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TicTacToeModelTest {
    final TicTacToeModel model = new TicTacToeModel(true);
    @Test
    @DisplayName("All buttons are able to be clicked and should return true")
    void allButtonsAreAbleToBeClickedAndShouldReturnTrue() {
        assertThat(model.usableButton(0,model.getBoard())).isTrue();
        assertThat(model.usableButton(1,model.getBoard())).isTrue();
        assertThat(model.usableButton(2,model.getBoard())).isTrue();
        assertThat(model.usableButton(3,model.getBoard())).isTrue();
        assertThat(model.usableButton(4,model.getBoard())).isTrue();
        assertThat(model.usableButton(5,model.getBoard())).isTrue();
        assertThat(model.usableButton(6,model.getBoard())).isTrue();
        assertThat(model.usableButton(7,model.getBoard())).isTrue();
        assertThat(model.usableButton(8,model.getBoard())).isTrue();
    }
    @Test
    @DisplayName("If a button has text it is not be able to be clicked and should return false")
    void ifAButtonHasTextItShouldReturnFalse() {
        model.setSymbol(0); //X
        model.setSymbol(8); //0

        assertThat(model.usableButton(0,model.getBoard())).isFalse();
        assertThat(model.usableButton(8,model.getBoard())).isFalse();
    }
    @Test
    @DisplayName("isGameOver should return false if gameOver method is called with an empty board")
    void isGameOverShouldReturnFalseIfGameOverMethodIsCalledWithAnEmptyBoard(){
        model.gameOver();

        assertThat(model.isGameOver()).isFalse();
    }
    @Test
    @DisplayName("isGameOver should return false if gameOver method is called without three in a row")
    void isGameOverShouldReturnFalseIfGameOverMethodIsCalledWithoutThreeInARow(){
        model.setSymbol(1); //X
        model.setSymbol(4); //0
        model.setSymbol(6); //X

        model.gameOver();

        assertThat(model.isGameOver()).isFalse();
    }
    @Test
    @DisplayName("isGameOver should return false if different symbols are in a row")
    void isGameOverShouldReturnFalseIfDifferentSymbolsAreInARow(){
        model.setSymbol(0); //X
        model.setSymbol(1); //0
        model.setSymbol(2); //X

        model.gameOver();

        assertThat(model.isGameOver()).isFalse();
    }
    @Test
    @DisplayName("isGameOver should return true if gameOver method is called with three in a row horizontal")
    void isGameOverShouldReturnTrueIfGameOverMethodIsCalledWithThreeInARowHorizontal(){
        model.setSymbol(0); //X
        model.setSymbol(8); //0
        model.setSymbol(1); //X
        model.setSymbol(7); //0
        model.setSymbol(2); //X

        model.gameOver();

        assertThat(model.isGameOver()).isTrue();
    }
    @Test
    @DisplayName("isGameOver should return true if gameOver method is called with three in a row vertical")
    void isGameOverShouldReturnTrueIfGameOverMethodIsCalledWithThreeInARowVertical(){
        model.setSymbol(0); //X
        model.setSymbol(8); //0
        model.setSymbol(3); //X
        model.setSymbol(5); //0
        model.setSymbol(6); //X

        model.gameOver();

        assertThat(model.isGameOver()).isTrue();
    }
    @Test
    @DisplayName("isGameOver should return true if gameOver method is called with three in a row diagonal")
    void isGameOverShouldReturnTrueIfGameOverMethodIsCalledWithThreeInARowDiagonal(){
        model.setSymbol(0); //X
        model.setSymbol(2); //0
        model.setSymbol(4); //X
        model.setSymbol(5); //0
        model.setSymbol(8); //X

        model.gameOver();

        assertThat(model.isGameOver()).isTrue();
    }
    @Test
    @DisplayName("isGameOver should return true if gameOver method is called and the turn is over 8 and the game is a draw")
    void isGameOverShouldReturnTrueIfGameOverMethodIsCalledAndTheTurnIsOver8AndTheGameIsADraw(){
        model.setSymbol(0); //X
        model.setSymbol(2); //0
        model.setSymbol(6); //X
        model.setSymbol(8); //0
        model.setSymbol(5); //X
        model.setSymbol(3); //0
        model.setSymbol(1); //X
        model.setSymbol(4); //0
        model.setSymbol(7); //X

        model.gameOver();

        assertThat(model.isGameOver()).isTrue();
        assertThat(model.getPlayer1Points()).isZero();
        assertThat(model.getPlayer2Points()).isZero();
    }
}