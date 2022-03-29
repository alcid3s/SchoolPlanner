package data.tilted;

import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class TiledObject {
    public String name;
    public int height;
    public int width;
    public int x;
    public int y;

    private String text;
    private String color;
    private String fontFamily;
    private int pixelSize;
    private JsonObject jsonObject;

    public TiledObject(JsonObject object) {
        name = object.getString("name");
        x = (int) (((JsonNumber) object.get("x")).doubleValue());
        y = (int) (((JsonNumber) object.get("y")).doubleValue());

        height = (int) (((JsonNumber) object.get("height")).doubleValue());
        width = (int) (((JsonNumber) object.get("width")).doubleValue());


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
        //System.out.println("TiledObject -> Is in object: " + (tileX * 32 >= x && tileX * 32 <= x + width && tileY * 32 >= y && tileY * 32 <= y + height) + " " + tileX + " " + tileY + " " + x + " " + y + " " + width + " " + height);
        return tileX * 32 >= x && tileX * 32 <= x + width && tileY * 32 >= y && tileY * 32 <= y + height;
    }
}
