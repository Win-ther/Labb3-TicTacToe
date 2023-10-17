module se.iths.labb3tictactoe {
    requires javafx.controls;
    requires javafx.fxml;


    opens se.iths.labb3tictactoe to javafx.fxml;
    exports se.iths.labb3tictactoe;
}