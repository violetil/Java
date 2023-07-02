package test;

import deque.ArrayDeque;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {
    @Test
    public void smallIntTest() {
        ArrayDeque<Integer> t1 = new ArrayDeque<>();

        assertTrue("The t1 deque at first should be empty.", t1.isEmpty());
        assertEquals(0, t1.size());

        t1.addFirst(3);
        assertFalse("The deque t1 should not empty after addFirst.", t1.isEmpty());
        assertEquals(1, t1.size());

        t1.addFirst(2);
        assertFalse("The deque t1 should not empty after addFirst.", t1.isEmpty());
        assertEquals(2, t1.size());
        assertEquals(2, (int) t1.get(0));

        t1.addLast(5);
        assertFalse("The deque t1 should not empty after addFirst.", t1.isEmpty());
        assertEquals(3, t1.size());
        assertEquals(2, (int) t1.get(0));
    }

    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    @Test
    public void addIsEmptySizeTest() {
        ArrayDeque<String> ad1 = new ArrayDeque<String>();

        assertTrue("A newly initialized ADeque should be empty", ad1.isEmpty());
        ad1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, ad1.size());
        assertFalse("ad1 should now contain 1 item", ad1.isEmpty());

        ad1.addLast("middle");
        assertEquals(2, ad1.size());

        ad1.addLast("back");
        assertEquals(3, ad1.size());

        System.out.println("Printing out deque: ");
        ad1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that da is empty afterwards. */
    public void addRemoveTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        // should be empty
        assertTrue("ad1 should be empty upon initialization", ad1.isEmpty());

        ad1.addFirst(10);
        // should not be empty
        assertFalse("ad1 should contain 1 item", ad1.isEmpty());

        ad1.removeFirst();
        // should be empty
        assertTrue("lld1 should be empty after removal", ad1.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(3);

        ad1.removeLast();
        ad1.removeFirst();
        ad1.removeLast();
        ad1.removeFirst();

        int size = ad1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create ArrayDeques with different parameterized types*/
    public void multipleParamTest() {
        ArrayDeque<String>  ad1 = new ArrayDeque<String>();
        ArrayDeque<Double>  ad2 = new ArrayDeque<Double>();
        ArrayDeque<Boolean> ad3 = new ArrayDeque<Boolean>();

        ad1.addFirst("string");
        ad2.addFirst(3.14159);
        ad3.addFirst(true);

        String s = ad1.removeFirst();
        double d = ad2.removeFirst();
        boolean b = ad3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty ArrayDeque. */
    public void emptyNullReturnTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();

        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, ad1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, ad1.removeLast());
    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigADequeTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<Integer>();
        for (int i = 0; i < 100000; i++) {
            ad1.addLast(i);
        }

        for (Integer i = 0; i < 50000; i++) {
            assertEquals("Should have the same value", i, ad1.removeFirst());
        }

        for (Integer i = 99999; i > 50000; i--) {
            assertEquals("Should have the same value", i, ad1.removeLast());
        }
    }

    @Test
    /** Add small number of elements and test the get method. */
    public void getTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 0; i < 100; i++) {
            ad1.addLast(i);
        }

        for (Integer i = 0; i < 100; i++) {
            assertEquals("Should get the same value.", i, ad1.get(i));
        }
    }
}
