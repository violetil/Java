package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        int nTest = 10000;
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();

        for (int size = 1000; size <= 128000; size *= 2) {
            /* Create a SLList with the size. */
            SLList<Integer> testList = new SLList<>();
            for (int i = 0; i < size; i += 1) {
                testList.addFirst(i);
            }
            /* Test getLast method. */
            Stopwatch sw = new Stopwatch();
            for (int i = 0; i < nTest; i += 1) {
                testList.getLast();
            }
            double time = sw.elapsedTime();

            Ns.addLast(size); times.addLast(time); opCounts.addLast(nTest);
        }

        printTimingTable(Ns, times, opCounts);
    }

}
