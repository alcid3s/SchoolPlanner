package data.tilted;

import org.jfree.fx.FXGraphics2D;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.util.ArrayList;

public class TiledMap {
    private ArrayList<TiledLayer> layers;
    private int tileWidth;

    public TiledMap(String filename) {
        JsonReader jsonReader = Json.createReader(getClass().getClassLoader().getResourceAsStream(filename));
        JsonObject object = jsonReader.readObject();
        layers = new ArrayList<>();
        this.tileWidth = object.getInt("tilewidth");
        object.getJsonArray("layers").forEach(layer -> {
            TiledLayer tiltedLayer = new TiledLayer(layer.asJsonObject());

            JsonArray layerData = layer.asJsonObject().getJsonArray("data");
            for (int i = 0 ; i < layerData.size(); i++) {
                int data = layerData.getInt(i);
                tiltedLayer.addValue(data, i/tiltedLayer.getHeight(), (i% tiltedLayer.getHeight()));
            }
            layers.add(tiltedLayer);
            System.out.println(tiltedLayer);
        });

        System.out.println("Loading tileSets");
        object.getJsonArray("tilesets").forEach(tileSet -> {
            TiledSetManager.getInstance().addTiledImageSet(new TiledImageSet(tileSet.asJsonObject()));
        });


    }

    public void draw(FXGraphics2D graphics) {
        for(TiledLayer layer : layers) {
            layer.draw(graphics);
        }
    }
}
