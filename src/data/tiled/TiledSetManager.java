package data.tiled;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Singleton Class TiledSetManager
 * Holds all the images using TiledImageSet.
 */

public class TiledSetManager {
    private static TiledSetManager tiledSetManager;
    private final ArrayList<TiledImageSet> images;

    /**
     * Constructor TiledSetManager
     */

    private TiledSetManager() {
        images = new ArrayList<>();
    }

    /**
     * Returns the instance of TiledSetManager
     * @return
     */

    public static TiledSetManager getInstance() {
        if (tiledSetManager == null) {
            tiledSetManager = new TiledSetManager();
        }
        return tiledSetManager;
    }

    /**
     * Method addTiledImageSet
     * @param tiledImageSet to add to the images.
     */

    public void addTiledImageSet(TiledImageSet tiledImageSet) {
        this.images.add(tiledImageSet);
    }

    /**
     * Method getImageFromId.
     * @param id of the image
     * @return BuffferedImage that represents the id.
     */

    public BufferedImage getImageFromID(int id) {
        for (TiledImageSet imageSet : images) {
            if (imageSet.contains(id)) {
                return imageSet.getTileImage(id);
            }
        }
        return null;
    }


}
