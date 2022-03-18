package gui.tabs;

import data.Group;
import data.Schedule;
import data.persons.Person;
import data.tilted.TiledMap;
import data.tilted.pathfinding.SpawnGroup;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.Resizable;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.List;

public class SimulationTab extends Tab implements Resizable {
    private BorderPane mainPane;
    private ResizableCanvas canvas;
    private TiledMap map;
    private Camera camera;
    private List<Group> groupList;
    private double timer = 0.5;

    private long lastFPSCheck = 0;
    private int currentFPS = 0;
    private int totalFrames = 0;



    public SimulationTab(){
        super("Simulation");
        map = new TiledMap("School_Map.json");
        setClosable(false);

        this.mainPane = new BorderPane();
        this.canvas = new ResizableCanvas(e -> draw(e), mainPane);
        FXGraphics2D g = new FXGraphics2D(canvas.getGraphicsContext2D());
        this.camera = new Camera(canvas, this, g);

        this.groupList = Schedule.getInstance().getGroupList();
        if(mainPane.getHeight() == 0 || mainPane.getWidth() == 0) {
            canvas.setWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth());
            canvas.setHeight(Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        } else {
            canvas.setWidth(mainPane.getWidth());
            canvas.setHeight(mainPane.getHeight());
        }
        mainPane.setTop(canvas);
        setContent(mainPane);

        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
                if(last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g);
            }
        }.start();
        draw(g);
    }

    private void update(double deltaTime) {
        if(timer > -0.1) {
            timer -= deltaTime;
        }
        for (Group group : groupList) {
            for (Person student : group.getStudents()) {
                if (!student.isSpawned()) {
                    if (timer <= 0) {
                        student.spawn(map.getStudentSpawn());
                        timer = 0.5;
                    }
                } else {
                    student.update(deltaTime);
                }
            }
        }
    }

    @Override
    public void draw(FXGraphics2D g2d) {
        //FPS COUNTER
        totalFrames++;
        if(System.nanoTime() > lastFPSCheck + 1000000000) {
            lastFPSCheck = System.nanoTime();
            currentFPS = totalFrames;
            totalFrames = 0;
        }

        g2d.setTransform(new AffineTransform());
        g2d.setBackground(Color.BLACK);
        g2d.setClip(null);
        g2d.clearRect(0, 0, (int)this.canvas.getWidth(), (int)this.canvas.getHeight());

        g2d.setTransform(camera.getTransform((int)this.canvas.getWidth(), (int)this.canvas.getHeight()));
        map.draw(g2d);
        g2d.setColor(Color.white);
        g2d.draw(new Line2D.Double(0,100,0,-100));
        g2d.draw(new Line2D.Double(100, 0, -100, 0));

        for (Group group : groupList) {
            for (Person student: group.getStudents()) {
                student.draw(g2d);
            }
        }

        //DRAW FPS COUNTER
        g2d.setTransform(new AffineTransform());
        g2d.setColor(Color.GREEN);
        g2d.setFont(new Font("Arial", Font.PLAIN, 25));
        g2d.drawString(currentFPS + "",(int) canvas.getWidth()-45, 25);

//        if(!this.groupList.isEmpty()) {
//            for (Group group : this.groupList) {
//                this.group.spawnGroup(group);
//            }
//        }

        
        


//        for(GameObject go : gameObjects) {
//            go.draw(g2d);
//        }

//        if(debugSelected) {
//            g2d.setColor(Color.blue);
//            DebugDraw.draw(g2d, world, 100);
//        }
        //g2d.setTransform(this.tx);
    }
}
