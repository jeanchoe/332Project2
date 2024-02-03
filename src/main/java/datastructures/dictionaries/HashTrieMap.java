

package datastructures.dictionaries;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Map<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new HashMap<A, HashTrieNode>();
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return pointers.entrySet().iterator();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @Override
    public V insert(K key, V value) {
        HashTrieNode curr = (HashTrieNode) this.root;

        if(key == null || value == null) {
            throw new IllegalArgumentException();
        } else {
            for (A letter : key) {
                if (!curr.pointers.containsKey(letter)) {
                    curr.pointers.put(letter, new HashTrieNode());
                }
                curr = curr.pointers.get(letter);
            }
            V oldValue = curr.value;
            curr.value = value;
            size++;
            return oldValue;
        }
    }

    @Override
    public V find(K key) {
        HashTrieNode curr = (HashTrieNode) this.root;
        if(key == null){
            throw new IllegalArgumentException();
        } else {
            for (A letter : key) {
                if (!curr.pointers.containsKey(letter)) {
                    return null;
                }
                curr = curr.pointers.get(letter);
            }
            return curr.value;
        }
    }

    @Override
    public boolean findPrefix(K key) {
        HashTrieNode curr = (HashTrieNode) this.root;
        if(key == null){
            throw new IllegalArgumentException();
        } else {
            for (A letter : key) {
                if (!curr.pointers.containsKey(letter)) {
                    return false;
                }
                curr = curr.pointers.get(letter);
            }
            return true;
        }
    }
    @Override
    public void delete(K key) {
        if(key == null){
            throw new IllegalArgumentException();
        } else {
            delRec((HashTrieNode) this.root, key.iterator(), null);
        }
    }

    private boolean delRec(HashTrieNode node, Iterator<A> initialKey, HashTrieNode branch) {
        if (!initialKey.hasNext()) {
            if (node != null) {
                node.value = null;
                return node.pointers.isEmpty() && node.value == null;
            }
            return false;
        }

        A letter = initialKey.next();
        HashTrieNode leaf = node.pointers.get(letter);

        if (leaf != null && delRec(leaf, initialKey, node)) {
            node.pointers.remove(letter);
            return node.pointers.isEmpty() && node.value == null;
        }
        return false;
    }

    @Override
    public void clear() {
        this.root = new HashTrieNode();
    }
}


