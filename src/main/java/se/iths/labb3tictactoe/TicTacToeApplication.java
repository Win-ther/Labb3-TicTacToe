package se.iths.labb3tictactoe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TicTacToeApplication extends Application {
    public static Stage window;
    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApplication.class.getResource("tictactoe-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        window.setTitle("Tic Tac Toe!");
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        launch();
    }
    public static void exitWindow(){
        window.close();
    }
}