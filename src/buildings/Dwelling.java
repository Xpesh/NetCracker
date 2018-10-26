package buildings;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

public class Dwelling implements Building, Serializable {
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
    @Override
    public int size(){
        return floors.length;
    }

    @Override
    public int numberSpaces(){
        int count = 0;
        for(Floor floor : floors){
            if(floor!=null){
                count+=floor.size();
            }
        }
        return count;
    }

    @Override
    public double totalSpace(){
        double count = 0;
        for(Floor floor : floors){
            if(floor!=null){
                count+=floor.totalSpace();
            }
        }
        return count;
    }

    @Override
    public int totalNumberRooms(){
        int count = 0;
        for(Floor floor : floors){
            if(floor!=null){
                count+=floor.totalNumberRooms();
            }
        }
        return count;
    }

    @Override
    public Floor[] getFloors() {
        return floors;
    }

    @Override
    public Floor getFloor(int index){
        if(index>size()){
            throw new FloorIndexOutOfBoundsException();
        }
        return floors[index];
    }

    @Override
    public Floor setFloor(int index, Floor floor){
        if(index>size()){
            throw new FloorIndexOutOfBoundsException();
        }
        Floor returnFlor = floors[index];
        floors[index]=floor;
        return returnFlor;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    public boolean contains(Object o) {
        for (Floor o1 : floors) {
            if (o1.equals(o)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsAll(Collection<?> c) {
        if (size() == 0 ) { return false; }
        if (c == null) { throw new NullPointerException("specified collection in null"); }
        if (c == this) { return true; }
        for (Object aC : c) {
            if (!(contains(aC))) {
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean equals(Object o) {
        if(this==o) return true;
        if(! (o instanceof OfficeBuilding)) return false;
        Dwelling dwelling = (Dwelling) o;
        if (size()!=dwelling.size()) return false;
        return containsAll(Arrays.asList(dwelling));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Dwelling ( ").append(size());
        for(Floor floor : floors){
            sb.append(" , ").append(floor);
        }
        return sb.append(" )").toString();
    }

    @Override
    public int hashCode() {
        int hash = size();
        for (Object i : floors) {
            hash ^= i.hashCode();
        }
        return hash;
    }

    @Override
    public Object clone() {
        Floor[] newFloors = new Floor[floors.length];
        for (int i = 0; i < floors.length; i++) {
            newFloors[i] = (Floor)(floors[i]).clone();
        }
        return new Dwelling(newFloors);
    }
}

