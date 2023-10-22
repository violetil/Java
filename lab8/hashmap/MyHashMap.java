package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Violet
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    /* Size of buckets */
    private int M;
    /* Size of key/val pairs */
    private int N;
    /* Load factor */
    private double loadFactor;

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /** Constructors */
    public MyHashMap() {
        this(16, 0.75d);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75d);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        N = 0;
        M = initialSize;
        loadFactor = maxLoad;
        buckets = createTable(M);

        for (int i = 0; i < M; i++) {
            buckets[i] = createBucket();
        }
    }

    /** Removes all the mappings from this map. */
    @Override
    public void clear() {
        MyHashMap<K, V> temp = new MyHashMap<>();
        buckets = temp.buckets;
        M = temp.M;
        loadFactor = temp.loadFactor;
        N = 0;
    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (key == null) throw new IllegalArgumentException("argument key is null");
        return get(key) != null;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("argument key is null");
        int i = hash(key);

        for (Node n : buckets[i]) {
            if (n.key.equals(key)) return n.value;
        }
        return null;
    }

    /** Hash function, convert the key to the array index. */
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return N;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("argument key is null");
        if (value == null) throw new IllegalArgumentException("argument value is null");

        /* Resize buckets with double capacity */
        if (N > loadFactor * M) {
            resize(2 * M);
        }

        int i = hash(key);
        Node n = createNode(key, value);
        for (Node t : buckets[i]) {
            if (t.key.equals(key)) {
                buckets[i].remove(t);
                N--;
                break;
            }
        }
        N++;
        buckets[i].add(n);
    }

    /**
     *  Resize the hash table to have the given number of chains,
     *  rehashing all keys.
     */
    private void resize (int chains) {
        MyHashMap<K, V> temp = new MyHashMap<>(chains);

        for (K key : this.keySet()) {
            temp.put(key, get(key));
        }
        this.buckets = temp.buckets;
        this.N = temp.N;
        this.M = temp.M;
    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        HashSet<K> s = new HashSet<>();
        for (int i = 0; i < M; i++) {
            for (Node n : buckets[i]) {
                s.add(n.key);
            }
        }
        return s;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public V remove(K key) {
        if (key == null) throw new IllegalArgumentException("remove() argument key is null");
        V ret = get(key);

        if (ret != null) {
            int i = hash(key);
            for (Node n : buckets[i]) {
                if (key.equals(n.key)) {
                    buckets[i].remove(n);
                    N--;
                    break;
                }
            }
            return ret;
        }
        return null;
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    @Override
    public V remove(K key, V value) {
        if (key == null) throw new IllegalArgumentException("remove() argument key is null");
        if (value == null) throw new IllegalArgumentException("remove() argument value is null");

        V ret = get(key);

        if (ret == value) {
            int i = hash(key);
            for (Node n : buckets[i]) {
                if (key.equals(n.key)) {
                    buckets[i].remove(n);
                    N--;
                    break;
                }
            }
            return ret;
        }
        return null;
    }

    /** Return an Iterator of MyHashmap that iterates over the stored keys. */
    @Override
    public Iterator<K> iterator() {
        Queue<K> q = new ArrayDeque<>();
        for (int i = 0; i < M; i++) {
            for (Node n : buckets[i]) {
                q.add(n.key);
            }
        }
        return q.iterator();
    }
}
