package gui.tabs;

import data.tilted.TiledMap;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.Resizable;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class SimulationTab extends Tab implements Resizable{
    private BorderPane mainPane;
    private ResizableCanvas canvas;
    private TiledMap map;
    private Camera camera;

    public SimulationTab(){
        super("Simulation");
        map = new TiledMap("School_Map.json");
        setClosable(false);

        mainPane = new BorderPane();
        canvas = new ResizableCanvas(e -> draw(e), mainPane);
        FXGraphics2D g = new FXGraphics2D(canvas.getGraphicsContext2D());
        camera = new Camera(canvas, this, g);

        if(mainPane.getHeight() == 0 || mainPane.getWidth() == 0){
            canvas.setWidth(1920);
            canvas.setHeight(935);
        }else{
            canvas.setWidth(mainPane.getWidth());
            canvas.setHeight(mainPane.getHeight());
        }
        mainPane.setTop(canvas);
        setContent(mainPane);
        draw(g);

    }

    @Override
    public void draw(FXGraphics2D g2d){
        canvas.setHeight(1080);
        canvas.setWidth(1920);
        g2d.setTransform(new AffineTransform());
        g2d.setBackground(Color.BLACK);
        g2d.setClip(null);
        g2d.clearRect(0, 0, (int)this.canvas.getWidth(), (int)this.canvas.getHeight());
        g2d.setTransform(camera.getTransform((int)this.canvas.getWidth(), (int)this.canvas.getHeight()));
        AffineTransform originalTransform = g2d.getTransform();
        g2d.setTransform(camera.getTransform((int)canvas.getWidth(), (int)canvas.getHeight()));
        g2d.scale(1,-1);


//        for(GameObject go : gameObjects) {
//            go.draw(g2d);
//        }

//        if(debugSelected) {
//            g2d.setColor(Color.blue);
//            DebugDraw.draw(g2d, world, 100);
//        }
        g2d.setTransform(originalTransform);
        map.draw(g2d);
    }
}
