package simulation.firealarm;

import data.tiled.TiledImageLayer;
import data.tiled.pathfinding.target.MapTarget;
import data.tiled.pathfinding.target.Target;
import callbacks.FireAlarmTriggerCallback;

import java.awt.*;

public class FireAlarmTrigger {
    private final Target target;
    private final int width = 32;
    private final int height = 32;
    private final int x;
    private final int y;
    private final FireAlarmTriggerCallback triggerCallback;


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
