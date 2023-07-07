package gh2;

import deque.ArrayDeque;
import deque.Deque;

public class HarpString {
    // Constants
    private static final int SR = 44100;             // Sampling Rate
    private static final double DECAY = 0.997;       // Energy decay factor (adjusted for harp-like sound)

    // Buffer for storing sound data
    private Deque<Double> buffer;

    // Create a harp string of the given frequency
    public HarpString(double frequency) {
        int capacity = (int) Math.round(SR / (2 * frequency));  // Adjusted buffer size for harp-like sound
        buffer = new ArrayDeque<Double>();
        for (int i = 0; i < capacity; i++) {
            buffer.addLast(0.0);
        }
    }

    // Pluck the harp string by replacing the buffer with white noise
    public void pluck() {
        double preNumber = 1.0;
        for (int i = 0; i < buffer.size(); ) {
            double r = Math.random() - 0.5;
            if (r != preNumber) {
                buffer.addFirst(r);
                buffer.removeLast();
                preNumber = r;
                i++;
            }
        }
    }

    // Advance the simulation one time step using the Karplus-Strong algorithm
    public void tic() {
        double front = buffer.removeFirst();
        double newDouble = -((front + buffer.get(0)) / 2) * DECAY;  // Flipping the sign for harp-like sound
        buffer.addLast(newDouble);
    }

    // Return the double at the front of the buffer
    public double sample() {
        return buffer.get(0);
    }
}
