package datastructures.worklists;

import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {

    private Node<E> front;
    private Node<E> back;
    public int size;
    public ListFIFOQueue() { this.size = 0; }

    @Override
    public void add(E work) {

        if (front == null) {
            front = new Node<>(work);
            back = front;
        } else {
            back.next = new Node<>(work);
            back = back.next;
        }
        size++;
    }
    @Override
    public E peek() {
        if(!hasWork()) throw new NoSuchElementException();
        return front.data;
    }

    @Override
    public E next() {
        if(!hasWork()) throw new NoSuchElementException();
        Node<E> thing = new Node<>(front.data);
        front = front.next;
        size--;
        return thing.data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        front = null;
        size = 0;
    }

    public static class Node<E> {
        public final E data;
        public Node<E> next;

        public Node(E data) {
            this(data, null);
        }

        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }
}

