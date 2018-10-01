package buildings;

public class DwellingFloor {
    private Flat[] flats;

    public DwellingFloor(Flat[] flats) {
        this.flats = flats;
    }

    public DwellingFloor(int numberFlats) {
        this.flats = new Flat[numberFlats];
    }

    public int size(){
        return flats.length;
    }

    public double totalArea(){
        double count=0;
        for(Flat flat : flats){
            count+=flat.getArea();
        }
        return count;
    }

    public double totalNumberRooms(){
        double count=0;
        for(Flat flat : flats){
            if (flat != null) count += flat.getNumberRooms();
        }
        return count;
    }

    public Flat[] getFlats() {
        return flats;
    }

    public Flat getFlat(int index){
        return flats[index];
    }

    public void setFlat(int index, Flat flat){
        flats[index]=flat;
    }

    public void addFlat(int index, Flat flat){
        if(index>size()){
            Flat[] bufFlats = new Flat[index*2];
            System.arraycopy(flats,0,bufFlats,0,size());
            flats=bufFlats;
        }
        flats[index]=flat;
    }

    public void removeFlat(int index){
        flats[index]=null;
    }

    public Flat getBestSpace(){
        Flat flat, bestFlat = flats[0];
        for(int i=1;i<size();i++){
            flat = flats[i];
            if(flat!=null && bestFlat.getArea()<flat.getArea())
                bestFlat=flat;
        }
        return bestFlat;
    }
}
