package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class FillGameWorld {
    /**
     * Add the game object into the game world in the P position. Return true if this
     * process is successful.
     *
     * @param world - game world.
     * @param obj - game object.
     * @param p - a position where the object was drawn.
     *
     * @return
     */
    public static boolean drawGameObjIntoWorld(TETile[][] world, GameObject obj, Position p) {
        int worldWidth = world.length;
        int worldHeight = world[0].length;

        int width = obj.getWidth();
        int height = obj.getHeight();

        // Return false if out of the bounds.
        if (width + p.getX() >= worldWidth || height + p.getY() >= worldHeight) return false;

        // Return false if world area have conflict with object.
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (!TileChecker.isCanBeDrawn(world[x+p.getX()][y+p.getY()], obj.getTiles()[x][y])) return false;
            }
        }

        // Draw process.
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (obj.getTiles()[x][y] != GameObjects.SPACE)
                    world[x+p.getX()][y+p.getY()] = obj.getTiles()[x][y];
            }
        }

        // Update the start position of the object.
        obj.setStartPos(p);

        return true;
    }

    /**
     * Add the game object into the game world according to the door position.
     * Return the door as the start position if object is succeed add into the
     * world, otherwise return null.
     *
     * 1. Calculate the initial position for the obj will be drawn.
     * 2. Call the `drawGameObjIntoWorld` with the initial position.
     *
     * @param world - game world
     * @param obj - game object
     * @param door - a door
     */
    public static Door drawGameObjIntoWorldWithDoor(TETile[][] world, GameObject obj, Door door) {
        // Get the door with the opposite direction of the given door in the object.
        Door objDoor = obj.haveDoorDirection(door.getDirection().opposite());

        // Return null if the objDoor is null.
        // It can be possible to add into world use the given door if
        // the object have not the door with opposite direction for
        // the given door.
        if (objDoor == null) return null;

        // Calculate the initial position for the obj will be added.
        Position startPos = getStartPointForDraw(world, objDoor, door);

        // Return null if the startPos is null.
        // It represents this object can't add into world because of
        // the size is out of the bound of world or other some reasons.
        if (startPos == null) return null;

        // Call the `drawGameObjIntoWorld` with the initial position.
        if (!drawGameObjIntoWorld(world, obj, startPos)) return null;

        return objDoor;
    }

    /**
     * Return the start position where the object will be added into world.
     *
     * 1. Get the position of the door relative to world.
     * 2. Calculate the position of the door of the object relative to world.
     * 3. Calculate the initial position.
     *
     * @param world - game world.
     * @param objDoor - be drawn object.
     * @param door
     * @return - start position.
     */
    private static Position getStartPointForDraw(TETile[][] world, Door objDoor, Door door) {
        int worldWidth = world.length;
        int worldHeight = world[0].length;

        // Get the position of the door relative to world.
        Position doorPosRelativeToWorld = door.getPosInWorld();

        // Calculate the position of the door of the object relative to world.
        Position objDoorPosRelativeToWorld = doorPosRelativeToWorld.add(door.getDirection());

        // Calculate the initial position.
        // Add the opposite position of original position equals to minus one.
        Position res = objDoorPosRelativeToWorld.add(objDoor.getDoorPos().opposite());

        // Return null if the position out of the bound of world.
        if (res.getX() < 0 || res.getY() < 0 ||
                res.getX() >= worldWidth || res.getY() >= worldHeight)
            return null;

        return res;
    }

    /**
     * Fill the tiles with NOTHING tile.
     * @param world
     */
    public static void fillBoardWithNothing(TETile[][] world) {
        int worldWidth = world.length;
        int worldHeight = world[0].length;

        for (int i = 0; i < worldWidth; i++) {
            for (int j = 0; j < worldHeight; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }
}
