package test;

import deque.LinkedListDeque;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class randomTestLLDeque {
    @Test
    /** Randomized call addFirst, addLast, test the size and isEmpty. */
    public void randomizedSizeEmptyTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        int size = 0;

        int N = 1000000;
        for (int i = 0; i < N; i++) {
            int operationNumber = StdRandom.uniform(0, 6);
            if (operationNumber == 0) {
                /* addFirst */
                int randValue = StdRandom.uniform(0, 100);
                lld1.addFirst(randValue);
                size += 1;
            } else if (operationNumber == 1) {
                /* addLast */
                int randValue = StdRandom.uniform(0, 100);
                lld1.addLast(randValue);
                size += 1;
            } else if (operationNumber == 2) {
                /* size */
                assertEquals("Size should be " + size + ".", size, lld1.size());
            } else if (operationNumber == 3) {
                /* isEmpty */
                assertEquals("Deque should be empty.", size == 0, lld1.isEmpty());
            } else if (operationNumber == 4) {
                /* removeFirst */
                lld1.removeFirst();
                size -= 1;
                if (size < 0) { size = 0; }
            } else if (operationNumber == 5) {
                /* removeLast */
                lld1.removeLast();
                size -= 1;
                if (size < 0) { size = 0; }
            }
        }
    }
}
