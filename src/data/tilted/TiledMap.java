package data.tilted;

import data.Schedule;
import data.rooms.*;
import org.jfree.fx.FXGraphics2D;

import javax.json.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Optional;

public class TiledMap {
    private ArrayList<TiledImageLayer> imageLayers;
    private ArrayList<TiledObjectLayer> objectLayers;
    private TiledImageLayer collisionLayer;
    private Point2D studentSpawn;
    private Point2D teacherSpawn;

    public TiledMap(String filename) {
        JsonReader jsonReader = Json.createReader(getClass().getClassLoader().getResourceAsStream(filename));
        JsonObject object = jsonReader.readObject();
        imageLayers = new ArrayList<>();
        objectLayers = new ArrayList<>();
        object.getJsonArray("layers").forEach(layer -> {
            if(layer.asJsonObject().getString("type").equalsIgnoreCase("tilelayer")) {
                if(layer.asJsonObject().getString("name").equalsIgnoreCase("Barrier")) {
                    collisionLayer = new TiledImageLayer(layer.asJsonObject());
                } else {
                    TiledImageLayer tiledLayer = new TiledImageLayer(layer.asJsonObject());

                    JsonArray layerData = layer.asJsonObject().getJsonArray("data");
                    for (int i = 0; i < layerData.size(); i++) {
                        int data = layerData.getInt(i);
                        tiledLayer.addValue(data, i % tiledLayer.getWidth(), (i / tiledLayer.getWidth()));
                    }
                    imageLayers.add(tiledLayer);
                    System.out.println(tiledLayer);
                }
            } else {
                TiledObjectLayer objectLayer = new TiledObjectLayer(layer.asJsonObject());
                if(objectLayer.getName().equalsIgnoreCase("Rooms")) {
                    objectLayer.getObjects().forEach(tiledObject -> {
                        Schedule.getInstance().addRoom(createNewRoom(tiledObject));
                    });
                } else {
                    objectLayers.add(new TiledObjectLayer(layer.asJsonObject()));
                }
            }
        });

        System.out.println("Loading tileSets");
        object.getJsonArray("tilesets").forEach(tileSet -> {
            TiledSetManager.getInstance().addTiledImageSet(new TiledImageSet(tileSet.asJsonObject()));
        });
        initSpawns();

    }

    private Room createNewRoom(TiledObject objectLayer) {
        String name = objectLayer.getName();
        int size = 0;
        for(JsonValue ob : objectLayer.getJsonObject().getJsonArray("properties")) {
            if(ob.asJsonObject().getString("name").equalsIgnoreCase("size"))
                size = ob.asJsonObject().getInt("value");
        }
        if(name.toLowerCase().contains("la") || name.toLowerCase().contains("zaal")) {
            return new Classroom(name, size);
        }
        if(name.toLowerCase().contains("xplora")) {
            return new Xplora(name, size);
        }
        if(name.toLowerCase().contains("teacher")) {
            return new TeacherRoom(name,size);
        }
        if(name.toLowerCase().contains("canteen")) {
            return new Canteen(name,size);
        }
        return new Classroom(name,size);

    }

    private void initSpawns() {
        studentSpawn = getCenterLocation(getObject("studentSpawn"));
        teacherSpawn = getCenterLocation(getObject("teacherSpawn"));
    }

    public Point2D getCenterLocation(Optional<TiledObject> objectOptional) {
        if(objectOptional.isPresent()) {
            TiledObject object = objectOptional.get();
            return new Point2D.Double(object.getX() + (object.getWidth() / 2), object.getY() + (object.getHeight() / 2));
        } else {
            return new Point2D.Double(0,0);
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
}
