package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;

import java.util.Iterator;

/**
 * MoveToFrontList is a deleteless dictionary that uses a linked list to
 * implement a move-to-front heuristic. The list is typically not sorted.
 * New items are added to the front of the list. When find or insert is
 * called on an existing key, the node is moved to the front of the list.
 * The iterator returns elements in the order they are stored in the list.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {

    private Node<K, V> start;
    private Node<K, V> end;

    public MoveToFrontList() {
        start = null;
        end = null;
        size = 0;
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public V insert(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }

        Node<K, V> existingNode = findNode(key);

        if (existingNode != null) {
            V first = existingNode.value;
            existingNode.value = value;
            moveToFront(existingNode);
            return first;
        } else {
            Node<K, V> newNode = new Node<>(key, value, start);
            start = newNode;
            if (end == null) {
                end = newNode;
            }
            size++;
            return null;
        }
    }

    private Node<K, V> findNode(K key) {
        Node<K, V> prev = null;
        Node<K, V> curr = start;

        while (curr != null) {
            if (curr.key.equals(key)) {
                if (prev != null) {
                    prev.next = curr.next;
                    curr.next = start;
                    start = curr;
                    if (end == curr) {
                        end = prev;
                    }
                }
                return curr;
            }
            prev = curr;
            curr = curr.next;
        }
        return null;
    }

    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }

        Node<K, V> node = findNode(key);

        return node != null ? node.value : null;
    }

    private void moveToFront(Node<K, V> node) {
        // Already at front, no need to move.
        if (node == start) {
            return;
        }

        Node<K, V> prev = null;
        Node<K, V> curr = start;

        while (curr != null && curr != node) {
            prev = curr;
            curr = curr.next;
        }

        if (curr == null) {
            // Node not found, should not happen
            return;
        }

        if (prev != null) {
            prev.next = node.next;
        }
        node.next = start;
        start = node;

        if (end == node) {
            end = prev;
        }
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new Iterator<Item<K, V>>() {
            Node<K, V> curr = start;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            public Item<K, V> next() {
                if (hasNext()) {
                    Item<K, V> item = new Item<>(curr.key, curr.value);
                    curr = curr.next;
                    return item;
                } else {
                    return null;
                }
            }
        };
    }
}