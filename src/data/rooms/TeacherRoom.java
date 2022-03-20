package data.rooms;

import data.Target;
import data.tilted.TiledMap;

import java.awt.*;

public class TeacherRoom extends Room{

    public TeacherRoom(TiledMap m, String name, int capacity, Point location, Target target, int x, int y, int width, int height){
        super(m,name, capacity, target,location, x, y, width, height);
    }
}
