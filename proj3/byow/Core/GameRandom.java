package byow.Core;

import java.util.List;
import java.util.Random;

public class GameRandom {
    private static Random RANDOM;

    public static void setRandomSeed(int seed) {
        RANDOM = new Random(seed);
    }

    /**
     * Randomly return the different type game object in the set of game objects.
     *
     * @return - GameObj.
     */
    public static GameObject randomGameObj() {
        int n = RANDOM.nextInt();
        n = getPositiveN(n);

        List<GameObject> gs = GameObjects.getAllGameObjects();
        return gs.get(n % gs.size());
    }

    /**
     * Return a random game 2D position use the N.
     *
     * @return - Position.
     */
    public static Position randomGamePosition() {
        int n = RANDOM.nextInt();

        n = getPositiveN(n);
        return new Position(n % Engine.WIDTH, n % Engine.HEIGHT);
    }

    private static int getPositiveN(int n) {
        if (n < 0) return -n;
        return n;
    }
}
