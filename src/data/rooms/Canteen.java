package data.rooms;

import data.tilted.pathfinding.target.MapTarget;
import data.tilted.TiledMap;

import java.awt.*;

public class Canteen extends Room{

    public Canteen(TiledMap m, String name, int capacity, Point location, MapTarget target, int x, int y, int width, int height){
        super(m,name, capacity, target,location, x, y, width, height);
    }
}
