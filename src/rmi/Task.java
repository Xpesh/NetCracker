package rmi;

public class Task implements TaskInterfeice {
    private static final double PI = Math.PI;
    private int x;
    private int y;

    public Task(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Task{" +
                "x=" + x +
                '}';
    }

    @Override
    public int getX(){
        return x;
    }

    @Override
    public int getY(){
        return y;
    }

    @Override
    public double function(){
        return Math.pow(x*PI,y);
    }

}