module se.iths.labb3tictactoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;


    opens se.iths.labb3tictactoe to javafx.fxml;
    exports se.iths.labb3tictactoe;
}