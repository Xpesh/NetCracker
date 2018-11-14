package buildings.threads;

public class Semaphore {
    private boolean repair = true;

    public synchronized void startRepair() {
        while (!repair) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void endRepair() {
        repair = false;
        this.notifyAll();
    }

    public synchronized void startClean() {
        while (repair) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void endClean() {
        repair = true;
        this.notifyAll();
    }

 }
