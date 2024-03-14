package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

import java.util.ArrayDeque;
import java.util.Queue;

public class Engine {
    static TERenderer ter = new TERenderer();
    public static final int WIDTH = 105;
    public static final int HEIGHT = 63;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save.   For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public static TETile[][] interactWithInputString(String input) {
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];

        // 1. Handle the input string format "N####S" (Get the seed)
        int seed = Utils.getSeed(input);

        // 2. Generate a 2D tile world using the seed
        generateRandomWorld(finalWorldFrame, seed);

        return finalWorldFrame;
    }

    /**
     * Generate a random world with an integer as the random seed.
     *
     * 1. Randomly generate a game object.
     * 2. Randomly generate a position.
     * 3. Add the object into the world in the position.
     * 4. Add the object's doors into the queue.
     * 5. Into the loop. (Pop the door as a position and redo the step 2, 3 and 4
     *                      until the queue is empty)
     *
     * @param world
     * @param seed
     */
    private static void generateRandomWorld(TETile[][] world, int seed) {
        Queue<Door> q = new ArrayDeque<>(); // the doors queue.
        GameRandom.setRandomSeed(seed); // Set the random seed.
        FillGameWorld.fillBoardWithNothing(world);

        // 1. Randomly generate a game object.
        GameObject obj1 = GameRandom.randomGameObj();

        // 2. Randomly generate a position.
        Position p = GameRandom.randomGamePosition();

        // 3. Add the object into the world in the position.
        FillGameWorld.drawGameObjIntoWorld(world, obj1, p);

        // 4. Add the object doors into the queue.
        q.addAll(obj1.getDoors());

        // 5. The loop.
        while (!q.isEmpty()) {
            Door door1 = q.poll();

            boolean isSuccess = false; // success add object into world use this door

            for (int i = 0; i < 10; i++) {
                // Back to 2
                GameObject obj2 = GameRandom.randomGameObj();
                Door door2 = FillGameWorld.drawGameObjIntoWorldWithDoor(world, obj2, door1);

                if (door2 != null) {
                    isSuccess = true;
                    q.addAll(obj2.getDoors());
                    q.remove(door2);
                    break;
                }
            }

            if (!isSuccess) world[door1.getPosInWorld().getX()][door1.getPosInWorld().getY()] = GameObjects.WALL;
            else isSuccess = false;
        }
    }

    /** Test the interactWithInputString() method. */
    public static void main(String[] args) {
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = interactWithInputString("n71111584754s");

        ter.renderFrame(world);
    }
}
