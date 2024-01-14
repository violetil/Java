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
    private static final int WIDTH = 27;
    private static final int HEIGHT = 30;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public static void addHexagon(int size, int xPos, int yPos, TETile[][] tiles) {
        /* whole screen size */
        int width = tiles.length;
        int height = tiles[0].length;

        /* hexagon square size */
        int hexRows = hexagonRows(size) + yPos;
        int hexCols = hexagonCols(size) + xPos;

        int rand = RANDOM.nextInt(5);
        /* Fill the hexagon square, start at the given position. */
        for (int i = yPos; i < height && i < hexRows; i++) {
            for (int j = xPos; j < width && j < hexCols; j++) {
                TETile tile = hexagonTile(size, i - yPos, j - xPos, rand);
                if (tile == null) tile = tiles[j][i];
                tiles[j][i] = tile;
            }
        }
    }

    private static void fillWithNothing(TETile[][] tiles) {
        int width = tiles.length;
        int height = tiles[0].length;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j] = Tileset.NOTHING;
            }
        }
    }

    private static TETile hexagonTile(int size, int row, int col, int rand) {
        int halfBlanks = rowBlanks(size, row) / 2;

        if (col >= halfBlanks && hexagonCols(size) - col > halfBlanks) return randomTile(rand);
        else return null;
    }

    private static int rowBlanks(int size, int row) {
        return hexagonCols(size) - rowHexs(size, row);
    }

    private static int rowHexs(int size, int row) {
        if (row < size) return row * 2 + size;
        else            return (hexagonRows(size) - 1 - row) * 2 + size;
    }

    private static int hexagonRows(int size) {
        return 2 * size;
    }

    private static int hexagonCols(int size) {
        return size + 2 * (size - 1);
    }

    private static TETile randomTile(int rand) {
        switch (rand) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.SAND;
            case 4: return Tileset.TREE;
            default: return Tileset.GRASS;
        }
    }

    private static void fillWithHexagons(TETile[][] tiles) {
        for (int i = 0; i < 27; i += 3) {
            fillRowHexagon(i / 3 + 1, i, tiles);
        }
    }

    private static void fillRowHexagon(int row, int yPos, TETile[][] tiles) {
        if (row <= 3) {
            switch (row) {
                case 1:
                    oneTile(yPos, tiles);
                    break;
                case 2:
                    twoTiles(yPos, tiles);
                    break;
                case 3:
                    threeTiles(yPos, tiles);
                    break;
            }
        } else if (row == 9) fillRowHexagon(1, yPos, tiles);
        else fillRowHexagon(row - 2, yPos, tiles);
    }

    private static void oneTile(int y, TETile[][] tiles) {
        addHexagon(3, 10, y, tiles);
    }

    private static void twoTiles(int y, TETile[][] tiles) {
        addHexagon(3, 5, y, tiles);
        addHexagon(3, 15, y, tiles);
    }

    private static void threeTiles(int y, TETile[][] tiles) {
        addHexagon(3, 0, y, tiles);
        addHexagon(3, 10, y, tiles);
        addHexagon(3, 20, y, tiles);
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] hexWorld = new TETile[WIDTH][HEIGHT];
        fillWithNothing(hexWorld);

        fillWithHexagons(hexWorld);

//        addHexagon(3, 10, 0, hexWorld);
//        addHexagon(3, 5, 3, hexWorld);
//        addHexagon(3, 15, 3, hexWorld);

        ter.renderFrame(hexWorld);
    }
}
