package data.rooms;

import data.tiled.pathfinding.target.MapTarget;
import data.tiled.TiledMap;

import java.awt.*;

/**
 * Class Canteen
 * Extends interface Room and creates canteen object
 */

public class Canteen extends Room {

    /**
     * Constructor Canteen
     * Creates an object Canteen
     * @param m (= tiledmap) for the object
     * @param name for the object
     * @param capacity for the object
     * @param location for the object
     * @param target for the object
     * @param x for the object
     * @param y for the object
     * @param width for the object
     * @param height for the object
     */

    public Canteen(TiledMap m, String name, int capacity, Point location, MapTarget target, int x, int y, int width, int height){
        super(m,name, capacity, target,location, x, y, width, height);
    }
}
