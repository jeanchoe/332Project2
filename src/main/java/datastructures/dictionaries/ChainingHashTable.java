package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import java.util.NoSuchElementException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;

import java.util.Iterator;
import java.util.function.Supplier;

/**
 * - You must implement a generic chaining hashtable. You may not
 *   restrict the size of the input domain (i.e., it must accept
 *   any key) or the number of inputs (i.e., it must grow as necessary).
 *
 * - ChainingHashTable should rehash as appropriate (use load factor as shown in lecture!).
 *
 * - ChainingHashTable must resize its capacity into prime numbers via given PRIME_SIZES list.
 *   Past this, it should continue to resize using some other mechanism (primes not necessary).
 *
 * - When implementing your iterator, you should NOT copy every item to another
 *   dictionary/list and return that dictionary/list's iterator.
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private double loadFactor;
    private Dictionary<K,V>[] array;
    private Supplier<Dictionary<K, V>> newChain;
    private int start;
    private double count;
    private int counter;

    static final int[] PRIME_SIZES =
            {11, 23, 47, 97, 193, 389, 773, 1549, 3089, 6173, 12347, 24697, 49393, 98779, 197573, 395147};

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        loadFactor = 0.0;
        array = new Dictionary[7];
        for(int i = 0; i < 7; i ++) {
            array[i] = newChain.get();
        }
        start = 0;
        count = 0.0;
        counter = 0;
    }


    public int size() {
        System.out.println("returning " + counter);
        return counter;
    }

    @Override
    public V insert(K key, V value) {
        if (loadFactor >= 1) {
            array = resize(array);
        }
        int index = Math.abs(key.hashCode() % array.length);
        if (index >= 0) {
            if (array[index] == null) {
                array[index] = newChain.get();
            }
            V returnValue = find(key);
            if (returnValue == null) {
                counter++;
            }
            array[index].insert(key, value);
            count++;
            loadFactor = count / array.length;
            return returnValue;
        } else {
            return null;
        }
    }

    @Override
    public V find(K key) {
        int index = Math.abs(key.hashCode() % array.length);
        if (index >= 0) {
            if (array[index] == null) {
                array[index] = newChain.get();
            }
            return array[index].find(key);
        } else {
            return null;
        }
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        Iterator<Item<K, V>> it = new Iterator<Item<K, V>>() {
            private int currentIndex = 0;
            private Iterator<Item<K, V>> currentIterator = (array[0] == null) ? newChain.get().iterator() : array[0].iterator();

            @Override
            public boolean hasNext() {
                while (currentIndex < array.length && !currentIterator.hasNext()) {
                    currentIndex++;
                    if (currentIndex < array.length && array[currentIndex] != null) {
                        currentIterator = array[currentIndex].iterator();
                    }
                }
                return currentIndex < array.length && currentIterator.hasNext();
            }

            @Override
            public Item<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return currentIterator.next();
            }
        };
        return it;
    }

    private Dictionary<K, V>[] resize(Dictionary<K, V>[] oldArray) {
        int newSize = (start > 15) ? oldArray.length * 2 : PRIME_SIZES[start++];
        Dictionary<K, V>[] newArray = new Dictionary[newSize];
        for (int i = 0; i < newSize; i++) {
            newArray[i] = newChain.get();
        }
        for (Dictionary<K, V> oldDict : oldArray) {
            if (oldDict != null) {
                for (Item<K, V> item : oldDict) {
                    int newIndex = Math.abs(item.key.hashCode() % newSize);
                    newArray[newIndex].insert(item.key, item.value);
                }
            }
        }
        count = counter; // Reset count to match the number of elements after resizing
        loadFactor = count / newSize;
        return newArray;
    }

    /**
     * Temporary fix so that you can debug on IntelliJ properly despite a broken iterator
     * Remove to see proper String representation (inherited from Dictionary)
     */
    @Override
    public String toString() {
        return "ChainingHashTable String representation goes here.";
    }

}