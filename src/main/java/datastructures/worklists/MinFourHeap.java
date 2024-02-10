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
    private E[] elements;
    private int currentSize;
    private int totalCapacity;

    private Comparator<E> elementComparator;
    public MinFourHeap(Comparator<E> c) {
        this.currentSize = 0;
        this.totalCapacity = 10;
        this.elements = (E[]) new Object[totalCapacity];
        elementComparator = c;
    }

    @Override
    public boolean hasWork() {
        return currentSize > 0;
    }

    @Override
    public void add(E element) {
        if (currentSize == totalCapacity) {
            E[] tempData = (E[]) new Object[totalCapacity * 2];
            System.arraycopy(elements, 0, tempData, 0, this.currentSize);
            this.elements = tempData;
            this.totalCapacity *= 2;
        }
        this.elements[currentSize] = element;
        this.currentSize++;
        int index = currentSize - 1;
        int parent = (int) Math.floor((index - 1) / 4);
        while (elementComparator.compare(elements[index], elements[parent]) < 0) {
            E temp = elements[parent];
            elements[parent] = elements[index];
            elements[index] = temp;
            index = parent;
            parent = (int) Math.floor((index - 1) / 4);
        }
    }

    @Override
    public E peek() {
        if (!hasWork()) throw new NoSuchElementException();
        return this.elements[0];
    }

    @Override
    public E next() {
        if (!hasWork()) throw new NoSuchElementException();
        E value = this.elements[0];
        elements[0] = elements[currentSize - 1];
        currentSize--;
        percolateDown(0);
        return value;
    }

    private void percolateDown(int currentIndex) {
        if (4 * currentIndex + 1 > this.currentSize) {
            return;
        }
        int minChildIndex = currentIndex * 4 + 1;
        for (int i = 2; i <= 4 && currentIndex * 4 + i < this.currentSize; i++) {
            if (elementComparator.compare(elements[currentIndex * 4 + i], elements[minChildIndex]) < 0) {
                minChildIndex = currentIndex * 4 + i;
            }
        }

        if (elementComparator.compare(elements[minChildIndex], elements[currentIndex]) > 0) {
            return;
        }

        E temp = elements[minChildIndex];
        elements[minChildIndex] = elements[currentIndex];
        elements[currentIndex] = temp;
        percolateDown(minChildIndex);
    }

    @Override
    public int size() {
        return this.currentSize;
    }

    @Override
    public void clear() {
        E[] temp = (E[]) new Object[totalCapacity];
        this.elements = temp;
        this.currentSize = 0;
    }
}