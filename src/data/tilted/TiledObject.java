package data.tilted;

import javax.json.JsonObject;

public class TiledObject {
    private String name;
    private int height;
    private int width;
    private int x;
    private int y;

    private String text;
    private String color;
    private String fontFamily;
    private int pixelSize;
    private JsonObject jsonObject;

    public TiledObject(JsonObject object) {
        name = object.getString("name");
        height = object.getInt("height");
        width = object.getInt("width");
        x = object.getInt("x");
        y = object.getInt("y");
        jsonObject = object;

        JsonObject textObject = object.getJsonObject("text");
        if(textObject != null) {
            fontFamily = textObject.getString("fontfamily");
            pixelSize = textObject.getInt("pixelsize");
            text = textObject.getString("text");
            color = textObject.getString("color");
        }
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

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public int getPixelSize() {
        return pixelSize;
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public boolean isInObject(int tileX, int tileY) {
        return tileX * 32 >= x && tileX * 32 <= x + width && tileY * 32 >= y && tileY * 32 <= y + height;
    }
}
