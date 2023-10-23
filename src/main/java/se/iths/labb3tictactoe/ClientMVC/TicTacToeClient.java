package se.iths.labb3tictactoe.ClientMVC;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.iths.labb3tictactoe.TicTacToeApplication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class TicTacToeClient extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String serverAdress = "localhost";
        int port = 1234;
        try {
            Socket socket = new Socket(serverAdress,port);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to server");
        } catch (IOException e) {
            System.out.println("Could not connect to server");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeApplication.class.getResource("client-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Tic Tac Toe!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
