package byow.Core;

import java.util.ArrayList;
import java.util.Random;

public class RoomGenerator {
    private int roomNumbers;
    private ArrayList<Room> roomList;

    private static final int MINROOMWIDTH = 4;
    private static final int MAXROOMWIDTH = 10;
    private static final int MINROOMHEIGHT = 4;
    private static final int MAXROOMHEIGHT = 10;
    private static final int MINXPOS = 5;
    private static final int MAXXPOS = 75;
    private static final int MINYPOS = 5;
    private static final int MAXYPOS = 40;

    public RoomGenerator() {
        roomList = new ArrayList<>();
        roomNumbers = 0;
    }

    /** Generate one rooms. */
    public void roomsGenerate(Random random, int rooms) {
        roomNumbers = rooms;
        for (int i = 0; i < roomNumbers; i++) {
            int width = RandomUtils.uniform(random, MINROOMWIDTH, MAXROOMWIDTH); // width
            int height = RandomUtils.uniform(random, MINROOMHEIGHT, MAXROOMHEIGHT); // height
            int x = RandomUtils.uniform(random, MINXPOS, MAXXPOS); // x pos
            int y = RandomUtils.uniform(random, MINYPOS, MAXYPOS); // y pos
            Room room = new Room(width, height, new Position(x, y));
            roomList.add(room);
        }
    }

    public ArrayList<Room> getRoomList() {
        return roomList;
    }
}
