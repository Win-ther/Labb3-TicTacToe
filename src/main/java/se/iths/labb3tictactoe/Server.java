package se.iths.labb3tictactoe;

import javafx.application.Platform;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ServerSocket server;
    private Socket connection;
    private boolean isUp = false;

    public Server() {
        try {
            server = new ServerSocket(6789);
        } catch (IOException e) {
            System.out.println("Server down bro");
        }
    }

    public void startRunning() {
        try {
            try {
                waitForConnection();
                setupStreams();
                Thread.ofVirtual().start(this::startListenerForButtonsPress);
            } catch (EOFException e) {
                System.out.println("Server ended connection");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForConnection() throws IOException {
        System.out.println("Waiting to connect");
        connection = server.accept();
        System.out.println("Connected to " + connection.getInetAddress().getHostName());
    }

    private void setupStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        System.out.println("Streams are now setup!");
    }

    public void closeCrap() {
        System.out.println("Closing connections");
        isUp = false;
        try {
            if (output != null)
                output.close();
            if (input != null)
                input.close();
            if (connection != null)
                connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendGameOver(String gameOver) {
        try {
            if (output != null) {
                output.writeObject(gameOver);
                output.flush();
            }
        } catch (IOException e) {
            System.out.println("Could not send gameOver");
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

    public void startListenerForButtonsPress() {
        while (true) {
            try {
                int index = (Integer) input.readObject();
                System.out.println(index);
                Platform.runLater(() -> TicTacToeController.player2ClickedSetSymbol(index));
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
                closeCrap();
                break;
            }
        }
    }

    public boolean isUp() {
        return isUp;
    }
}
