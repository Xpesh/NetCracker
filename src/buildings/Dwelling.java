package buildings;

import java.util.Arrays;

public class Dwelling implements Building {
    private Floor[] floors;

    public Dwelling(Floor[] floors) {
        this.floors = floors;
    }

    public Dwelling(int numberFloor, int[] sizeFloor) {
        floors = new Floor[numberFloor];
        for(int i=0;i<numberFloor;i++){
            floors[i] = new DwellingFloor(sizeFloor[i]);
        }
    }

    public int size(){
        return floors.length;
    }

    public int numberSpaces(){
        int count = 0;
        for(Floor floor : floors){
            if(floor!=null){
                count+=floor.size();
            }
        }
        return count;
    }

    public double totalSpace(){
        double count = 0;
        for(Floor floor : floors){
            if(floor!=null){
                count+=floor.totalSpace();
            }
        }
        return count;
    }

    public int totalNumberRooms(){
        int count = 0;
        for(Floor floor : floors){
            if(floor!=null){
                count+=floor.totalNumberRooms();
            }
        }
        return count;
    }

    public Floor[] getFloors() {
        return floors;
    }

    public Floor getFloor(int index){
        if(index>size()){
            throw new FloorIndexOutOfBoundsException();
        }
        return floors[index];
    }

    public Floor setFloor(int index, Floor floor){
        if(index>size()){
            throw new FloorIndexOutOfBoundsException();
        }
        Floor returnFlor = floors[index];
        floors[index]=floor;
        return returnFlor;
    }

    public Space get(int index){
        if(index> numberSpaces()){
            throw new SpaceIndexOutOfBoundsException();
        }
        for(int i=0;i<size();i++){
            if(index >= floors[i].size())
                index-= floors[i].size();
            else
                return floors[i].get(index);
        }
        return null;
    }

    public Space set(int index, Space space){
        if(index>= numberSpaces())
            throw new SpaceIndexOutOfBoundsException();
        for(int i=0;i<size();i++){
            if(index >= floors[i].size())
                index-= floors[i].size();
            else {
               return floors[i].set(index, space);
            }
        }
        throw new SpaceIndexOutOfBoundsException();
    }

    public boolean add(int index, Space space){
        if(index> numberSpaces())
            throw new SpaceIndexOutOfBoundsException();
        for(int i=0;i<size();i++){
            if(index >= floors[i].size())
                index-= floors[i].size();
            else {
                return floors[i].add(index, space);
            }
        }
        return false;
    }

    public void remove(int index){
        if(index>= numberSpaces())
            throw new SpaceIndexOutOfBoundsException();
        for(int i=0;i<size();i++){
            if(index >= floors[i].size())
                index-= floors[i].size();
            else {
                floors[i].remove(index);
                return;
            }
        }
    }



    public Space getBestSpace(){
        Space space, bestSpace = floors[0].getBestSpace();
        for(int i=1;i<size();i++){
            space = floors[i].getBestSpace();
            if(bestSpace.getSpace()<space.getSpace()){
                bestSpace=space;
            }
        }
        return bestSpace;
    }

    public Space[] getSortedSpace() {
        Space[] arr = new Space[numberSpaces()];
        Space[] toAdd;
        int c = 0;
        for (int i = 0; i < floors.length; i++) {
            toAdd = floors[i].getSpaces();
            System.arraycopy(toAdd, 0, arr, c, toAdd.length);
            c += toAdd.length;
        }
        quickSort(arr,0,arr.length-1);
        return arr;
    }

    private static void quickSort(Space[] a, int first, int last) {
        int i = first;
        int j = last;
        double x = a[(first + last) / 2].getSpace();
        Space temp;
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
        sb.append("floors=").append(Arrays.toString(floors));
        sb.append('}');
        return sb.toString();
    }
}

