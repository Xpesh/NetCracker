package buildings;

public class Office {
    private double space;
    private int numberRooms;
    private static final int DEFAULT_NUMBER_ROOMS=1;
    private static final int DEFAULT_SPACE =250;

    public Office(double space, int numberRooms) {
        if(space<=0){
            throw new InvalidSpaceAreaException();
        }
        if(numberRooms<1){
            throw new InvalidRoomsCountException();
        }
        this.space = space;
        this.numberRooms = numberRooms;
    }

    public Office() {
        this(DEFAULT_SPACE,DEFAULT_NUMBER_ROOMS);
    }

    public Office(double space) {
        this(space,DEFAULT_NUMBER_ROOMS);
    }

    public double getSpace() {
        return space;
    }

    public void setSpace(double space) {
        this.space = space;
    }

    public int getNumberRooms() {
        return numberRooms;
    }

    public void setNumberRooms(int numberRooms) {
        this.numberRooms = numberRooms;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Office{");
        sb.append("space=").append(space);
        sb.append(", numberRooms=").append(numberRooms);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Office office = (Office) o;

        if (Double.compare(office.space, space) != 0) return false;
        return numberRooms == office.numberRooms;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(space);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + numberRooms;
        return result;
    }
}
