package byow.Core;

import byow.TileEngine.*;

import java.util.ArrayList;
import java.util.Random;

/** Represent the game world, include the locked door and player.
 *  Supports the generatorWorld method that take a long seed parameter and generate a random world.
 *
 *  Initialize this class with the world width and height, and call `generateWorld()` to get the random world.
 */
public class WorldGenerator {
    private Random random;
    private TETile[][] world;
    private Player player;
    private int width;
    private int height;
    private RoomGenerator roomGenerator;
    private HallwayGenerator hallwayGenerator;

    private static final TETile WALL = Tileset.WALL;
    private static final TETile SPACE = Tileset.NOTHING;
    private static final TETile FLOOR = Tileset.FLOOR;
    private static final TETile LOCKED_DOOR = Tileset.LOCKED_DOOR;
    private static final int MINROOMS = 15;
    private static final int MAXROOMS = 30;

    public WorldGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        world = new TETile[width][height];
        roomGenerator = new RoomGenerator();
        hallwayGenerator = new HallwayGenerator();
    }

    /** Generate and return the world tiles based on the seed. */
    public TETile[][] generateWorld(long seed) {
        random = new Random(seed);
        fillNothing();

        int rs = RandomUtils.uniform(random, MINROOMS, MAXROOMS);
        roomGenerator.roomsGenerate(random, rs);
        ArrayList<Room> rlists = roomGenerator.getRoomList();
        for (Room r : rlists) {
            drawnRoom(r);
        }
        // Room optimization.

        return world;
    }

    private void fillNothing() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                world[x][y] = SPACE;
            }
        }
    }

    private void drawnRoom(Room r) {
        Position p = r.getBottomLeftConner();
        int w = p.x + r.getWidth();
        int h = p.y + r.getHeight();
        for (int x = p.x; x < w; x++) {
            for (int y = p.y; y < h; y++) {
                if (x == p.x || x == w-1 || y == p.y || y == h-1) {
                    world[x][y] = WALL;
                } else world[x][y] = FLOOR;
            }
        }
    }
}
