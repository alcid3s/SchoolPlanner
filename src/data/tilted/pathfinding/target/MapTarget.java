package data.tilted.pathfinding.target;

import data.tilted.TiledImageLayer;
import javafx.util.Pair;
import org.jfree.fx.FXGraphics2D;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.*;

public class MapTarget extends Target {

    public static final HashMap<RenderingHints.Key, Object> RenderingProperties = new HashMap<>();

    static {
        RenderingProperties.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        RenderingProperties.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        RenderingProperties.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    }

    public MapTarget(Point location, TiledImageLayer collisionLayer) {
        super(location, collisionLayer,collisionLayer.getWidth(), collisionLayer.getHeight());
        Pair<Integer, Integer> start = new Pair<>(tileXLocation, tileYLocation);

        Queue<Pair<Integer,Integer>> frontier = new LinkedList<>();
        frontier.offer(start);
        System.out.println("Created 2d map with [" + collisionLayer.getHeight() + "] [" + collisionLayer.getWidth() + "]");
        System.out.println("Collision layer bounds: ");
        collisionLayer.print2DMapValues();
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

    public HashMap<Integer, BufferedImage> images = new HashMap<>();
    public void draw(FXGraphics2D graphics) {
        for(int i = 0; i < reached.length; i++) {
            for (int j = 0; j < reached[i].length; j++) {
                int value = reached[i][j];
                if(value == Integer.MAX_VALUE) {
                    value = -1;
                }
                BufferedImage image;
                if(images.containsKey(value)) {
                    image = images.get(value);
                } else {
                    image = textToImage(value + "", new JLabel("").getFont(), 12);
                    images.put(value, image);
                }
                //graphics.drawString(value + "", j * 32 + 16, i * 32 + 16);

                AffineTransform transformImage = graphics.getTransform();
                transformImage.translate(j * 32 + 8, i * 32 + 8);
                graphics.drawImage(image,transformImage, null);

            }
        }

    }

    public static BufferedImage textToImage(String Text, Font f, float Size){
        //Derives font to new specified size, can be removed if not necessary.
        f = f.deriveFont(Size);

        FontRenderContext frc = new FontRenderContext(null, true, true);

        //Calculate size of buffered image.
        LineMetrics lm = f.getLineMetrics(Text, frc);

        Rectangle2D r2d = f.getStringBounds(Text, frc);

        BufferedImage img = new BufferedImage((int)Math.ceil(r2d.getWidth()), (int)Math.ceil(r2d.getHeight()), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = img.createGraphics();

        g2d.setRenderingHints(RenderingProperties);

        g2d.setBackground(Color.WHITE);
        g2d.setColor(Color.BLACK);

        g2d.clearRect(0, 0, img.getWidth(), img.getHeight());

        g2d.drawString(Text, 0, lm.getAscent());

        g2d.dispose();

        return img;
    }

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

    public int getTileXLocation() {
        return tileXLocation;
    }

    public void setTileXLocation(int tileXLocation) {
        this.tileXLocation = tileXLocation;
    }

    public int getTileYLocation() {
        return tileYLocation;
    }

    public void setTileYLocation(int tileYLocation) {
        this.tileYLocation = tileYLocation;
    }

    public int[][] getReached() {
        return reached;
    }

    public void setReached(int[][] reached) {
        this.reached = reached;
    }

    public HashMap<Integer, BufferedImage> getImages() {
        return images;
    }

    public void setImages(HashMap<Integer, BufferedImage> images) {
        this.images = images;
    }
}
