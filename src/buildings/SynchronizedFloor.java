package buildings;

import java.util.Iterator;

public class SynchronizedFloor implements Floor {
    private Floor floor;

    public SynchronizedFloor(Floor floor) {
        this.floor = floor;
    }

    @Override
    public synchronized int size() {
        return floor.size();
    }

    @Override
    public synchronized double totalSpace() {
        return floor.totalSpace();
    }

    @Override
    public synchronized int totalNumberRooms() {
        return floor.totalNumberRooms();
    }

    @Override
    public synchronized Space[] getSpaces() {
        return floor.getSpaces();
    }

    @Override
    public synchronized Space get(int id) {
        return floor.get(id);
    }

    @Override
    public synchronized Space set(int id, Space space) {
        return floor.set(id,space);
    }

    @Override
    public synchronized boolean add(int id, Space space) {
        return floor.add(id,space);
    }

    @Override
    public synchronized Space getBestSpace() {
        return floor.getBestSpace();
    }

    @Override
    public synchronized void remove(int id) {
        floor.remove(id);
    }

    @Override
    public synchronized Object clone() {
        return floor.clone();
    }

    @Override
    public synchronized boolean equals(Object o) {
        return floor.equals(o);
    }

    @Override
    public synchronized int hashCode() {
        return floor.hashCode();
    }

    @Override
    public synchronized String toString() {
        return floor.toString();
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException("Not supported.");
    }
}
