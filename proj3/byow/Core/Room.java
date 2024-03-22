package byow.Core;

public class Room {
    private int width;
    private int height;
    private Position center; // x=(width-1)/2; y=(height-1)/2
    private Position bottomLeftConner;

    public Room(int width, int height, Position center) {
        this.width = width;
        this.height = height;
        this.center = center;
        int x = center.x - (width-1)/2;
        int y = center.y - (height-1)/2;
        bottomLeftConner = new Position(x, y);
    }

    public Position getBottomLeftConner() {
        return bottomLeftConner;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
