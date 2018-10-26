package buildings.office;

import buildings.Floor;
import buildings.Space;
import buildings.SpaceIndexOutOfBoundsException;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class OfficeFloor implements Collection<Space>, Floor, Serializable {
    private int size=0;
    private Node<Space> lastNode = new Node<Space>();
    private Node<Space> firstNode = new Node<Space>(null,lastNode);

    {
        lastNode.nextElement=firstNode;
    }


    public OfficeFloor(int size) {
        for (int i=0;i<size;i++){
            add(new Office());
        }
    }

    public OfficeFloor(Space[] spaces) {
        for (Space space : spaces) {
            add(space);
        }
    }

    private Node<Space> getNode(int id){
        if(id>=size())
            throw new SpaceIndexOutOfBoundsException();
        Node<Space> buf = firstNode.nextElement;
        for(int i=0;i<id;i++){
            buf=buf.nextElement;
        }
        return buf;
    }
    private void removeNode(int id){
        Node<Space> buf = getNode(id-1);
        buf.nextElement=buf.nextElement.nextElement;
        size--;
    }
    public void remove(int id){
        removeNode(id);
    }

    private void addNode(int id, Node<Space> node){
        Node<Space> buf = getNode(id-1);
        node.nextElement=buf.nextElement;
        buf.nextElement=node;
    }

    public int size() {
        return size;
    }

    public double totalSpace(){
        double count=0;
        for(Space space : this){
            count+=space.getSpace();
        }
        return count;
    }

    public int totalNumberRooms(){
        int count=0;
        for(Space space : this){
            count += space.getNumberRooms();
        }
        return count;
    }
    public Space[] getSpaces(){
        return (Space[]) toArray();
    }

    public Space get(int id){
        if(id>=size())
            throw new SpaceIndexOutOfBoundsException();
        return getNode(id).currentElement;
    }

    public Space set(int id, Space space){
        Node<Space> node  =getNode(id);
        Space returnSpace = node.currentElement;
        node.currentElement=space;
        return returnSpace;
    }

    public boolean add(int id, Space space){
        if(id==size)
            return add(space);
        if(id>size)
            throw new SpaceIndexOutOfBoundsException();
        Node<Space> node = getNode(id-1);
        Node<Space> nodeId = new Node<>(space,node.nextElement);
        node.nextElement=nodeId;
        size++;
        return true;
    }

    public Space getBestSpace(){
        Space bestFlat = firstNode.nextElement.currentElement;
        for(Space space : this){
            if(bestFlat.getSpace()<space.getSpace())
                bestFlat=space;
        }
        return bestFlat;
    }

    //Collection



    public boolean isEmpty() {
        return size()==0;
    }

    @Override
    public boolean contains(Object o) {
        for (Space o1 : this) {
            if (o1.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<Space> iterator() {
        return new Iterator<Space>() {
            Node<Space> currentNode = firstNode;
            int currentId = 0;
            @Override
            public boolean hasNext() {
                return currentId<size  && currentNode.nextElement.currentElement!=null;
            }

            @Override
            public Space next() {
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
    public boolean add(Space space) {
        Node<Space> prev = lastNode;
        prev.currentElement = space;
        lastNode = new Node<Space>(null,firstNode);
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
        Node<Space> prev = getNode(0);
        Node<Space> buf = prev.nextElement;
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
            if (!(contains(aC))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Space> c) {
        if (c == null) { throw new NullPointerException("specified collection in null"); }
        for (Space aC : c) {
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
        Node<Space> prev = firstNode;
        Node<Space> buf = firstNode.nextElement;
        while (buf!=null){
            prev.nextElement=buf.nextElement;
            prev=buf;
            buf=buf.nextElement;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OfficeFloor ( ").append(size);
        for(Space space : this){
            sb.append(" , ").append(space);
        }
        return sb.append(" )").toString();
    }

    @Override
    public boolean equals(Object o) {
        if(this==o) return true;
        if(! (o instanceof OfficeBuilding)) return false;
        OfficeBuilding officeBuilding = (OfficeBuilding) o;
        if (size!=officeBuilding.size()) return false;
        return containsAll(officeBuilding);
    }

    @Override
    public int hashCode() {
        int hash = size;
        for (Object i : this) {
            hash ^= i.hashCode();
        }
        return hash;
    }

    @Override
    public Object clone() {
        Space[] spaces = getSpaces();
        for (int i = 0; i < spaces.length; i++) {
            spaces[i] = (Space)spaces[i].clone();
        }
        return new OfficeFloor(spaces);
    }

    private class  Node<E> implements Serializable{
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
