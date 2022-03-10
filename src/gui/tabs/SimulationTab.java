package gui.tabs;

import data.Schedule;
import data.tilted.TiledImageLayer;
import data.tilted.TiledMap;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;

import java.awt.geom.AffineTransform;
import java.util.Optional;
import java.util.Scanner;

public class SimulationTab extends Tab {
    private BorderPane mainPane;
    private Canvas canvas;
    private TiledMap map;
    private FXGraphics2D graphics2D;

    public SimulationTab() {
        super("Simulation");
        map = new TiledMap("School_Map.json");
        setClosable(false);

        mainPane = new BorderPane();
        canvas = new Canvas();

        if(mainPane.getHeight() == 0 || mainPane.getWidth() == 0){
            canvas.setWidth(1920);
            canvas.setHeight(935);
        }else{
            canvas.setWidth(mainPane.getWidth());
            canvas.setHeight(mainPane.getHeight());
        }
        mainPane.setTop(canvas);
        setContent(mainPane);
        graphics2D = new FXGraphics2D(canvas.getGraphicsContext2D());
        draw(graphics2D);

    }


    public void draw(FXGraphics2D graphics) {
        AffineTransform transform = graphics.getTransform();
        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());
        transform.scale(0.5,0.5);
        graphics.setTransform(transform);
        map.draw(graphics);

    }

}
