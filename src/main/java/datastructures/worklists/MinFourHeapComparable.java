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
    private int capacity;

    public MinFourHeapComparable() {
        this.size = 0;
        this.capacity = 10;
        this.data = (E[]) new Comparable[capacity];
    }

    @Override
    public boolean hasWork() {
        if (0 < size){
            return true;
    }
        return false;
    }

    @Override
    public void add(E work) {
        if (capacity == size){
            E[] storeData= (E[]) new Comparable[capacity*2];
            for (int i = 0; i < this.size; i++) {
                storeData[i] = data[i];
            }
            this.data = (E[]) new Comparable[capacity*2];
            this.data = storeData;
            this.capacity *= 2;
        }
        this.data[size]=work;
        this.size++;
        int index = size - 1;
        int start = (int) Math.floor((index-1)/4);
        while(data[index].compareTo(data[start])<0){
            E temp = data[start];
            data[start] = data[index];
            data[index] = temp;
            index = start;
            start = (int) Math.floor((index-1)/4);
        }
    }

    @Override
    public E peek() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        return this.data[0];
    }

    @Override
    public E next() {
        if (!hasWork()) {
            throw new NoSuchElementException();
        }
        E value= this.data[0];
        data[0] = data[size-1];
        size--;
        percolateDown(0);
        return value;
    }

    private void percolateDown(int currIndex){
        int minIndex = currIndex * 4 + 1;
        if(this.size < 4*currIndex+1){
            return;
        }
        for(int i = 2; i<=4 && currIndex * 4+i < this.size; i++){
            if (data[currIndex*4+i].compareTo(data[minIndex]) < 0)
            {
                minIndex = currIndex*4+i;
            }
        }
        if(data[minIndex].compareTo(data[currIndex])>0){
            return;
        }
        E temp = data[minIndex];
        data[minIndex] = data[currIndex];
        data[currIndex] = temp;
        percolateDown(minIndex);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        E[] temp = (E[]) new Comparable[capacity];
        this.data=temp;
        this.size=0;

    }
}