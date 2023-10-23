package se.iths.labb3tictactoe;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TicTacToeServer {
    ServerSocket serverSocket;

    public TicTacToeServer() {
        try {
            serverSocket = new ServerSocket(1234);
        } catch (IOException e) {
            System.out.println("Could not listen on port 1234");
            System.exit(-1);
        }
        Thread.ofVirtual().start(() -> {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client Connected");
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
