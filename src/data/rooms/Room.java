package data.rooms;

import data.Target;
import data.persons.Person;
import data.persons.Student;
import data.tilted.TiledImageLayer;
import data.tilted.TiledMap;
import data.tilted.TiledObject;
import data.tilted.TiledObjectLayer;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Optional;

public abstract class Room implements Comparable, Serializable {
    private TiledMap map;
    private String name;
    private int capacity;
    private Target target;
    private int x;
    private int y;
    private int width;
    private int height;
    private Point location;
    private HashMap<Point, Boolean> studentsChairs;
    private HashMap<Point, Boolean> teacherChairs;


    public Room(TiledMap map, String name, int capacity, Target target, Point location, int x, int y, int width, int height) {
        this.map = map;
        this.name = name;
        this.capacity = capacity;
        this.target = target;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.location = location;

        initChairs();
    }

    private void initChairs() {
        studentsChairs = new HashMap<>();
        teacherChairs = new HashMap<>();

        Optional<TiledImageLayer> layerOptional = map.getLayer("Chairs");
        Optional<TiledObject> roomOptional = map.getObject("student" + name);
        if(roomOptional.isPresent() && layerOptional.isPresent()) {
            TiledImageLayer layer = layerOptional.get();
            TiledObject room = roomOptional.get();
            for(int i = 0; i < layer.getValues().length; i++) {
                for(int j = 0; j < layer.getValues()[i].length; j++) {
                    if(isInRoom(i,j)) {
                        Point p = new Point(i,j);
                        if(room.isInObject(i,j)) {
                            studentsChairs.put(p, false);
                        } else {
                            teacherChairs.put(p, false);
                        }
                    }
                }
            }
        }

    }

    public String getName(){
        return this.name;
    }

    public String getSystemName(){
        return name + " (" + capacity + ")";
    }

    public int getCapacity(){
        return this.capacity;
    }

    @Override
    public String toString(){
        return this.name + " (" + this.capacity + ")";
    }

    @Override
    public int compareTo(Object o){
        return this.toString().compareTo(o.toString());
    }

    public boolean isInRoom(Person p) {
        return p.getPosition().getX() >= x && p.getPosition().getX() <= x + width && p.getPosition().getY() >= y && p.getPosition().getY() <= y + height;
    }

    public boolean isInRoom(int tileX, int tileY) {
        return tileX * 32 >= x && tileX * 32 <= x + width && tileY * 32 >= y && tileY * 32 <= y + height;
    }

    public Target getTarget() {
        return target;
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