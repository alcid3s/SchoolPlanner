package data.tilted;

import javax.json.JsonObject;
import java.util.ArrayList;

public class TiledObjectLayer {
    private ArrayList<TiledObject> objects;
    private int offsetX;
    private int offsetY;
    private int x;
    private int y;
    private String name;
    private JsonObject jsonObject;

    public TiledObjectLayer(JsonObject object) {
        if(object.containsKey("offsetx")) {
            offsetX = object.getInt("offsetx");
            offsetY = object.getInt("offsety");
        } else {
            offsetX = 0;
            offsetY = 0;
        }
        this.jsonObject = object;
        x = object.getInt("x");
        y = object.getInt("y");
        name = object.getString("name");
        objects = new ArrayList<>();
        object.getJsonArray("objects").forEach(o -> {
            objects.add(new TiledObject(o.asJsonObject()));
        });
    }

    public ArrayList<TiledObject> getObjects() {
        return objects;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }
}
