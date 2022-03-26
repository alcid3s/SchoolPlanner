package data.tilted;

import data.Schedule;
import data.tilted.pathfinding.target.MapTarget;
import data.rooms.*;
import org.jfree.fx.FXGraphics2D;

import javax.json.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Optional;

public class TiledMap {
    private ArrayList<TiledImageLayer> imageLayers;
    private ArrayList<TiledObjectLayer> objectLayers;
    private TiledImageLayer collisionLayer;
    private TiledObjectLayer roomLayer;
    private Point studentSpawn;
    private Point teacherSpawn;
    private int height;
    private int width;

    public TiledMap(String filename) {
        JsonReader jsonReader = Json.createReader(getClass().getClassLoader().getResourceAsStream(filename));
        JsonObject object = jsonReader.readObject();
        imageLayers = new ArrayList<>();
        objectLayers = new ArrayList<>();
        this.height = object.getInt("height") * 32;
        this.width = object.getInt("width") * 32;
        object.getJsonArray("layers").forEach(layer -> {
            if(layer.asJsonObject().getString("type").equalsIgnoreCase("tilelayer")) {
                if(layer.asJsonObject().getString("name").equalsIgnoreCase("Barrier")) {
                    collisionLayer = new TiledImageLayer(layer.asJsonObject());
                    System.out.println(collisionLayer);
                } else {
                    TiledImageLayer tiledLayer = new TiledImageLayer(layer.asJsonObject());
                    imageLayers.add(tiledLayer);
                    System.out.println(tiledLayer);
                }
            } else {
                TiledObjectLayer objectLayer = new TiledObjectLayer(layer.asJsonObject());
                if(objectLayer.getName().equalsIgnoreCase("Rooms")) {
                    roomLayer = objectLayer;
                } else {
                    objectLayers.add(new TiledObjectLayer(layer.asJsonObject()));
                }
            }
        });

        System.out.println("Loading tileSets");
        object.getJsonArray("tilesets").forEach(tileSet -> {
            TiledSetManager.getInstance().addTiledImageSet(new TiledImageSet(tileSet.asJsonObject()));
        });

        if(roomLayer != null) {
            roomLayer.getObjects().forEach(tiledObject -> {
                Schedule.getInstance().addRoom(createNewRoom(tiledObject));
            });
        }
        initSpawns();

    }

    private Room createNewRoom(TiledObject objectLayer) {
        String name = objectLayer.getName();
        int size = 0;
        Point location = new Point(0,0);
        for(JsonValue ob : objectLayer.getJsonObject().getJsonArray("properties")) {
            if(ob.asJsonObject().getString("name").equalsIgnoreCase("size"))
                size = ob.asJsonObject().getInt("value");
            if(ob.asJsonObject().getString("name").equalsIgnoreCase("centerLocation")) {
                String[] values = ob.asJsonObject().getString("value").split(",");
                location = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
            }
        }
        System.out.println(location);
        MapTarget t = new MapTarget(location, collisionLayer);
        if(name.toLowerCase().contains("la") || name.toLowerCase().contains("zaal")) {
            return new Classroom(this, name, size, location, t, objectLayer.getX(), objectLayer.getY(), objectLayer.getWidth(), objectLayer.getHeight());
        }
        if(name.toLowerCase().contains("xplora")) {
            return new Xplora(this, name, size, location, t, objectLayer.getX(), objectLayer.getY(), objectLayer.getWidth(), objectLayer.getHeight());
        }
        if(name.toLowerCase().contains("teacher")) {
            return new TeacherRoom(this, name, size, location, t, objectLayer.getX(), objectLayer.getY(), objectLayer.getWidth(), objectLayer.getHeight());
        }
        if(name.toLowerCase().contains("canteen")) {
            return new Canteen(this, name, size, location, t, objectLayer.getX(), objectLayer.getY(), objectLayer.getWidth(), objectLayer.getHeight());
        }
        if(name.toLowerCase().contains("wc")) {
            return new Toilet(this, name, size, location, t, objectLayer.getX(), objectLayer.getY(), objectLayer.getWidth(), objectLayer.getHeight());
        }
        return new Classroom(this, name,size, location, t, objectLayer.getX(), objectLayer.getY(), objectLayer.getWidth(), objectLayer.getHeight());

    }

    private void initSpawns() {
        studentSpawn = getCenterLocation(getObject("studentSpawn"));
        teacherSpawn = getCenterLocation(getObject("teacherSpawn"));
    }

    public Point getCenterLocation(Optional<TiledObject> objectOptional) {
        if(objectOptional.isPresent()) {
            TiledObject object = objectOptional.get();
            return new Point(object.getX() + (object.getWidth() / 2), object.getY() + (object.getHeight() / 2));
        } else {
            return new Point(0,0);
        }
    }

    public void draw(FXGraphics2D graphics) {
        for(TiledImageLayer layer : imageLayers) {
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

    public ArrayList<TiledImageLayer> getImageLayers() {
        return imageLayers;
    }

    public ArrayList<TiledObjectLayer> getObjectLayers() {
        return objectLayers;
    }

    public TiledImageLayer getCollisionLayer() {
        return collisionLayer;
    }

    public Point2D getStudentSpawn() {
        return studentSpawn;
    }

    public Point2D getTeacherSpawn() {
        return teacherSpawn;
    }

    public TiledObjectLayer getRoomLayer() {
        return roomLayer;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
