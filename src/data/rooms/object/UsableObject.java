package data.rooms.object;

import data.rooms.Room;
import data.tilted.TiledImageLayer;
import data.persons.Person;
import data.tilted.pathfinding.target.RoomObjectTarget;
import data.tilted.pathfinding.target.Target;

import java.awt.*;
import java.util.ArrayList;

public class UsableObject {
    private ArrayList<Person> users;
    private Target target;
    private int maxUsers;
    private int x;
    private int y;
    private int width;
    private int height;
    private Room r;

    public UsableObject(Room r, TiledImageLayer collisionLayer, int maxUsers, Point loc, int width, int height) {
        this.users = new ArrayList<>();
        this.r = r;
        this.maxUsers = maxUsers;
        this.x = loc.x;
        this.y = loc.y;
        this.width = width;
        this.height = height;
        target = new RoomObjectTarget(r, loc, collisionLayer);
    }

    public boolean startUsingEvent(Person p) {
        if (maxUsers > users.size()) {
            users.add(p);
            return true;
        }
        return false;
    }

    public boolean stopUsingEvent(Person p) {
        return users.remove(p);
    }

    public boolean isInsideUsableRange(int x, int y) {
        int realX = this.x * 32 + r.getX();
        int realY = this.y * 32 + r.getY();
        return (x >= realX && y >= realY && x <= realX + width && y <= realY + height);
    }

    public boolean isFree() {
        return maxUsers > users.size();
    }

    public ArrayList<Person> getUsers() {
        return users;
    }

    public Target getTarget() {
        return target;
    }

    public int getMaxUsers() {
        return maxUsers;
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

    public void check(Person p) {
        if(isInsideUsableRange((int) p.getPosition().getX(), (int) p.getPosition().getY()) && !users.contains(p)) {
            users.add(p);
        }
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
}
