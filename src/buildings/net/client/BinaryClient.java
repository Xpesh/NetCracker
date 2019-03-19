package buildings.net.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class BinaryClient {

    public static void main (String[] args) {
//        /*
        args = new String[3];
        args[0] = "/Users/tatanasoboleva/IdeaProjects/NetCracker/BinaryCost.txt";
        args[1] = "/Users/tatanasoboleva/IdeaProjects/NetCracker/BinaryBuildings.txt";
        args[2] = "/Users/tatanasoboleva/IdeaProjects/NetCracker/BinaryName";
//        */


        try (
                Socket socket = new Socket("127.0.0.1", 7777);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter outCosts = new PrintWriter(new File(args[0]))
        ) {
            Scanner buildScan = new Scanner(new File(args[1]));
            Scanner typeScan = new Scanner(new File(args[2]));
            String line;
            while (buildScan.hasNextLine()) {
                line = buildScan.nextLine();
                if (line.length() == 0) {
                    out.println("e");
                    out.println(typeScan.nextLine());
                    try {
                        outCosts.println(receiveCost(in));
                    } catch (NumberFormatException e) {
                        outCosts.println("Building is under arrest");
                    }
                    outCosts.flush();
                } else {
                    out.println(line);
                }
            }
            out.println("e");
            out.println(typeScan.nextLine());
            try {
                outCosts.println(receiveCost(in));
            } catch (NumberFormatException e) {
                outCosts.println("Building is under arrest");
            }
            outCosts.flush();

            buildScan.close();
            typeScan.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double receiveCost (BufferedReader in) throws IOException, NumberFormatException {
        String input;
        while (!in.ready()) {}
        while ((input = in.readLine()) != null) {
            return Double.parseDouble(input);
        }
        return -1;
    }

}
