package data.rooms;

import data.persons.Facing;
import data.rooms.object.UsableObject;
import data.tiled.TiledImageLayer;
import data.tiled.TiledObject;
import data.tiled.pathfinding.target.MapTarget;
import data.tiled.TiledMap;

import javax.json.JsonValue;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

/**
 * Class Classroom
 * Creates objects that inherit Room
 */

public class Classroom extends Room {
    private ArrayList<UsableObject> teacherStandLocations;

    /**
     * Constructor Classroom
     * Creates an object Classroom
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

    public Classroom(TiledMap m, String name, int capacity, Point location, MapTarget target, int x, int y, int width, int height) {
        super(m,name, capacity, target,location, x, y, width, height);
    }

    /**
     * Method initChairs
     * Add chairs for students
     * Add chairs and standLocations for teachers
     */

    @Override
    public void initChairs() {
        studentsChairs = new ArrayList<>();
        teacherChairs = new ArrayList<>();

        Optional<TiledImageLayer> layerOptional = map.getLayer("Chairs");
        Optional<TiledObject> roomOptional = map.getObject("student" + name);
        if(roomOptional.isPresent() && layerOptional.isPresent()) {
            TiledImageLayer layer = layerOptional.get();
            TiledObject room = roomOptional.get();

            int xTile = (int) Math.floor(x/32);
            int yTile = (int) Math.floor(y/32);
            int widthTile = (int) Math.floor(width/32);
            int heightTile = (int) Math.floor(height/32);
            for(int i = xTile; i <= xTile + widthTile; i++) {
                for(int j = yTile; j <= yTile + heightTile; j++) {
                    if(layer.getValues()[i][j] == 0) {
                        continue;
                    }

                    /*
                     * Adds chair to correct ArrayList
                     */

                    if(isInRoom(i,j)) {
                        Point p = new Point(i,j);
                        Point pTile = new Point(i - xTile,j - yTile);
                        UsableObject object = new UsableObject(this,map.getCollisionLayer(),1,pTile,32,32, getFacingOfObject(layer.getValues()[i][j]));
                        if(room.isInObject(i,j)) {
                            studentsChairs.add(object);
                        } else {
                            teacherChairs.add(object);
                        }
                    }
                }
            }
        }

        /*
         * Creates ArrayList with places for teacher to stand
         */

        Optional<TiledObject> roomTeacherOptional = map.getObject("teacher" + name);
        teacherStandLocations = new ArrayList<>();
        if(roomTeacherOptional.isPresent()) {
            TiledObject teachers = roomTeacherOptional.get();
            int xTile = (int) Math.floor(x/32);
            int yTile = (int) Math.floor(y/32);
            int widthTile = (int) Math.floor(width/32);
            int heightTile = (int) Math.floor(height/32);

            for(int i = xTile; i <= xTile + widthTile; i++) {
                for (int j = yTile; j <= yTile + heightTile; j++) {
                    if(!isInRoom(i,j)) {
                        continue;
                    }
                    if(teachers.isInObject(i,j)) {
                        Point pTile = new Point(i - xTile,j - yTile);
                        Facing f = null;
                        for (JsonValue ob : teachers.getJsonObject().getJsonArray("properties")) {
                            if(ob.asJsonObject().getString("name").equalsIgnoreCase("facing")) {
                                String fac = ob.asJsonObject().getString("value");
                                f = Facing.valueOf(fac);
                            }
                        }
                        teacherStandLocations.add(new UsableObject(this, map.getCollisionLayer(), 999, pTile,32,32, f));
                    }
                }
            }
        }
    }

    /**
     * Method getFreeSpot
     * @return shuffled teacherStandLocations
     */

    public UsableObject getFreeSpot() {
        Collections.shuffle(teacherStandLocations);
        return teacherStandLocations.get(0);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        teacherStandLocations.forEach(UsableObject::update);
    }
}
