package data.rooms;

import data.Schedule;
import data.persons.Facing;
import data.persons.Student;
import data.persons.Teacher;
import data.rooms.object.UsableObject;
import data.tiled.pathfinding.target.MapTarget;
import data.persons.Person;
import data.tiled.TiledImageLayer;
import data.tiled.TiledMap;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

/**
 * Abstract class Room
 * Creates objects that inherit this class with various methods
 */

public abstract class Room implements Comparable<Room>, Serializable {
    TiledMap map;
    String name;
    int capacity;
    MapTarget target;
    int x;
    int y;
    int width;
    int height;
    Point location;
    ArrayList<UsableObject> studentsChairs;
    ArrayList<UsableObject> teacherChairs;

    /**
     * Constructor room
     * Creates an object Room
     * @param map for the object
     * @param name for the object
     * @param capacity for the object
     * @param target for the object
     * @param location for the object
     * @param x for the object
     * @param y for the object
     * @param width for the object
     * @param height for the object
     */

    public Room(TiledMap map, String name, int capacity, MapTarget target, Point location, int x, int y, int width, int height) {
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

    /**
     * Method initChairs
     * Creates ArrayList with chairs for teachers and students
     */

    public void initChairs() {
        studentsChairs = new ArrayList<>();
        teacherChairs = new ArrayList<>();

        Optional<TiledImageLayer> layerOptional = map.getLayer("Chairs");
        if(layerOptional.isPresent()) {
            TiledImageLayer layer = layerOptional.get();

            int xTile = (int) Math.floor(x/32);
            int yTile = (int) Math.floor(y/32);
            int widthTile = (int) Math.floor(width/32);
            int heightTile = (int) Math.floor(height/32);
            for(int i = xTile; i <= xTile + widthTile; i++) {
                for(int j = yTile; j <= yTile + heightTile; j++) {
                    if(layer.getValues()[i][j] == 0) {
                        continue;
                    }
                    if(isInRoom(i,j)) {
                        Point pTile = new Point(i - xTile,j - yTile);
                        UsableObject object = new UsableObject(this,map.getCollisionLayer(),1,pTile,32,32, getFacingOfObject(layer.getValues()[i][j]));
                        studentsChairs.add(object);
                    }
                }
            }
        }
    }

    /**
     * Method getFreeChair
     * @param p (=person) for type of person
     * @return free UsableObject or with corresponding object
     */

    public Optional<UsableObject> getFreeChair(Person p) {
        ArrayList<UsableObject> list = new ArrayList<>();
        if(p instanceof Student) {
            list = studentsChairs;
        } else if(p instanceof Teacher) {
            list = teacherChairs;
        }
        Collections.shuffle(list);
        for(UsableObject object : list) {
            if(object.isFree()) {
                return Optional.of(object);
            }
        }
        return Optional.empty();
    }

    /**
     * Method getName
     * @return name
     */

    public String getName(){
        return this.name;
    }

    /**
     * Method getSystemName
     * @return name + capacity
     */

    public String getSystemName(){
        return name + " (" + capacity + ")";
    }

    /**
     * Method getCapacity
     * @return capacity
     */

    public int getCapacity(){
        return this.capacity;
    }

    /**
     * Method toString
     * @return string of name and capacity
     */

    @Override
    public String toString(){
        return this.name + " (" + this.capacity + ")";
    }

    /**
     * Method compareTo
     * @param r (=room) name to compare to other room name
     * @return 0 if room has the same name, 1 if not
     */

    @Override
    public int compareTo(Room r){
        return this.name.compareTo(r.getName());
    }

    /**
     * Method isInRoom
     * @param tileX of room
     * @param tileY of room
     * @return true is tile is in room, false if not
     */

    public boolean isInRoom(int tileX, int tileY) {
        return tileX * 32 > x && Math.floor(tileX * 32) < x + width && Math.floor(tileY * 32) > y && Math.floor(tileY * 32) < y + height;
    }

    /**
     * Method getTarget
     * @return target
     */

    public MapTarget getTarget() {
        return target;
    }

    /**
     * Method getX
     * @return x
     */

    public int getX() {
        return x;
    }

    /**
     * Method getY
     * @return y
     */

    public int getY() {
        return y;
    }

    /**
     * Method getWidth
     * @return width
     */

    public int getWidth() {
        return this.width;
    }

    /**
     * Method getHeight
     * @return height
     */

    public int getHeight() {
        return height;
    }

    /**
     * Method update
     * Updates chairs in ArrayList
     * @param deltaTime to do update
     */

    public void update(double deltaTime) {
        studentsChairs.forEach(UsableObject::update);
        teacherChairs.forEach(UsableObject::update);
    }

    /**
     * Static method getRandomRoom
     * @param c (= class) room to get instance of
     * @return instance of room
     */

    public static Room getRandomRoom(Class<? extends Room> c) {
        ArrayList<Room> rooms = new ArrayList<>(Schedule.getInstance().getRoomList());
        Collections.shuffle(rooms);
        for(Room room : rooms) {
            if(c.isInstance(room)) {
                return room;
            }
        }
        return null;
    }

    /**
     * HashMap to get facing directions
     */

    private final HashMap<Integer, Facing> hashMap = new HashMap<Integer, Facing>()
    {{
        put(286, Facing.NORTH);
        put(287, Facing.SOUTH);
        put(288, Facing.NORTH);
        put(302, Facing.WEST);
        put(303, Facing.EAST);
        put(304, Facing.WEST);

        put(271, Facing.EAST);
        put(272, Facing.WEST);
        put(270, Facing.WEST);
        put(254, Facing.NORTH);
        put(255, Facing.SOUTH);
        put(256, Facing.NORTH);
        put(1, Facing.SOUTH);
        put(147, Facing.WEST);
        put(149,Facing.EAST);
        put(97,Facing.NORTH);
        put(2707, Facing.SOUTH);
    }};

    /**
     * Method getFacingOfObject
     * @param id to get facing of
     * @return facing of object
     */

    public Facing getFacingOfObject(int id) {
        if(hashMap.containsKey(id)) {
            return hashMap.get(id);
        }
        return Facing.SOUTH;
    }
}