package deque;

import java.util.Objects;

public class LinkedListDeque<Item> implements Deque<Item> {
    private static class DequeNode<Item> {
        Item item;
        DequeNode<Item> previous;
        DequeNode<Item> next;

        public DequeNode(Item item, DequeNode<Item> previous, DequeNode<Item> next) {
            this.item = item;
            this.previous = previous;
            this.next = next;
        }
    }

    private int size;
    /*  The first node (if it exists) is always in sentinel.next;
        the last node (if it exists) is always in sentinel.previous.
    */
    private DequeNode<Item> sentinel;

    /** Create an empty deque. */
    public LinkedListDeque() {
        sentinel = new DequeNode<>(null, null, null);
        sentinel.previous = sentinel.next = sentinel;
        size = 0;
    }

    /** Create a Deque with an item. */
    public LinkedListDeque(Item item) {
        sentinel = new DequeNode<>(null, null, null);
        sentinel.previous = sentinel.next = new DequeNode<>(item, sentinel, sentinel);
        size = 1;
    }

    /** Return true if a and b have the same content, otherwise return false. */
    private boolean equalsHelper(LinkedListDeque<?> a, LinkedListDeque<?> b) {
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
        if (o instanceof LinkedListDeque<?>) {
            LinkedListDeque<?> deque = (LinkedListDeque<?>) o;
            if (deque.size() == this.size()) {
                return equalsHelper(this, deque);
            }
        }
        return false;
    }

    /** Returns the hash code value for the Deque. */
    @Override
    public int hashCode() {
        int hashCode = 1;

        DequeNode<Item> d = sentinel.next;
        while (d != sentinel) {
            hashCode = 31 * hashCode + Objects.hashCode(d.item);
            d = d.next;
        }

        return hashCode;
    }

    /** Add the item into the front of Deque. */
    @Override
    public void addFirst(Item item) {
        sentinel.next = new DequeNode<>(item, sentinel, sentinel.next);
        sentinel.next.next.previous = sentinel.next;
        size += 1;
    }

    /** Add the item into the back of Deque. */
    @Override
    public void addLast(Item item) {
        sentinel.previous = new DequeNode<>(item, sentinel.previous, sentinel);
        sentinel.previous.previous.next = sentinel.previous;
        size += 1;
    }

    /** Return the number of items in the deque. */
    @Override
    public int size() {
        return this.size;
    }

    /** Removes and returns the item at the front of the Deque,
     *  if no such item exists, returns null. */
    @Override
    public Item removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        DequeNode<Item> tmp = sentinel.next;
        sentinel.next = tmp.next;
        tmp.next.previous = sentinel;
        size -= 1;
        return tmp.item;
    }

    /** Removes and returns the item at the back of the Deque,
     *  if no such item exists, returns null. */
    @Override
     public Item removeLast() {
         if (this.isEmpty()) {
             return null;
         }
         DequeNode<Item> tmp = sentinel.previous;
         sentinel.previous = tmp.previous;
         tmp.previous.next = sentinel;
         size -= 1;
         return tmp.item;
     }

    /** Get the item at the given index, where 0 is front,
     *  1 is the next item, and so forth. If no such item exists, returns null. */
    @Override
     public Item get(int index) {
        DequeNode<Item> d = sentinel.next;

        /*  Let d continue to advance until the item is found
            (i == index), or doesn't fond until the end of Deque. */
        for (int i = 0; d != sentinel && i < index; i += 1) {
            d = d.next;
        }
        return d.item;
     }

    /** Return null or d.item if d equals to null or index equals to 0,
     *  otherwise recursively call the getRecHelper with
     *  d.next and index - 1. */
     private Item getRecHelper(DequeNode<Item> d, int index) {
         if (d == null) {
             return null;
         } else if (index == 0) {
             return d.item;
         }
         return getRecHelper(d.next, index - 1);
     }

     /** Same as get, but uses recursion. */
     public Item getRecursive(int index) {
        return getRecHelper(sentinel.next, index);
     }

    /** Returns whether the parameter o is equals to the Deque.
     *  o is considered equals if it is a Deque and if it contains the same contents.
     */
    // public boolean equals(Object o);

    /** Prints the items in the Deque from first to last, separated by a space.
     *  Once all the items have been printed, print out a new line. */
    @Override
    public void printDeque() {
        DequeNode<Item> d = sentinel.next;

        /* Let advanced d to iterates the all items. */
        while (d != sentinel) {
            System.out.print(d.item + " ");
            d = d.next;
        }
        System.out.println();
     }
}
