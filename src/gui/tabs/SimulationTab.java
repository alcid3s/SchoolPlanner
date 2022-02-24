package gui.tabs;

import data.tilted.TiledMap;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;

import java.awt.geom.AffineTransform;

public class SimulationTab extends Tab {
    private BorderPane mainPane;
    private Canvas canvas;
    private TiledMap map;

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
        draw( new FXGraphics2D(canvas.getGraphicsContext2D()));
    }

    public void draw(FXGraphics2D graphics) {
        AffineTransform transform = new AffineTransform();
        transform.scale(0.5,0.5);
        graphics.setTransform(transform);
        map.draw(graphics);
    }

}
