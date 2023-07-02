package deque;

import org.antlr.v4.runtime.misc.ObjectEqualityComparator;

import java.lang.reflect.Array;

public class ArrayDeque<Item> {
    private Item[] items;
    private int nextFirst; // Always front in the first item.
    private int nextLast; // Always back in the last item.
    private int size;

    /** Create an empty array deque. */
    public ArrayDeque() {
        items = (Item[]) new Object[8];
        nextFirst = items.length / 2;
        nextLast = (nextFirst + 1) % items.length;
        size = 0;
    }

    /** Create an array deque with an item. */
    public ArrayDeque(Item item) {
        items = (Item[]) new Object[8];
        nextFirst = items.length / 2;
        nextLast = (nextFirst + 1) % items.length;
        size = 0;

        addFirst(item);
    }

    /** Add the item into the front of array deque,
     *  resizing if out the length of items. */
    public void addFirst(Item item) {
        if (size == items.length) {
            resizing(size * 2);
        }
        items[nextFirst] = item;
        expandNextFirst();
        size += 1;
    }

    /** Add the item into the back of array deque,
     *  resizing if out the length of items. */
    public void addLast(Item item) {
        if (size == items.length) {
            resizing(size * 2);
        }
        items[nextLast] = item;
        expandNextLast();
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

        Item tmp = items[(nextFirst + 1) % items.length];
        items[(nextFirst + 1) % items.length] = null;
        shrinkNextFirst();
        size -= 1;

        if (size > 16 && (double) size / items.length < 0.25) {
            resizing(size);
        }
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

        Item tmp = items[(nextLast - 1 + items.length) % items.length];
        items[(nextLast - 1 + items.length) % items.length] = null;
        shrinkNextLast();
        size -= 1;

        if (size > 16 && (double) size / items.length < 0.25) {
            resizing(size);
        }
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
        if (index >= items.length || index < 0) {
            return null;
        }
        int realIndex = (index + nextFirst + 1) % items.length;
        return items[realIndex];
    }

    /** Prints the items in the deque from first to last, separated by space.
     *  Once all items have been printed, prints out a new line. */
    public void printDeque() {
        int index = (nextFirst + 1) % items.length;
        while (index != nextLast) {
            System.out.print(items[index] + " ");
            index = (index + 1) % items.length;
        }
        System.out.println();
    }


    /**                             ArrayDeque Class Helper Method.                             */


    private void resizing(int capacity) {
        Item[] nItems = (Item[]) new Object[capacity];

        // Copy elements from the old array to the new array
        int oldIndex = (nextFirst + 1) % items.length;
        for (int newIndex = 0; newIndex < size; newIndex++) {
            nItems[newIndex] = items[oldIndex];
            oldIndex = (oldIndex + 1) % items.length;
        }

        items = nItems;
        nextFirst = capacity - 1; // Update nextFirst to the last index of the new array
        nextLast = size; // Update nextLast to the next available index in the new array
    }


    /** Move nextFirst back one bit and
     *  set it to the end of array if it is less than zero.
     */
    private void expandNextFirst() {
        nextFirst -= 1;
        if (nextFirst < 0) {
            nextFirst = items.length - 1;
        }
    }

    /** Move nextFirst forward one bit,
     *  if it exceeds the array length, start from zero.
     */
    private void shrinkNextFirst() {
        nextFirst += 1;
        if (nextFirst == items.length) {
            nextFirst = 0;
        }
    }

    /** Move nextLast forward one bit,
     *  if it exceeds the array length, start from zero.
     */
    private void expandNextLast() {
        nextLast += 1;
        if (nextLast == items.length) {
            nextLast = 0;
        }
    }

    /** Move nextLast back one bit,
     *  set it to the end of array if it is less than zero.
     */
    private void shrinkNextLast() {
        nextLast -= 1;
        if (nextLast < 0) {
            nextLast = items.length - 1;
        }
    }
}
