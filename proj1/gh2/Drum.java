package gh2;

import deque.ArrayDeque;
import deque.Deque;

public class Drum {
    // Constants
    private static final int SR = 44100;             // Sampling Rate
    private static final double DECAY = 1.0;         // Energy decay factor (no decay for better sound)

    // Buffer for storing sound data
    private Deque<Double> buffer;

    // Create a drum with the given frequency
    public Drum(double frequency) {
        int capacity = (int) Math.round(SR / frequency);  // Adjust buffer size for drum-like sound
        buffer = new ArrayDeque<Double>();
        for (int i = 0; i < capacity; i++) {
            buffer.addLast(0.0);
        }
    }

    // Pluck the drum by replacing the buffer with white noise
    public void pluck() {
        for (int i = 0; i < buffer.size(); i++) {
            double r = Math.random() - 0.5;
            buffer.addLast(r);
            buffer.removeFirst();
        }
    }

    // Advance the simulation one time step using the Karplus-Strong algorithm
    public void tic() {
        double front = buffer.removeFirst();
        double newDouble = (front + buffer.get(0)) / 2;
        if (Math.random() < 0.5) {
            newDouble *= -1;  // Flipping the sign with probability 0.5 for drum-like sound
        }
        buffer.addLast(newDouble);
    }

    // Return the double at the front of the buffer
    public double sample() {
        return buffer.get(0);
    }
}
