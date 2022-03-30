package data.rooms;

import data.rooms.object.UsableObject;
import data.tiled.TiledImageLayer;
import data.tiled.pathfinding.target.MapTarget;
import data.tiled.TiledMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

public class TeacherRoom extends Room {

    public TeacherRoom(TiledMap m, String name, int capacity, Point location, MapTarget target, int x, int y, int width, int height) {
        super(m, name, capacity, target, location, x, y, width, height);
    }

    @Override
    public void initChairs() {
        studentsChairs = new ArrayList<>();
        teacherChairs = new ArrayList<>();

        Optional<TiledImageLayer> layerOptional = map.getLayer("Chairs");
        if (layerOptional.isPresent()) {
            TiledImageLayer layer = layerOptional.get();

            int xTile = (int) Math.floor(x / 32);
            int yTile = (int) Math.floor(y / 32);
            int widthTile = (int) Math.floor(width / 32);
            int heightTile = (int) Math.floor(height / 32);
            for (int i = xTile; i <= xTile + widthTile; i++) {
                for (int j = yTile; j <= yTile + heightTile; j++) {
                    if (layer.getValues()[i][j] == 0) {
                        continue;
                    }
                    if (isInRoom(i, j)) {
                        Point pTile = new Point(i - xTile, j - yTile);
                        UsableObject object = new UsableObject(this, map.getCollisionLayer(), 1, pTile, 32, 32, getFacingOfObject(layer.getValues()[i][j]));
                        teacherChairs.add(object);
                    }
                }
            }
        }
    }
}
