package buildings;

import java.io.Serializable;

public interface Space extends Serializable {
    double getSpace();
    void setSpace(double space);
    int getNumberRooms();
    void setNumberRooms(int numberRooms);
}
