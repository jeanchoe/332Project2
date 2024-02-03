package datastructures.worklists;

//import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;
import java.util.NoSuchElementException;




/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private E[] array;
    private int size = 10;
    private int index = 0;
    public ArrayStack() {
        array = (E[])new Object [size];
    }

    @Override
    public void add(E work) {
        if (index == size) {
            size = size * 2;
            E[] newArray = (E[]) new Object[size];
            for (int i = 0; i < index; i++) {
                newArray[i] = array[i];
            }
            array = newArray;
        }

        array[index] = work;
        index++;
    }

    @Override
    public E peek() {
        if (index > 0) {
            return array[index - 1];
        } else if(index == 1){
            throw new NullPointerException("There is only one thing in the stack");
        }

        else {
            throw new NoSuchElementException("There is nothing in the stack");
        }

    }

    @Override
    public E next() {

        if (index > 0) {
            E data = array[index - 1];
            index--;
            return data;
        } else {
            throw new NoSuchElementException("The Stack is Empty");
        }

    }

    @Override
    public int size() {

        return index;
    }

    @Override
    public void clear() {

        index = 0;
        size = 0;
    }
}
