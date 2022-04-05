package data.tiled.pathfinding.target;

import data.persons.Person;
import data.rooms.Room;
import data.tiled.TiledImageLayer;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class MapTarget
 * Creates objects on the tiled map that inherit Target
 */

public class RoomObjectTarget extends Target {
    private final Room room;
    private final int tileRoomWidth;
    private final int tileRoomHeight;
    private final int tileRoomX;
    private final int tileRoomY;

    /**
     * Constructor
     * @param room the object is in
     * @param location of the object in the room
     * @param collisionLayer layer that defines unusable points
     */

    public RoomObjectTarget(Room room, Point location, TiledImageLayer collisionLayer) {
        super(location, collisionLayer, room.getWidth()/32, room.getHeight()/32);
        this.room = room;
        this.tileRoomWidth = room.getWidth()/32;
        this.tileRoomHeight = room.getHeight()/32;
        this.tileRoomX = room.getX()/32;
        this.tileRoomY = room.getY()/32;

        Pair<Integer, Integer> start = new Pair<>(tileXLocation, tileYLocation);

        Queue<Pair<Integer,Integer>> frontier = new LinkedList<>();
        frontier.offer(start);
        reached[start.getValue()][start.getKey()] = 0;

        ArrayList<Pair<Integer,Integer>> valuesToAdd = new ArrayList<>(Arrays.asList(new Pair<>(1, 0), new Pair<>(-1,0), new Pair<>(0,1), new Pair<>(0,-1)));
        while(!frontier.isEmpty()) {
            Pair<Integer,Integer> current = frontier.poll();
            for(Pair<Integer,Integer> p : valuesToAdd) {
                Pair<Integer,Integer> pointToCheck = new Pair<>(current.getKey() + p.getKey(), current.getValue() + p.getValue());
                if(pointToCheck.getKey() < 0 || pointToCheck.getKey() >= collisionLayer.getWidth() || pointToCheck.getValue() < 0 || pointToCheck.getValue() >= collisionLayer.getHeight())
                    continue;
                if(reached.length <= pointToCheck.getValue() || reached[pointToCheck.getValue()].length <= pointToCheck.getKey()) {
                    continue;
                }
                if(reached[pointToCheck.getValue()][pointToCheck.getKey()] == Integer.MAX_VALUE) {
                    if(collisionLayer.getValues()[pointToCheck.getKey() + tileRoomX][pointToCheck.getValue() + tileRoomY] != 2585) {
                        reached[pointToCheck.getValue()][pointToCheck.getKey()] = reached[current.getValue()][current.getKey()] + 1;
                        frontier.offer(pointToCheck);
                    }
                }
            }
        }
    }

    /**
     * Method getDirection
     * @param tileX x-position of the target
     * @param tileY y-position of the target
     * @return direction of the target
     */

    public Point getDirection(int tileX, int tileY) {
        if(room.isInRoom(tileX,tileY)) {
            tileX = tileX - tileRoomX;
            tileY = tileY - tileRoomY;
            ArrayList<Point> directions = new ArrayList<>(Arrays.asList(new Point(1, 0), new Point(-1, 0), new Point(0, 1), new Point(0, -1)));

            Point pointToReturn = new Point(0, 0);
            if (tileY > 0 && reached.length > tileY && tileX > 0 && reached[tileY].length > tileX) {
                int current = reached[tileY][tileX];
                for (Point p : directions) {
                    Point newPoint = new Point(tileX + p.x, tileY + p.y);
                    if (newPoint.y >= 0 && reached.length > newPoint.y && newPoint.x >= 0 && reached[newPoint.y].length > newPoint.x) {
                        if (reached[newPoint.y][newPoint.x] < current) {
                            current = reached[newPoint.y][newPoint.x];
                            pointToReturn = p;
                        }
                    }
                }
            }
            return pointToReturn;
        }
        return room.getTarget().getDirection(tileX,tileY);
    }

    /**
     * Method getRoom
     * @return room where target is in
     */

    public Room getRoom() {
        return room;
    }

    /**
     * Inherited method isAtTarget
     * Checks if coordinates are at the same tile as the coordinates of this object
     * @param tileX x-position of Person
     * @param tileY y-position of Person
     * @return check as boolean
     */

    @Override
    public boolean isAtTarget(int tileX, int tileY) {
        return super.isAtTarget(tileX-tileRoomX, tileY-tileRoomY);
    }

    /**
     * Inherited method isExactAtTarget
     * @param x position of Person
     * @param y position of Person
     * @return check as boolean
     */

    @Override
    public boolean isExactAtTarget(double x, double y) {
        return super.isExactAtTarget(x-tileRoomX*32, y-tileRoomY*32);
    }

    /**
     * Inherited method isAtTarget
     * @param p Person to check location from
     * @return check as boolean
     */

    @Override
    public boolean isAtTarget(Person p) {
        int tX = getTotalTileXLocation() * 32;
        int tY = getTotalTileYLocation() * 32;
        int x = (int) p.getPosition().getX();
        int y = (int) p.getPosition().getY();
        return x >= tX && y >= tY && x <= tX + 32 && y <= tY + 32;
    }

    /**
     * Inherited method isExactAtTarget
     * @param p Person to check location from
     * @return check as boolean
     */

    @Override
    public boolean isExactAtTarget(Person p) {
        int realX = (tileRoomX + tileRoomX) * 32 + 16;
        int realY = (tileRoomY + tileRoomY) * 32 + 16;
        int x = (int) p.getPosition().getX();
        int y = (int) p.getPosition().getX();
        return x == realX && y == realY;
    }

    /**
     * Inherited method getTotalTileXLocation
     * @return location of x-position
     */

    @Override
    public int getTotalTileXLocation() {
        return tileXLocation + tileRoomX;
    }

    /**
     * Inherited method getTotalTileYLocation
     * @return location of y-position
     */

    @Override
    public int getTotalTileYLocation() {
        return tileYLocation + tileRoomY;
    }
}
