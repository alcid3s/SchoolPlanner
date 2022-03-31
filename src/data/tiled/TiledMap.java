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

    public TiledMap(String filename) {
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

    public static TiledMap getInstance() {
        if (map == null) {
            map = new TiledMap("School_Map.json");
        }
        return map;
    }

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

    private void initSpawns() {
        studentSpawn = getCenterLocation(getObject("studentSpawn"));
        Point s = new Point((int) studentSpawn.getX() / 32, (int) studentSpawn.getY() / 32);
        exitTarget = new MapTarget(s, collisionLayer);

    }

    public Point getCenterLocation(Optional<TiledObject> objectOptional) {
        if (objectOptional.isPresent()) {
            TiledObject object = objectOptional.get();
            return new Point(object.getX() + (object.getWidth() / 2), object.getY() + (object.getHeight() / 2));
        } else {
            return new Point(0, 0);
        }
    }

    public void draw(FXGraphics2D graphics) {
        for (TiledImageLayer layer : imageLayers) {
            layer.draw(graphics);
        }
    }

    public Optional<TiledImageLayer> getLayer(String value) {
        for (TiledImageLayer imageLayer : imageLayers) {
            if (imageLayer.getName().equalsIgnoreCase(value)) {
                return Optional.of(imageLayer);
            }
        }
        return Optional.empty();
    }

    public Optional<TiledObject> getObject(String name) {
        for (TiledObjectLayer layer : objectLayers) {
            for (TiledObject object : layer.getObjects()) {
                if (object.getName().equalsIgnoreCase(name))
                    return Optional.of(object);
            }
        }
        return Optional.empty();
    }

    public TiledImageLayer getCollisionLayer() {
        return collisionLayer;
    }

    public Point getStudentSpawn() {
        return studentSpawn;
    }

    public MapTarget getExitTarget() {
        return exitTarget;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public TiledImageLayer getFireAlarmLayer() {
        return fireAlarmLayer;
    }
}