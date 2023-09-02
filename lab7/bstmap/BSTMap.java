package bstmap;

import edu.princeton.cs.algs4.BST;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/** A BSTMap using a BST (Binary Search Tree) as its core data structure.
 *
 *  @author violet
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    /** Print out BSTMap in order of increasing key. */
    public void printInOrder() {
        printInOrder(findMin(root));
    }

    private void printInOrder(BSTNode x) {
        if (x == null) return;
        System.out.print(x.value + " ");
        printInOrder(findSuccessor(x));
    }

    /** Removes all the mappings from this map. */
    @Override
    public void clear() {
        root = null;
    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (key == null) throw new IllegalArgumentException("argument containsKey() is null");
        return containsKey(root, key);
    }

    private boolean containsKey(BSTNode x, K key) {
        if (x == null) return false;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return containsKey(x.left, key);
        else if (cmp > 0) return containsKey(x.right, key);
        else return true;
    }

    /** Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key. */
    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(BSTNode x, K key) {
        if (key == null) throw new IllegalArgumentException("can't get() with a null key");
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)        return get(x.left, key);
        else if (cmp > 0)   return get(x.right, key);
        else                return x.value;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size(root);
    }

    private int size(BSTNode x) {
        if (x == null) return 0;
        return x.size;
    }

    /** Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("argument key in put() is null");
        root = put(root, null, key, value);
    }

    private BSTNode put (BSTNode x, BSTNode parent, K key, V value) {
        if (x == null) return new BSTNode(key, value, parent, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, x, key, value);
        else if (cmp > 0) x.right = put(x.right, x, key, value);
        else x.value = value;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /** Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    @Override
    public Set<K> keySet() {
        HashSet<K> s = new HashSet<>();
        BSTMapIter iterator = new BSTMapIter();
        while (iterator.hasNext()) {
            s.add(iterator.next());
        }
        return s;
    }

    /** Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        if (key == null) throw new IllegalArgumentException("call remove() with null key");

        V res = get(key);
        root = remove(root, key);
        return res;
    }

    private BSTNode remove(BSTNode x, K key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = remove(x.left, key);
        else if (cmp > 0) x.right = remove(x.right, key);
        else {
            if (x.right == null) {
                if (x.left != null) x.left.parent = x.parent;
                return x.left;
            }
            if (x.left == null) {
                if (x.right != null) x.right.parent = x.parent;
                return x.right;
            }
            BSTNode t = x;

            BSTNode min = findMin(x.right);
            min.parent = x.parent;
            x = min;

            x.right = removeMin(t.right);
            t.left.parent = x;
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    private BSTNode removeMin(BSTNode x) {
        if (x == null) return null;
        if (x.left == null) {
            if (x.right != null) x.right.parent = x.parent;
            return x.right;
        }
        x.left = removeMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /** Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if (key == null) throw new IllegalArgumentException("call remove() with null key");

        V res = get(key);
        if (value.equals(res)) return remove(key);
        else return null;
    }

    /** Find the minimum from this node. Return null if this node is null. */
    private BSTNode findMin(BSTNode x) {
        if (x == null) return null;
        if (x.left == null) return x;
        else return findMin(x.left);
    }

    /** Find the successor for this node. */
    private BSTNode findSuccessor(BSTNode x) {
        if (x == null) {
            return null;
        }
        if (x.right != null) {
            return findMin(x.right);
        }
        // If there is no right subtree, move up the tree until we find a parent node
        // where the current node is in the left subtree.
        BSTNode parent = x.parent;
        BSTNode c = x;
        while (parent != null && c == parent.right) {
            c = parent;
            parent = c.parent;
        }
        return parent;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIter();
    }

    /** Keys and values are stored in a binary search tree of BSTNode objects.
     *  This variable stores the first pair in this binary search tree. */
    private BSTNode root;

    /** Represent one node in the BST that store key-value pairs. */
    private class BSTNode {
        /** Store KEY as the key in this key-value pair, VAL as the value,
         *  LEFT and RIGHT as the left and right node the BST. */
        BSTNode(K key, V value, BSTNode parent, int size) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.size = size;
        }

        /** Store number of node in subtree. */
        int size;
        /** Store the key of the key-value pair of this node in the BST. */
        K key;
        /** Store the value of the key-value of this node in the BST. */
        V value;
        /** Store the left, right and parent node in the BST. */
        BSTNode left, right, parent;
    }

    /** An iterator that iterates over the keys of dictionary. */
    private class BSTMapIter implements Iterator<K> {
        /** Create new BSTMapIter by setting cur to first node in
         *  the binary search tree that store first key-value pair. */
        public BSTMapIter() {
            cur = findMin(root);
        }

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public K next() {
            K key = cur.key;
            cur = findSuccessor(cur);
            return key;
        }

        /** Store the current key-value pair. */
        private BSTNode cur;
    }
}
