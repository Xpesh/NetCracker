package buildings;

import java.util.Arrays;

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
            if(dwellingFloor!=null){
                count+=dwellingFloor.size();
            }
        }
        return count;
    }

    public double totalSpace(){
        double count = 0;
        for(DwellingFloor dwellingFloor : dwellingFloors){
            if(dwellingFloor!=null){
                count+=dwellingFloor.totalSpace();
            }
        }
        return count;
    }

    public int totalNumberRooms(){
        int count = 0;
        for(DwellingFloor dwellingFloor : dwellingFloors){
            if(dwellingFloor!=null){
                count+=dwellingFloor.totalNumberRooms();
            }
        }
        return count;
    }

    public DwellingFloor[] getDwellingFloors() {
        return dwellingFloors;
    }

    public DwellingFloor getDwellingFloor(int index){
        if(index>size()){
            throw new FloorIndexOutOfBoundsException();
        }
        return dwellingFloors[index];
    }

    public void setDwellingFloor(int index, DwellingFloor dwellingFloor){
        if(index>size()){
            throw new FloorIndexOutOfBoundsException();
        }
        dwellingFloors[index]=dwellingFloor;
    }

    public Flat get(int index){
        if(index>numberFlats()){
            throw new SpaceIndexOutOfBoundsException();
        }
        for(int i=0;i<size();i++){
            if(index >= dwellingFloors[i].size())
                index-=dwellingFloors[i].size();
            else
                return dwellingFloors[i].get(index);
        }
        return null;
    }

    public void set(int index, Flat flat){
        if(index>=numberFlats())
            throw new SpaceIndexOutOfBoundsException();
        for(int i=0;i<size();i++){
            if(index >= dwellingFloors[i].size())
                index-=dwellingFloors[i].size();
            else {
                dwellingFloors[i].set(index, flat);
                return;
            }
        }
    }

    public void add(int index, Flat flat){
        if(index>numberFlats())
            throw new SpaceIndexOutOfBoundsException();
        for(int i=0;i<size();i++){
            if(index >= dwellingFloors[i].size())
                index-=dwellingFloors[i].size();
            else {
                dwellingFloors[i].add(index, flat);
                return;
            }
        }
    }

    public void remove(int index){
        if(index>=numberFlats())
            throw new SpaceIndexOutOfBoundsException();
        for(int i=0;i<size();i++){
            if(index >= dwellingFloors[i].size())
                index-=dwellingFloors[i].size();
            else {
                dwellingFloors[i].remove(index);
                return;
            }
        }
    }



    public Flat getBestSpace(){
        Flat flat, bestFlat = dwellingFloors[0].getBestSpace();
        for(int i=1;i<size();i++){
            flat = dwellingFloors[i].getBestSpace();
            if(bestFlat.getSpace()<flat.getSpace()){
                bestFlat=flat;
            }
        }
        return bestFlat;
    }

    public Flat[] getSortedFlat () {
        Flat[] arr = new Flat[numberFlats()];
        Flat[] toAdd;
        int c = 0;
        for (int i = 0; i < dwellingFloors.length; i++) {
            toAdd = dwellingFloors[i].getFlats();
            System.arraycopy(toAdd, 0, arr, c, toAdd.length);
            c += toAdd.length;
        }
        quickSort(arr,0,arr.length-1);
        return arr;
    }

    private static void quickSort(Flat[] a, int first, int last) {
        int i = first;
        int j = last;
        double x = a[(first + last) / 2].getSpace();
        Flat temp;
        do {
            while (a[i].getSpace() < x) i++;
            while (a[j].getSpace() > x) j--;
            if (i <= j) {
                if (i < j) {
                    temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
                i++;
                j--;
            }
        } while (i <= j);
        if (i < last)
            quickSort(a, i, last);
        if (first < j)
            quickSort(a, first, j);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Dwelling{");
        sb.append("dwellingFloors=").append(Arrays.toString(dwellingFloors));
        sb.append('}');
        return sb.toString();
    }
}

