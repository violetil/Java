package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import java.util.Random;

public class WorldGeneratorTest {
    public static void main(String[] args) {
        Random r = new Random();

        TERenderer ter = new TERenderer();
        WorldGenerator worldGenerator = new WorldGenerator(80, 45);
        ter.initialize(80, 45);
        TETile[][] world = worldGenerator.generateWorld(r.nextLong());
        ter.renderFrame(world);
//        System.out.println(Math.toDegrees(Math.atan2(9.0, 23.0)));
    }
}
