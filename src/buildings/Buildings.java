package buildings;

import buildings.dwelling.Dwelling;
import buildings.dwelling.DwellingFactory;
import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;
import buildings.office.Office;
import buildings.office.OfficeBuilding;
import buildings.office.OfficeFloor;

import java.io.*;
import java.util.Comparator;
import java.util.Scanner;

public class Buildings implements Serializable{

    private static BuildingFactory buildingFactory = new DwellingFactory();

    public static void setBuildingFactory(BuildingFactory buildingFactory) {
        Buildings.buildingFactory = buildingFactory;
    }

    public static Space createSpace(double area) {
        return buildingFactory.createSpace(area);
    }

    public static Space createSpace(int roomsCount, double area) {
        return buildingFactory.createSpace(roomsCount,area);
    }

    public static Floor createFloor(int spacesCount) {
        return buildingFactory.createFloor(spacesCount);
    }

    public static Floor createFloor(Space[] spaces) {
        return buildingFactory.createFloor(spaces);
    }

    public static Building createBuilding(int floorsCount, int[] spacesCounts) {
        return buildingFactory.createBuilding(floorsCount,spacesCounts);
    }

    public static Building createBuilding(Floor[] floors) {
        return buildingFactory.createBuilding(floors);
    }

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
    public static Building readBuilding(Scanner s){
        try {
            return parseBuilding(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Building parseBuilding(Scanner scanner) throws IOException {
        Building building;
        switch (scanner.next()){
            case "OfficeBuilding" :{
                scanner.next();
//                building = new OfficeBuilding(parseFloors(scanner, scanner.nextInt()));
                break;
            }
            case "Dwelling" :{
                scanner.next();
//                building = new Dwelling(parseFloors(scanner, scanner.nextInt()));
                break;
            }
            default: throw new IOException();
        }
        building = createBuilding(parseFloors(scanner,scanner.nextInt()));
        return building;
    }
    private static Floor[] parseFloors(Scanner scanner, int size) throws IOException {
        Floor[] floors = new Floor[size];
        for (int i=0; i<size;i++) {
            scanner.next();
            switch (scanner.next()) {
                case "OfficeFloor" :{
                    scanner.next();
//                    floors[i] = new OfficeFloor(parseSpaces(scanner, scanner.nextInt()));
                    floors[i]=createFloor(parseSpaces(scanner,scanner.nextInt()));
                    scanner.next();
                    break;
                }
                case "DwellingFloor" :{
                    scanner.next();
//                    floors[i] = new DwellingFloor(parseSpaces(scanner, scanner.nextInt()));
                    floors[i]=createFloor(parseSpaces(scanner,scanner.nextInt()));
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
//                    spaces[i] = new Office(area, numberRooms);
                    spaces[i] = createSpace(numberRooms,area);
                    scanner.next();
                    break;
                }
                case "Flat" :{
                    scanner.next();
                    int numberRooms = scanner.nextInt();
                    scanner.next();
                    double area = scanner.nextDouble();
//                    spaces[i] = new Flat(area, numberRooms);
                    spaces[i] = createSpace(numberRooms,area);
                    scanner.next();
                    break;
                }
                default: throw new IOException();
            }
        }
        return spaces;
    }

    public static void serializeBuilding(Building building, OutputStream sout) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(sout);
        out.writeObject(building);
    }

    public static Building deserializeBuilding(InputStream in) {
        try (ObjectInputStream ois = new ObjectInputStream(in)) {
            return (Building)ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <E extends Space, Floor> void sort(E[] a){
        Sort.quickSort(a,0,a.length);
    }
    public static <E> void sort(E[] a, Comparator<E> comparator){
        Sort.quickSort(a,0,a.length, comparator);
    }

}
