package data.tiled;

import javax.json.JsonObject;
import java.util.ArrayList;

/**
 * Class TiledObjectLayer
 * Holds objects for a specific layer.
 */

public class TiledObjectLayer {
    private final ArrayList<TiledObject> objects;
    private final int x;
    private final int y;
    private final String name;

    /**
     * Constructor of TiledObjectLayer
     * @param object where the objects are in.
     */

    public TiledObjectLayer(JsonObject object) {
        x = object.getInt("x");
        y = object.getInt("y");
        name = object.getString("name");
        objects = new ArrayList<>();
        object.getJsonArray("objects").forEach(o -> objects.add(new TiledObject(o.asJsonObject())));
    }

    /**
     * Method getObjects
     * @return list of all the objects.
     */

    public ArrayList<TiledObject> getObjects() {
        return objects;
    }

    /**
     * Method getX
     * @return private int x
     */

    public int getX() {
        return x;
    }

    /**
     * Method getY
     * @return private int y
     */

    public int getY() {
        return y;
    }

    /**
     * Method getName
     * @return private String name
     */

    public String getName() {
        return name;
    }
}
