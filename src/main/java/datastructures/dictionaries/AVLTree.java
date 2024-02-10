package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;

/**
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * <p>
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 * instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode.  This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance).  Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must be correct
 */

public class AVLTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V> {

    private static final int BALANCE_THRESHOLD = 1;

    private class AVLNode extends BSTNode {
        int nodeHeight;

        public AVLNode(K key, V value) {
            super(key, value);
            this.nodeHeight = 0;
        }
    }

    public AVLTree() {
        super();
    }

    private AVLNode insertAndBalance(AVLNode insertedNode, AVLNode currentNode) {
        if (currentNode == null) return insertedNode;

        int compareResult = insertedNode.key.compareTo(currentNode.key);
        if (compareResult < 0) {
            currentNode.children[0] = insertAndBalance(insertedNode, (AVLNode) currentNode.children[0]);
        } else if (compareResult > 0) {
            currentNode.children[1] = insertAndBalance(insertedNode, (AVLNode) currentNode.children[1]);
        } else {
            currentNode.value = insertedNode.value;
            return currentNode; // No change in height, return immediately
        }

        updateNodeHeight(currentNode);
        return rebalance(currentNode);
    }

    private void updateNodeHeight(AVLNode node) {
        node.nodeHeight = Math.max(getNodeHeight((AVLNode) node.children[0]), getNodeHeight((AVLNode) node.children[1])) + 1;
    }

    private AVLNode rebalance(AVLNode node) {
        int balanceFactor = getNodeHeight((AVLNode) node.children[0]) - getNodeHeight((AVLNode) node.children[1]);

        if (balanceFactor > BALANCE_THRESHOLD) {
            if (getNodeHeight((AVLNode) node.children[0].children[0]) >= getNodeHeight((AVLNode) node.children[0].children[1])) {
                return rotateRight(node);
            } else {
                return doubleRotateRight(node);
            }
        } else if (balanceFactor < -BALANCE_THRESHOLD) {
            if (getNodeHeight((AVLNode) node.children[1].children[1]) >= getNodeHeight((AVLNode) node.children[1].children[0])) {
                return rotateLeft(node);
            } else {
                return doubleRotateLeft(node);
            }
        }

        return node;
    }

    private AVLNode rotateRight(AVLNode node) {
        AVLNode tempNode = (AVLNode) node.children[0];
        node.children[0] = tempNode.children[1];
        tempNode.children[1] = node;
        updateNodeHeight(node);
        updateNodeHeight(tempNode);
        return tempNode;
    }

    private AVLNode rotateLeft(AVLNode node) {
        AVLNode tempNode = (AVLNode) node.children[1];
        node.children[1] = tempNode.children[0];
        tempNode.children[0] = node;
        updateNodeHeight(node);
        updateNodeHeight(tempNode);
        return tempNode;
    }

    private AVLNode doubleRotateRight(AVLNode node) {
        node.children[0] = rotateLeft((AVLNode) node.children[0]);
        return rotateRight(node);
    }

    private AVLNode doubleRotateLeft(AVLNode node) {
        node.children[1] = rotateRight((AVLNode) node.children[1]);
        return rotateLeft(node);
    }

    private int getNodeHeight(AVLNode node) {
        return node == null ? -1 : node.nodeHeight;
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) throw new IllegalArgumentException();
        V existingValue = this.find(key);
        AVLNode insertedNode = new AVLNode(key, value);
        if (existingValue == null) {
            this.size++;
            existingValue = value;
        }
        this.root = insertAndBalance(insertedNode, (AVLNode) this.root);
        return existingValue;
    }
}
