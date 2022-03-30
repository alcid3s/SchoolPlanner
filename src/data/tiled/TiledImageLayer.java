package data.tiled;

import org.jfree.fx.FXGraphics2D;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TiledImageLayer {
    private final int width;
    private final int height;
    private final int offsetX;
    private final int offsetY;
    private final int[][] values;
    private final String name;

    public TiledImageLayer(JsonObject layerObject) {
        this.height = layerObject.getInt("height");
        this.width = layerObject.getInt("width");
        this.name = layerObject.getString("name");
        if (layerObject.containsKey("offsetx")) {
            this.offsetX = layerObject.getInt("offsetx");
            this.offsetY = layerObject.getInt("offsety");
        } else {
            this.offsetX = 0;
            this.offsetY = 0;
        }

        values = new int[width][height];

        JsonArray layerData = layerObject.getJsonArray("data");
        for (int i = 0; i < layerData.size(); i++) {
            int data = layerData.getInt(i);
            addValue(data, i % getWidth(), (i / getWidth()));
        }
    }

    public void addValue(int data, int width, int height) {
        if (values.length > width && values[width].length > height) {
            values[width][height] = data;
        }
    }

    @Override
    public String toString() {
        ArrayList<String> valuesAsString = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                if (values[i][j] != 0) {
                    valuesAsString.add("(" + i + "," + j + ") " + values[i][j]);
                }
            }
        }
        return "TiltedLayer: " + name + "\n" +
                "Width: " + width + "\n" +
                "Height: " + height + "\n" +
                "Values: " + valuesAsString;
    }

    public void draw(FXGraphics2D graphics) {
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                int data = values[i][j];
                if (data == 0) {
                    continue;
                }
                BufferedImage image = TiledSetManager.getInstance().getImageFromID(data);
                AffineTransform transformImage = graphics.getTransform();
                transformImage.translate(i * 32 + offsetX, j * 32 + offsetY);
                graphics.drawImage(image, transformImage, null);
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int[][] getValues() {
        return values;
    }

    public String getName() {
        return name;
    }

}
