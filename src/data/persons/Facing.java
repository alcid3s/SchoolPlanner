package data.persons;

import java.awt.*;

public enum Facing {
    NORTH(0,-1),
    EAST(1,0),
    SOUTH(0,1),
    WEST(-1,0),
    STATIONARY(0,0);

    private final Point direction;
    private static final Facing[] facing = values();
    Facing(int x, int y) {
        direction = new Point(x,y);
    }

    public Point getDirection() {
        return direction;
    }

    public static Facing getFacing(Point dir) {
        for(Facing f : facing) {
            if(f.getDirection().x == dir.x && f.getDirection().y == dir.y) {
                return f;
            }
        }
        return null;
    }
}
