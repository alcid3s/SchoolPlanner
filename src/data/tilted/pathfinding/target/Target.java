package data.tilted.pathfinding.target;

import data.persons.Person;
import data.tilted.TiledImageLayer;

import java.awt.*;

public abstract class Target {
    public int tileXLocation;
    public int tileYLocation;
    public TiledImageLayer collisionLayer;
    public int[][] reached;

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



    public void print() {
        for(int i = 0; i < reached.length; i++) {
            for(int j = 0; j < reached[i].length; j++) {
                int value = reached[i][j];
                if(value == Integer.MAX_VALUE) {
                    System.out.print("-1\t");
                } else {
                    System.out.print(value + "\t");
                }
            }
            System.out.println();
        }
    }
    public abstract Point getDirection(int tileX, int tileY);

    public boolean isAtTarget(int tileX, int tileY) {
        return tileX == tileXLocation && tileY == tileYLocation;
    }

    public boolean isExactAtTarget(double x, double y) {
        return Math.abs(x - tileXLocation*32) == 0 && Math.abs(y - tileYLocation*32) == 0;
    }

    public int getTileXLocation() {
        return tileXLocation;
    }

    public int getTileYLocation() {
        return tileYLocation;
    }


    public TiledImageLayer getCollisionLayer() {
        return collisionLayer;
    }

    public int[][] getReached() {
        return reached;
    }

    public boolean isAtTarget(Person p) {
        System.out.println((Math.floor(p.getPosition().getX()/32) == tileXLocation && Math.floor(p.getPosition().getY()/32) == tileYLocation) + " " + Math.floor(p.getPosition().getX()/32) + " " + Math.floor(p.getPosition().getY()/32) + " " + tileXLocation + " " + tileYLocation);
        return Math.floor(p.getPosition().getX()/32) == tileXLocation && Math.floor(p.getPosition().getY()/32) == tileYLocation;
    }

    public boolean isExactAtTarget(Person p) {
        System.out.println((p.getPosition().getX() - tileXLocation*32 == 16 && p.getPosition().getY() - tileYLocation*32 == 16) + " Exact at target?");
        return p.getPosition().getX() - tileXLocation*32 == 16 && p.getPosition().getY() - tileYLocation*32 == 16;
    }

    public int getTotalTileXLocation() {
        return tileXLocation;
    }

    public int getTotalTileYLocation() {
        return tileXLocation;
    }
}
