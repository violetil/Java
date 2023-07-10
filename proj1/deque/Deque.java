package deque;

import java.util.Objects;

public interface Deque<Item> extends Iterable<Item> {
    /** Add the item into front of deque. */
    void addFirst(Item item);

    /** Add the item into back of deque. */
    void addLast(Item item);

    /** Removes and returns the front item in deque. */
    Item removeFirst();

    /** Removes and returns the end item in deque. */
    Item removeLast();

    /** Return true if deque is empty, otherwise false. */
    default boolean isEmpty() {
        return size() == 0;
    }

    /** Return the numbers of deque. */
    int size();

    /** Return the ith items in the deque. */
    Item get(int i);

    /** Prints the items in the deque from first to last, separated by space.
     *  Once all items have been printed, prints out a new line. */
    default void printDeque() {
        for (int i = 0; i < size(); i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }
}
