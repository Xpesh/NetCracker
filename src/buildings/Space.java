package buildings;

import java.io.Serializable;

public interface Space extends Serializable, Cloneable, Comparable<Space> {
    double getSpace();
    void setSpace(double space);
    int getNumberRooms();
    void setNumberRooms(int numberRooms);
    Object clone();

    @Override
    default int compareTo(Space o) {
        return (int) (this.getSpace()-o.getSpace());
    }
}
