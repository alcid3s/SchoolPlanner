package data.tilted;

import javax.json.JsonObject;
import java.util.ArrayList;

public class TiledObjectLayer {
    private final ArrayList<TiledObject> objects;
    private int offsetX;
    private int offsetY;
    private final int x;
    private final int y;
    private final String name;

    public TiledObjectLayer(JsonObject object) {
        if (object.containsKey("offsetx")) {
            offsetX = object.getInt("offsetx");
            offsetY = object.getInt("offsety");
        } else {
            offsetX = 0;
            offsetY = 0;
        }
        x = object.getInt("x");
        y = object.getInt("y");
        name = object.getString("name");
        objects = new ArrayList<>();
        object.getJsonArray("objects").forEach(o -> objects.add(new TiledObject(o.asJsonObject())));
    }

    public ArrayList<TiledObject> getObjects() {
        return objects;
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
}
