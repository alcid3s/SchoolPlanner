package data.rooms;

import data.tiled.pathfinding.target.MapTarget;
import data.tiled.TiledMap;

import java.awt.*;

/**
 * Class Xplora
 * Creates objects that inherit Room
 */

public class Xplora extends Room {

    /**
     * Constructor Xplora
     * @param m tiled map for the object
     * @param name for the object
     * @param capacity for the object
     * @param location for the object
     * @param target for the object
     * @param x for the object
     * @param y for the object
     * @param width for the object
     * @param height for the object
     */

    public Xplora(TiledMap m, String name, int capacity, Point location, MapTarget target, int x, int y, int width, int height){
        super(m,name, capacity, target,location, x, y, width, height);
    }

}
