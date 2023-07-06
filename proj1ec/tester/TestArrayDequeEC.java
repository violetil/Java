package tester;

import static org.junit.Assert.*;
import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {
    @Test
    public void randTest() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> cad1 = new ArrayDequeSolution<>();

        for (int i = 0; i < 10000; i += 1) {
            int numberOperations = StdRandom.uniform(0, 5);

            if (numberOperations == 0) {
                int randValue = StdRandom.uniform(0, 100);
                sad1.addFirst(randValue);
                cad1.addFirst(randValue);
            } else if (numberOperations == 1) {
                int randValue = StdRandom.uniform(0, 100);
                sad1.addLast(randValue);
                cad1.addLast(randValue);
            } else if (numberOperations == 2) {
                /* size */
                assertEquals("Size is not correct.", cad1.size(), sad1.size());
            } else if (numberOperations == 3 && cad1.size() != 0) {
                /* removeFirst */
                assertEquals("RemoveFirst is not correct.", cad1.removeFirst(), sad1.removeFirst());
            } else if (numberOperations == 4 && cad1.size() != 0) {
                assertEquals("RemoveLast is not correct.", cad1.removeLast(), sad1.removeLast());
            }
        }
    }
}
