package p2.sorts;


import java.util.Comparator;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        quickSortHelper(array, 0, array.length, comparator);
    }

    public static <E> void quickSortHelper(E[] array, int bot, int top, Comparator<E> comparator){

        int positionPivot;
        if(bot >= top){
            return;
        } else {
            E pivot = array[top - 1];
            positionPivot = bot - 1;
            for(int i = bot; i < top; i++){
                if (comparator.compare(array[i], pivot) <= 0 ){
                    positionPivot = positionPivot + 1;
                    E tempVal = array[i];
                    array[i] = array[positionPivot];
                    array[positionPivot] = tempVal;
                }
            }
            quickSortHelper(array, bot, positionPivot, comparator);
            quickSortHelper(array, positionPivot + 1, top, comparator);
        }
    }


}