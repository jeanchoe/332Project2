package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import javax.swing.*;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {

    private E[] elements;
    private int totalSize;
    private int headIndex;
    private int tailIndex;

    @SuppressWarnings("unchecked")
    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        elements = (E[]) new Comparable[capacity];
        totalSize = 0;
        headIndex = -1;
        tailIndex = -1;
    }

    @Override
    public void add(E element) {
        if(isFull())
            throw new IllegalStateException();

        if (!hasWork()){
            headIndex = 0;
            tailIndex = 0;
        }
        else
            tailIndex = (tailIndex + 1) % elements.length;

        elements[tailIndex] = element;
        totalSize++;
    }

    @Override
    public E peek() {
        return peek(0);
    }

    @Override
    public E peek(int index) {
        if(!hasWork()){
            throw new NoSuchElementException();
        }
        else if(index < 0 || index >= size()){
            throw new IndexOutOfBoundsException();
        }
        int position = (headIndex + index) % elements.length;
        return elements[position];
    }

    @Override
    public E next() {
        if(!hasWork())
            throw new NoSuchElementException();

        E element = elements[headIndex];
        headIndex = (headIndex + 1) % elements.length;
        totalSize--;
        return element;

    }

    @Override
    public void update(int index, E value) {
        if(!hasWork())
            throw new NoSuchElementException();
        if(index < 0 || index >= size())
            throw new IndexOutOfBoundsException();

        elements[(headIndex + index) % capacity()] = value;
    }

    @Override
    public int size() {
        return this.totalSize;
    }

    @Override
    public void clear() {
        headIndex = 0;
        tailIndex = 0;
        totalSize = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {

        int curr = 0;

        while(curr < this.size() && curr < other.size()){
            if(this.peek(curr).compareTo(other.peek(curr)) != 0)
                return this.peek(curr).compareTo(other.peek(curr));

            curr++;
        }

        return this.size() - other.size();

    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {

            FixedSizeFIFOWorkList<E> list2 = (FixedSizeFIFOWorkList<E>) obj;

            // Check if they are the same size
            if(this.size() != list2.size())
                return false;

            // Check all elements are the same
            for(int i = 0; i < this.size(); i++){
                if(this.peek(i) == list2.peek(i)){
                    return true;
                } else {
                    return false;
                }

            }

            return true;
        }
    }

    @Override
    public int hashCode() {
        int result = this.peek().hashCode();
        for(int i = 1; i < this.size(); i++){
            result*= 37 + peek(i).hashCode();
        }
        return result;
    }
}