package buildings.net.server.parallel;

import buildings.Building;
import buildings.net.server.CostCalculator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SerialThread extends Thread {

    private Socket socket;

    public SerialThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            Building building = (Building) in.readObject();
            out.writeObject(CostCalculator.costCalc(building));
            socket.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Class of a serialized object cannot be found.");
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
