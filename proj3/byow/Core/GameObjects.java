package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;

/**
 * Manage a set of GameObjects: rooms, hallways and so on.
 */
public class GameObjects {
    /** The main tiles in game world. */
    public static TETile WALL = Tileset.WALL;
    public static TETile FLOOR = Tileset.FLOOR;
    public static TETile SPACE = Tileset.NOTHING;

    /**
     * Get all types game objects.
     */
    public static List<GameObject> getAllGameObjects() {
        List<GameObject> gs = new ArrayList<>();
        gs.add(getRoom1());
        gs.add(getRoom2());
        gs.add(getRoom3());
        gs.add(getRoom4());
        gs.add(getRoom5());
        gs.add(getHallway1());
        gs.add(getHallway2());
        gs.add(getHallway3());
        gs.add(getHallway4());

        return gs;
    }

    /**
     * Return the GameObject of the type HALLWAY_4.
     *
     * Wall: #;   Path: .;   Door: @;
     *
     * HALLWAY_4 :        # @ #
     *              # # # # . @
     *              # . . . . #
     *              # # # # # #
     *
     * @return - HALLWAY_4 GameObject.
     */
    public static GameObject getHallway4() {
        GameObject HALLWAY_4 = new GameObject();

        // 1. width   2. height
        HALLWAY_4.setWidth(6);
        HALLWAY_4.setHeight(4);

        // 3. tiles
        TETile[][] tiles = new TETile[HALLWAY_4.getWidth()][HALLWAY_4.getHeight()];
        fillTiles(tiles, "HALLWAY_4");
        HALLWAY_4.setTiles(tiles);

        // 4. doors
        List<Door> doors = new ArrayList<>();
        doors.add(new Door(5, 2, Position.RIGHT, HALLWAY_4));
        doors.add(new Door(4, 3, Position.UP, HALLWAY_4));
        HALLWAY_4.setDoors(doors);

        // 5. type
        HALLWAY_4.setType("HALLWAY_4");

        return HALLWAY_4;
    }

    /**
     * Return the GameObject of the type HALLWAY_3.
     *
     * Wall: #;   Path: .;   Door: @;
     *
     * HALLWAY_3 :  # # #
     *              # . #
     *              # . #
     *              # . #
     *              # @ #
     *
     * @return - HALLWAY_3 GameObject.
     */
    public static GameObject getHallway3() {
        GameObject HALLWAY_3 = new GameObject();

        // 1. width   2. height
        HALLWAY_3.setWidth(3);
        HALLWAY_3.setHeight(5);

        // 3. tiles
        TETile[][] tiles = new TETile[HALLWAY_3.getWidth()][HALLWAY_3.getHeight()];
        fillTiles(tiles, "HALLWAY_3");
        HALLWAY_3.setTiles(tiles);

        // 4. doors
        List<Door> doors = new ArrayList<>();
        doors.add(new Door(1, 0, Position.DOWN, HALLWAY_3));
        HALLWAY_3.setDoors(doors);

        // 5. type
        HALLWAY_3.setType("HALLWAY_3");

        return HALLWAY_3;
    }

    /**
     * Return the GameObject of the type HALLWAY_2.
     *
     * Wall: #;   Path: .;   Door: @;
     * HALLWAY_2 :  # # #
     *              # . #
     *              # . #
     *              # . # # # #
     *              # . . . . #
     *              # # # # . #
     *                    # . #
     *                    # . #
     *                    # @ #
     *
     * @return - HALLWAY_2 GameObject.
     */
    public static GameObject getHallway2() {
        GameObject HALLWAY_2 = new GameObject();

        // 1. width   2. height
        HALLWAY_2.setWidth(6);
        HALLWAY_2.setHeight(9);

        // 3. tiles
        TETile[][] tiles = new TETile[HALLWAY_2.getWidth()][HALLWAY_2.getHeight()];
        fillTiles(tiles, "HALLWAY_2");
        HALLWAY_2.setTiles(tiles);

        // 4. doors
        List<Door> doors = new ArrayList<>();
        doors.add(new Door(4, 0, Position.DOWN, HALLWAY_2));
        HALLWAY_2.setDoors(doors);

        // 5. type
        HALLWAY_2.setType("HALLWAY_2");

        return HALLWAY_2;
    }

