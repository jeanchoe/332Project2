package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;
    private int capacity;

    private Comparator<E> comparator;
    public MinFourHeap(Comparator<E> c) {
        this.data = (E[]) new Object[capacity];
        this.size = 0;
        this.capacity = 10;
        comparator = c;
    }

    @Override
    public boolean hasWork() {
        if(0 < size){
         return true;
        }
        return false;
    }

    @Override
    public void add(E work) {
        if (capacity == size){
            E[] storeData= (E[]) new Object[capacity*2];
            for (int i = 0; i <this.size ; i++) {
                storeData[i] = data[i];
            }
            this.data = (E[]) new Object[capacity*2];
            this.data=storeData;
            this.capacity*=2;
        }
        this.data[size]=work;
        this.size++;
        int index = size-1;
        int start = (int) Math.floor((index-1)/4);
        while(comparator.compare(data[index], data[start]) < 0) {
            E tempVal = data[start];
            data[start] = data[index];
            data[index] = tempVal;
            index = start;
            start = (int) Math.floor((index-1)/4);
        }
    }

    @Override
    public E peek() {
        if (!hasWork()) throw new NoSuchElementException();
        return this.data[0];
    }

    @Override
    public E next() {
        if (!hasWork()) throw new NoSuchElementException();
        E value= this.data[0];
        data[0] = data[size - 1];
        size--;
        percolateDown(0);
        return value;
    }

    private void percolateDown(int currIndex){
        if(this.size < 4*currIndex+1){
            return;
        }
        int minIndex = currIndex*4+1;
        for(int i = 2; i<=4 && currIndex*4+i < this.size; i++){
            if(comparator.compare(data[currIndex*4+i], data[minIndex]) < 0)
            {
                minIndex = currIndex*4+i;
            }
        }

        if(comparator.compare(data[minIndex], data[currIndex])>0){
            return;
        }

        E tempVal = data[minIndex];
        data[minIndex] = data[currIndex];
        data[currIndex] = tempVal;
        percolateDown(minIndex);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        E[] temp = (E[]) new Object[capacity];
        this.data=temp;
        this.size=0;

    }
}