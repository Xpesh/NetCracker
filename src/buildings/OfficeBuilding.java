package buildings;

import java.util.Collection;
import java.util.Iterator;

public class OfficeBuilding implements Collection<OfficeFloor>{

    private int size=0;
    private Node<OfficeFloor> lastNode = new Node<OfficeFloor>();
    private Node<OfficeFloor> firstNode = new Node<OfficeFloor>(null,lastNode,lastNode);
    {
        lastNode.nextElement=firstNode;
        lastNode.prevElement=firstNode;
    }

    public OfficeBuilding(OfficeFloor[] officeFloors) {
        for(OfficeFloor officeFloor : officeFloors){
            add(officeFloor);
        }
    }

    public OfficeBuilding(int numberDwellingFloor, int[] sizeDwellingFloor) {
        for(int i : sizeDwellingFloor){
            add(new OfficeFloor(i));
        }
    }

    public int numbersOffice() {
        int count=0;
        for(OfficeFloor officeFloor : this){
            count+=officeFloor.size();
        }
        return count;
    }
    public double totalArea() {
        double count=0;
        for(OfficeFloor officeFloor : this){
            count+=officeFloor.totalArea();
        }
        return count;
    }
    public int totalNumberRooms() {
        int count=0;
        for(OfficeFloor officeFloor : this){
            count+=officeFloor.totalNumberRooms();
        }
        return count;
    }

    public Office getOffice(int index){
        for(OfficeFloor officeFloor : this){
            if(index >= officeFloor.size())
                index-=officeFloor.size();
            else
                return officeFloor.get(index);
        }
        return null;
    }

    public void setFlat(int index, Office office){
        for(OfficeFloor officeFloor : this){
            if(index >= officeFloor.size())
                index-=officeFloor.size();
            else {
                officeFloor.set(index, office);
                return;
            }
        }
    }

    public void addFlat(int index, Office office){
        for(OfficeFloor officeFloor : this){
            if(index >= officeFloor.size())
                index-=officeFloor.size();
            else {
                officeFloor.add(index, office);
                return;
            }
        }
    }

    public void removeFlat(int index){
        for(OfficeFloor officeFloor : this){
            if(index >= officeFloor.size())
                index-=officeFloor.size();
            else {
                officeFloor.remove(index);
                return;
            }
        }
    }

    public Office getBestSpace(){
        Office bestFlat = firstNode.nextElement.currentElement.getBestSpace();
        for(OfficeFloor officeFloor : this){
            if(bestFlat.getArea()<officeFloor.getBestSpace().getArea())
                bestFlat=officeFloor.getBestSpace();
        }
        return bestFlat;
    }

    public Office[] getSortedFlat () {
        Office[] arr = new Office[numbersOffice()];
        Office[] toAdd;
        int c = 0;
        for (OfficeFloor officeFloor : this) {
            toAdd = (Office[]) officeFloor.toArray();
            System.arraycopy(toAdd, 0, arr, c, toAdd.length);
            c += toAdd.length;
        }
        quickSort(arr,0,arr.length-1);
        return arr;
    }

    private static void quickSort(Office[] a, int first, int last) {
        int i = first;
        int j = last;
        double x = a[(first + last) / 2].getArea();
        Office temp;
        do {
            while (a[i].getArea() < x) i++;
            while (a[j].getArea() > x) j--;
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

    private Node<OfficeFloor> getNode(int id){
        Node<OfficeFloor> buf = firstNode.nextElement;
        for(int i=0;i<id;i++){
            buf=buf.nextElement;
        }
        return buf;
    }
    private void removeNode(int id){
        Node<OfficeFloor> buf = getNode(id-1);
        buf.nextElement=buf.nextElement.nextElement;
    }
    public void remove(int id){
        removeNode(id);
    }

    private void addNode(int id, Node<OfficeFloor> node){
        Node<OfficeFloor> buf = getNode(id-1);
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
        for (OfficeFloor o1 : this) {
            if (o1.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<OfficeFloor> iterator() {
        return new Iterator<OfficeFloor>() {
            Node<OfficeFloor> currentNode = firstNode;
            int currentId = 0;
            @Override
            public boolean hasNext() {
                return currentId<size  && currentNode.nextElement.currentElement!=null;
            }

            @Override
            public OfficeFloor next() {
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
        Object[] objects = new Office[size];
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
    public boolean add(OfficeFloor officeFloor) {
        Node<OfficeFloor> prev = lastNode;
        prev.currentElement = officeFloor;
        lastNode = new Node<OfficeFloor>(null,firstNode,prev);
        prev.nextElement = lastNode;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if(getNode(0).currentElement.equals(o)){
            firstNode.nextElement=getNode(1);
            size--;
            return true;
        }
        Node<OfficeFloor> buf = getNode(1);
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
            if (!(contains((Office) aC))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends OfficeFloor> c) {
        if (c == null) { throw new NullPointerException("specified collection in null"); }
        for (OfficeFloor aC : c) {
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
        Node<OfficeFloor> prev = firstNode;
        Node<OfficeFloor> buf = firstNode.nextElement;
        while (buf!=null){
            prev.nextElement=buf.nextElement;
            prev=buf;
            buf=buf.nextElement;
        }
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
