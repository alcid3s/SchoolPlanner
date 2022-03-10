package data.tilted;

import org.jfree.fx.FXGraphics2D;

import javax.json.JsonObject;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class TiledImageLayer {
    private int width;
    private int height;
    private int offsetX;
    private int offsetY;
    private int[][] values;
    private String name;

    public TiledImageLayer(JsonObject layerObject) {
        this.height = layerObject.getInt("height");
        this.width = layerObject.getInt("width");
        this.name = layerObject.getString("name");
        if(layerObject.containsKey("offsetx")) {
            this.offsetX = layerObject.getInt("offsetx");
            this.offsetY = layerObject.getInt("offsety");
        } else {
            this.offsetX = 0;
            this.offsetY = 0;
        }
        System.out.println("Created layer:");
        System.out.println("Name: " + this.name);
        System.out.println("Width: " + width + " Height: " + height);
        System.out.println("OffsetX: " + offsetX + " OffsetY: " + offsetY);

        values = new int[width][height];
    }

    public boolean addValue(int data, int width, int height) {
        if(values.length > width && values[width].length > height) {
            values[width][height] = data;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        ArrayList<String> valuesAsString = new ArrayList<>();
        for(int i = 0; i < values.length; i++) {
            for(int j = 0; j < values[i].length; j++) {
                    if(values[i][j] != 0) {
                        valuesAsString.add("(" + i + "," + j + ") " + values[i][j]);
                    }
                }
        }
        return "TiltedLayer:\n" +
                "Width: " + width + "\n" +
                "Height: " + height + "\n" +
                "Values: " + valuesAsString.toString();
    }

    public void draw(FXGraphics2D graphics) {
        for(int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                int data = values[i][j];
                BufferedImage image = TiledSetManager.getInstance().getImageFromID(data);
                AffineTransform transformImage = graphics.getTransform();
                System.out.println("---");
                System.out.println("Translate: " + transformImage.getTranslateX() + " , " + transformImage.getTranslateY());
                transformImage.translate(i * 32 + offsetX, j * 32 + offsetY);
                System.out.println("Translate: " + transformImage.getTranslateX() + " , " + transformImage.getTranslateY());
                graphics.drawImage(image,transformImage, null);
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public int[][] getValues() {
        return values;
    }

    public String getName() {
        return name;
    }
}