    /**
     * Return the GameObject of type HALLWAY_1.
     *
     * Wall: #;   Path: .;    Door: @;
     * HALLWAY_1 :  # @ #
     *              # . #
     *              # . #
     *              # . #
     *              # . #
     *              # . #
     *              # . #
     *              # @ #
     *
     * @return - HALLWAY_1 GameObject.
     */
    public static GameObject getHallway1() {
        GameObject HALLWAY_1 = new GameObject();

        // 1. width    2. height
        HALLWAY_1.setWidth(3);
        HALLWAY_1.setHeight(8);

        // 3. tiles
        TETile[][] tiles = new TETile[HALLWAY_1.getWidth()][HALLWAY_1.getHeight()];
        fillTiles(tiles, "HALLWAY_1");
        HALLWAY_1.setTiles(tiles);

        // 4. doors
        List<Door> doors = new ArrayList<>();
        doors.add(new Door(1, 0, Position.DOWN, HALLWAY_1));
        doors.add(new Door(1, 7, Position.UP, HALLWAY_1));
        HALLWAY_1.setDoors(doors);

        // 5. type
        HALLWAY_1.setType("HALLWAY_1");

        return HALLWAY_1;
    }

    /**
     * Return the GameObject of type ROOM_4.
     *
     * Wall: #;   Path: .;    Door: @;
     *
     * ROOM_4 :     # # @ # #
     *              # . . . #
     *              @ . . . #
     *              # . . . #
     *              # . . . #
     *              # . . . #
     *              # . . . #
     *              # @ # # #
     *
     * @return - ROOM_4 GameObject.
     */
    public static GameObject getRoom4() {
        GameObject ROOM_4 = new GameObject();

        // 1. width    2. height
        ROOM_4.setWidth(5);
        ROOM_4.setHeight(8);

        // 3. tiles
        TETile[][] tiles = new TETile[ROOM_4.getWidth()][ROOM_4.getHeight()];
        fillTiles(tiles, "ROOM_4");
        ROOM_4.setTiles(tiles);

        // 4. doors
        List<Door> doors = new ArrayList<>();
        doors.add(new Door(1, 0, Position.DOWN, ROOM_4));
        doors.add(new Door(0, 5, Position.LEFT, ROOM_4));
        doors.add(new Door(2, 7, Position.UP, ROOM_4));
        ROOM_4.setDoors(doors);

        // 5. type
        ROOM_4.setType("ROOM_4");

        return ROOM_4;
    }

    /**
     * Return the GameObject of type ROOM_5.
     *
     * Wall: #;   Path: .;    Door: @;
     *
     * ROOM_5 :     # # # # #
     *              # . . . #
     *              # . . . @
     *              # @ # # #
     *
     * @return - ROOM_5 GameObject.
     */
    public static GameObject getRoom5() {
        GameObject ROOM_5 = new GameObject();

        // 1. width    2. height
        ROOM_5.setWidth(5);
        ROOM_5.setHeight(4);

        // 3. tiles
        TETile[][] tiles = new TETile[ROOM_5.getWidth()][ROOM_5.getHeight()];
        fillTiles(tiles, "ROOM_5");
        ROOM_5.setTiles(tiles);

        // 4. doors
        List<Door> doors = new ArrayList<>();
        doors.add(new Door(1, 0, Position.DOWN, ROOM_5));
        doors.add(new Door(4, 1, Position.RIGHT, ROOM_5));
        ROOM_5.setDoors(doors);

        // 5. type
        ROOM_5.setType("ROOM_5");

        return ROOM_5;
    }

    /**
     * Return the GameObject of type ROOM_3.
     *
     * Wall: #;   Path: .;    Door: @;
     *
     * ROOM_3 :     # # @ #
     *              # . . #
     *              # . . #
     *              # . . @
     *              # # @ #
     *
     * @return - ROOM_3 GameObject.
     */
    public static GameObject getRoom3() {
        GameObject ROOM_3 = new GameObject();

        // 1. width    2. height
        ROOM_3.setWidth(4);
        ROOM_3.setHeight(5);

        // 3. tiles
        TETile[][] tiles = new TETile[ROOM_3.getWidth()][ROOM_3.getHeight()];
        fillTiles(tiles, "ROOM_3");
        ROOM_3.setTiles(tiles);

        // 4. doors
        List<Door> doors = new ArrayList<>();
        doors.add(new Door(2, 0, Position.DOWN, ROOM_3));
        doors.add(new Door(3, 1, Position.RIGHT, ROOM_3));
        doors.add(new Door(2, 4, Position.UP, ROOM_3));
        ROOM_3.setDoors(doors);

        // 5. type
        ROOM_3.setType("ROOM_3");

        return ROOM_3;
    }

