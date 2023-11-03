package se.iths.labb3tictactoe;

import javafx.application.Platform;

import java.io.*;
import java.net.Socket;

public class Client {

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private final String serverIP;
    private Socket connection;

    public Client(String host) {
        serverIP = host;
    }
    public void startRunning(){
        try {
            connectToServer();
            setupStreams();
            Thread.ofVirtual().start(this::startListenerForButtonsPressAndGameOver);
        }catch (EOFException ef){
            System.out.println("Client ended connection");
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private void connectToServer() throws IOException {
        System.out.println("Waiting to connect");
        connection = new Socket(serverIP,6789);
        System.out.println("Connected to " + connection.getInetAddress().getHostName());
    }

    private void setupStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        System.out.println("Streams are now setup!");
    }

    public void startListenerForButtonsPressAndGameOver() {
        while(true) {
            Object obj;
            try {
                obj = input.readObject();
                if (obj == null)
                    return;
                if (obj instanceof Integer){
                    int index = (int) obj;
                    Platform.runLater(() -> ClientController.player1ClickedSetSymbol(index));
                }else if (obj instanceof String gameOver){
                    Platform.runLater(() -> ClientController.gotGameOverFromServerNowSettingIt(gameOver));
                }

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Connection closed");
                System.out.println(e.getMessage());
                closeCrap();
                break;
            }
        }
    }


    private void closeCrap() {
        System.out.println("Closing shit down");
        try {
            output.close();
            input.close();
            connection.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void sendSymbolIndex(int index) {
        try {
            output.writeObject(index);
            output.flush();
        } catch (IOException e) {
            System.out.println("Could not send index");
        }
    }
}
