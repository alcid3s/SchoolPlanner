package data.persons;
import java.awt.*;

/**
 * Enum Facing
 * Creates facings for objects that inherits abstract class Person
 */

public enum Facing {

    NORTH(0,-1),
    EAST(1,0),
    SOUTH(0,1),
    WEST(-1,0),
    STATIONARY(0,0);

    private final Point direction;
    private static final Facing[] facing = values();

    Facing(int x, int y) {
        this.direction = new Point(x,y);
    }

    /**
     * Method GetDirection
     * @return private attribute direction
     */

    public Point getDirection() {
        return this.direction;
    }

    /**
     * Static method getFacing
     * @param dir current facing direction of object that inherits abstract class Person
     * @return new facing direction of object that inherits abstract class Person
     */

    public static Facing getFacing(Point dir) {
        for(Facing f : facing) {
            if(f.getDirection().x == dir.x && f.getDirection().y == dir.y) {
                return f;
            }
        }
        return null;
    }
}
