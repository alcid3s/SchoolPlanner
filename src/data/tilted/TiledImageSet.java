package data.tilted;

import javax.imageio.ImageIO;
import javax.json.JsonObject;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class TiledImageSet {
    private int columns;
    private int tileWidth;
    private int tileHeight;
    private int imageHeight;
    private int imageWidth;
    private int tileCount;
    private int firstGid;
    private String imageName;
    private String name;
    private HashMap<Integer, BufferedImage> tiles;

    public TiledImageSet(JsonObject tiledSet) {
        columns = tiledSet.getInt("columns");
        tileWidth = tiledSet.getInt("tilewidth");
        tileHeight = tiledSet.getInt("tileheight");
        imageHeight = tiledSet.getInt("imageheight");
        imageWidth = tiledSet.getInt("imagewidth");
        tileCount = tiledSet.getInt("tilecount");
        firstGid = tiledSet.getInt("firstgid");
        imageName = tiledSet.getString("image");
        name = tiledSet.getString("name");
        tiles = new HashMap<>();
        final BufferedImage image;
        try {
            image = ImageIO.read(getClass().getClassLoader().getResource(imageName));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        for (int i = 0; i < tileCount; i++) {
            BufferedImage currentImage = image.getSubimage(i % columns * tileWidth, i / columns * tileHeight, tileWidth, tileHeight);
            tiles.put(i + firstGid, currentImage);
        }
    }

    public boolean contains(int data) {
        return tiles.containsKey(data);
    }

    public BufferedImage getTileImage(int id) {
        return tiles.get(id);
    }
}
