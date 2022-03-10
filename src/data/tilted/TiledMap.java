package data.tilted;

import org.jfree.fx.FXGraphics2D;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Optional;

public class TiledMap {
    private ArrayList<TiledImageLayer> imageLayers;
    private ArrayList<TiledImageLayer> objectLayers;

    public TiledMap(String filename) {
        JsonReader jsonReader = Json.createReader(getClass().getClassLoader().getResourceAsStream(filename));
        JsonObject object = jsonReader.readObject();
        imageLayers = new ArrayList<>();
        object.getJsonArray("layers").forEach(layer -> {
            if(layer.asJsonObject().getString("type").equalsIgnoreCase("tilelayer")) {

                TiledImageLayer tiledLayer = new TiledImageLayer(layer.asJsonObject());

                JsonArray layerData = layer.asJsonObject().getJsonArray("data");
                for (int i = 0; i < layerData.size(); i++) {
                    int data = layerData.getInt(i);
                    tiledLayer.addValue(data, i % tiledLayer.getWidth(), (i / tiledLayer.getWidth()));
                }
                imageLayers.add(tiledLayer);
                System.out.println(tiledLayer);
            }
        });

        System.out.println("Loading tileSets");
        object.getJsonArray("tilesets").forEach(tileSet -> {
            TiledSetManager.getInstance().addTiledImageSet(new TiledImageSet(tileSet.asJsonObject()));
        });


    }

    public void draw(FXGraphics2D graphics) {
        for(TiledImageLayer layer : imageLayers) {
            layer.draw(graphics);
        }
    }

    public Optional<TiledImageLayer> getLayer(String value) {
        for (TiledImageLayer imageLayer : imageLayers) {
            if (imageLayer.getName().equalsIgnoreCase(value)) {
                return Optional.of(imageLayer);
            }
        }
        return Optional.empty();
    }
}
