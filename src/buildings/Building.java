package buildings;

public interface Building {
    int size();
    int numberSpaces();
    double totalSpace();
    int totalNumberRooms();
    Floor[] getFloors();
    Floor getFloor(int index);
    Floor setFloor(int index, Floor floor);
    Space get(int index);
    Space set(int index, Space space);
    boolean add(int index, Space space);
    void remove(int index);
    Space getBestSpace();
    Space[] getSortedSpace();
}
