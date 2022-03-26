package data.persons;

import data.Animation;
import data.Schedule;
import data.rooms.Room;
import data.rooms.object.UsableObject;
import data.tilted.pathfinding.target.Target;
import org.jfree.fx.FXGraphics2D;
import tasks.LeaveTask;
import tasks.Task;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import java.io.Serializable;
import java.util.Optional;
import java.util.Random;

public abstract class Person implements Comparable, Serializable {
    private String name;
    public Animation animation;
    public final int size;
    public Task task;
    public double angle;
    public double speed;
    private boolean isSpawned;
    protected boolean doUpdate;
    private boolean doSpawn;
    private Point2D position;
    public Point direction;

    public Person(String name, Animation animation) {
        this.name = name;
        this.animation = animation;
        this.angle = 0;
        this.speed = 200;
        this.size = this.animation.getImage().getWidth();

        doSpawn = false;
        doUpdate = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        Schedule.getInstance().sort();
    }

    public void draw(FXGraphics2D graphics) {
        if (isSpawned() && doUpdate) {
            AffineTransform tx = graphics.getTransform();
            tx.translate(getPosition().getX() - (size / 2.0), getPosition().getY() - (size / 2.0));
            graphics.drawImage(animation.getImage(), tx, null);
        }
    }
    public abstract void update(double deltaTime);

    public void spawn(Point2D position) {
        if(!isSpawned() && doSpawn) {
            setSpawned(true);
            setPosition(new Point2D.Double(position.getX() - size/2, position.getY() - size/2));
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

    public void setDoSpawn(boolean set){
        doSpawn = set;
    }

    public void setDoUpdate(boolean set){
        doUpdate = set;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Point2D calculateMovement(Point direction, int tileX, int tileY) {
        Point2D goTo = new Point2D.Double(32 * (tileX + direction.x) + 16, 32 * (tileY + direction.y) + 16);
        Point2D neededToMove = new Point2D.Double(goTo.getX() - getPosition().getX(), goTo.getY() - getPosition().getY());
        neededToMove = new Point2D.Double(neededToMove.getX() / neededToMove.distance(0, 0) * speed, neededToMove.getY() / neededToMove.distance(0, 0) * speed);
        return neededToMove;
    }

    public void move(Point2D neededToMove, double deltaTime) {
        setPosition(new Point2D.Double(getPosition().getX() + (neededToMove.getX() * deltaTime), getPosition().getY() + (neededToMove.getY() * deltaTime)));
    }

    public void goCloserToTarget(Target target, double deltaTime) {
        if(isSpawned && doUpdate){
            int tileX = (int) Math.floor(getPosition().getX() / 32);
            int tileY = (int) Math.floor(getPosition().getY() / 32);
            if(!target.isAtTarget(tileX, tileY)) {
                this.direction = target.getDirection(tileX, tileY);
                if(direction.x != 0 || direction.y != 0) {
                    Point2D neededToMove = calculateMovement(direction, tileX,tileY);
                    move(neededToMove, deltaTime);
                }
            } else {
                if(task instanceof LeaveTask){
                    isSpawned = false;
                    doUpdate = false;
                    task = null;
                }
                this.direction = null;
            }
        }
    }

    public void setTask(Task task){
        this.task = task;
    }

    public void leave() {
        this.task = new LeaveTask(this);
    }
}
