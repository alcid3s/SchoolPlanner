package data.tilted;

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
        this.tileWidth = object.getInt("tilewidth");
        object.getJsonArray("layers").forEach(layer -> {
            JsonObject layerObject = layer.asJsonObject();
            int height = layerObject.getInt("height");
            int width = layerObject.getInt("width");
            TiledLayer tiltedLayer = new TiledLayer(width, height);

            JsonArray layerData = layerObject.getJsonArray("data");
            for (int i = 0 ; i < layerData.size(); i++) {
                int data = layerData.getInt(i);
                tiltedLayer.addValue(data, i/height, (i%height));
            }
            System.out.println(tiltedLayer);
        });

    }
}
