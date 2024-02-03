package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeapComparable<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;

    private int size;

    public MinFourHeapComparable() {
       data = (E[]) new Comparable[10];

       size = 0;
    }

    @Override
    public boolean hasWork() {
        return size > 0;
    }

    @Override
    public void add(E work) {
        if(size == this.data.length){
            E[] newArray = (E[]) new Comparable [this.data.length * 2];
            for(int i = 0; i < this.data.length; i++){
                newArray[i] = this.data[i];
            }
            this.data = newArray;
        }
        int length = size;

        while (work.compareTo(this.data[(length-1) / 4]) < 0 && length > 0){
            this.data[length] = this.data[(length - 1) / 4];
            length = (length - 1)/4;

        }
        size ++;
        this.data[length] = work;
    }

    @Override
    public E peek() {
        if(!hasWork()){
            throw new NoSuchElementException();
        }
        return data[0];
    }

    @Override
    public E next() {
        if(!hasWork()){
            throw new NoSuchElementException();
        }
        E next = data[0];
        E element = data[size - 1];
        int start = 0;

        while(size - 1 > start * 4){
            int end = 4 * start + 1;
            for(int i = (4 * start) + 2; i < (4 * start) + 5; i++){
                if (i < size && data[i].compareTo(data[end]) < 0) {
                    end = i;
                }
            }
            if(data[end].compareTo(element) < 0){
                data[start] = data[end];
                start = end;
            } else {
                break;
            }
        }
        data[start] = element;
        size--;
        return next;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        size = 0;
        data = (E[]) new Comparable[10];
    }
}
