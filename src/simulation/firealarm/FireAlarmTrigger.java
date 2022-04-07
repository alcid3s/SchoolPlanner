package simulation.firealarm;

import data.tiled.TiledImageLayer;
import data.tiled.pathfinding.target.MapTarget;
import data.tiled.pathfinding.target.Target;
import callbacks.FireAlarmTriggerCallback;

import java.awt.*;

/**
 * Class FireAlarmTrigger
 * Creates triggers for activating the fire alarm
 */

public class FireAlarmTrigger {

    private final Target target;
    private final int width = 32;
    private final int height = 32;
    private final int x;
    private final int y;
    private final FireAlarmTriggerCallback triggerCallback;

    /**
     * Constructor
     * @param p point of the triggerable object
     * @param collisionLayer layer that defines all unavailable points on the map
     * @param triggerCallback callback to activate when triggered
     */

    public FireAlarmTrigger(Point p, TiledImageLayer collisionLayer, FireAlarmTriggerCallback triggerCallback) {
        this.target = new MapTarget(p, collisionLayer);
        this.x = p.x;
        this.y = p.y;
        this.triggerCallback = triggerCallback;
    }

    /**
     * Method isInsideUsableRange
     * @param x position of objects that need to use
     * @param y position of objects that need to use
     * @return check if range was considered usable
     */

    public boolean isInsideUsableRange(int x, int y) {
        int realX = this.x * 32;
        int realY = this.y * 32;
        return (x >= realX && y >= realY && x <= realX + width && y <= realY + height);
    }

    /**
     * Method getTarget
     * @return private attribute target
     */

    public Target getTarget() {
        return target;
    }

    /**
     * Method getWidth
     * @return private attribute width
     */

    public int getWidth() {
        return width;
    }

    /**
     * Method getHeight
     * @return private attribute height
     */

    public int getHeight() {
        return height;
    }

    /**
     * Method getX
     * @return private attribute x
     */

    public int getX() {
        return x;
    }

    /**
     * Method getY
     * @return private attribute y
     */

    public int getY() {
        return y;
    }

    /**
     * Method getTriggerCallback
     * @return private attribute triggerCallback
     */

    public FireAlarmTriggerCallback getTriggerCallback() {
        return triggerCallback;
    }
}
