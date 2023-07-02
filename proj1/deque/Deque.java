package deque;

public interface Deque<Item> {
    /** Add the item into front of deque. */
    public void addFirst(Item item);

    /** Add the item into back of deque. */
    public void addLast(Item item);

    /** Removes and returns the front item in deque. */
    public Item removeFirst();

    /** Removes and returns the end item in deque. */
    public Item removeLast();

    /** Return true if deque is empty, otherwise false. */
    public boolean isEmpty();

    /** Return the numbers of deque. */
    public int size();

    /** Return the ith items in the deque. */
    public Item get(int i);

    /** Prints the items in the deque from first to last, separated by space.
     *  Once all items have been printed, prints out a new line. */
    public void printDeque();
}
