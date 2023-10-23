module se.iths.labb3tictactoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens se.iths.labb3tictactoe to javafx.fxml;
    exports se.iths.labb3tictactoe;
    exports se.iths.labb3tictactoe.ClientMVC;
    opens se.iths.labb3tictactoe.ClientMVC to javafx.fxml;
}