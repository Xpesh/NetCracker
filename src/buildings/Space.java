package buildings;

import java.io.Serializable;

public interface Space extends Serializable, Cloneable {
    double getSpace();
    void setSpace(double space);
    int getNumberRooms();
    void setNumberRooms(int numberRooms);
    Object clone();
}
