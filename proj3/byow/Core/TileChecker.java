package byow.Core;

import byow.TileEngine.TETile;

public class TileChecker {

    /** Return true if tile1 equals to tile2. */
    public static boolean isSame(TETile tile1, TETile tile2) {
        return tile1 == tile2;
    }

    /** Return true if these two pieces of tiles array is equals. */
    public static boolean isSameArea(TETile[][] tiles1, TETile[][] tiles2) {
        int width = tiles1.length;
        int height = tiles1[0].length;

        // Return false if the size not equals.
        if (width != tiles2.length && height != tiles2[0].length) return false;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (!isSame(tiles1[x][y], tiles2[x][y])) return false;
            }
        }

        return true;
    }

    /** Return true if t1 and t2 can be mixed. */
    public static boolean isCanBeDrawn(TETile t1, TETile t2) {
        if (t1 == GameObjects.SPACE || t2 == GameObjects.SPACE) return true;
        return false;
    }
}
