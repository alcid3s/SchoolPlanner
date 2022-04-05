package data.tiled;

import javax.imageio.ImageIO;
import javax.json.JsonObject;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

/**
 * Class TiledImageSet
 */
public class TiledImageSet {
    private final HashMap<Integer, BufferedImage> tiles;

    /**
     * Constructor of TiledImageSet
     * @param tiledSet object of all the images.
     */
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

    /**
     * Method contains
     * @param data of the value to check
     * @return boolean that represents if the value is in the hashmap.
     */
    public boolean contains(int data) {
        return tiles.containsKey(data);
    }

    /**
     * Method getTileImage
     * @param id of the value to get
     * @return BufferedImage that matches the id.
     */
    public BufferedImage getTileImage(int id) {
        return tiles.get(id);
    }
}
