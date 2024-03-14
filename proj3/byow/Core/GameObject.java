package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent the basic object in the game world and have some basic attributes.
 *
 * */
public class GameObject {
    private TETile[][] tiles;
    private Position startPos;
    private int width;
    private int height;
    private List<Door> doors;
    private String type;

    /** The default GameObject represent a nothing. */
    public GameObject(TETile[][] tiles, Position startPos, int width, int height,
                      List<Door> doors, String type) {
        this.tiles = tiles;
        this.startPos = startPos;
        this.width = width;
        this.height = height;
        this.doors = doors;
        this.type = type;
    }

    public GameObject() {
        TETile[][] tiles = new TETile[1][1];
        tiles[0][0] = Tileset.NOTHING;
        this.tiles = tiles;
        this.startPos = new Position(0, 0);
        this.width = 1;
        this.height = 1;
        this.doors = new ArrayList<>();
        this.type = "DEFAULT";
    }

    /**
     * Return the door if the object have the door with specific direction,
     * return null otherwise.
     *
     * @param direction
     * @return
     */
    public Door haveDoorDirection(Position direction) {
        // Angle of the rotation
        int angle = 0;

        // If the object have not the door with the direction and rotation
        // angle is less than 360 rotates the object to find the door.
        while (angle <= 360 && !haveDirection(direction)) {
            this.rightRotate();
            angle += 90;
        }

        for (Door door : this.getDoors()) {
            if (direction.equals(door.getDirection())) return door;
        }

        return null;
    }

    /**
     * Return true if the object have the door with specific direction,
     * return false otherwise.
     *
     * @param direction
     * @return
     */
    private boolean haveDirection(Position direction) {
        for (Door door : this.getDoors()) {
            if (direction.equals(door.getDirection())) return true;
        }
        return false;
    }

    /**
     * Rotate the game object 90 degrees to the right.
     */
    public void rightRotate() {
        // Transpose the array
        TETile[][] transposedTiles = new TETile[height][width];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                transposedTiles[height - 1 - y][width - 1 - x] = tiles[x][y];
            }
        }

        // Reverse each column (instead of each row for the top-left origin)
        TETile[][] rotatedTiles = new TETile[height][width];
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                rotatedTiles[height - 1 - x][y] = transposedTiles[x][y];
            }
        }

        // Update the GameObject's tiles to the rotatedTiles
        tiles = rotatedTiles;

        // Update the GameObject's doors
        // Standard mathematical rotation
        // x' = y
        // y' = width - 1 - x
        for (Door door : this.doors) {
            Position originPos = door.getDoorPos();
            door.setDoorPos(new Position(originPos.getY(), this.width - 1 - originPos.getX()));
            door.setDirection(rightRotateDirection(door.getDirection()));
        }

        // Since the GameObject has been rotated, swap its width and height
        int temp = width;
        width = height;
        height = temp;
    }

    private Position rightRotateDirection(Position direction) {
        return new Position(direction.getY(), -direction.getX());
    }


    public Position getStartPos() {
        return startPos;
    }

    public void setStartPos(Position startPos) {
        this.startPos = startPos;
    }

    public TETile[][] getTiles() {
        return tiles;
    }

    public void setTiles(TETile[][] tiles) {
        this.tiles = tiles;
        if (tiles == null) return;
        setWidth(tiles.length);
        setHeight(tiles[0].length);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int length) {
        this.width = length;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Door> getDoors() {
        return doors;
    }

    public void setDoors(List<Door> doors) {
        this.doors = doors;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static void main(String[] args) {
        GameObject g = new GameObject();

        TETile[][] tiles = new TETile[2][3];

        tiles[0][0] = Tileset.FLOOR;
        tiles[0][1] = Tileset.WALL;
        tiles[0][2] = Tileset.FLOWER;
        tiles[1][0] = Tileset.TREE;
        tiles[1][1] = Tileset.MOUNTAIN;
        tiles[1][2] = Tileset.SAND;

        g.setTiles(tiles);
        g.setHeight(3);
        g.setWidth(2);
        g.rightRotate();

        TERenderer ter = new TERenderer();
        ter.initialize(g.getWidth(),  g.getHeight());
        ter.renderFrame(g.getTiles());
    }
}
