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
        if(size>0) return true;
        return false;
    }

    @Override
    public void add(E work) {
        if (size==capacity){
            E[] datatemp= (E[]) new Comparable[capacity*2];
            for (int i = 0; i <this.size ; i++) {
                datatemp[i] = data[i];
            }
            this.data = (E[]) new Comparable[capacity*2];
            this.data=datatemp;
            this.capacity*=2;
        }
        this.data[size]=work;
        this.size++;
        int index = size-1;
        int parent = (int) Math.floor((index-1)/4);
        while(data[index].compareTo(data[parent])<0){
            E temp = data[parent];
            data[parent] = data[index];
            data[index] = temp;
            index = parent;
            parent = (int) Math.floor((index-1)/4);
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
        E val= this.data[0];
        data[0] = data[size-1];
        size--;
        percolateDown(0);
        return val;
    }

    private void percolateDown(int currIndex){
        if(4*currIndex+1 > this.size){
            return;
        }
        int minChildIndex = currIndex*4+1;
        for(int i = 2; i<=4 && currIndex*4+i < this.size; i++){
            if (data[currIndex*4+i].compareTo(data[minChildIndex]) < 0)
            {
                minChildIndex = currIndex*4+i;
            }
        }
        if(data[minChildIndex].compareTo(data[currIndex])>0){
            return;
        }

        E temp = data[minChildIndex];
        data[minChildIndex] = data[currIndex];
        data[currIndex] = temp;
        percolateDown(minChildIndex);
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