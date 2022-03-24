package data.rooms;

import data.persons.Student;
import data.persons.Teacher;
import data.rooms.object.UsableObject;
import data.tilted.pathfinding.target.MapTarget;
import data.persons.Person;
import data.tilted.TiledImageLayer;
import data.tilted.TiledMap;
import data.tilted.TiledObject;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

public abstract class Room implements Comparable, Serializable {
    protected TiledMap map;
    protected String name;
    protected int capacity;
    protected MapTarget target;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Point location;
    protected ArrayList<UsableObject> studentsChairs;
    protected ArrayList<UsableObject> teacherChairs;


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
        System.out.println("\n\n\n\n\n----name " + name);

        initChairs();
    }

    public void initChairs() {
        studentsChairs = new ArrayList<>();
        teacherChairs = new ArrayList<>();

        Optional<TiledImageLayer> layerOptional = map.getLayer("Chairs");
        if(layerOptional.isPresent()) {
            System.out.println("Loading chairs...");
            TiledImageLayer layer = layerOptional.get();
            System.out.println("Room start point: " + name + " " + x/32 + " " + y/32);

            int xTile = (int) Math.floor(x/32);
            int yTile = (int) Math.floor(y/32);
            int widthTile = (int) Math.floor(width/32);
            int heightTile = (int) Math.floor(height/32);
            for(int i = xTile; i <= xTile + widthTile; i++) {
                for(int j = yTile; j <= yTile + heightTile; j++) {
                    //System.out.println(room.getName() + " " + i + " " + j);
                    if(layer.getValues()[i][j] == 0) {
                        continue;
                    }
                    if(isInRoom(i,j)) {
                        Point pTile = new Point(i - xTile,j - yTile);
                        UsableObject object = new UsableObject(this,map.getCollisionLayer(),1,pTile,32,32);
                        studentsChairs.add(object);
                    }
                }
            }
        } else {
            System.out.println("Could not find Chairs layer or room with name: student" + name + " (" + name + ")");
            return;
        }

        System.out.println("Room: " + name);
        System.out.println("\t(x,y) " + x + " , " + y + " (width,height) " + width + " , " + height + " <- Whole room");
        System.out.println("\tStudents: " + studentsChairs.size() + "\n\tTeachers: " + teacherChairs.size());
    }

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
        return tileX * 32 > x && Math.floor(tileX * 32) < x + width && Math.floor(tileY * 32) > y && Math.floor(tileY * 32) < y + height;
    }

    public MapTarget getTarget() {
        return target;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return height;
    }

    public void update(double deltaTime) {
        studentsChairs.forEach(o -> {
            o.update();
        });
        teacherChairs.forEach(o -> {
            o.update();
        });
    }
}