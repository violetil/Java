package byow.Core;

public class Hallway {
    private Position start;
    private Position end;

    public Hallway(Position s, Position e) {
        start = s;
        end = e;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }
}
