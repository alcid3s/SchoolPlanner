package data.tilted.pathfinding.target;

import java.awt.*;

public interface Target {
    void print();
    Point getDirection(int tileX, int tileY);

}
