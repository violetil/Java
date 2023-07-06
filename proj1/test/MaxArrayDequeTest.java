package test;

import java.util.Comparator;
import deque.MaxArrayDeque;
import org.junit.Test;
import static org.junit.Assert.*;

public class MaxArrayDequeTest {
    private static class testMaxComparator implements Comparator<Integer> {
        public int compare(Integer a, Integer b) {
            return a - b;
        }
    }

    private static class testMinComparator implements Comparator<Integer> {
        public int compare(Integer a, Integer b) {
            return b - a;
        }
    }

    /** Call max in empty maxArrayDeque, add some values and call the max method. */
    @Test
    public void testMaxNoPara() {

        testMaxComparator c = new testMaxComparator();
        MaxArrayDeque<Integer> mad1 = new MaxArrayDeque<>(c);

        assertEquals("Empty mad1 call max should be null.", null, mad1.max());

        mad1.addLast(3);
        mad1.addLast(10);
        mad1.addLast(-5);
        mad1.addLast(94);
        assertEquals("The maximum element not right.", (Integer) 94, mad1.max());
    }

    /** Call max with the parameter of minComparator in empty deque,
     *  add some values and call this max again.
     */
    @Test
    public void testMaxOnePara() {

        testMinComparator c = new testMinComparator();
        MaxArrayDeque<Integer> mad1 = new MaxArrayDeque<>(c);

        assertEquals("Empty mad1 call max should be null.", null, mad1.max(c));

        mad1.addLast(3);
        mad1.addLast(10);
        mad1.addLast(-5);
        mad1.addLast(94);
        assertEquals("The minimum element not right.", (Integer) (-5), mad1.max(c));
    }
}
