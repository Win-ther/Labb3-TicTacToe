package se.iths.labb3tictactoe;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBoxNames {
    public static String[] display(String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        String[] playerNames = new String[2];
        Label label = new Label(message);
        TextField textFieldP1 = new TextField();
        textFieldP1.setPromptText("Enter name for player 1");
        TextField textFieldP2 = new TextField();
        textFieldP2.setPromptText("Enter name for player 2");
        Button closeButton = new Button("Confirm");

        closeButton.setOnAction(e -> closeAndTransferNames(playerNames, textFieldP1, textFieldP2, window));
        window.setOnCloseRequest(e -> closeAndTransferNames(playerNames, textFieldP1, textFieldP2, window));

        HBox textFields = new HBox(10);
        textFields.getChildren().addAll(textFieldP1,textFieldP2);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,textFields, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        if (playerNames[0].isEmpty() || playerNames[1].isEmpty()) {
            playerNames[0] = "Player1";
            playerNames[1] = "Player2";
        }

        return playerNames;
    }

    private static void closeAndTransferNames(String[] playerNames, TextField player1Name, TextField player2Name, Stage window) {
        playerNames[0] = player1Name.getText();
        playerNames[1] = player2Name.getText();
        window.close();
    }
}
