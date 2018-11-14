package buildings;

import buildings.office.Office;
import buildings.office.OfficeBuilding;
import buildings.office.OfficeFloor;
import buildings.threads.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class Test {
    public static void main(String[] args) throws Exception {
//        Dwelling dwelling = new Dwelling(2, new int[]{5, 5});
//        dwelling.set(6, new Flat(251,4) );
//        dwelling.add(5, new Flat(25,5) );
//        System.out.println(dwelling);
        OfficeBuilding officeBuilding;
        OfficeFloor[] officeFloors = new OfficeFloor[10];
        int count = 1;
        for(int j=0;j<officeFloors.length;j++) {
            Space[] spaces = new Space[5];
            for (int i = 0; i < spaces.length; i++) {
                spaces[i] = new Office(50, count++);
            }
            officeFloors[j] = new OfficeFloor(spaces);
        }
        Semaphore semaphor = new Semaphore();
        Thread thread1 = new Thread(new SequentalCleaner(officeFloors[0],semaphor));
        Thread thread2 = new Thread(new SequentalRepairer(officeFloors[0],semaphor));
        thread1.setPriority(Thread.MAX_PRIORITY);
        thread1.start();
        thread2.start();

//        officeBuilding=new OfficeBuilding(officeFloors);
//        System.out.println(officeBuilding);
//        System.out.println();
//        System.out.println();
//        Locale.setDefault(new Locale("en", "USA"));
//        Building building = Buildings.inputBuilding(System.in);
//        Buildings.outputBuilding(building,System.out);
        //OfficeBuilding ( 10 , OfficeFloor ( 5 , Flat ( 1 , 50.0 ) , Office ( 2 , 50.0 ) , Office ( 3 , 50.0 ) , Office ( 4 , 50.0 ) , Office ( 5 , 50.0 ) ) , DwellingFloor ( 5 , Office ( 6 , 50.0 ) , Office ( 7 , 50.0 ) , Office ( 8 , 50.0 ) , Office ( 9 , 50.0 ) , Office ( 10 , 50.0 ) ) , OfficeFloor ( 5 , Office ( 11 , 50.0 ) , Office ( 12 , 50.0 ) , Office ( 13 , 50.0 ) , Office ( 14 , 50.0 ) , Office ( 15 , 50.0 ) ) , OfficeFloor ( 5 , Office ( 16 , 50.0 ) , Office ( 17 , 50.0 ) , Office ( 18 , 50.0 ) , Office ( 19 , 50.0 ) , Office ( 20 , 50.0 ) ) , OfficeFloor ( 5 , Office ( 21 , 50.0 ) , Office ( 22 , 50.0 ) , Office ( 23 , 50.0 ) , Office ( 24 , 50.0 ) , Office ( 25 , 50.0 ) ) , OfficeFloor ( 5 , Office ( 26 , 50.0 ) , Office ( 27 , 50.0 ) , Office ( 28 , 50.0 ) , Office ( 29 , 50.0 ) , Office ( 30 , 50.0 ) ) , OfficeFloor ( 5 , Office ( 31 , 50.0 ) , Office ( 32 , 50.0 ) , Office ( 33 , 50.0 ) , Office ( 34 , 50.0 ) , Office ( 35 , 50.0 ) ) , OfficeFloor ( 5 , Office ( 36 , 50.0 ) , Office ( 37 , 50.0 ) , Office ( 38 , 50.0 ) , Office ( 39 , 50.0 ) , Office ( 40 , 50.0 ) ) , OfficeFloor ( 5 , Office ( 41 , 50.0 ) , Office ( 42 , 50.0 ) , Office ( 43 , 50.0 ) , Office ( 44 , 50.0 ) , Office ( 45 , 50.0 ) ) , OfficeFloor ( 5 , Office ( 46 , 50.0 ) , Office ( 47 , 50.0 ) , Office ( 48 , 50.0 ) , Office ( 49 , 50.0 ) , Office ( 50 , 50.0 ) ) )


//        try (FileOutputStream fos = new FileOutputStream("OfficeBuildingSer", false)) {
//            Buildings.serializeBuilding(officeBuilding, fos);
//            fos.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}