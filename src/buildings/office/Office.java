package buildings.office;

import buildings.InvalidRoomsCountException;
import buildings.InvalidSpaceAreaException;
import buildings.Space;

import java.io.Serializable;

public class Office implements Space, Serializable{
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
        return new StringBuilder("Office ( ").append(numberRooms).append(" , ").append(space).append(" )").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Office) return false;
        Office office = (Office) o;
        if (Double.compare(office.space, space) != 0) return false;
        return numberRooms == office.numberRooms;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(space) ^ numberRooms;
    }

    @Override
    public Object clone() {
        return new Office(space, numberRooms);
    }
}
