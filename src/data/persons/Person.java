package data.persons;

import data.Schedule;
import data.rooms.Room;
import data.tilted.pathfinding.target.Target;
import org.jfree.fx.FXGraphics2D;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public abstract class Person implements Comparable, Serializable {
    private String name;
    private final BufferedImage[] sprites;
    public Target target;
    public double angle;
    public double speed;
    private boolean isSpawned;
    private Point2D position;


    public Person(String name, BufferedImage[] sprites) {
        this.name = name;
        this.sprites = sprites;
        this.angle = 0;
        this.speed = 200;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        Schedule.getInstance().sort();
    }

    public abstract void draw(FXGraphics2D graphics);
    public abstract void update(double deltaTime);

    public void spawn(Point2D position) {
        if(!isSpawned()) {
            setSpawned(true);
            setPosition(new Point2D.Double(position.getX() - sprites[0].getWidth()/2, position.getY() - sprites[0].getWidth()/2));
        }
    }

    @Override
    public String toString(){
        return this.name;
    }

    @Override
    public int compareTo(Object o){
        return this.toString().compareTo(o.toString());
    }

    public String getJsonString() {
        return name;
    }


    public boolean isSpawned() {
        return isSpawned;
    }

    public void setSpawned(boolean spawned) {
        isSpawned = spawned;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public BufferedImage[] getSprites() {
        return sprites;
    }
}
