package se.iths.labb3tictactoe;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
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
        }catch (EOFException ef){
            System.out.println("Client ended connection");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void connectToServer() throws IOException {
        System.out.println("Waiting to connect");
        connection = new Socket(InetAddress.getLocalHost(),6789);
        System.out.println("Connected to " + connection.getInetAddress().getHostName());
    }

    private void setupStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        System.out.println("Streams are now setup!");
    }

    private void closeCrap() {
        System.out.println("Closing shit down");
        try {
            output.close();
            input.close();
            connection.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public int whilePlaying(int indexOfBoard) {
        int index = indexOfBoard;
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
}
