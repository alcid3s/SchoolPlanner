package data.tilted;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TiledSetManager {
    private static TiledSetManager tiledSetManager;
    private ArrayList<TiledImageSet> images;

    public TiledSetManager() {
        images = new ArrayList<>();
    }

    public static TiledSetManager getInstance() {
        if(tiledSetManager == null) {
            tiledSetManager = new TiledSetManager();
        }
        return tiledSetManager;
    }

    public void addTiledImageSet(TiledImageSet tiledImageSet) {
        this.images.add(tiledImageSet);
    }

    public BufferedImage getImageFromID(int id) {
        for (TiledImageSet imageSet : images) {
            if (imageSet.contains(id)) {
                return imageSet.getTileImage(id);
            }
        }
        return null;
    }


}
