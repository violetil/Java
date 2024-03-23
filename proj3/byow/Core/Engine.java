package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

public class Engine {
    TERenderer ter = new TERenderer();
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
    }

    /** Interact user input string, create a random world return TETile array. */
    public TETile[][] interactWithInputString(String input) {
        WorldGenerator wg = new WorldGenerator(WIDTH, HEIGHT);
        TETile[][] finalWorldFrame = null;
        char[] ca = input.toUpperCase().toCharArray();
        if (ca[0] != 'N' && ca[0] != 'L') return null;

        for (int i = 0; i < ca.length; i++) {
            long seed = 0;
            if (ca[i] == 'N') {
                i += 1;
                while (i < ca.length && Character.isDigit(ca[i])) {
                    seed = seed*10 + (ca[i] - '0'); // Get the random seed.
                    i += 1;
                }
                if (i < ca.length && ca[i] == 'S') finalWorldFrame = wg.generateWorld(seed); // Create random world.
                else return null; // Or return null.
            }
        }
        return finalWorldFrame;
    }
}
