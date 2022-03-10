package gui.tabs;

import data.Group;
import data.Schedule;
import data.tilted.TiledMap;
import data.tilted.pathfinding.SpawnGroup;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.geom.AffineTransform;
import java.util.List;

public class SimulationTab extends Tab {
    private BorderPane mainPane;
    private ResizableCanvas canvas;
    private TiledMap map;
    private AffineTransform tx;

    public SimulationTab() {
        super("Simulation");
        map = new TiledMap("School_Map.json");
        setClosable(false);

        mainPane = new BorderPane();
        canvas = new ResizableCanvas();

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


        List<Group> groups = Schedule.getInstance().getGroupList();
        SpawnGroup spawnGroup = new SpawnGroup(this.canvas, this.tx);

    }

    public void draw(FXGraphics2D graphics) {
        this.tx = graphics.getTransform();
        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());
        this.tx.scale(0.5,0.5);
        graphics.setTransform(this.tx);
        map.draw(graphics);
    }
}
