package data.rooms.object;

import data.persons.Facing;
import data.rooms.Room;
import data.tiled.TiledImageLayer;
import data.persons.Person;
import data.tiled.pathfinding.target.RoomObjectTarget;
import data.tiled.pathfinding.target.Target;

import java.awt.*;
import java.util.ArrayList;

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

    public boolean isInsideUsableRange(int x, int y) {
        int realX = this.x * 32 + r.getX();
        int realY = this.y * 32 + r.getY();
        return (x >= realX && y >= realY && x <= realX + width && y <= realY + height);
    }

    public boolean isFree() {
        return maxUsers > users.size();
    }

    public Target getTarget() {
        return target;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isUsingEvent(Person p) {
        return users.contains(p);
    }

    public boolean check(Person p) {
        if(isInsideUsableRange((int) p.getPosition().getX(), (int) p.getPosition().getY()) && !users.contains(p)) {
            return users.add(p);
        }
        return false;
    }

    public void update() {
        users.removeIf(p -> !isInsideUsableRange((int) p.getPosition().getX(), (int) p.getPosition().getY()));
    }

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

    public ArrayList<Person> getUsers() {
        return users;
    }

    public Facing getFacingWhenUsing() {
        return facingWhenUsing;
    }
}
