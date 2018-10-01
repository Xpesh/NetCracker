package buildings;

public class Dwelling {
    private DwellingFloor[] dwellingFloors;

    public Dwelling(DwellingFloor[] dwellingFloors) {
        this.dwellingFloors = dwellingFloors;
    }

    public Dwelling(int numberDwellingFloor, int[] sizeDwellingFloor) {
        dwellingFloors = new DwellingFloor[numberDwellingFloor];
        for(int i=0;i<numberDwellingFloor;i++){
            dwellingFloors[i] = new DwellingFloor(sizeDwellingFloor[i]);
        }
    }

    public int size(){
        return dwellingFloors.length;
    }

    public int numberFlats(){
        int count = 0;
        for(DwellingFloor dwellingFloor : dwellingFloors){
            if(dwellingFloor!=null){//todo или лучше тернарный оператор
                count+=dwellingFloor.size();
            }
        }
        return count;
    }

    public double summArea(){
        double count = 0;
        for(DwellingFloor dwellingFloor : dwellingFloors){
            if(dwellingFloor!=null){//todo или лучше тернарный оператор
                count+=dwellingFloor.totalArea();
            }
        }
        return count;
    }

    public int totalNumberRooms(){
        int count = 0;
        for(DwellingFloor dwellingFloor : dwellingFloors){
            if(dwellingFloor!=null){//todo или лучше тернарный оператор
                count+=dwellingFloor.totalNumberRooms();
            }
        }
        return count;
    }

    public DwellingFloor[] getDwellingFloors() {
        return dwellingFloors;
    }

    public DwellingFloor getDwellingFloor(int index){
        return dwellingFloors[index];
    }

    public void setDwellingFloor(int index, DwellingFloor dwellingFloor){
        dwellingFloors[index]=dwellingFloor;
    }

    public Flat getFlat(int index){
        for(int i=0;i<size();i++){
            if(index >= dwellingFloors[i].size())
                index-=dwellingFloors[i].size();
            else
                return dwellingFloors[i].getFlat(index);
        }
        return null;
    }

    public void setFlat(int index, Flat flat){
        for(int i=0;i<size();i++){
            if(index >= dwellingFloors[i].size())
                index-=dwellingFloors[i].size();
            else
                dwellingFloors[i].setFlat(index,flat);
        }
    }

    public void addFlat(int index, Flat flat){
        for(int i=0;i<size();i++){
            if(index >= dwellingFloors[i].size())
                index-=dwellingFloors[i].size();
            else
                dwellingFloors[i].addFlat(index,flat);
        }
    }

    public void removeFlat(int index, Flat flat){
        for(int i=0;i<size();i++){
            if(index >= dwellingFloors[i].size())
                index-=dwellingFloors[i].size();
            else
                dwellingFloors[i].removeFlat(index);
        }
    }

    public Flat getBestSpace(){
        Flat flat, bestFlat = dwellingFloors[0].getBestSpace();
        for(int i=1;i<size();i++){
            flat = dwellingFloors[i].getBestSpace();
            if(bestFlat.getArea()<flat.getArea()){
                bestFlat=flat;
            }
        }
        return bestFlat;
    }

    public Flat[] flatsSorted(){
        Flat[] flats = new Flat[numberFlats()];
        int count=0;
        for(DwellingFloor dwellingFloor : dwellingFloors){
            System.arraycopy(dwellingFloor.getFlats(), 0,flats,count,dwellingFloor.size());
            count+=dwellingFloor.size();
        }
        return new QuickSort(flats,0,flats.length-1).sort();
    }




}
class QuickSort{
    private Flat[] flats;
    private int startIndex;
    private int endIndex;

    public QuickSort(Flat[] flats, int startIndex, int endIndex) {
        this.flats = flats;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public Flat[] sort(){
        doSort(startIndex, endIndex);
        return flats;
    }

    private void doSort(int start, int end) {
        if (start >= end)
            return;
        int i = start, j = end;
        Flat temp;
        int cur = i - (i - j) / 2;
        while (i < j) {
            while (i < cur && (flats[i].getArea() <= flats[cur].getArea())) {
                i++;
            }
            while (j > cur && (flats[cur].getArea() <= flats[j].getArea())) {
                j--;
            }
            if (i < j) {
                temp = flats[i];
                flats[i] = flats[j];
                flats[j] = temp;
                if (i == cur)
                    cur = j;
                else if (j == cur)
                    cur = i;
            }
        }
        doSort(start, cur);
        doSort(cur + 1, end);
    }

}
