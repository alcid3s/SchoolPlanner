package data.persons;

import simulation.Animation;
import data.*;
import data.tiled.pathfinding.target.Target;
import org.jfree.fx.FXGraphics2D;
import tasks.IdleTask;
import tasks.LeaveTask;
import tasks.Task;
import tasks.TriggerFireAlarmTask;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * Abstract class Person
 * Class to create objects that inherit this class with various methods
 */

public abstract class Person implements Comparable<Person>, Serializable {
    private String name;
    private final Animation animation;
    final int size;
    private Task task;
    private Task previousTask;
    public double angle;
    public double speed;
    private boolean isSpawned;
    boolean doUpdate;
    private boolean doSpawn;
    private Point2D position;
    public Point direction;

    /**
     * Constructor Person
     * Creates an object Person
     * @param name for the object
     * @param animation for the object
     */

    public Person(String name, Animation animation) {
        this.name = name;
        this.animation = animation;
        this.angle = 0;
        this.speed = 200;
        this.size = this.animation.getImage().getWidth();

        /*
         * Check if school is open and students are allowed to spawn
         */
        if (Clock.getTime().getHour() < 8 || Clock.getTime().getHour() > 16) {
            this.doUpdate = false;
            this.doSpawn = false;
        } else {
            this.doUpdate = true;
            this.doSpawn = true;
        }
    }

    /**
     * Method getName
     * @return name
     */

    public String getName() {
        return this.name;
    }

    /**
     * Method setName
     * @param name to set to
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method draw
     * Draws image for the object on the canvas
     * @param graphics of the canvas
     */

    public void draw(FXGraphics2D graphics) {
        if (isSpawned() && this.doUpdate) {
            /*
             * Creates new AffineTransform for object
             */
            AffineTransform tx = graphics.getTransform();
            tx.translate(getPosition().getX() - (this.size / 2.0), getPosition().getY() - (this.size / 2.0));
            graphics.drawImage(this.animation.getImage(), tx, null);
        }
    }

    /**
     * Method update
     * Updates position of object on the canvas
     * @param deltaTime to do update
     */

    public void update(double deltaTime) {
        if (this.doUpdate) {
            if (this.task == null) {
                this.task = new IdleTask(this);
            }
            if (this.direction != null) {
                if (this.task.isPlayerUsingObject()) {
                    this.animation.update(0, Facing.getFacing(this.direction));
                } else {
                    this.animation.update(deltaTime, Facing.getFacing(this.direction));
                }
            }
            this.task.update(deltaTime);
        }
    }

    /**
     * Method spawn
     * @param position to spawn object
     */

    public void spawn(Point2D position) {
        if (!isSpawned() && this.doSpawn) {
            setSpawned(true);
            setPosition(new Point2D.Double(position.getX() - size / 2, position.getY() - size / 2));
        }
    }

    /**
     * Inherited method toString
     * @return String of object
     */

    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Implemented method compareTo
     * Compares names of two objects of type person
     * @param person to compare with
     * @return compared value as integer
     */

    @Override
    public int compareTo(Person person) {
        return this.name.compareTo(person.getName());
    }

    /**
     * Method getJsonString
     * @return String intended for JSON files
     */

    public String getJsonString() {
        return this.name;
    }

    /**
     * Method isSpawned
     * @return check if object is spawned
     */

    public boolean isSpawned() {
        return this.isSpawned;
    }

    /**
     * Method setSpawned
     * @param set to private attribute isSpawned
     */

    public void setSpawned(boolean set) {
        this.isSpawned = set;
    }

    /**
     * Method setDoSpawn
     * @param set to private attribute doSpawn
     */

    public void setDoSpawn(boolean set) {
        this.doSpawn = set;
    }

    /**
     * Method setDoUpdate
     * @param set to private attribute doUpdate
     */

    public void setDoUpdate(boolean set) {
        this.doUpdate = set;
    }

    /**
     * Method getPosition
     * @return position of object
     */

    public Point2D getPosition() {
        return position;
    }

    /**
     *
     * @param position
     */

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
        if (isSpawned && doUpdate) {
            int tileX = (int) Math.floor(getPosition().getX() / 32);
            int tileY = (int) Math.floor(getPosition().getY() / 32);
            if (!target.isAtTarget(tileX, tileY)) {
                this.direction = target.getDirection(tileX, tileY);
                if (direction.x != 0 || direction.y != 0) {
                    Point2D neededToMove = calculateMovement(direction, tileX, tileY);
                    move(neededToMove, deltaTime);
                }
            } else {
                if (task instanceof LeaveTask) {
                    isSpawned = false;
                    doUpdate = true;
                    task = null;
                    doSpawn = false;
                }
                this.direction = null;
            }
        }
    }

    public void setTask(Task task){
        if(task instanceof TriggerFireAlarmTask){
            this.previousTask = this.task;
        }
        this.task = task;
    }

    public void leave() {
        if(!(this.task instanceof TriggerFireAlarmTask)){
            this.previousTask = this.task;
        }
        this.task = new LeaveTask(this);
    }

    public void moveToExactLocation(Target target, double deltaTime) {
        int neededX = target.getTotalTileXLocation() * 32 + 16;
        int neededY = target.getTotalTileYLocation() * 32 + 16;

        double gotoX = neededX - getPosition().getX();
        double gotoY = neededY - getPosition().getY();

        this.direction = new Point((int) gotoX, (int) gotoY);
        Point2D.Double p = new Point2D.Double(gotoX, gotoY);
        if (Math.abs(p.x) >= 3 || Math.abs(p.y) >= 3) {
            Point2D neededToMove = calculateExactMovement(p, getPosition().getX(), getPosition().getY());
            move(neededToMove, deltaTime);
        }
    }

    public Point2D calculateExactMovement(Point2D direction, double x, double y) {
        Point2D goTo = new Point2D.Double(direction.getX() + x, direction.getY() + y);
        Point2D neededToMove = new Point2D.Double(goTo.getX() - x, goTo.getY() - y);
        neededToMove = new Point2D.Double(neededToMove.getX() / neededToMove.distance(0, 0) * speed, neededToMove.getY() / neededToMove.distance(0, 0) * speed);
        return neededToMove;
    }


    public int getSize() {
        return size;
    }

    public Task getTask() {
        return task;
    }

    public Task getPreviousTask(){
        return previousTask;
    }

    public void setPreviousTask(Task task){
        this.previousTask = task;
    }
}
