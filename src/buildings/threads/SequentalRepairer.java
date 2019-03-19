package buildings.threads;

import buildings.Floor;

public class SequentalRepairer implements Runnable {
    private Floor floor;
    private Semaphore semaphore;

    public SequentalRepairer(Floor floor,Semaphore semaphore){
        this.floor = floor;
        this.semaphore = semaphore;
    }
    @Override
    public void run() {
        for (int i = 0;i < floor.size(); i++) {
            semaphore.startRepair();
            System.out.println("Repairing space number " + i + " with total area " + floor.get(i).getSpace() + " square meters");
            semaphore.endRepair();
        }
    }
}
