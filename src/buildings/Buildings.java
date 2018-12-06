package buildings;

import buildings.dwelling.DwellingFactory;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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

    public static Floor createFloor(Space ... spaces) {
        return buildingFactory.createFloor(spaces);
    }

    public static Building createBuilding(int floorsCount, int[] spacesCounts) {
        return buildingFactory.createBuilding(floorsCount,spacesCounts);
    }

    public static Building createBuilding(Floor ... floors) {
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

    public static <E extends Space, Floor> void sort(E ... a){
        Sort.quickSort(a,0,a.length);
    }

    public static <E> void sort(Comparator<E> comparator, E... a){
        Sort.quickSort(a,0,a.length, comparator);
    }
    public static <E extends Floor,Space> void sort(E... a){
        Sort.quickSort(a, 0, a.length, Floor::compareTo);
    }

    public static Floor synchronizedFloor (Floor floor){
        return new SynchronizedFloor(floor);
    }

    //10

    public static Space createSpace(double area, Class<? extends Space> type) {
        try {
            Constructor<? extends Space> con = type.getConstructor(double.class);
            return con.newInstance(area);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public static Space createSpace(int roomsCount, double area, Class<? extends Space> type) {
        try {
            Constructor<? extends Space> con = type.getConstructor(double.class, int.class);
            return con.newInstance(area, roomsCount);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public static Floor createFloor(int spacesCount, Class<? extends Floor> type) {
        try {
            Constructor<? extends Floor> con = type.getConstructor(int.class);
            return con.newInstance(spacesCount);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public static Floor createFloor(Class<? extends Floor> type, Space ... spaces) {
        try {
            Constructor<? extends Floor> con = type.getConstructor(Space[].class);
            return con.newInstance(spaces);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public static Building createBuilding(int floorsCount, int[] spacesCounts, Class<? extends Building> type) {
        try {
            Constructor<? extends Building> con = type.getConstructor(int.class, int[].class);
            return con.newInstance(floorsCount, spacesCounts);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public static Building createBuilding(Class<? extends Building> type, Floor... floors) {
        try {
            Constructor<? extends Building> con = type.getConstructor(Floor[].class);
            return con.newInstance(floors);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public static Building readBuilding(Scanner scan,
                                        Class<? extends Space> s,
                                        Class<? extends Floor> f,
                                        Class<? extends Building> b) {
        return readForReflectionMethods(scan,s,f,b);
    }

    public static Building readBuilding(Reader in,
                                        Class<? extends Space> s,
                                        Class<? extends Floor> f,
                                        Class<? extends Building> b) {
        Scanner scan = new Scanner(in);
        return readForReflectionMethods(scan,s,f,b);
    }

    public static Building inputBuilding(InputStream in,
                                         Class<? extends Space> s,
                                         Class<? extends Floor> f,
                                         Class<? extends Building> b) {
        Scanner scan = new Scanner(in);
        return readForReflectionMethods(scan,s,f,b);
    }

    private static Building readForReflectionMethods (
            Scanner s,
            Class<? extends Space> spaceType,
            Class<? extends Floor> floorType,
            Class<? extends Building> buildingType
    ) {
        Building building = createBuilding(0,new int[]{},buildingType);
        s.nextLine();
        String[] spaceData;
        Floor currentFloor;
        int floorCounter = 0;
        while (s.hasNextLine()) {
            spaceData = s.nextLine().split("\\s+");
            building.addFloor(
                    createFloor(0,floorType));
            currentFloor = building.getFloor(floorCounter);
            for (int i = 0; i < spaceData.length; i += 2) {
                currentFloor.add(
                        currentFloor.size(),
                        createSpace(
                                Integer.parseInt(spaceData[i+1]),
                                Double.parseDouble(spaceData[i]),
                                spaceType
                        )

                );
            }
            floorCounter++;
        }
        //s.close();
        return building;
    }

}
