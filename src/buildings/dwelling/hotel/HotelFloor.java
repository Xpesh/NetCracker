package buildings.dwelling.hotel;

import buildings.Space;
import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;

import java.util.Arrays;

public class HotelFloor extends DwellingFloor {
    private int stars;
    private static final int DEFAULT_STARS = 1;

    public HotelFloor(int numOfFlat) {
        super(numOfFlat);
        this.stars = DEFAULT_STARS;
    }

    public HotelFloor(Space[] spaces) {
        super(spaces);
        this.stars = DEFAULT_STARS;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DwellingFloor ( ").append(stars).append(" , ").append(size());
        for(Space space : getSpaces()){
            sb.append(" , ").append(space);
        }
        return sb.append(" )").toString();
    }

    @Override
    public boolean equals(Object o) {
        if(this==o) return true;
        if(! (o instanceof HotelFloor)) return false;
        HotelFloor hotelFloor = (HotelFloor) o;
        if(stars!=hotelFloor.stars) return false;
        if (size()!=hotelFloor.size()) return false;
        return containsAll(Arrays.asList(hotelFloor));
    }

    @Override
    public int hashCode() {
        return super.hashCode()^stars;
    }
}
