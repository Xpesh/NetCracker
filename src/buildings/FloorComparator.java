package buildings;

import java.util.Comparator;

public class FloorComparator implements Comparator<Floor> {
    @Override
    public int compare(Floor o1, Floor o2) {
        return o1.size()-o2.size();
    }
}
