package buildings.net.server.parallel;

import buildings.net.server.BuildingUnderArrestException;
import buildings.net.server.CostCalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BinaryThread extends Thread{

    private Socket socket;

    public BinaryThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            double cost;
            String input, buildingType;
            StringBuilder buildingStr = new StringBuilder();
            while ((input = in.readLine()) != null) {
                if (input.equals("e")) {
                    buildingType = in.readLine();
                    try {
                        cost = CostCalculator.costCalc(buildingStr.toString(), buildingType);
                        out.write(String.valueOf(cost));
                    } catch (BuildingUnderArrestException e) {
                        out.write(e.toString());
                    }
                    out.println("");
                    out.flush();
                    buildingStr.delete(0,buildingStr.length());
                } else {
                    buildingStr.append(input).append("\n");
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
