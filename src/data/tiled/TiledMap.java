package data.tiled;

import data.Schedule;
import data.rooms.*;
import data.tiled.pathfinding.target.MapTarget;
import org.jfree.fx.FXGraphics2D;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Class TiledMap
 */
public class TiledMap {
    private final ArrayList<TiledImageLayer> imageLayers;
    private final ArrayList<TiledObjectLayer> objectLayers;
    private TiledImageLayer collisionLayer;
    private TiledObjectLayer roomLayer;
    private Point studentSpawn;
    private MapTarget exitTarget;
    private final int height;
    private final int width;
    private static TiledMap map;
    private TiledImageLayer fireAlarmLayer;

    /**
     * Constructor TiledMap
     * @param filename of the tiledMap. Needs to be in resources and local file.
     */
    private TiledMap(String filename) {
        map = this;
        JsonReader jsonReader = Json.createReader(getClass().getClassLoader().getResourceAsStream(filename));
        JsonObject object = jsonReader.readObject();
        imageLayers = new ArrayList<>();
        objectLayers = new ArrayList<>();
        this.height = object.getInt("height") * 32;
        this.width = object.getInt("width") * 32;
        object.getJsonArray("layers").forEach(layer -> {
            if (layer.asJsonObject().getString("type").equalsIgnoreCase("tilelayer")) {
                if (layer.asJsonObject().getString("name").equalsIgnoreCase("Barrier")) {
                    collisionLayer = new TiledImageLayer(layer.asJsonObject());
                } else {
                    TiledImageLayer tiledLayer = new TiledImageLayer(layer.asJsonObject());
                    if(tiledLayer.getName().equalsIgnoreCase("firealarm")) {
                        fireAlarmLayer = tiledLayer;
                    }
                    imageLayers.add(tiledLayer);
                }
            } else {
                TiledObjectLayer objectLayer = new TiledObjectLayer(layer.asJsonObject());
                if (objectLayer.getName().equalsIgnoreCase("Rooms")) {
                    roomLayer = objectLayer;
                } else {
                    objectLayers.add(new TiledObjectLayer(layer.asJsonObject()));
                }
            }
        });

        object.getJsonArray("tilesets").forEach(tileSet -> {
            TiledSetManager.getInstance().addTiledImageSet(new TiledImageSet(tileSet.asJsonObject()));
        });

        if (roomLayer != null) {
            roomLayer.getObjects().forEach(tiledObject -> {
                Schedule.getInstance().addRoom(createNewRoom(tiledObject));
            });
        }
        initSpawns();

    }

    /**
     * Method getInstance
     * Returns the instance of TiledMap
     * @return tiledMap
     */
    public static TiledMap getInstance() {
        if (map == null) {
            map = new TiledMap("School_Map.json");
        }
        return map;
    }

    /**
     * Method createNewRoom
     * @param objectLayer of the room
     * @return The room created out of the objectLayer
     */
    private Room createNewRoom(TiledObject objectLayer) {
        String name = objectLayer.getName();
        int size = 0;
        Point location = new Point(0, 0);
        for (JsonValue ob : objectLayer.getJsonObject().getJsonArray("properties")) {
            if (ob.asJsonObject().getString("name").equalsIgnoreCase("size"))
                size = ob.asJsonObject().getInt("value");
            if (ob.asJsonObject().getString("name").equalsIgnoreCase("centerLocation")) {
                String[] values = ob.asJsonObject().getString("value").split(",");
                location = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
            }
        }
        MapTarget t = new MapTarget(location, collisionLayer);
        if (name.toLowerCase().contains("la") || name.toLowerCase().contains("college")) {
            return new Classroom(this, name, size, location, t, objectLayer.getX(), objectLayer.getY(), objectLayer.getWidth(), objectLayer.getHeight());
        }
        if (name.toLowerCase().contains("xplora")) {
            return new Xplora(this, name, size, location, t, objectLayer.getX(), objectLayer.getY(), objectLayer.getWidth(), objectLayer.getHeight());
        }
        if (name.toLowerCase().contains("teacher")) {
            return new TeacherRoom(this, name, size, location, t, objectLayer.getX(), objectLayer.getY(), objectLayer.getWidth(), objectLayer.getHeight());
        }
        if (name.toLowerCase().contains("canteen")) {
            return new Canteen(this, name, size, location, t, objectLayer.getX(), objectLayer.getY(), objectLayer.getWidth(), objectLayer.getHeight());
        }
        if (name.toLowerCase().contains("wc")) {
            return new Toilet(this, name, size, location, t, objectLayer.getX(), objectLayer.getY(), objectLayer.getWidth(), objectLayer.getHeight());
        }
        return new Classroom(this, name, size, location, t, objectLayer.getX(), objectLayer.getY(), objectLayer.getWidth(), objectLayer.getHeight());

    }

    /**
     * Method initSpawns
     * Sets the spawn for the student and the target for the exit
     */
    private void initSpawns() {
        studentSpawn = getCenterLocation(getObject("studentSpawn"));
        Point s = new Point((int) studentSpawn.getX() / 32, (int) studentSpawn.getY() / 32);
        exitTarget = new MapTarget(s, collisionLayer);

    }

    /**
     * Method getCenterLocation
     * @param objectOptional of the tiledObject.
     * @return center Location of the object. If object does not exists it will return a point with locations 0,0
     */
    public Point getCenterLocation(Optional<TiledObject> objectOptional) {
        if (objectOptional.isPresent()) {
            TiledObject object = objectOptional.get();
            return new Point(object.getX() + (object.getWidth() / 2), object.getY() + (object.getHeight() / 2));
        } else {
            return new Point(0, 0);
        }
    }

    /**
     * Method draw
     * @param graphics to draw the map on.
     */
    public void draw(FXGraphics2D graphics) {
        for (TiledImageLayer layer : imageLayers) {
            layer.draw(graphics);
        }
    }

    /**
     * Method getLayer
     * @param value (name) of the layer.
     * @return Optional of layer.
     */
    public Optional<TiledImageLayer> getLayer(String value) {
        for (TiledImageLayer imageLayer : imageLayers) {
            if (imageLayer.getName().equalsIgnoreCase(value)) {
                return Optional.of(imageLayer);
            }
        }
        return Optional.empty();
    }

    /**
     * Method getObject
     * @param name of the object
     * @return Optional of the TiledObject
     */
    public Optional<TiledObject> getObject(String name) {
        for (TiledObjectLayer layer : objectLayers) {
            for (TiledObject object : layer.getObjects()) {
                if (object.getName().equalsIgnoreCase(name))
                    return Optional.of(object);
            }
        }
        return Optional.empty();
    }

    /**
     * Method getCollisionLayer
     * @return
     */
    public TiledImageLayer getCollisionLayer() {
        return collisionLayer;
    }

    /**
     * Method getStudentSpawn
     * @return spawn of the Student.
     */
    public Point getStudentSpawn() {
        return studentSpawn;
    }

    /**
     * Method getExitTarget
     * @return exitTarget
     */
    public MapTarget getExitTarget() {
        return exitTarget;
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
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * Method getFireAlarmLayer
     * @return the fire alarm layer.
     */
    public TiledImageLayer getFireAlarmLayer() {
        return fireAlarmLayer;
    }
}
