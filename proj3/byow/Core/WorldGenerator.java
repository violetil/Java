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
    private static final int MINROOMS = 10;
    private static final int MAXROOMS = 25;

    public WorldGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        world = new TETile[width][height];
        roomGenerator = new RoomGenerator(width, height);
        hallwayGenerator = new HallwayGenerator();
    }

    /** Call this function to generate and return the world tiles based on the seed. */
    public TETile[][] generateWorld(long seed) {
        random = new Random(seed);
        fillNothing();
        // Generate rooms.
        int rs = RandomUtils.uniform(random, MINROOMS, MAXROOMS);
        roomGenerator.roomsGenerate(random, rs);
        ArrayList<Room> rLists = roomGenerator.getRoomList();
        for (Room r : rLists) {
            drawnRoom(r);
        }
        // Connect rooms by create hallways.
        hallwayGenerator.connectAllRooms(random, rLists);
        for (Hallway h : hallwayGenerator.getHallLists()) {
            drawHall(h);
            // world[h.getStart().x][h.getStart().y] = Tileset.AVATAR;
            // world[h.getEnd().x][h.getEnd().y] = Tileset.AVATAR;
            // System.out.println("Draw hall: " + h.getStart() + " -> " + h.getEnd());
        }

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
                if ((x == p.x || x == w-1 || y == p.y || y == h-1) && (world[x][y] == WALL || world[x][y] == SPACE)) {
                    world[x][y] = WALL;
                } else world[x][y] = FLOOR;
            }
        }
    }

    private void drawHall(Hallway h) {
        Position first = h.getStart();
        Position second = h.getEnd();
        // Which conner type? Find the degree of second for first point.
        double y = second.y - first.y;
        double x = second.x - first.x;
        double d = Math.toDegrees(Math.atan2(y, x));
        if (d > 0 && d < 90) drawConner(new Position(second.x, first.y), ConnerType.leftTop); // 0 < d < 90: leftTop conner
        else if (d > 90 && d < 180) drawConner(new Position(second.x, first.y), ConnerType.rightTop); // 90 < d < 180: rightTop conner
        else if (d > -180 && d < -90) drawConner(new Position(second.x, first.y), ConnerType.rightDown); // -180 < d < -90: rightDown conner
        else if (d > -90 && d < 0) drawConner(new Position(second.x, first.y), ConnerType.leftDown); // -90 < d < 0: leftDown conner
        drawHorizontalHall(first, new Position(second.x, first.y));
        drawVerticalHall(second, new Position(second.x, first.y));
    }

    private void drawHorizontalHall(Position first, Position second) {
        if (first.y != second.y) return;
        int yLevel = first.y;
        drawHorizontalLine(yLevel, first.x, second.x, FLOOR); // Floor in the middle.
        drawHorizontalLine(yLevel-1, first.x, second.x, WALL); // Wall in the down.
        drawHorizontalLine(yLevel+1, first.x, second.x, WALL); // Wall in the up.
    }

    private void drawVerticalHall(Position first, Position second) {
        if (first.x != second.x) return;
        int xLevel = first.x;
        drawVerticalLine(xLevel, first.y, second.y, FLOOR); // Floor in the middle.
        drawVerticalLine(xLevel-1, first.y, second.y, WALL); // Wall in the left.
        drawVerticalLine(xLevel+1, first.y, second.y, WALL); // Wall in the right.
    }

    private void drawConner(Position center, ConnerType type) {
        for (int x = center.x-1; x <= center.x+1; x++) {
            for (int y = center.y-1; y <= center.y+1; y++) {
                if (x == center.x && y == center.y) world[x][y] = FLOOR;
                else if (world[x][y] == SPACE) world[x][y] = WALL;
            }
        }
        switch (type) {
            case leftTop:
                world[center.x-1][center.y] = FLOOR;
                world[center.x][center.y+1] = FLOOR;
                break;
            case leftDown:
                world[center.x-1][center.y] = FLOOR;
                world[center.x][center.y-1] = FLOOR;
                break;
            case rightDown:
                world[center.x+1][center.y] = FLOOR;
                world[center.x][center.y-1] = FLOOR;
                break;
            case rightTop:
                world[center.x+1][center.y] = FLOOR;
                world[center.x][center.y+1] = FLOOR;
                break;
        }
    }

    private void drawHorizontalLine(int y, int xFirst, int xSecond, TETile tile) {
        for (int x = Math.min(xFirst, xSecond); x <= Math.max(xFirst, xSecond); x++) {
            if (world[x][y] == SPACE || tile == FLOOR) world[x][y] = tile;
        }
    }

    private void drawVerticalLine(int x, int yFirst, int ySecond, TETile tile) {
        for (int y = Math.min(yFirst, ySecond); y <= Math.max(yFirst, ySecond); y++) {
            if (world[x][y] == SPACE || tile == FLOOR) world[x][y] = tile;
        }
    }

    private enum ConnerType
    {
        leftTop,
        leftDown,
        rightTop,
        rightDown
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = height-1; i >= 0; i--) {
            for (int j = width-1; j >= 0; j--) {
                sb.append(world[i][j].character());
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
