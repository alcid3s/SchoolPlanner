package data.persons;

import data.Schedule;
import data.rooms.Room;
import data.rooms.object.UsableObject;
import data.tilted.pathfinding.target.Target;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Optional;
import java.util.Random;

public abstract class Person implements Comparable, Serializable {
    private String name;
    private final BufferedImage[] sprites;
    public Target target;
    public double angle;
    public double speed;
    private boolean isSpawned;
    private Point2D position;
    private int animationCounter = 0;

    public Person(String name, BufferedImage[] sprites) {
        this.name = name;
        this.sprites = sprites;
        this.angle = 0;
        this.speed = 200;
        Room r = Schedule.getInstance().getRoomList().get(new Random().nextInt(Schedule.getInstance().getRoomList().size()));
        //Room r = Schedule.getInstance().getRoom("Xplora4Kamer");
        Optional<UsableObject> object = r.getFreeChair(this);
        if(object.isPresent()) {
            target = object.get().getTarget();
            System.out.println("Student -> Found Target! (Room: " + r.getName() + ")");
            target.print();
        } else {
            System.out.println("Student -> Target not found! (Room: " + r.getName() + ")");
        }
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

    public void move(Point2D neededToMove, double deltaTime) {
        setPosition(new Point2D.Double(getPosition().getX() + (neededToMove.getX() * deltaTime), getPosition().getY() + (neededToMove.getY() * deltaTime)));

    }

    public Point2D calculateMovement(Point direction, int tileX, int tileY) {
        Point2D goTo = new Point2D.Double(32 * (tileX + direction.x) + 16, 32 * (tileY + direction.y) + 16);
        Point2D neededToMove = new Point2D.Double(goTo.getX() - getPosition().getX(), goTo.getY() - getPosition().getY());
        neededToMove = new Point2D.Double(neededToMove.getX() / neededToMove.distance(0, 0) * speed, neededToMove.getY() / neededToMove.distance(0, 0) * speed);
        double angleTo = Math.atan2(neededToMove.getY(), neededToMove.getX());
        double aDiff = angleTo - angle;
        while (aDiff < -Math.PI) {
            aDiff += 2 * Math.PI;
        }
        while (aDiff > Math.PI) {
            aDiff -= 2 * Math.PI;
        }
        angle = angleTo;
        return neededToMove;
    }

    public void drawAnimation(FXGraphics2D graphics, AffineTransform tx, int state) {
        if(state == 1 && this.animationCounter >= 11 || state == 1 && this.animationCounter < 9){
            this.animationCounter = 9;
        }else if(state == 2 && this.animationCounter >= 8 || state == 2 && this.animationCounter < 6){
            this.animationCounter = 6;
        }else if(state == 3 && this.animationCounter >= 5 || state == 3 && this.animationCounter < 3){
            this.animationCounter = 3;
        }else if(state == 4 && this.animationCounter >= 2 || state == 4 && this.animationCounter < 0){
            this.animationCounter = 0;
        }else if(state == 5){
            this.animationCounter = 0;
        }
        graphics.drawImage(getSprites()[animationCounter], tx, null);
        animationCounter++;
    }
}
