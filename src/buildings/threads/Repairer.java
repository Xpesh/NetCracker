package buildings.threads;

import buildings.Floor;

public class Repairer extends Thread{
    private Floor floor;

    public Repairer(Floor floor){
        this.floor = floor;
    }
    @Override
    public void run() {
        for (int i = 0; !this.isInterrupted() && i < floor.size(); i++) {
            System.out.println("Repairing space number "+i+" with total area "+floor.get(i).getSpace()+" square meters");
        }
    }
}
