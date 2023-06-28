package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

public class testThreeAndThreeRemove {
    @Test
    public void testThree() {
        AListNoResizing<Integer> correct = new AListNoResizing();
        BuggyAList<Integer> broken = new BuggyAList();

        correct.addLast(5);
        correct.addLast(10);
        correct.addLast(15);

        broken.addLast(5);
        broken.addLast(10);
        broken.addLast(15);

        assertEquals(correct.size(), broken.size());

        assertEquals(correct.removeLast(), broken.removeLast());
        assertEquals(correct.removeLast(), broken.removeLast());
        assertEquals(correct.removeLast(), broken.removeLast());
    }

    @Test
    public void RandomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> bL = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
            int size = L.size();
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                bL.addLast(randVal);
            } else if (operationNumber == 1 && size != 0) {
                // getLast
                int correct = L.getLast(); int broken = bL.getLast();
                assertEquals(correct, broken);
            } else if (operationNumber == 2 && size != 0) {
                // removeLast
                assertEquals(L.removeLast(), bL.removeLast());
            }
        }
    }
}
