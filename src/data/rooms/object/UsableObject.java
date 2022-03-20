package data.rooms.object;

import data.rooms.Room;
import data.tilted.TiledImageLayer;
import data.tilted.pathfinding.target.MapTarget;
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

    public UsableObject(Room r, TiledImageLayer collisionLayer, int maxUsers, Point loc, int width, int height) {
        this.users = new ArrayList<>();
        this.maxUsers = maxUsers;
        this.x = loc.x;
        this.y = loc.y;
        this.width = width;
        this.height = height;
        target = new RoomObjectTarget(r, loc, collisionLayer);
    }

    public void stopUsingEvent(Person p) {
        users.remove(p);
    }

    public boolean startUsingEvent(Person p) {
        if(maxUsers > users.size()) {
            users.add(p);
            return true;
        }
        return false;
    }

    public void update() {
        users.forEach(p -> {
            if(!isInsideUsableRange((int) p.getPosition().getX(), (int) p.getPosition().getY())) {
                stopUsingEvent(p);
            }
        });

    }

    public boolean isInsideUsableRange(int x, int y) {
        return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
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
}
