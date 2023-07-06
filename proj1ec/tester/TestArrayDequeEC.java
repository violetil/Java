package tester;

import static org.junit.Assert.*;
import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {
    @Test
    public void randomizedTestRF() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> cad1 = new ArrayDequeSolution<>();

        String failedMessage = new String("\n");
        for (int i = 0; i < 10000; i += 1) {
            int numberOperations = StdRandom.uniform(0, 2);

            if (numberOperations == 0) {
                /* Call addFirst. */
                int randValue = StdRandom.uniform(0, 100);
                sad1.addFirst(randValue); cad1.addFirst(randValue);
                failedMessage += "addFirst(" + randValue + ")\n";

            } else if (numberOperations == 1 && cad1.size() != 0) {
                /* Call removeFirst. */
                failedMessage += "removeFist()\n";
                assertEquals(failedMessage, cad1.removeFirst(), sad1.removeFirst());
            }
        }
    }

    @Test
    public void randomizedTestRL() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> cad1 = new ArrayDequeSolution<>();

        String failedMessage = new String("\n");
        for (int i = 0; i < 10000; i += 1) {
            int numberOperations = StdRandom.uniform(0, 2);

            if (numberOperations == 0) {
                /* Call addLast. */
                int randValue = StdRandom.uniform(0, 100);
                sad1.addLast(randValue); cad1.addLast(randValue);
                failedMessage += "addLast(" + randValue + ")\n";

            } else if (numberOperations == 1 && cad1.size() != 0) {
                /* Call removeLast. */
                failedMessage += "removeLast()\n";
                assertEquals(failedMessage, cad1.removeLast(), sad1.removeLast());
            }
        }    }
}
