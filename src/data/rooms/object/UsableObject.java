package data.rooms.object;

import data.tilted.pathfinding.target.MapTarget;
import data.persons.Person;

import java.util.ArrayList;

public class UsableObject {
    private ArrayList<Person> users;
    private MapTarget target;
    private int maxUsers;
    private int x;
    private int y;
    private int width;
    private int height;

    public UsableObject(int maxUsers, int x, int y, int width, int height) {
        this.users = new ArrayList<>();
        this.maxUsers = maxUsers;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
}
