package buildings.net.server.parallel;

import java.io.IOException;
import java.net.ServerSocket;

public class BinaryServer {

    public static void main (String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(7777);
        } catch (IOException e) {
            System.out.println("IO exception while opening the socket");
            e.printStackTrace();
        }

        Thread thread;
        try {
            while (true) {
                System.out.println("Waiting for connection");
                thread = new BinaryThread(serverSocket.accept());
                thread.start();
                System.out.println("New client");
            }
        } catch (IOException e) {
            System.out.println("I/O error occurs when waiting for a connection.");
            e.printStackTrace();
        }

    }

}