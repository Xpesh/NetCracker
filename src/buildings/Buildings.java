package buildings;

import java.io.*;
import java.util.Scanner;

public class Buildings {

    public static void outputBuilding (Building building, OutputStream out) {
        try (DataOutputStream dos = new DataOutputStream(out)) {
            byte[] values = building.toString().getBytes();
            for (byte v : values) {
                dos.write(v);
            }
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Building inputBuilding (InputStream in){
        Building building = null;
        try {
            building  = parseBuilding(new Scanner(in));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return building;
    }

    public static void writeBuilding (Building building, Writer out) {
        try {
            out.write(building.toString().toCharArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Building readBuilding (Reader in) {
        Building building = null;
        try {
            building  = parseBuilding(new Scanner(in));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return building;
    }

    private static Building parseBuilding(Scanner scanner) throws IOException {
        Building building;
        switch (scanner.next()){
            case "OfficeBuilding" :{
                scanner.next();
                building = new OfficeBuilding(parseFloors(scanner, scanner.nextInt()));
                break;
            }
            case "Dwelling" :{
                scanner.next();
                building = new Dwelling(parseFloors(scanner, scanner.nextInt()));
                break;
            }
            default: throw new IOException();
        }
        return building;
    }
    private static Floor[] parseFloors(Scanner scanner, int size) throws IOException {
        Floor[] floors = new Floor[size];
        for (int i=0; i<size;i++) {
            scanner.next();
            switch (scanner.next()) {
                case "OfficeFloor" :{
                    scanner.next();
                    floors[i] = new OfficeFloor(parseSpaces(scanner, scanner.nextInt()));
                    scanner.next();
                    break;
                }
                case "DwellingFloor" :{
                    scanner.next();
                    floors[i] = new DwellingFloor(parseSpaces(scanner, scanner.nextInt()));
                    scanner.next();
                    break;
                }
                default: throw new IOException();
            }
        }
        return floors;
    }
    private static Space[] parseSpaces(Scanner scanner, int size) throws IOException {
        Space[] spaces = new Space[size];
        for (int i=0; i<size;i++) {
            scanner.next();
            switch (scanner.next()) {
                case "Office" :{
                    scanner.next();
                    int numberRooms = scanner.nextInt();
                    scanner.next();
                    double area = scanner.nextDouble();
                    spaces[i] = new Office(area, numberRooms);
                    scanner.next();
                    break;
                }
                case "Flat" :{
                    scanner.next();
                    int numberRooms = scanner.nextInt();
                    scanner.next();
                    double area = scanner.nextDouble();
                    spaces[i] = new Flat(area, numberRooms);
                    scanner.next();
                    break;
                }
                default: throw new IOException();
            }
        }
        return spaces;
    }

    public static void serializeBuilding(Building building, OutputStream out) throws IOException {
        ObjectOutputStream oos = (ObjectOutputStream) out;
        oos.writeObject(building);
    }

    public static Building deserializeBuilding(InputStream in) {
        try (ObjectInputStream ois = new ObjectInputStream(in)) {
            return (Building)ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
