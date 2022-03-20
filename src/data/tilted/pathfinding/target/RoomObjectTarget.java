package data.tilted.pathfinding.target;

import data.rooms.Room;
import data.tilted.TiledImageLayer;

import java.awt.*;

public class RoomObjectTarget extends MapTarget {
    private Room room;

    public RoomObjectTarget(Point location, TiledImageLayer collisionLayer) {
        super(location,collisionLayer);
    }


}
