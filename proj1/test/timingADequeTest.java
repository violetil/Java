package test;

import deque.ArrayDeque;
import edu.princeton.cs.algs4.Stopwatch;

public class timingADequeTest {
    private static void printTimingTable(ArrayDeque<Integer> Ns, ArrayDeque<Double> times, ArrayDeque<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "times(s)", "#ops", "microSec/op");
        System.out.printf("-------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    private static void timeADequeConstruction() {
        ArrayDeque<Integer> Ns = new ArrayDeque<>(); // Size of deque.
        ArrayDeque<Double> times = new ArrayDeque<>();
        ArrayDeque<Integer> opCounts = new ArrayDeque<>();

        for (int i = 1000; i <= 512000; i *= 2) {
            ArrayDeque<Integer> ad1 = new ArrayDeque<>();
            int N = i; double time; int opCount = i;
            for (int j = 0; j < i; j += 1) {
                ad1.addLast(j);
            }
            Stopwatch sw = new Stopwatch();
            for (int j = 0; j < i; j += 1) {
                ad1.get(j);
            }
            time = sw.elapsedTime();
            Ns.addLast(N); times.addLast(time); opCounts.addLast(opCount);
        }
        printTimingTable(Ns, times, opCounts);
    }

    public static void main(String[] args) {
        timeADequeConstruction();
    }
}
