package deque;

import java.lang.reflect.Array;

public class ArrayDeque<Item> {
    private Item[] items = (Item[]) new Object();
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

    /** Resizing the array items with the specified capacity,
     *  copy the length of items start at the source location and the length
     *  into destination array start at the specified location. */
     private void resizing(int srcPos, int destPos, int length, int capacity) {
         Item[] newItems = (Item[]) new Object[capacity];
         System.arraycopy(items, srcPos, newItems, destPos, length);
         items = newItems;
     }

    /** Resizing array items with the specified capacity,
     *  and copy the whole items into destination array start at 1 location. */
     private void resizeAddFirst(int capacity) {
        resizing(0, 1, items.length, capacity);
     }

    /** Resizing array items with the capacity,
     *  and copy the whole items into destination array start at 0 location. */
     private void resizeAddLast(int capacity) {
         resizing(0, 0, items.length, capacity);
     }

    /** Add the item into the front of array deque,
     *  resizing if out the length of items. */
    public void addFirst(Item item) {
        if (this.size == this.items.length) {
            resizeAddFirst(items.length * 2);
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
            resizeAddLast(items.length * 2);
        }
        items[size] = item;
        size += 1;
    }

    /** Resizing the items with specified capacity,
     *  copy the size - 1 of items start at the 1 location
     *  into the destination array at the 0 location. */
     private void resizeRemoveFirst(int capacity) {
         resizing(1, 0, size - 1, capacity);
     }

    /** Resizing the items with specified capacity,
     *  copy the size - 1 of items start at the 0 location
     *  into the destination array at the 0 location. */
     private void resizeRemoveLast(int capacity) {
         resizing(0, 0, size - 1, capacity);
     }

    /** Removes and returns the front item in the array deque,
     *  if no such item exists, returns null.
     *  Resizing the length of items when the length bigger than 8. */
    public Item removeFirst() {
        Item tmp = items[0];
        if (size > 8) {
            resizeRemoveFirst(size - 1);
        } else {
            resizeRemoveFirst(size);
        }
        size -= 1;
        return tmp;
    }

    /** Removes and returns the back item in the array deque,
     *  if no such item exists, returns null.
     *  Resizing the length of items when the length less than 8. */
    public Item removeLast() {
        Item tmp = items[0];
        if (size > 8) {
            resizeRemoveLast(size - 1);
        } else {
            resizeRemoveLast(size);
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
}
