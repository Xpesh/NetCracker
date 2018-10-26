package buildings;

import java.io.Serializable;

public class Flat implements Space,Serializable{
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
        return new StringBuilder("Flat ( ").append(numberRooms).append(" , ").append(space).append(" )").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flat)) return false;
        Flat flat = (Flat) o;
        if (Double.compare(flat.space, space) != 0) return false;
        return numberRooms == flat.numberRooms;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(space) ^ numberRooms;
    }

    @Override
    public Object clone() {
        return new Flat(space, numberRooms);
    }
}
