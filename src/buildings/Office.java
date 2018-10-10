package buildings;

public class Office {
    private double area;
    private int numberRooms;
    private static final int DEFAULT_NUMBER_ROOMS=1;
    private static final int DEFAULT_AREA=250;

    public Office(double area, int numberRooms) {
        this.area = area;
        this.numberRooms = numberRooms;
    }

    public Office() {
        this(DEFAULT_AREA,DEFAULT_NUMBER_ROOMS);
    }

    public Office(double area) {
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
        final StringBuilder sb = new StringBuilder("Office{");
        sb.append("area=").append(area);
        sb.append(", numberRooms=").append(numberRooms);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Office office = (Office) o;

        if (Double.compare(office.area, area) != 0) return false;
        return numberRooms == office.numberRooms;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(area);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + numberRooms;
        return result;
    }
}
