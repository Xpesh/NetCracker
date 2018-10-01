package buildings;

public class Flat {
    private double area;
    private int numberRooms;
    private static final int DEFAULT_NUMBER_ROOMS=2;
    private static final int DEFAULT_AREA=50;

    public Flat(double area, int numberRooms) {
        this.area = area;
        this.numberRooms = numberRooms;
    }

    public Flat() {
        this(DEFAULT_AREA,DEFAULT_NUMBER_ROOMS);
    }

    public Flat(double area) {
        this(area,DEFAULT_NUMBER_ROOMS);
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getNumberRooms() {
        return numberRooms;
    }

    public void setNumberRooms(int numberRooms) {
        this.numberRooms = numberRooms;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Flat{");
        sb.append("area=").append(area);
        sb.append(", numberRooms=").append(numberRooms);
        sb.append('}');
        return sb.toString();
    }
}
