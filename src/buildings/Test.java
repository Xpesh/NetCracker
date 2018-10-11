package buildings;

public class Test {
    public static void main(String[] args) {
//        Dwelling dwelling = new Dwelling(2, new int[]{5, 5});
//        dwelling.set(6, new Flat(251,4) );
//        dwelling.add(5, new Flat(25,5) );
//        System.out.println(dwelling);
        OfficeBuilding officeBuilding;
        OfficeFloor[] officeFloors = new OfficeFloor[10];
        int count = 1;
        for(int j=0;j<officeFloors.length;j++) {
            Space[] spaces = new Space[10];
            for (int i = 0; i < spaces.length; i++) {
                spaces[i] = new Office(50, count++);
            }
            officeFloors[j] = new OfficeFloor(spaces);
        }
        officeBuilding=new OfficeBuilding(officeFloors);
//        for(OfficeFloor officeFloor : officeFloors){
//            System.out.println(officeFloor);
//        }
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(officeBuilding);
    }
}