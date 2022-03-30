package simulation.firealarm;

import data.persons.Person;
import data.tilted.TiledImageLayer;
import data.tilted.pathfinding.target.MapTarget;
import data.tilted.pathfinding.target.Target;
import callbacks.FireAlarmTriggerCallback;

import java.awt.*;

public class FireAlarmTrigger {
    private Target target;
    private int width = 32;
    private int height = 32;
    private int x;
    private int y;
    private FireAlarmTriggerCallback triggerCallback;


    public FireAlarmTrigger(Point p, TiledImageLayer collisionLayer, FireAlarmTriggerCallback triggerCallback) {
        this.target = new MapTarget(p, collisionLayer);
        this.x = p.x;
        this.y = p.y;
        this.triggerCallback = triggerCallback;
    }
    public boolean isInsideUsableRange(int x, int y) {
        int realX = this.x * 32;
        int realY = this.y * 32;
        return (x >= realX && y >= realY && x <= realX + width && y <= realY + height);
    }

    public void check(Person p) {
        if(isInsideUsableRange((int) p.getPosition().getX(), (int) p.getPosition().getY())) {
            triggerCallback.callback(p);
        }
    }

    public Target getTarget() {
        return target;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public FireAlarmTriggerCallback getTriggerCallback() {
        return triggerCallback;
    }
}
