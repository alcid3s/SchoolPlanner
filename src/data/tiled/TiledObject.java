package data.tiled;

import javax.json.JsonNumber;
import javax.json.JsonObject;

public class TiledObject {
    public String name;
    public int height;
    public int width;
    public int x;
    public int y;

    private final JsonObject jsonObject;

    public TiledObject(JsonObject object) {
        name = object.getString("name");
        x = (int) (((JsonNumber) object.get("x")).doubleValue());
        y = (int) (((JsonNumber) object.get("y")).doubleValue());

        height = (int) (((JsonNumber) object.get("height")).doubleValue());
        width = (int) (((JsonNumber) object.get("width")).doubleValue());

        jsonObject = object;
    }


    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public boolean isInObject(int tileX, int tileY) {
        return tileX * 32 >= x && tileX * 32 <= x + width && tileY * 32 >= y && tileY * 32 <= y + height;
    }
}
