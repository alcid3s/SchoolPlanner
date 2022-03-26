package data.rooms;

import data.rooms.object.UsableObject;
import data.tilted.TiledImageLayer;
import data.tilted.TiledMap;
import data.tilted.pathfinding.target.MapTarget;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

public class Toilet extends Room {

    public Toilet(TiledMap m, String name, int capacity, Point location, MapTarget target, int x, int y, int width, int height){
        super(m,name, capacity, target,location, x, y, width, height);
    }

    @Override
    public void initChairs() {
        studentsChairs = new ArrayList<>();
        teacherChairs = new ArrayList<>();

        Optional<TiledImageLayer> layerOptional = map.getLayer("Chairs");
        if(layerOptional.isPresent()) {
            System.out.println("Loading chairs...");
            TiledImageLayer layer = layerOptional.get();
            System.out.println("Room start point: " + name + " " + x/32 + " " + y/32);

            int xTile = (int) Math.floor(x/32);
            int yTile = (int) Math.floor(y/32);
            int widthTile = (int) Math.floor(width/32);
            int heightTile = (int) Math.floor(height/32);
            for(int i = xTile; i <= xTile + widthTile; i++) {
                for(int j = yTile; j <= yTile + heightTile; j++) {
                    //System.out.println(room.getName() + " " + i + " " + j);
                    if(layer.getValues()[i][j] == 0) {
                        continue;
                    }
                    if(isInRoom(i,j)) {
                        Point pTile = new Point(i - xTile,j - yTile);
                        UsableObject object = new UsableObject(this,map.getCollisionLayer(),1,pTile,32,32, getFacingOfObject(layer.getValues()[i][j]));
                        studentsChairs.add(object);
                        teacherChairs.add(object);
                    }
                }
            }
        } else {
            System.out.println("Could not find Chairs layer or room with name: student" + name + " (" + name + ")");
            return;
        }

        System.out.println("Room: " + name);
        System.out.println("\t(x,y) " + x + " , " + y + " (width,height) " + width + " , " + height + " <- Whole room");
        System.out.println("\tStudents: " + studentsChairs.size() + "\n\tTeachers: " + teacherChairs.size());
    }
}
