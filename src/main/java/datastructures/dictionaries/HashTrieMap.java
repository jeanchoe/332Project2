

package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;
import datastructures.worklists.ArrayStack;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfacesq/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {

    public class HashTrieNode extends TrieNode<ChainingHashTable<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new ChainingHashTable<>(MoveToFrontList::new);
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return iterator();

        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
        HashTrieNode curr = (HashTrieNode) this.root;
        Iterator<A> iterator = key.iterator();

        if (key == null || value == null ) {
            throw new IllegalArgumentException();
        }
        while(iterator.hasNext()){
            A charVal =  iterator.next();
            if(curr.pointers.find(charVal) == null){
                HashTrieNode newNode = new HashTrieNode();
                curr.pointers.insert(charVal, newNode);
            }
            curr = curr.pointers.find(charVal);
        }
        V prevVal = curr.value;
        if(prevVal==null){
            size++;
        }
        curr.value = value;
        return prevVal;
    }

    @Override
    public V find(K key) {
        HashTrieNode curr = (HashTrieNode) this.root;
        Iterator<A> iterator = key.iterator();
        if (key == null) {
            throw new IllegalArgumentException();
        }
        while(iterator.hasNext()){
            A charVal =  iterator.next();
            if(curr.pointers.find(charVal) == null){
                return null;
            }
            curr = curr.pointers.find(charVal);
        }
        return curr.value;
    }

    @Override
    public boolean findPrefix(K key) {
        HashTrieNode curr = (HashTrieNode) this.root;
        Iterator<A> iterator = key.iterator();
        if(key == null) {
            throw new IllegalArgumentException();
        }
        while(iterator.hasNext()){
            A charVal =  iterator.next();
            if(curr.pointers.find(charVal) == null){
                return false;
            }
            curr = curr.pointers.find(charVal);
        }
        return true;
    }

    @Override
    public void delete(K key) {
        if(key == null){
            throw new IllegalArgumentException();
        }
        deleteHelper((HashTrieNode) this.root, key.iterator());
    }

    private boolean deleteHelper(HashTrieNode curr, Iterator iterator){
        A charVal = (A) iterator.next();
        if(!iterator.hasNext()){
            size--;
            curr.value = null;
            return curr.pointers.isEmpty();
        }
        if(deleteHelper(curr.pointers.find(charVal), iterator)){
            curr.pointers.delete(charVal);
            size--;
        }
        if(curr.pointers.find(charVal) == null){
            return false;
        }
        return curr.pointers.isEmpty();
    }
    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}