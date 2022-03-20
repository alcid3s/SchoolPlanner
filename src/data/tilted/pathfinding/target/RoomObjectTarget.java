package data.tilted.pathfinding.target;

import data.rooms.Room;
import data.tilted.TiledImageLayer;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class RoomObjectTarget extends Target {
    private Room room;
    private int tileRoomWidth;
    private int tileRoomHeight;
    private int tileRoomX;
    private int tileRoomY;

    public RoomObjectTarget(Room room, Point location, TiledImageLayer collisionLayer) {
        super(location, collisionLayer, room.getWidth()/32, room.getHeight()/32);
        this.room = room;
        this.tileRoomWidth = room.getWidth()/32;
        this.tileRoomHeight = room.getHeight()/32;
        this.tileRoomX = room.getX()/32;
        this.tileRoomY = room.getY()/32;

        System.out.println("Created 2d map with [" + reached.length + "] [" + reached[0].length + "]");

        Pair<Integer, Integer> start = new Pair<>(tileXLocation, tileYLocation);
        System.out.println("Start position: " + start.getKey() + " " + start.getValue());

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
               // System.out.println("Checking... " + pointToCheck.getKey() + " " + pointToCheck.getValue());
                if(reached[pointToCheck.getValue()][pointToCheck.getKey()] == Integer.MAX_VALUE) {
                    if(collisionLayer.getValues()[pointToCheck.getKey() + tileRoomX][pointToCheck.getValue() + tileRoomY] != 2585) {
                        reached[pointToCheck.getValue()][pointToCheck.getKey()] = reached[current.getValue()][current.getKey()] + 1;
                        frontier.offer(pointToCheck);
                    }
                }
            }
        }
        print();
    }


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

    public Room getRoom() {
        return room;
    }

    public int getTileRoomWidth() {
        return tileRoomWidth;
    }

    public int getTileRoomHeight() {
        return tileRoomHeight;
    }

    public int getTileRoomX() {
        return tileRoomX;
    }

    public int getTileRoomY() {
        return tileRoomY;
    }

    public int[][] getReached() {
        return reached;
    }

    @Override
    public boolean isAtTarget(int tileX, int tileY) {
        return super.isAtTarget(tileX-tileRoomX, tileY-tileRoomY);
    }

    @Override
    public boolean isExactAtTarget(double x, double y) {
        return super.isExactAtTarget(x-tileRoomX*32, y-tileRoomY*32);
    }
}
