package buildings;

import java.util.Arrays;

public class DwellingFloor {
    private Flat[] flats;

    public DwellingFloor(Flat[] flats) {
        this.flats = flats;
    }

    public DwellingFloor(int numberFlats) {
        this.flats = new Flat[numberFlats];
        for(int i =0;i<numberFlats;i++){
            flats[i]=new Flat();
        }
    }

    public int size(){
        return flats.length;
    }

    public double totalSpace(){
        double count=0;
        for(Flat flat : flats){
            count+=flat.getSpace();
        }
        return count;
    }

    public int totalNumberRooms(){
        int count=0;
        for(Flat flat : flats){
            count += flat.getNumberRooms();
        }
        return count;
    }

    public Flat[] getFlats() {
        return flats;
    }

    public Flat get(int index){
        if(index>=size())
            throw new SpaceIndexOutOfBoundsException();
        return flats[index];
    }

    public void set(int index, Flat flat){
        flats[index]=flat;
    }

    public void add(int index, Flat flat){
        if(index>size()){
            Flat[] bufFlats = new Flat[size()+1];
            System.arraycopy(flats,0,bufFlats,0,size());
            flats=bufFlats;
        }
        flats[index]=flat;
    }

    public void remove(int index){
        Flat[] bufFlats = new Flat[flats.length-1];
        System.arraycopy(flats,index+1,bufFlats,index,flats.length-index-1);
        System.arraycopy(flats,0,bufFlats,0,index);
        flats=bufFlats;
    }

    public Flat getBestSpace(){
        Flat bestFlat = flats[0];
        for(Flat flat : flats){
            if(bestFlat.getSpace()<flat.getSpace())
                bestFlat=flat;
        }
        return bestFlat;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DwellingFloor{");
        sb.append("flats=").append(Arrays.toString(flats));
        sb.append('}');
        return sb.toString();
    }
}