    /**
     * Return the GameObject of type ROOM_2.
     *
     * Wall: #;   Path: .;    Door: @;
     *
     * ROOM_2 :     # # # # # # # #
     *              # . . . . . . @
     *              # . . . . . . #
     *              # . . . . . . #
     *              @ . . . . . . #
     *              # . . . . . . #
     *              # # # # # # # #
     *
     * @return - ROOM_2 GameObject.
     */
    public static GameObject getRoom2() {
        GameObject ROOM_2 = new GameObject();

        // 1. width    2. height
        ROOM_2.setWidth(8);
        ROOM_2.setHeight(7);

        // 3. tiles
        TETile[][] tiles = new TETile[ROOM_2.getWidth()][ROOM_2.getHeight()];
        fillTiles(tiles, "ROOM_2");
        ROOM_2.setTiles(tiles);

        // 4. doors
        List<Door> doors = new ArrayList<>();
        doors.add(new Door(0, 2, Position.LEFT, ROOM_2));
        doors.add(new Door(7, 5, Position.RIGHT, ROOM_2));
        ROOM_2.setDoors(doors);

        // 5. type
        ROOM_2.setType("ROOM_2");

        return ROOM_2;
    }

    /**
     * Return the GameObject of type ROOM_1.
     *
     * Wall: #;   Path: .;    Door: @;
     *
     * ROOM_1 :     # # @ # # # # #
     *              # . . . . . . #
     *              # . . . . . . #
     *              # . . . . . . #
     *              # . . . . . . #
     *              # . . . . . . #
     *              # . . . . . . #
     *              # # # # # # @ #
     *
     * @return - ROOM_1 GameObject.
     */
    public static GameObject getRoom1() {
        GameObject ROOM_1 = new GameObject();

        // 1. width    2. height
        ROOM_1.setWidth(8);
        ROOM_1.setHeight(8);

        // 3. tiles
        TETile[][] tiles = new TETile[ROOM_1.getWidth()][ROOM_1.getHeight()];
        fillTiles(tiles, "ROOM_1");
        ROOM_1.setTiles(tiles);

        // 4. doors
        List<Door> doors = new ArrayList<>();
        doors.add(new Door(2, 7, Position.UP, ROOM_1));
        doors.add(new Door(6, 0, Position.DOWN, ROOM_1));
        ROOM_1.setDoors(doors);

        // 5. type
        ROOM_1.setType("ROOM_1");

        return ROOM_1;
    }

    /**
     * Fill the different type game object's tiles.
     *
     * @param tiles
     * @param type - the type of game object.
     */
    private static void fillTiles(TETile[][] tiles, String type) {
        if (type.equals("ROOM_1")) fillROOM1Tiles(tiles);
        else if (type.equals("ROOM_2")) fillROOM2Tiles(tiles);
        else if (type.equals("ROOM_3")) fillROOM3Tiles(tiles);
        else if (type.equals("ROOM_4")) fillROOM4Tiles(tiles);
        else if (type.equals("ROOM_5")) fillROOM5Tiles(tiles);
        else if (type.equals("HALLWAY_1")) fillHALLWAY1Tiles(tiles);
        else if (type.equals("HALLWAY_2")) fillHALLWAY2Tiles(tiles);
        else if (type.equals("HALLWAY_3")) fillHALLWAY3Tiles(tiles);
        else if (type.equals("HALLWAY_4")) fillHALLWAY4Tiles(tiles);
        else throw new IllegalArgumentException("Not have this type of game object!");
    }

