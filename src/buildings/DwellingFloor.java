package buildings;

import java.io.Serializable;
import java.util.Arrays;

public class DwellingFloor implements Floor,Serializable{
    private Space[] spaces;

    public DwellingFloor(Space[] spaces) {
        this.spaces = spaces;
    }

    public DwellingFloor(int numberSpaces) {
        this.spaces = new Space[numberSpaces];
        for(int i =0;i<numberSpaces;i++){
            spaces[i]=new Flat();
        }
    }

    @Override
    public int size(){
        return spaces.length;
    }

    @Override
    public double totalSpace(){
        double count=0;
        for(Space space : spaces){
            count+=space.getSpace();
        }
        return count;
    }

    @Override
    public int totalNumberRooms(){
        int count=0;
        for(Space space : spaces){
            count += space.getNumberRooms();
        }
        return count;
    }

    @Override
    public Space[] getSpaces() {
        return spaces;
    }

    @Override
    public Space get(int index){
        if(index>=size())
            throw new SpaceIndexOutOfBoundsException();
        return spaces[index];
    }

    @Override
    public Space set(int index, Space space){
        Space returSpace = spaces[index];
        spaces[index]=space;
        return returSpace;
    }

    @Override
    public boolean add(int index, Space space){
        if(index<0 || index>size())
            throw new SpaceIndexOutOfBoundsException();
        Space[] bufSpaces = new Space[size()+1];
        if(index==size()){
            System.arraycopy(spaces,0,bufSpaces,0,size());
        }else if(index==0){
            System.arraycopy(spaces,0,bufSpaces,1,size());
        }
        else {
            System.arraycopy(spaces,0,bufSpaces,0,index);
            System.arraycopy(spaces,index,bufSpaces,index+1,size()-index-1);
        }
        bufSpaces[index]=space;
        spaces =bufSpaces;
        return true;
    }

    @Override
    public void remove(int index){
        Space[] bufSpaces = new Space[spaces.length-1];
        System.arraycopy(spaces,index+1,bufSpaces,index, spaces.length-index-1);
        System.arraycopy(spaces,0,bufSpaces,0,index);
        spaces =bufSpaces;
    }

    @Override
    public Space getBestSpace(){
        Space bestSpace = spaces[0];
        for(Space space : spaces){
            if(bestSpace.getSpace()<space.getSpace())
                bestSpace=space;
        }
        return bestSpace;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DwellingFloor ( ").append(size());
        for(Space space : spaces){
            sb.append(" , ").append(space);
        }
        return sb.append(" )").toString();
    }
}
