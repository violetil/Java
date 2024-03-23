package byow.Core;

import java.util.ArrayList;
import java.util.Random;

public class RoomGenerator {
    private int roomNumbers;
    private ArrayList<Room> roomList;
    private int separateRoomNumbers;
    private ArrayList<Room> separateRoomLists;

    private static final int MINROOMWIDTH = 6;
    private static final int MAXROOMWIDTH = 14;
    private static final int MINROOMHEIGHT = 6;
    private static final int MAXROOMHEIGHT = 14;
    private static final int MINXPOS = 7;
    private int MAXXPOS; // world width - 5
    private static final int MINYPOS = 7;
    private int MAXYPOS; // world height - 5

    public RoomGenerator(int width, int height) {
        MAXXPOS = width - 7;
        MAXYPOS = height - 7;
        roomList = new ArrayList<>();
        separateRoomLists = new ArrayList<>();
        roomNumbers = 0;
        separateRoomNumbers = 0;
    }

    /** Generate one rooms. */
    public void roomsGenerate(Random random, int rooms) {
        roomNumbers = rooms;
        for (int i = 0; i < roomNumbers; i++) {
            int width = RandomUtils.uniform(random, MINROOMWIDTH, MAXROOMWIDTH); // width
            int height = RandomUtils.uniform(random, MINROOMHEIGHT, MAXROOMHEIGHT); // height
            int x = RandomUtils.uniform(random, MINXPOS, MAXXPOS); // x pos
            int y = RandomUtils.uniform(random, MINYPOS, MAXYPOS); // y pos
            Position center = new Position(x, y);
            Room room = new Room(width, height, center);
            roomList.add(room);
        }
    }

    public ArrayList<Room> getRoomList() {
        return roomList;
    }
}
