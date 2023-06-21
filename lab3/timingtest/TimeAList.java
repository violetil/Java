package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
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
        timeAListConstruction();
    }

    private static void testAList(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        for (int nTest = 1000; nTest <= 12800000; nTest *= 2) {
            Ns.addLast(nTest);
            AList<Integer> aTest = new AList<>();
            int countAddLast;
            Stopwatch timeCount = new Stopwatch();
            for (countAddLast = 0; countAddLast < nTest; countAddLast += 1) {
                aTest.addLast(countAddLast);
            }
            double time = timeCount.elapsedTime();
            opCounts.addLast(countAddLast);
            times.addLast(time);
        }
    }

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE
        AList<Integer> Ns = new AList<>();
        AList<Integer> opCounts = new AList<>();
        AList<Double> times = new AList<>();

        /* Loop of test AList. */
        testAList(Ns, times, opCounts);

        printTimingTable(Ns, times, opCounts);
    }
}
