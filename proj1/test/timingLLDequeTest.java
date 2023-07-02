package test;

import deque.LinkedListDeque;
import edu.princeton.cs.algs4.Stopwatch;

public class timingLLDequeTest {
    private static void printTimingTable(LinkedListDeque<Integer> Ns, LinkedListDeque<Double> times, LinkedListDeque<Integer> opCounts) {
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

    private static void timeLLDequeConstruction() {
        LinkedListDeque<Integer> Ns = new LinkedListDeque<>(); // Size of deque.
        LinkedListDeque<Double> times = new LinkedListDeque<>();
        LinkedListDeque<Integer> opCounts = new LinkedListDeque<>();

        for (int i = 1000; i <= 128000; i *= 2) {
            LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
            int N = i; double time; int opCount = i;
            for (int j = 0; j < i; j += 1) {
                lld1.addFirst(j);
            }
            Stopwatch sw = new Stopwatch();
            for (int j = 0; j < i; j += 1) {
                lld1.removeFirst();
            }
            time = sw.elapsedTime();
            Ns.addLast(N); times.addLast(time); opCounts.addLast(opCount);
        }
        printTimingTable(Ns, times, opCounts);
    }

    public static void main(String[] args) {
        timeLLDequeConstruction();
    }
}