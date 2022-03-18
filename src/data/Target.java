package data;

import data.tilted.TiledImageLayer;
import data.tilted.TiledSetManager;
import javafx.util.Pair;
import org.jfree.fx.FXGraphics2D;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.*;

public class Target {
    private int tileXLocation;
    private int tileYLocation;
    private int[][] reached;

    public static final HashMap<RenderingHints.Key, Object> RenderingProperties = new HashMap<>();

    static{
        RenderingProperties.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        RenderingProperties.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        RenderingProperties.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    }

    public Target(Point2D location, TiledImageLayer collisionLayer) {
        tileXLocation = (int) (location.getX()/32);
        tileYLocation = (int) (location.getY()/32);
        Pair<Integer, Integer> start = new Pair<>(tileXLocation, tileYLocation);

        Queue<Pair<Integer,Integer>> frontier = new LinkedList<>();
        frontier.offer(start);
        reached = new int[collisionLayer.getHeight()][collisionLayer.getWidth()];
        System.out.println("Created 2d map with [" + collisionLayer.getHeight() + "] [" + collisionLayer.getWidth() + "]");
        System.out.println("Collision layer bounds: ");
        collisionLayer.print2DMapValues();
        for(int i = 0; i < collisionLayer.getHeight(); i++) {
            for(int j = 0; j < collisionLayer.getWidth(); j++) {
                reached[i][j] = Integer.MAX_VALUE;
            }
        }
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
        print();
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
}
