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
    private ArrayList<TiledObjectLayer> objectLayers;
    private TiledImageLayer collisionLayer;

    public TiledMap(String filename) {
        JsonReader jsonReader = Json.createReader(getClass().getClassLoader().getResourceAsStream(filename));
        JsonObject object = jsonReader.readObject();
        imageLayers = new ArrayList<>();
        objectLayers = new ArrayList<>();
        object.getJsonArray("layers").forEach(layer -> {
            if(layer.asJsonObject().getString("type").equalsIgnoreCase("tilelayer")) {
                if(layer.asJsonObject().getString("name").equalsIgnoreCase("Barrier")) {
                    collisionLayer = new TiledImageLayer(layer.asJsonObject());
                } else {
                    TiledImageLayer tiledLayer = new TiledImageLayer(layer.asJsonObject());

                    JsonArray layerData = layer.asJsonObject().getJsonArray("data");
                    for (int i = 0; i < layerData.size(); i++) {
                        int data = layerData.getInt(i);
                        tiledLayer.addValue(data, i % tiledLayer.getWidth(), (i / tiledLayer.getWidth()));
                    }
                    imageLayers.add(tiledLayer);
                    System.out.println(tiledLayer);
                }
            } else {
                    objectLayers.add(new TiledObjectLayer(layer.asJsonObject()));
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
