package data.tiled.pathfinding.target;

import data.tiled.TiledImageLayer;
import javafx.util.Pair;

import java.awt.*;
import java.util.*;

/**
 * Class MapTarget
 * Creates objects on the tiled map that inherit Target
 */

public class MapTarget extends Target {

    public static final HashMap<RenderingHints.Key, Object> RenderingProperties = new HashMap<>();

    /**
     * Static
     * Initialises all renderings for the map
     */

    static {
        RenderingProperties.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        RenderingProperties.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        RenderingProperties.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    }

    /**
     * Constructor
     * @param location of the object
     * @param collisionLayer layer that defines unusable points
     */

    public MapTarget(Point location, TiledImageLayer collisionLayer) {
        super(location, collisionLayer,collisionLayer.getWidth(), collisionLayer.getHeight());
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
                if(reached[pointToCheck.getValue()][pointToCheck.getKey()] == Integer.MAX_VALUE) {
                    if(collisionLayer.getValues()[pointToCheck.getKey()][pointToCheck.getValue()] != 2585) {
                        reached[pointToCheck.getValue()][pointToCheck.getKey()] = reached[current.getValue()][current.getKey()] + 1;
                        frontier.offer(pointToCheck);
                    }
                }
            }
        }
    }

    /**
     * Methid getDirection
     * @param tileX x-direction of overall direction
     * @param tileY y-direction of overall direction
     * @return point of direction
     */

    public Point getDirection(int tileX, int tileY) {
        ArrayList<Point> directions = new ArrayList<>(Arrays.asList(new Point(1,0), new Point(-1,0), new Point(0,1), new Point(0,-1)));

        Point pointToReturn = new Point(0,0);
        if(tileY > 0 && reached.length > tileY && tileX > 0 && reached[tileY].length > tileX) {
            int current = reached[tileY][tileX];
            for (Point p : directions) {
                Point newPoint = new Point(tileX + p.x, tileY + p.y);
                if (newPoint.y > 0 && reached.length > newPoint.y && newPoint.x > 0 && reached[newPoint.y].length > newPoint.x) {
                    if (reached[newPoint.y][newPoint.x] < current) {
                        current = reached[newPoint.y][newPoint.x];
                        pointToReturn = p;
                    }
                }
            }
        }
        return pointToReturn;
    }
}
