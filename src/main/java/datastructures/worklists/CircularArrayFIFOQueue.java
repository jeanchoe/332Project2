package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E> extends FixedSizeFIFOWorkList<E> {
    private E[] array;
    private int front;
    private int back;
    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        this.array = (E[]) new Object[capacity];
        this.front = 0;
        this.back = 0;
    }

    @Override
    public void add(E work) {
        if (size() == array.length) {
            throw new IllegalStateException();
        }

        array[back % array.length] = work;
        back++;
    }

    @Override
    public E peek() {
        if (array[front % array.length] == null) {
            throw new NoSuchElementException("Empty");
        }

        return array[front % array.length];
    }

    @Override
    public E peek(int i) {
        if (array[front % array.length] == null) {
            throw new NoSuchElementException("Empty");
        }

        if (i < 0 || i >= array.length) {
            throw new IndexOutOfBoundsException();
        }

        int index = (front + i) % array.length;
        return array[index];
    }
    @Override
    public E next() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }

        E data = array[front % array.length];
        array[front % array.length] = null;
        front++;
        if (size() == 0) {
            back = 0;
            front = 0;
        }
        return data;
    }


    @Override
    public void update(int i, E value) {
        if (array[front % array.length] == null) {
            throw new NoSuchElementException();
        }

        if (i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException();
        }

        int index = (front + i) % array.length;
        array[index] = value;
    }


    @Override
    public int size() {
        return back - front;
    }

    @Override
    public void clear() {
        this.array = (E[]) new Object[array.length];
        this.front = 0;
        this.back = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
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
            // Uncomment the line below for p2 when you implement equals
            // FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;

            // Your code goes here

            throw new NotYetImplementedException();
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }
}