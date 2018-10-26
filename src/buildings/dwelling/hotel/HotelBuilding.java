package buildings.dwelling.hotel;

import buildings.Floor;
import buildings.Space;
import buildings.dwelling.Dwelling;

import java.util.Arrays;

public class HotelBuilding  extends Dwelling{

    public HotelBuilding(Floor[] floors) {
        super(floors);
    }

    public HotelBuilding(int numberFloor, int[] sizeFloor) {
        super(numberFloor, sizeFloor);
    }

    public int getStars() {
        int bestStarFloor=0;
        int starFlor;
        for(Floor floor : super.getFloors()){
            if(floor instanceof HotelFloor){
                starFlor = ((HotelFloor)floor).getStars();
                if(bestStarFloor<starFlor){
                    bestStarFloor=starFlor;
                }
            }
        }
        return bestStarFloor;
    }

    @Override
    public Space getBestSpace(){
        Floor[] floors = getFloors();
        Space space, bestSpace = floors[0].getBestSpace();
        for(int i=1;i<size();i++){
            space = floors[i].getBestSpace();
            if(bestSpace.getSpace()*coeff(floors[i])<space.getSpace()*coeff(floors[i])){
                bestSpace=space;
            }
        }
        return bestSpace;
    }

    private double coeff(Floor floor) {
        if(!(floor instanceof HotelFloor)) return 0;
        switch (((HotelFloor)floor).getStars()){
            case 1 : return 0.25;
            case 2 : return 0.5;
            case 3 : return 1;
            case 4 : return 1.25;
            case 5 : return 1.5;
            default: return 0;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Dwelling ( ").append(getStars()).append(" , ").append(size());
        for(Floor floor : getFloors()){
            sb.append(" , ").append(floor);
        }
        return sb.append(" )").toString();
    }

    @Override
    public boolean equals(Object o) {
        if(this==o) return true;
        if(! (o instanceof HotelBuilding)) return false;
        HotelBuilding hotelBuilding = (HotelBuilding) o;
        if (size()!=hotelBuilding.size()) return false;
        return containsAll(Arrays.asList(hotelBuilding));
    }
}
