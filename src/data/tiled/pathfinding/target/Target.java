package data.tiled.pathfinding.target;

import data.persons.Person;
import data.tiled.TiledImageLayer;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Abstract class Target
 * Superclass to create objects that inherit this class
 */

public abstract class Target {
    int tileXLocation;
    int tileYLocation;
    TiledImageLayer collisionLayer;
    int[][] reached;

    /**
     * Constructor
     * @param location of the target on the map
     * @param collisionLayer layer that defines unusable points
     * @param xSizeArray x-size of object
     * @param ySizeArray y-size of object
     */

    public Target(Point location, TiledImageLayer collisionLayer, int xSizeArray, int ySizeArray) {
        this.tileXLocation = location.x;
        this.tileYLocation = location.y;
        this.collisionLayer = collisionLayer;
        this.reached = new int[ySizeArray+1][xSizeArray+1];
        for(int i = 0; i <= ySizeArray; i++) {
            for(int j = 0; j <= xSizeArray; j++) {
                reached[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    /**
     * Abstract method getDirection
     * @param tileX
     * @param tileY
     * @return
     */

    public abstract Point getDirection(int tileX, int tileY);

    /**
     * Method isAtTarget
     * Checks if coordinates are at the same tile as the coordinates of this object
     * @param tileX x-position of Person
     * @param tileY y-position of Person
     * @return check as boolean
     */

    public boolean isAtTarget(int tileX, int tileY) {
        return tileX == tileXLocation && tileY == tileYLocation;
    }

    /**
     * Method isExactAtTarget
     * Checks if coordinates are at the exact same location as the coordinates of this object
     * @param x position of Person
     * @param y position of Person
     * @return check as boolean
     */

    public boolean isExactAtTarget(double x, double y) {
        return Math.abs(x - tileXLocation*32) == 0 && Math.abs(y - tileYLocation*32) == 0;
    }

    /**
     * Method isAtTarget
     * Checks if Person is at the same tile as this object
     * @param p Person to check location from
     * @return check as boolean
     */

    public boolean isAtTarget(Person p) {
        return Math.floor(p.getPosition().getX()/32) == tileXLocation && Math.floor(p.getPosition().getY()/32) == tileYLocation;
    }

    /**
     * Method isExactAtTarget
     * Checks if Person is at the exact same location as this object
     * @param p Person to check location from
     * @return check as boolean
     */

    public boolean isExactAtTarget(Person p) {
        return p.getPosition().getX() - tileXLocation*32 == 16 && p.getPosition().getY() - tileYLocation*32 == 16;
    }

    /**
     * Method getTotalTileXLocation
     * @return private attribute tileXLocation
     */

    public int getTotalTileXLocation() {
        return tileXLocation;
    }

    /**
     * Method getTotalTileYLocation
     * @return private attribute tileXLocation
     */

    public int getTotalTileYLocation() {
        return tileXLocation;
    }

    /**
     * Method getSteps
     * @param position start where the steps have to be calculated from
     * @return amount of steps
     */

    public int getSteps(Point2D position) {
        return reached[(int) (position.getY()/32)][(int) (position.getX()/32)];
    }
}
