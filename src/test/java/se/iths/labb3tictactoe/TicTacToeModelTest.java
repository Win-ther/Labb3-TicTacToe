package se.iths.labb3tictactoe;

import org.assertj.core.api.Assertions.*;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TicTacToeModelTest {

    @Test
    @DisplayName("Given add when values 1 and 2 then returns 3")
    void givenAddWhenValues1And2ThenReturns3() {
        //Arrange
        int value1 = 1;
        int value2 = 2;
        //Act
        int result = TicTacToeModel.add(value1,value2);
        //Assert
        assertThat(result).isEqualTo(3);
    }
    @Test
    @DisplayName("After calling setValue with 2, getValue should return 2")
    void afterCallingSetValueWith2GetValueShouldReturn2() {
        //Arrange
        TicTacToeModel model = new TicTacToeModel();
        //Act
        model.setValue(2);
        //Assert
        assertThat(model.getValue()).isEqualTo(2);
    }

}