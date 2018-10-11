package buildings;

public class Flat implements Space{
    private double space;
    private int numberRooms;
    private static final int DEFAULT_NUMBER_ROOMS=2;
    private static final int DEFAULT_SPACE =50;

    public Flat(double space, int numberRooms) {
        if(space<=0){
            throw new InvalidSpaceAreaException();
        }
        if(numberRooms<1){
            throw new InvalidRoomsCountException();
        }
        this.space = space;
        this.numberRooms = numberRooms;
    }

    public Flat() {
        this(DEFAULT_SPACE,DEFAULT_NUMBER_ROOMS);
    }

    public Flat(double space) {
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
        final StringBuilder sb = new StringBuilder("\nFlat{");
        sb.append("space=").append(space);
        sb.append(", numberRooms=").append(numberRooms);
        sb.append('}');
        return sb.toString();
    }
}
