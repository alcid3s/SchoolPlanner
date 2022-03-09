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
            if(layer.asJsonObject().getString("type").equalsIgnoreCase("tilelayer")) {

                TiledLayer tiledLayer = new TiledLayer(layer.asJsonObject());

                JsonArray layerData = layer.asJsonObject().getJsonArray("data");
                for (int i = 0; i < layerData.size(); i++) {
                    int data = layerData.getInt(i);
                    tiledLayer.addValue(data, i / tiledLayer.getHeight(), (i % tiledLayer.getHeight()));
                }
                layers.add(tiledLayer);
                System.out.println(tiledLayer);
            }
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
