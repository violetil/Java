package deque;

import java.lang.reflect.Array;

public class ArrayDeque<Item> {
    private Item[] items;
    private int size;

    /** Create an empty array deque. */
    public ArrayDeque() {
        items = (Item[]) new Object[8];
        size = 0;
    }

    /** Create an array deque with an item. */
    public ArrayDeque(Item item) {
        items = (Item[]) new Object[8];
        items[0] = item;
        size = 1;
    }

    /** Add the item into the front of array deque,
     *  resizing if out the length of items. */
    public void addFirst(Item item) {
        if (size == items.length) {
            resizeAddFirst(size * 2); // Faster than size+1
        } else {
            resizeAddFirst(items.length);
        }
        items[0] = item;
        size += 1;
    }

    /** Add the item into the back of array deque,
     *  resizing if out the length of items. */
    public void addLast(Item item) {
        if (this.size == this.items.length) {
            resizeAddLast(size * 2); // Faster than size+1
        }
        items[size] = item;
        size += 1;
    }

    /** Removes and returns the front item in the array deque,
     *  if no such item exists, returns null.
     *  Resizing items when the use factor less than 25%.
     *  Use factor: size / items.length. */
    public Item removeFirst() {
        if (isEmpty()) {
            return null;
        }

        Item tmp = items[0];
        if (size < 16) {
            resizeRemoveFirst(16);
        } else if ((double) size / items.length < 0.25) {
            resizeRemoveFirst(size);
        } else {
            resizeRemoveFirst(items.length);
        }
        size -= 1;
        return tmp;
    }

    /** Removes and returns the back item in the array deque,
     *  if no such item exists, returns null.
     *  Resizing items when the use factor less than 25%.
     *  Use factor: size / items.length. */
    public Item removeLast() {
        if (isEmpty()) {
            return null;
        }

        Item tmp = items[size - 1];
        if (size < 16) {
            resizeRemoveLast(16);
        } else if ((double) size / items.length < 0.25) {
            resizeRemoveLast(size);
        } else {
            resizeRemoveLast(items.length);
        }
        size -= 1;
        return tmp;
    }

    /** Return true if the array deque is empty, otherwise false. */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /** Returns the number of items in the array deque. */
    public int size() {
        return this.size;
    }

    /** Get the item at the given index, where 0 is front,
     *  1 is the next item, and so forth. If no such item exists, returns null. */
    public Item get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return items[index];
    }

    /** Prints the items in the deque from first to last, separated by space.
     *  Once all items have been printed, prints out a new line. */
    public void printDeque() {
        for (int i = 0; i < size; i += 1) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }


    /**                             ArrayDeque Class Helper Method.                             */


    /** Resizing the array items with the new capacity,
     *  and copy the length of items start at the source position
     *  into the new items start at the target position. */
    private void resizing(int srcPos, int destPos, int length, int capacity) {
        Item[] newItems = (Item[]) new Object[capacity];
        System.arraycopy(items, srcPos, newItems, destPos, length);
        items = newItems;
    }

    /** Resizing array items with the new capacity,
     *  and move all items back one position. */
    private void resizeAddFirst(int capacity) {
        resizing(0, 1, size, capacity);
    }

    /** Resizing array items with the new capacity,
     *  and copy all items into new items. */
    private void resizeAddLast(int capacity) {
        resizing(0, 0, size, capacity);
    }

    /** Resizing the items with new capacity,
     *  move all items forward except the first. */
    private void resizeRemoveFirst(int capacity) {
        resizing(1, 0, size - 1, capacity);
    }

    /** Resizing the items with new capacity,
     *  move all items forward expect the last. */
    private void resizeRemoveLast(int capacity) {
        resizing(0, 0, size - 1, capacity);
    }
}
