package deque;

import java.util.*;

public class ArrayDeque<Item> implements Deque<Item>{
    private Item[] items;
    private int first;
    private int last;
    private int size;

    /** Create an empty array deque. */
    public ArrayDeque() {
        items = (Item[]) new Object[8];
        first = 0; last = items.length - 1;
        size = 0;
    }

    /** Create an array deque with an item. */
    public ArrayDeque(Item item) {
        items = (Item[]) new Object[8];
        first = 0; last = items.length - 1;
        size = 0;

        addFirst(item);
    }

    /** Return true if a and b have the same content, otherwise return false. */
    private boolean equalsHelper(ArrayDeque<?> a, ArrayDeque<?> b) {
        for (int i = 0; i < a.size(); i++) {
            if (!Objects.equals(a.get(i), b.get(i))) {
                return false;
            }
        }
        return true;
    }

    /** Returns whether the parameter o is equal to the Deque.
     *  o is considered equal if it is a Deque and if it contains the same contents. */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        /* Check the type is ArrayDeque. */
        if (o instanceof ArrayDeque ad) {
            /* Returns false if the size is not the same. */
            if (size != ad.size) {
                return false;
            }

            /* Check all elements if is the same. */
            for (int i = 0; i < size; i += 1) {
                if (! (items[i].equals(ad.items[i]))) {
                    return false;
                }
            }

            return true;
        }
        /* Type is not is LinkedListDeque. */
        return false;
    }

    /** Return the point forward one bit. */
    private int forwardPoint(int point) {
        return (point + 1) % items.length;
    }

    /** Return the point back one bit. */
    private int backPoint(int point) {
        return (point - 1 + items.length) % items.length;
    }

    /** Resize items with new capacity,
     *  and copy all elements into new items.
     */
    private void resize(int capacity) {
        Item[] nItems = (Item[]) new Object[capacity];
        int oldIndex = first; int newIndex = first % nItems.length;
        first = newIndex;
        for (int i = 0; i < size; i++) {
            nItems[newIndex] = items[oldIndex];
            oldIndex = (oldIndex + 1) % items.length;
            newIndex = (newIndex + 1) % nItems.length;
        }
        items = nItems;
        last = backPoint(newIndex);
    }

    /** Add the item into the front of array deque,
     *  resizing if out the length of items. */
    @Override
    public void addFirst(Item item) {
        if (size == items.length) {
            resize(size * 2);
        }
        first = backPoint(first);
        items[first] = item;
        size += 1;
    }

    /** Add the item into the back of array deque,
     *  resizing if out the length of items. */
    @Override
    public void addLast(Item item) {
        if (size == items.length) {
            resize(size * 2);
        }
        last = forwardPoint(last);
        items[last] = item;
        size += 1;
    }

    /** Removes and returns the front item in the array deque,
     *  if no such item exists, returns null.
     *  Resizing items when the use factor less than 25%.
     *  Use factor: size / items.length. */
    @Override
    public Item removeFirst() {
        if (isEmpty()) {
            return null;
        }

        Item tmp = items[first];
        items[first] = null;
        first = forwardPoint(first);
        size -= 1;

        if (size > 16 && (double) size / items.length < 0.25) {
            resize(size);
        }
        return tmp;
    }

    /** Removes and returns the back item in the array deque,
     *  if no such item exists, returns null.
     *  Resizing items when the use factor less than 25%.
     *  Use factor: size / items.length. */
    @Override
    public Item removeLast() {
        if (isEmpty()) {
            return null;
        }

        Item tmp = items[last];
        items[last] = null;
        last = backPoint(last);
        size -= 1;

        if (size > 16 && (double) size / items.length < 0.25) {
            resize(size);
        }
        return tmp;
    }

    /** Returns the number of items in the array deque. */
    @Override
    public int size() {
        return this.size;
    }

    /** Get the item at the given index, where 0 is front,
     *  1 is the next item, and so forth. If no such item exists, returns null. */
    @Override
    public Item get(int index) {
        if (index >= items.length || index < 0) {
            return null;
        }
        int realIndex = (index + first) % items.length;
        return items[realIndex];
    }

    /** Returns an iterator. */
    public Iterator<Item> iterator() {
        return new ADIterator<Item>();
    }

    private class ADIterator<Item> implements Iterator<Item> {
        private int curPos;
        private int restItems;

        public ADIterator() {
            curPos = first;
            restItems = size;
        }

        public boolean hasNext() {
            return restItems != 0;
        }

        public Item next() {
            Item res = (Item) items[curPos];
            curPos = forwardPoint(curPos);
            restItems -= 1;
            return res;
        }

    }

    public String toString() {
        List<String> listOfString = new ArrayList<>();
        for (Item i : this) {
            listOfString.add(i.toString());
        }
        return "{" + String.join(", ", listOfString) + "}";
    }

    public static <T> ArrayDeque<T> of(T... stuff) {
        ArrayDeque<T> returnDeque = new ArrayDeque<>();
        for (T x : stuff) {
            returnDeque.addLast(x);
        }
        return returnDeque;
    }

    public static void main(String[] args) {
        ArrayDeque<String> ad = ArrayDeque.of("Beijing", "Tokyo", "NewYork");

        System.out.println(ad);

        ArrayDeque<String> ad1 = ArrayDeque.of("Beijing", "Tokyo", "NewYork");

        System.out.println(ad.equals(ad1));
    }
}
