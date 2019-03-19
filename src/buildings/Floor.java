package buildings;

import java.io.Serializable;
import java.util.Iterator;

public interface Floor extends Serializable, Cloneable, Iterable<Space>, Comparable<Floor> {
    int size();
    double totalSpace();
    int totalNumberRooms();
    Space[] getSpaces();
    Space get(int id);
    Space set(int id, Space space);
    boolean add(int id, Space space);
    Space getBestSpace();
    void remove(int id);
    Object clone();
    Iterator iterator();

    @Override
    default int compareTo(Floor o) {
        return this.size()-o.size();
    }
}
