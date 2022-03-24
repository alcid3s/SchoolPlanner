package data.tilted.pathfinding.target;

import data.tilted.TiledImageLayer;

import java.awt.*;

public abstract class Target {
    public int tileXLocation;
    public int tileYLocation;
    public TargetCallback enterCallback;
    public TargetCallback leaveCallback;
    public TiledImageLayer collisionLayer;
    public int[][] reached;

    public Target(Point location, TiledImageLayer collisionLayer, int xSizeArray, int ySizeArray) {
        this.tileXLocation = location.x;
        this.tileYLocation = location.y;
        this.enterCallback = enterCallback;
        this.leaveCallback = leaveCallback;
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

    public TargetCallback getEnterCallback() {
        return enterCallback;
    }

    public TargetCallback getLeaveCallback() {
        return leaveCallback;
    }

    public TiledImageLayer getCollisionLayer() {
        return collisionLayer;
    }

    public int[][] getReached() {
        return reached;
    }
}
