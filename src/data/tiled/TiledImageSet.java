package data.tiled;

import javax.imageio.ImageIO;
import javax.json.JsonObject;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class TiledImageSet {
    private final HashMap<Integer, BufferedImage> tiles;

    public TiledImageSet(JsonObject tiledSet) {
        int columns = tiledSet.getInt("columns");
        int tileWidth = tiledSet.getInt("tilewidth");
        int tileHeight = tiledSet.getInt("tileheight");
        int tileCount = tiledSet.getInt("tilecount");
        int firstGid = tiledSet.getInt("firstgid");
        String imageName = tiledSet.getString("image");
        tiles = new HashMap<>();
        final BufferedImage image;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource(imageName)));
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
