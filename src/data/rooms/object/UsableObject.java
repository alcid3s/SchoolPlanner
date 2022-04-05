package data.rooms.object;

import data.persons.Facing;
import data.rooms.Room;
import data.tiled.TiledImageLayer;
import data.persons.Person;
import data.tiled.pathfinding.target.RoomObjectTarget;
import data.tiled.pathfinding.target.Target;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class UsableObject
 * Creates usableObject with various methods
 */

public class UsableObject {
    private final ArrayList<Person> users;
    private final Target target;
    private final int maxUsers;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Room r;
    private final Facing facingWhenUsing;

    /**
     * Constructor UsableObject
     * Creates an object UsableObject
     * @param r room for the object
     * @param collisionLayer for the object
     * @param maxUsers for the object
     * @param loc location for the object
     * @param width for the object
     * @param height for the object
     * @param facingWhenUsing for the object
     */

    public UsableObject(Room r, TiledImageLayer collisionLayer, int maxUsers, Point loc, int width, int height, Facing facingWhenUsing) {
        this.users = new ArrayList<>();
        this.r = r;
        this.maxUsers = maxUsers;
        this.x = loc.x;
        this.y = loc.y;
        this.width = width;
        this.height = height;
        this.facingWhenUsing = facingWhenUsing;
        target = new RoomObjectTarget(r, loc, collisionLayer);
    }

    /**
     * Method isInsideUsableRange
     * Returns true or false based on if the position is in range or not
     * @param x position
     * @param y position
     * @return if the position is in range or not
     */

    public boolean isInsideUsableRange(int x, int y) {
        int realX = this.x * 32 + r.getX();
        int realY = this.y * 32 + r.getY();
        return (x >= realX && y >= realY && x <= realX + width && y <= realY + height);
    }

    /**
     * Method isFree
     * @return if object is free
     */

    public boolean isFree() {
        return maxUsers > users.size();
    }

    /**
     * Method getTarget
     * @return target
     */

    public Target getTarget() {
        return target;
    }

    /**
     * Method getX
     * @return x
     */

    public int getX() {
        return x;
    }

    /**
     * Methode getY
     * @return y
     */

    public int getY() {
        return y;
    }

    /**
     * Method getWidth
     * @return width
     */

    public int getWidth() {
        return width;
    }

    /**
     * Method getHeight
     * @return height
     */

    public int getHeight() {
        return height;
    }

    /**
     * Method isUsingEvent
     * @param p to get person
     * @return if users contains person
     */

    public boolean isUsingEvent(Person p) {
        return users.contains(p);
    }

    /**
     * Method check
     * Checks if Person p is in range of UsableObject
     * @param p to get person
     * @return person added to users
     */

    public boolean check(Person p) {
        if(isInsideUsableRange((int) p.getPosition().getX(), (int) p.getPosition().getY()) && !users.contains(p)) {
            return users.add(p);
        }
        return false;
    }

    /**
     * Method update
     * Updates users
     */

    public void update() {
        users.removeIf(p -> !isInsideUsableRange((int) p.getPosition().getX(), (int) p.getPosition().getY()));
    }

    /**
     * Method toString
     * @return string with current parameter values
     */

    @Override
    public String toString() {
        return "UsableObject{" +
                "target=" + target +
                ", maxUsers=" + maxUsers +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    /**
     * Method getUsers
     * @return users
     */

    public ArrayList<Person> getUsers() {
        return users;
    }

    /**
     * Methode getFacingWhenUsing
     * @return facingWhenUsing
     */

    public Facing getFacingWhenUsing() {
        return facingWhenUsing;
    }
}
