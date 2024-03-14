package byow.Core;

/**
 * This class can represent a 2D coordinate and direction.
 */
public class Position {
    /** Four default direction for 2D game world. */
    public static Position UP = new Position(0, 1);
    public static Position DOWN = new Position(0, -1);
    public static Position LEFT = new Position(-1, 0);
    public static Position RIGHT = new Position(1, 0);

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Return the opposite position of the calling instance relative to the origin.
     *
     * @return - opposite position.
     */
    public Position opposite() {
        return new Position(-this.getX(), -this.getY());
    }

    /**
     * Return true if P is a direction, false otherwise.
     *
     * @param p - a position.
     * @return - boolean.
     */
    public static boolean isDirection(Position p) {
        if (Math.sqrt(p.getX()*p.getX() + p.getY()*p.getY()) == 1) return true;
        return false;
    }

    /**
     * The addition operation for Position class.
     *
     * @return - position after add operation
     */
    public Position add(Position p) {
        return new Position(this.x + p.x, this.y + p.y);
    }

    /**
     * Return true if these two position are equals with x and y.
     *
     * @param obj - compared position.
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Position)) return false;
        if (obj == this) return true;

        Position p = (Position) obj;
        return p.getX() == this.getX() && p.getY() == this.getY();
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static void main(String[] args) {
        Position p1 = new Position(0, 1);
        Position p2 = new Position(12, -5);

        System.out.println(p1.equals(p2));
    }
}
