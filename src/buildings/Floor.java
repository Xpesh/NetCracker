package buildings;

public interface Floor {
    int size();
    double totalSpace();
    int totalNumberRooms();
    Space[] getSpaces();
    Space get(int id);
    Space set(int id, Space space);
    boolean add(int id, Space space);
    Space getBestSpace();
    void remove(int id);
}
