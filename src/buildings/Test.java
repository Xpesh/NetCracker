package buildings;

import java.util.Random;

public class Test {
    public static void main(String[] args) {
        Dwelling dwelling = new Dwelling(2, new int[]{5, 5});
        Random random = new Random();
        for(int i=0;i<dwelling.numberFlats();i++){
            dwelling.setFlat(i,new Flat(random.nextDouble(),random.nextInt()));
        }
        dwelling.addFlat(4, new Flat(250,5) );
        dwelling.addFlat(5, new Flat(250,5) );
        Flat[] flats = dwelling.flatsSorted();
        for(Flat flat : flats){
            System.out.println(flat);
        }
    }
}