    private static void fillBaseRectangle(TETile[][] tiles) {
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                if (x == 0 || x == tiles.length - 1) tiles[x][y] = WALL;
                else if (y == 0 || y == tiles[0].length - 1) tiles[x][y] = WALL;
                else tiles[x][y] = FLOOR;
            }
        }
    }

    /**
     * ROOM_1 :     # # . # # # # #
     *              # . . . . . . #
     *              # . . . . . . #
     *              # . . . . . . #
     *              # . . . . . . #
     *              # . . . . . . #
     *              # . . . . . . #
     *              # # # # # # . #
     *
     * @param tiles
     */
    private static void fillROOM1Tiles(TETile[][] tiles) {
        fillBaseRectangle(tiles);

        tiles[6][0] = FLOOR;
        tiles[2][7] = FLOOR;
    }

    /**
     * ROOM_2 :     # # # # # # # #
     *              # . . . . . . @
     *              # . . . . . . #
     *              # . . . . . . #
     *              @ . . . . . . #
     *              # . . . . . . #
     *              # # # # # # # #
     *
     * @return - ROOM_2 GameObject.
     */
    private static void fillROOM2Tiles(TETile[][] tiles) {
        fillBaseRectangle(tiles);

        tiles[0][2] = FLOOR;
        tiles[7][5] = FLOOR;
    }

    /**
     * ROOM_3 :     # # . #
     *              # . . #
     *              # . . #
     *              # . . .
     *              # # . #
     *
     * @return - ROOM_3 GameObject.
     */
    private static void fillROOM3Tiles(TETile[][] tiles) {
        fillBaseRectangle(tiles);

        tiles[2][0] = FLOOR;
        tiles[3][1] = FLOOR;
        tiles[2][4] = FLOOR;
    }

    /**
     * ROOM_4 :     # # . # #
     *              # . . . #
     *              . . . . #
     *              # . . . #
     *              # . . . #
     *              # . . . #
     *              # . . . #
     *              # . # # #
     *
     * @return - ROOM_4 GameObject.
     */
    private static void fillROOM4Tiles(TETile[][] tiles) {
        fillBaseRectangle(tiles);

        tiles[1][0] = FLOOR;
        tiles[0][5] = FLOOR;
        tiles[2][7] = FLOOR;
    }

    /**
     * ROOM_5 :     # # # # #
     *              # . . . #
     *              # . . . @
     *              # @ # # #
     *
     * @return - ROOM_5 GameObject.
     */
    private static void fillROOM5Tiles(TETile[][] tiles) {
        fillBaseRectangle(tiles);

        tiles[1][0] = FLOOR;
        tiles[4][1] = FLOOR;
    }

    /**
     * HALLWAY_1:   # . #
     *              # . #
     *              # . #
     *              # . #
     *              # . #
     *              # . #
     *              # . #
     *              # . #
     *
     * @param tiles
     */
    private static void fillHALLWAY1Tiles(TETile[][] tiles) {
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                if (x == 0 || x == tiles.length - 1) tiles[x][y] = WALL;
                else tiles[x][y] = FLOOR;
            }
        }
    }

    /**
     * HALLWAY_2 :  # # #
     *              # . #
     *              # . #
     *              # . # # # #
     *              # . . . . #
     *              # # # # . #
     *                    # . #
     *                    # . #
     *                    # . #
     * @param tiles
     */
    private static void fillHALLWAY2Tiles(TETile[][] tiles) {
        // First, fill the entire tiles with WALL.
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                tiles[x][y] = WALL;
            }
        }

        // Second, fill the two rectangles of outspace with SPACE.
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                tiles[x][y] = SPACE;
            }
        }

        for (int x = 3; x < tiles.length; x++) {
            for (int y = 6; y < tiles[0].length; y++) {
                tiles[x][y] = SPACE;
            }
        }

        // Third, create the path by filling specific tiles with FLOOR.
        for (int y = 4; y < tiles[0].length - 1; y++) { // Vertical corridor
            tiles[1][y] = FLOOR;
        }

        for (int x = 2; x <= 3; x++) { // Bottom horizontal corridor
            tiles[x][4] = FLOOR;
        }

        for (int y = 0; y <= 4; y++) { // Right vertical corridor
            tiles[4][y] = FLOOR;
        }
    }

    /**
     * HALLWAY_3 :  # # #
     *              # . #
     *              # . #
     *              # . #
     *              # . #
     *
     * @param tiles
     */
    private static void fillHALLWAY3Tiles(TETile[][] tiles) {
        fillBaseRectangle(tiles);

        tiles[1][0] = FLOOR;
    }

    /**
     * HALLWAY_4 :        # @ #
     *              # # # # . @
     *              # . . . . #
     *              # # # # # #
     *
     * @param tiles
     */
    private static void fillHALLWAY4Tiles(TETile[][] tiles) {
        fillBaseRectangle(tiles);

        for (int x = 0; x < 3; x++) {
            tiles[x][3] = SPACE;
        }

        for (int x = 1; x < 4; x++) {
            tiles[x][2] = WALL;
        }

        for (int x = 1; x < 5; x++) {
            tiles[x][1] = FLOOR;
        }

        tiles[4][2] = FLOOR;
        tiles[5][2] = FLOOR;
        tiles[4][3] = FLOOR;
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        GameObject g = getHallway4();
        //g.rightRotate();
        // g.rightRotate();

        ter.initialize(g.getWidth(), g.getHeight());
        ter.renderFrame(g.getTiles());
    }
}
