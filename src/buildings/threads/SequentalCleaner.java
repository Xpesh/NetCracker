package buildings.threads;

import buildings.Floor;

public class SequentalCleaner implements Runnable {
    private Floor floor;
    private Semaphore semaphore;

    public SequentalCleaner(Floor floor,Semaphore semaphore){
        this.floor = floor;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        for (int i = 0; i < floor.size(); i++) {
            semaphore.startClean();
            System.out.println("Cleaning room number "+i+" with total area "+floor.get(i).getSpace()+" square meters");
            semaphore.endClean();
        }
    }
}
