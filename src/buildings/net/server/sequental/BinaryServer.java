package buildings.net.server.sequental;

import buildings.net.server.BuildingUnderArrestException;
import buildings.net.server.CostCalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class BinaryServer {

    private static double[] costsOfTypes = new double[]{1000, 1500, 2000};
    private static Random rand = new Random();

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
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            double cost;
            String input, buildingType;
            StringBuffer buildingStr = new StringBuffer();
            System.out.println("new connection from " + clientSocket.getRemoteSocketAddress());
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
