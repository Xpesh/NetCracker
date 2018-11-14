package buildings;

import java.util.Comparator;

public class Sort{

//    public static void quickSort(Space[] a, int first, int last) {
//        int i = first;
//        int j = last;
//        Space x = a[(first + last) / 2];
//        Space temp;
//        do {
//            while (a[i].compareTo(x)<0) i++;
//            while (a[j].compareTo(x)>0) j--;
//            if (i <= j) {
//                if (i < j) {
//                    temp = a[i];
//                    a[i] = a[j];
//                    a[j] = temp;
//                }
//                i++;
//                j--;
//            }
//        } while (i <= j);
//        if (i < last)
//            quickSort(a, i, last);
//        if (first < j)
//            quickSort(a, first, j);
//    }
//
//    public static void quickSort(Floor[] a, int first, int last) {
//        int i = first;
//        int j = last;
//        Floor x = a[(first + last) / 2];
//        Floor temp;
//        do {
//            while (a[i].compareTo(x)<0) i++;
//            while (a[j].compareTo(x)>0) j--;
//            if (i <= j) {
//                if (i < j) {
//                    temp = a[i];
//                    a[i] = a[j];
//                    a[j] = temp;
//                }
//                i++;
//                j--;
//            }
//        } while (i <= j);
//        if (i < last)
//            quickSort(a, i, last);
//        if (first < j)
//            quickSort(a, first, j);
//    }
    public static <E extends Comparable> void quickSort(E[] a, int first, int last) {
        int i = first;
        int j = last;
        E x = a[(first + last) / 2];
        E temp;
        do {
            while (a[i].compareTo(x)<0) i++;
            while (a[j].compareTo(x)>0) j--;
            if (i <= j) {
                if (i < j) {
                    temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
                i++;
                j--;
            }
        } while (i <= j);
        if (i < last)
            quickSort(a, i, last);
        if (first < j)
            quickSort(a, first, j);
    }

    public static <E> void quickSort(E[] a, int first, int last, Comparator<E> comparator) {
        int i = first;
        int j = last;
        E x = a[(first + last) / 2];
        E temp;
        do {
            while (comparator.compare(a[i],x)<0) i++;
            while (comparator.compare(a[j],x)>0) j--;
            if (i <= j) {
                if (i < j) {
                    temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
                i++;
                j--;
            }
        } while (i <= j);
        if (i < last)
            quickSort(a, i, last, comparator);
        if (first < j)
            quickSort(a, first, j, comparator);
    }


}
