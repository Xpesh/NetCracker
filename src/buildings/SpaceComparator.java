package buildings;

import java.util.Comparator;

public class SpaceComparator implements Comparator<Space> {

    @Override
    public int compare(Space o1, Space o2) {
        return (int) (o1.getSpace()-o2.getSpace());
    }
}
