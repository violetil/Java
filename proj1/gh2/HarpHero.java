package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A client that uses the synthesizer package to replicate a plucked guitar string sound
 */
public class HarpHero {
    public static final double CONCERT = 440.0;
    public static final int NOTES = 37;

    private static void fillFrequency(double[] arrF) {
        for (int i = 0; i < NOTES; i++) {
            arrF[i] = CONCERT * Math.pow(2, ((double) i - 24.0) / 12.0);
        }
    }

    public static void main(String[] args) {
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        double[] arrFrequency = new double[NOTES];
        HarpString[] strings = new HarpString[NOTES];

        fillFrequency(arrFrequency);

        for (int i = 0; i < NOTES; i++) {
            strings[i] = new HarpString(arrFrequency[i]);
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
