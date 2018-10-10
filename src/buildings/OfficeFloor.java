package buildings;

import java.util.Collection;
import java.util.Iterator;

public class OfficeFloor implements Collection<Office> {
    private int size=0;
    private Node<Office> lastNode = new Node<Office>();
    private Node<Office> firstNode = new Node<Office>(null,lastNode);

    {
        lastNode.nextElement=firstNode;
    }


    public OfficeFloor(int size) {
        for (int i=0;i<size;i++){
            add(new Office());
        }
    }

    public OfficeFloor(Office[] offices) {
        for (Office office : offices) {
            add(office);
        }
    }

    private Node<Office> getNode(int id){
        if(id>=size())
            throw new SpaceIndexOutOfBoundsException();
        Node<Office> buf = firstNode.nextElement;
        for(int i=0;i<id;i++){
            buf=buf.nextElement;
        }
        return buf;
    }
    private void removeNode(int id){
        Node<Office> buf = getNode(id-1);
        buf.nextElement=buf.nextElement.nextElement;
    }
    public void remove(int id){
        removeNode(id);
    }

    private void addNode(int id, Node<Office> node){
        Node<Office> buf = getNode(id-1);
        node.nextElement=buf.nextElement;
        buf.nextElement=node;
    }

    public int size() {
        return size;
    }

    public double totalSpace(){
        double count=0;
        for(Office office : this){
            count+=office.getSpace();
        }
        return count;
    }

    public int totalNumberRooms(){
        int count=0;
        for(Office office : this){
            count += office.getNumberRooms();
        }
        return count;
    }

    public Office get(int id){
        if(id>=size())
            throw new SpaceIndexOutOfBoundsException();
        return getNode(id).currentElement;
    }

    public void set(int id, Office office){
        getNode(id).currentElement=office;
    }

    public boolean add(int id, Office office){
        if(id==size){
            return add(office);
        }
        if(id>=size())
            throw new SpaceIndexOutOfBoundsException();
        return false;
    }

    public Office getBestSpace(){
        Office bestFlat = firstNode.nextElement.currentElement;
        for(Office office : this){
            if(bestFlat.getSpace()<office.getSpace())
                bestFlat=office;
        }
        return bestFlat;
    }

    //Collection



    public boolean isEmpty() {
        return size()==0;
    }

    @Override
    public boolean contains(Object o) {
        for (Office o1 : this) {
            if (o1.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<Office> iterator() {
        return new Iterator<Office>() {
            Node<Office> currentNode = firstNode;
            int currentId = 0;
            @Override
            public boolean hasNext() {
                return currentId<size  && currentNode.nextElement.currentElement!=null;
            }

            @Override
            public Office next() {
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
    public boolean add(Office office) {
        Node<Office> prev = lastNode;
        prev.currentElement = office;
        lastNode = new Node<Office>(null,firstNode);
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
        Node<Office> prev = getNode(0);
        Node<Office> buf = prev.nextElement;
        while (buf.currentElement!=null){
            if(buf.currentElement.equals(o)){
                prev.nextElement=buf.nextElement;
                size--;
                return true;
            }
            prev=buf;
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
    public boolean addAll(Collection<? extends Office> c) {
        if (c == null) { throw new NullPointerException("specified collection in null"); }
        for (Office aC : c) {
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
        Node<Office> prev = firstNode;
        Node<Office> buf = firstNode.nextElement;
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
        for(Office office : this){
            sb.append("\n").append(office);
        }
        sb.append('}');
        return sb.toString();
    }

    private class  Node<E>{
        private E currentElement;
        private Node<E> nextElement;

        public Node(E currentElement, Node<E> nextElement) {
            this.currentElement = currentElement;
            this.nextElement = nextElement;
        }

        public Node() {

        }

        @Override
        public String toString() {
            return currentElement.toString();
        }
    }
}
