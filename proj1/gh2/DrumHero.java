package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A client that uses the synthesizer package to replicate a plucked guitar string sound
 */
public class DrumHero {
    public static final int NOTES = 37;
    public static final int maxFrequency = 500;
    public static final int minFrequency = 215;

    private static void fillFrequency(double[] arrF) {
        for (int i = 0; i < NOTES; i++) {
            arrF[i] = Math.random() * (maxFrequency - minFrequency) + minFrequency;

        }
    }

    public static void main(String[] args) {
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        double[] arrFrequency = new double[NOTES];
        Drum[] strings = new Drum[NOTES];

        fillFrequency(arrFrequency);

        for (int i = 0; i < NOTES; i++) {
            strings[i] = new Drum(arrFrequency[i]);
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = keyboard.indexOf(key);
                if (index >= 0) {
                    strings[index].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0.0;
            for (int i = 0; i < NOTES; i++) {
                sample += strings[i].sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < NOTES; i++) {
                strings[i].tic();
            }
        }
    }
}
