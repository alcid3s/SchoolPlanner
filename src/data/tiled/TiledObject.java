package data.tiled;

import javax.json.JsonNumber;
import javax.json.JsonObject;

/**
 * Class TiledObject
 */
public class TiledObject {
    public String name;
    public int height;
    public int width;
    public int x;
    public int y;

    private final JsonObject jsonObject;

    /**
     * Constructor TiledObject
     * @param object of the object values.
     */
    public TiledObject(JsonObject object) {
        name = object.getString("name");
        x = (int) (((JsonNumber) object.get("x")).doubleValue());
        y = (int) (((JsonNumber) object.get("y")).doubleValue());

        height = (int) (((JsonNumber) object.get("height")).doubleValue());
        width = (int) (((JsonNumber) object.get("width")).doubleValue());

        jsonObject = object;
    }

    /**
     * Method getName
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * Method getHeight
     * @return height
     */
    public int getHeight() {
        return height;
    }
    /**
     * Method getWidth
     * @return width
     */
    public int getWidth() {
        return width;
    }
    /**
     * Method getX
     * @return x
     */
    public int getX() {
        return x;
    }
    /**
     * Method getY
     * @return y
     */
    public int getY() {
        return y;
    }
    /**
     * Method getJsonObject
     * @return jsonObject
     */
    public JsonObject getJsonObject() {
        return jsonObject;
    }

    /**
     * Method isInObject
     * Checks if location is inside object
     * @return boolean that represents whether the coordinates are in the object
     */
    public boolean isInObject(int tileX, int tileY) {
        return tileX * 32 >= x && tileX * 32 <= x + width && tileY * 32 >= y && tileY * 32 <= y + height;
    }
}
