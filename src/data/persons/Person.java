package data.persons;

import simulation.Animation;
import data.Clock;
import data.Schedule;
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

    public Person(String name, Animation animation) {
        this.name = name;
        this.animation = animation;
        this.angle = 0;
        this.speed = 200;
        this.size = this.animation.getImage().getWidth();

        if (Clock.getTime().getHour() < 8 || Clock.getTime().getHour() > 16) {
            this.doUpdate = false;
            this.doSpawn = false;
        } else {
            this.doUpdate = true;
            this.doSpawn = true;
        }
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

    public void update(double deltaTime) {
        if (doUpdate) {
            if (task == null) {
                task = new IdleTask(this);
            }
            if (direction != null) {
                if (task.isPlayerUsingObject()) {
                    animation.update(0, Facing.getFacing(direction));
                } else {
                    animation.update(deltaTime, Facing.getFacing(direction));
                }
            }
            task.update(deltaTime);
        }
    }

    public void spawn(Point2D position) {
        if (!isSpawned() && doSpawn) {
            setSpawned(true);
            setPosition(new Point2D.Double(position.getX() - size / 2, position.getY() - size / 2));
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Person p) {
        return this.name.compareTo(p.getName());
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

    public void setDoSpawn(boolean set) {
        doSpawn = set;
    }

    public void setDoUpdate(boolean set) {
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
