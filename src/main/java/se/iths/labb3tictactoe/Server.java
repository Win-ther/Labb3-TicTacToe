package se.iths.labb3tictactoe;

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

    /*
    Vad behöver jag skicka över från Server till Client och tillbaks.
    ToDo:
        1. Skicka vart den nya symbolen lades ut!
        * Skicka gameOver (Eller det kanske räcker med att kolla detta på både klienten och servern, då behöver inte denna skickas. Poäng och winnerText behöver inte heller
        skickas då)
        * Skicka poäng (kanske inte)
        * Skicka winnerText (kanske inte)
    */

    public Server() {
        try {
            server = new ServerSocket(6789, 10);
        } catch (IOException e) {
            System.out.println("Server down bro");
        }
    }
    public void startRunning(){
        isUp = true;
        try {
            while(true){
                try {
                    waitForConnection();
                    setupStreams();
                }catch (EOFException e){
                    System.out.println("Server ended connection");
                }
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

    public int whilePlaying(int indexOfBoard) {
        int index = indexOfBoard;
        System.out.println("You are now connected");
        sendSymbolIndex(index);

        try {
            index = (int) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Something wrong with incoming index");
        }

        return index;
    }

    private void sendSymbolIndex(int index) {
        try {
            output.writeObject(index);
            output.flush();
        } catch (IOException e) {
            System.out.println("Could not send index");
        }
    }

    public boolean isUp() {
        return isUp;
    }
}
