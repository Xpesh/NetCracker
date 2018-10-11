package buildings;

import java.util.Collection;
import java.util.Iterator;

public class OfficeBuilding implements Collection<Floor>, Building{

    private int size=0;
    private Node<Floor> headNode = new Node<Floor>();
    {
        headNode.nextElement=headNode;
        headNode.prevElement=headNode;
    }

    public OfficeBuilding(Floor[] floors) {
        for(Floor floor : floors){
            add(floor);
        }
    }

    public OfficeBuilding(int numberFloor, int[] sizeFloor) {
        for(int i : sizeFloor){
            add(new OfficeFloor(i));
        }
    }

    public int numberSpaces() {
        int count=0;
        for(Floor floor : this){
            count+=floor.size();
        }
        return count;
    }
    public double totalSpace() {
        double count=0;
        for(Floor floor : this){
            count+=floor.totalSpace();
        }
        return count;
    }
    public int totalNumberRooms() {
        int count=0;
        for(Floor floor : this){
            count+=floor.totalNumberRooms();
        }
        return count;
    }

    @Override
    public Floor[] getFloors() {
        return (Floor[]) toArray();
    }

    @Override
    public Floor getFloor(int index) {
        return getNode(index).currentElement;
    }

    @Override
    public Floor setFloor(int index, Floor floor) {
        Node<Floor> node = getNode(index);
        Floor returnFloor = node.currentElement;
        node.currentElement=floor;
        return returnFloor;
    }

    public Space get(int index){
        for(Floor floor : this){
            if(index >= floor.size())
                index-=floor.size();
            else
                return floor.get(index);
        }
        return null;
    }

    public Space set(int index, Space space){
        if(index<0 || index>=size()){
            throw new SpaceIndexOutOfBoundsException();
        }
        for(Floor floor : this){
            if(index >= floor.size())
                index-=floor.size();
            else {
               return floor.set(index, space);
            }
        }
        throw new SpaceIndexOutOfBoundsException();
    }

    public boolean add(int index, Space space){
        if(index>size()){
            throw new SpaceIndexOutOfBoundsException();
        }
        for(Floor floor : this){
            if(index >= floor.size())
                index-=floor.size();
            else {
                return floor.add(index, space);
            }
        }
        return false;
    }

    public Space getBestSpace(){
        Space bestFlat = headNode.nextElement.currentElement.getBestSpace();
        for(Floor floor : this){
            if(bestFlat.getSpace()<floor.getBestSpace().getSpace())
                bestFlat=floor.getBestSpace();
        }
        return bestFlat;
    }

    public Space[] getSortedSpace() {
        Space[] arr = new Space[numberSpaces()];
        Space[] toAdd;
        int c = 0;
        for (Floor floor : this) {
            toAdd = (Space[]) floor.getSpaces();
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

    //Collection

    private Node<Floor> getNode(int id){
        if(id<-1 || id>=size()){
            throw new FloorIndexOutOfBoundsException();
        }
        if(id==-1){
            return headNode;
        }
        Node<Floor> buf = headNode.nextElement;
        for(int i=0;i<id;i++){
            buf=buf.nextElement;
        }
        return buf;
    }
    private void removeNode(int id){
        Node<Floor> buf = getNode(id-1);
        buf.nextElement=buf.nextElement.nextElement;
        size--;
    }
    public void remove(int id){
        removeNode(id);
    }

    private void addNode(int id, Node<Floor> node){
        Node<Floor> buf = getNode(id-1);
        node.nextElement=buf.nextElement;
        buf.nextElement=node;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size()==0;
    }

    @Override
    public boolean contains(Object o) {
        for (Floor o1 : this) {
            if (o1.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<Floor> iterator() {
        return new Iterator<Floor>() {
            Node<Floor> currentNode = headNode;
            int currentId = 0;
            @Override
            public boolean hasNext() {
                return currentId<size  && currentNode.nextElement.currentElement!=null;
            }

            @Override
            public Floor next() {
                if(hasNext()){
                    currentNode=currentNode.nextElement;
                    currentId++;
                    return currentNode.currentElement;
                }
                throw new NullPointerException();
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] objects = new Space[size];
        int count = 0;
        for (Object o : this) {
            objects[count++] = o;
        }
        return objects;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if(size()>a.length){
            a = (T[]) new Object[size()];
        }
        System.arraycopy(a,0,toArray(),0,size());
        return a;
    }

    @Override
    public boolean add(Floor officeFloor) {
        Node<Floor> node;
        if(size==0){
            node = new Node<Floor>(officeFloor,null,null);
            node.nextElement=node;
            node.prevElement=node;
            headNode.nextElement=node;
        } else {
            node = new Node<Floor>(officeFloor, headNode.nextElement, headNode.nextElement.prevElement);
        }
        headNode.nextElement.prevElement.nextElement=node;
        headNode.nextElement.prevElement=node;
        size++;
        return true;

    }

    @Override
    public boolean remove(Object o) {
        if(getNode(0).currentElement.equals(o)){
            headNode.nextElement=getNode(1);
            size--;
            return true;
        }
        Node<Floor> buf = getNode(1);
        while (buf.currentElement!=null){
            if(buf.currentElement.equals(o)){
                buf.prevElement.nextElement=buf.nextElement;
                size--;
                return true;
            }
            buf=buf.nextElement;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (size() == 0 ) { return false; }
        if (c == null) { throw new NullPointerException("specified collection in null"); }
        if (c == this) { return true; }
        for (Object aC : c) {
            if (!(contains((Space) aC))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Floor> c) {
        if (c == null) { throw new NullPointerException("specified collection in null"); }
        for (Floor aC : c) {
            this.add(aC);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) { throw new NullPointerException("specified collection in null"); }
        boolean isChanged = false;
        for(Object o : c){
            if(remove(o)){
                isChanged=true;
            }
        }
        return isChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) { throw new NullPointerException("specified collection in null"); }
        boolean isChanged = false;
        for(Object o : this){
            if(!c.contains(o)){
                remove(o);
                isChanged=true;
            }
        }
        return isChanged;
    }

    @Override
    public void clear() {
        Node<Floor> prev = headNode;
        Node<Floor> buf = headNode.nextElement;
        while (buf!=null){
            prev.nextElement=buf.nextElement;
            prev=buf;
            buf=buf.nextElement;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OfficeFloor{");
        sb.append("size=").append(size);
        sb.append(", Nodes=");
        for(Floor officeFloor : this){
            sb.append("\n ---").append(officeFloor);
        }
        sb.append('}');
        return sb.toString();
    }

    private class  Node<E>{
        private E currentElement;
        private Node<E> nextElement;
        private Node<E> prevElement;

        public Node(E currentElement, Node<E> nextElement, Node<E> prevElement) {
            this.currentElement = currentElement;
            this.nextElement = nextElement;
            this.prevElement = prevElement;
        }

        public Node() { }

        @Override
        public String toString() {
    return currentElement.toString();
}
    }
}
