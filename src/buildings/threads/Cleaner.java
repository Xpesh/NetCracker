package buildings.threads;

import buildings.Floor;

public class Cleaner extends Thread{

    private Floor floor;

    public Cleaner(Floor floor){
        this.floor = floor;
    }

    @Override
    public void run() {
        for (int i = 0; !this.isInterrupted() && i < floor.size(); i++) {
            System.out.println("Cleaning room number "+i+" with total area "+floor.get(i).getSpace()+" square meters");
        }
    }
}
