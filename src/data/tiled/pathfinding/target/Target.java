package data.tiled.pathfinding.target;

import data.persons.Person;
import data.tiled.TiledImageLayer;

import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Target {
    int tileXLocation;
    int tileYLocation;
    TiledImageLayer collisionLayer;
    int[][] reached;

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

    public abstract Point getDirection(int tileX, int tileY);

    public boolean isAtTarget(int tileX, int tileY) {
        return tileX == tileXLocation && tileY == tileYLocation;
    }

    public boolean isExactAtTarget(double x, double y) {
        return Math.abs(x - tileXLocation*32) == 0 && Math.abs(y - tileYLocation*32) == 0;
    }

    public boolean isAtTarget(Person p) {
        return Math.floor(p.getPosition().getX()/32) == tileXLocation && Math.floor(p.getPosition().getY()/32) == tileYLocation;
    }

    public boolean isExactAtTarget(Person p) {
        return p.getPosition().getX() - tileXLocation*32 == 16 && p.getPosition().getY() - tileYLocation*32 == 16;
    }

    public int getTotalTileXLocation() {
        return tileXLocation;
    }

    public int getTotalTileYLocation() {
        return tileXLocation;
    }

    public int getSteps(Point2D position) {
        return reached[(int) (position.getY()/32)][(int) (position.getX()/32)];
    }
}
