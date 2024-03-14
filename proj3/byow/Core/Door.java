package byow.Core;

/**
 * This class represents the entrance and exit of the game object connected to
 * other game objects.
 */
public class Door {
    private Position doorPos; // Position relative to the parent object.
    private Position direction; // The face to direction of this door.
    private GameObject parent;

    public Door(int xPos, int yPos, Position direction, GameObject parent) {
        doorPos = new Position(xPos, yPos);
        this.direction = direction;
        this.parent = parent;
    }

    /** Default door. */
    public Door() {
        this(0, 0, Position.UP, null);
    }

    /**
     * Return the position relative to the game world.
     *
     * @return - position relative to world.
     */
    public Position getPosInWorld() {
        if (parent == null) return null;
        return doorPos.add(parent.getStartPos());
    }

    public GameObject getParent() {
        return parent;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public Position getDirection() {
        return direction;
    }

    public void setDirection(Position direction) {
        this.direction = direction;
    }

    public Position getDoorPos() {
        return doorPos;
    }

    public void setDoorPos(Position doorPos) {
        this.doorPos = doorPos;
    }
}
