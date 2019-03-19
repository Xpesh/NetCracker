package buildings.net.server.sequental;

import buildings.Building;
import buildings.net.server.CostCalculator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SerialServer {

    public static void main (String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(7777);
        } catch (IOException e) {
            System.out.println("IO exception while opening the socket");
            e.printStackTrace();
        }
        try (
                Socket clientSocket = serverSocket.accept();
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            Building building = (Building) in.readObject();
            out.writeObject(CostCalculator.costCalc(building));
        } catch (ClassNotFoundException e) {
            System.out.println("Class of a serialized object cannot be found.");
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
