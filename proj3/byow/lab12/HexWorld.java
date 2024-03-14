package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;

    private static final long SEED = 2873145;
    private static final Random RANDOM = new Random(SEED);

    /**
     * Draw a row of tiles to the board, anchored at a given position.
     */
    private static void drawRow(TETile[][] tiles, Position p, TETile tile, int length) {
        for (int dx = 0; dx < length; dx++) {
            tiles[p.x + dx][p.y] = tile;
        }
    }

    /**
     * Helper method for addHexagon
     */
    private static void addHexagonHelper(TETile[][] tiles, Position p, TETile tile, int b, int t) {
        // Draw this row
        Position startOfRow = p.shift(b, 0);
        drawRow(tiles, startOfRow, tile, t);

        // Draw remaining rows recursively
        if (b > 0) {
            Position nextP = p.shift(0, -1);
            addHexagonHelper(tiles, nextP, tile, b - 1, t + 2);
        }

        // Draw this row again to be the reflection
        Position startOfReflectionRow = startOfRow.shift(0, -(2*b + 1));
        drawRow(tiles, startOfReflectionRow, tile, t);
    }

    /**
     * Adds a hexagon to the world at position P of size SIZE
     */
    private static void addHexagon(TETile[][] tiles, Position p, TETile tile, int size) {
        if (size < 2) return;

        addHexagonHelper(tiles, p, tile, size - 1, size);
    }

    /**
     * Adds a column of NUM hexagons, each of whose biomes are chosen randomly
     * to the world at position P. Each of the hexagons are of size SIZE.
     */
    private static void addHexColumn(TETile[][] tiles, Position p, int size, int num) {
        if (num < 1) return;

        // Draw this hexagon
        addHexagon(tiles, p, randomTile(), size);

        // Draw n - 1 hexagon
        if (num > 1) {
            Position bottomNeighbor = getBottomNeighbor(p, size);
            addHexColumn(tiles, bottomNeighbor, size, num - 1);
        }
    }

    /**
     * Get the position of the top right of a hexagon at position P.
     * N is the size of the hexagon we are tessellating.
     */
    private static Position getTopRightNeighbor(Position p, int n) {
        return p.shift(2*n - 1, n);
    }

    /**
     * Get the position of the bottom right of a hexagon at position P.
     * N is the size of the hexagon we are tessellating.
     */
    private static Position getBottomRightNeighbor(Position p, int n) {
        return p.shift(2*n - 1, -n);
    }

    /**
     * Gets the position of the bottom neighbor of a hexagon at position P.
     * N is the size of the hexagon we are tessellating.
     */
    private static Position getBottomNeighbor(Position p, int n) {
        return new Position(p.x, p.y - 2*n);
    }

    /**
     * Fills the given 2D array of tiles with NOTHING tile.
     * @param tiles
     */
    public static void fillBoardWithNothing(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    /** Picks a RANDOM tile. */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.GRASS;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.SAND;
            case 3: return Tileset.MOUNTAIN;
            case 4: return Tileset.TREE;
            default: return Tileset.NOTHING;
        }
    }

    private static class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position shift(int dx, int dy) {
            return new Position(this.x + dx, this.y + dy);
        }
    }

    private static void drawWorld(TETile[][] tiles, Position p, int hexSize, int tessSize) {
        // Draw the first hexagon
        addHexColumn(tiles, p, hexSize, tessSize);

        // Expand up and to the right
        for (int i = 1; i < tessSize; i++) {
            p = getTopRightNeighbor(p, hexSize);
            addHexColumn(tiles, p, hexSize, tessSize + i);
        }

        // Expand down and to the right
        for (int i = tessSize - 2; i >= 0; i--) {
            p = getBottomRightNeighbor(p, hexSize);
            addHexColumn(tiles, p, hexSize, tessSize + i);
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        fillBoardWithNothing(world);

        Position p = new Position(10, 65);
        drawWorld(world, p , 4, 4);

        ter.renderFrame(world);
    }
}
