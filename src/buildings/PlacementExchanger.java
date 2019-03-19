package buildings;

public class PlacementExchanger {

    public static boolean isExchange(Space space1, Space space2){
        return space1.getSpace()==space2.getSpace() && space1.getNumberRooms()==space2.getNumberRooms();
    }

    public static boolean isExchange(Floor floor1, Floor floor2){
        return floor1.totalSpace()==floor2.totalSpace() && floor1.totalNumberRooms()==floor2.totalNumberRooms();
    }

    public static void exchange(Floor floor1, int index1, Floor floor2, int index2) throws InexchangeableSpacesException {
        if(index1<0 || index2<0 || index1>=floor1.size() || index2>=floor2.size() ){
            throw new FloorIndexOutOfBoundsException();
        }
        if(isExchange(floor1,floor2)){
            throw new InexchangeableSpacesException();
        }
    }
    public static void exchange(Building building1, int index1, Building building2, int index2) throws InexchangeableFloorsException {
        if(index1<0 || index2<0 || index1>=building1.size() || index2>=building2.size() ){
            throw new FloorIndexOutOfBoundsException();
        }
        if(isExchange(building1.getFloor(index1),building2.getFloor(index2))){
            throw new InexchangeableFloorsException();
        }
    }
}
