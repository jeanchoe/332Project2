package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }


    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {

        MinFourHeap<E> data = new MinFourHeap<>(comparator);
        if(array.length == 0){
            return;
        } else if(k > array.length) {
            k = array.length;
        }
        if(k != 0){
            for(int i = 0; i < array.length; i++){
                if(data.size() >= k){
                    if(comparator.compare(data.peek(), array[i])< 0){
                        data.next();
                        data.add(array[i]);
                    }
                } else {
                    data.add(array[i]);
                }
            }
        }
        for(int i = 0; i < k; i++){
            if(data.hasWork()){
                array[i] = data.next();
            } else {
                throw new IllegalArgumentException();
            }
        }
        for(int i = k; i < array.length; i++){
            array[i] = null;
        }
    }
}
