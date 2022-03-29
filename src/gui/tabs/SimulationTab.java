package gui.tabs;

import data.*;
import data.persons.Person;
import data.tilted.TiledMap;
import simulation.firealarm.FireAlarm;
import gui.GUI;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.Resizable;
import tasks.LessonTask;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class SimulationTab extends Tab implements Resizable, ClockCallback, TimerCallback {
    private Canvas canvas;
    private final FXGraphics2D gBackground;
    private FXGraphics2D g;
    private boolean updateBackground;
    private final TiledMap map;
    private final Camera camera;
    private final List<Group> groupList;
    private final Pane pane;
    private double timer = 0.5;
    private final List<Updatable> timers;
    private FireAlarm fireAlarm;

    private long lastFPSCheck = 0;
    private int currentFPS = 0;
    private int totalFrames = 0;

    private final Clock clockTime;

    public SimulationTab() {
        super("Simulation");
        this.timers = new ArrayList<>();
        this.timers.add(clockTime = new Clock(this));
        map = TiledMap.getInstance();
        this.fireAlarm = new FireAlarm(this);
        this.groupList = Schedule.getInstance().getGroupList();
        setClosable(false);

        BorderPane mainPane = new BorderPane();
        this.canvas = new Canvas(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        Canvas backgroundCanvas = new Canvas(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), Toolkit.getDefaultToolkit().getScreenSize().getHeight());

        pane = new Pane();
        this.camera = new Camera(this);

        pane.getChildren().add(backgroundCanvas);
        pane.getChildren().add(canvas);
        canvas.toFront();

        g = new FXGraphics2D(canvas.getGraphicsContext2D());
        gBackground = new FXGraphics2D(backgroundCanvas.getGraphicsContext2D());
        updateBackground = true;
        mainPane.setTop(pane);
        setContent(mainPane);

        GUI.getScene().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (GUI.getTabPane().getSelectionModel().getSelectedItem().equals(this)) {
                switch (event.getCode()) {
                    case B:
                        fireAlarm.toggle();
                }
            }
        });

        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g);
            }
        }.start();
        draw(g);
    }

    private void update(double deltaTime) {
        if (timer > -0.1) {
            timer -= deltaTime;
        }
        //groupList.get(0).getStudents().get(0).spawn(map.getStudentSpawn());
        for (Person p : Schedule.getInstance().getAllPersons()) {
            if (!p.isSpawned() && !fireAlarm.isOn()) {
                if (timer <= 0) {
                    p.spawn(map.getStudentSpawn());
                    timer = 0.5;
                }
            } else {
                p.update(deltaTime);
            }
        }
        
        Schedule.getInstance().getRoomList().forEach(room -> room.update(deltaTime));

        Schedule.getInstance().getLessonList().forEach(lesson -> {
            int hour = lesson.getStartDate().getHour();
            int minute = lesson.getStartDate().getMinute();
//            if(minute < 15){
//                hour -= 1;
//                minute = 45 + minute;
//            }else{
//                minute -= 15;
//            }
            if (hour == Clock.getTime().getHour() && minute <= Clock.getTime().getMinute() && !lesson.getHasTask()) {
                lesson.setHasTask(true);
                timers.add(new Timer(lesson, this));
                //if(!l.getTeacher().getName().equalsIgnoreCase("Jessica")){
                lesson.getGroup().getStudents().forEach(s -> s.setTask(new LessonTask(s, lesson.getRoom())));
                //}
                lesson.getTeacher().setTask(new LessonTask(lesson.getTeacher(), lesson.getRoom()));
            }
        });
        this.timers.forEach(t -> t.update(deltaTime));
        fireAlarm.update(deltaTime);
    }

    private void drawBackground(FXGraphics2D g) {
        long millis = System.nanoTime();
        g.setTransform(new AffineTransform());
        g.setBackground(Color.BLACK);
        g.setClip(null);
        g.clearRect(0, 0, (int) this.canvas.getWidth(), (int) this.canvas.getHeight());

        g.setTransform(camera.getTransform());


        map.draw(g);

        updateBackground = false;
        millis = System.nanoTime() - millis;
        if (millis / 1000000.0 > 6.0)
            System.out.println("Total time to draw background " + millis / 1000000.0 + " ms");

    }

    @Override
    public void draw(FXGraphics2D g2d) {
        //FPS COUNTER
        totalFrames++;
        if (System.nanoTime() > lastFPSCheck + 1000000000) {
            lastFPSCheck = System.nanoTime();
            currentFPS = totalFrames;
            totalFrames = 0;
        }
        if (updateBackground) {
            drawBackground(gBackground);
        }
        long millis = System.nanoTime();

        canvas = createNewCanvas();
        g2d = g;
        g2d.setTransform(camera.getTransform());

        for (Group group : groupList) {
            for (Person student : group.getStudents()) {
                student.draw(g2d);
            }
        }

        for (Person teacher : Schedule.getInstance().getTeacherList()) {
            teacher.draw(g2d);
        }
        fireAlarm.draw(g2d);

        g2d.setColor(Color.BLUE);
        //SEE WHERE STUDENT 0 GOES TO
        /*try {
            UsableObject t = groupList.get(0).getStudents().get(0).getTask().getUsableObject();
            if (t != null) {

                AffineTransform tx = g2d.getTransform();
                tx.translate(t.getX() * 32 + t.getR().getX() - (32 / 2.0), t.getY() * 32 + t.getR().getY() - (32 / 2.0));
                g2d.drawImage(ImageIO.read(getClass().getClassLoader().getResource("student.png")), tx, null);
            }
        } catch (IndexOutOfBoundsException | IOException e) {
            e.printStackTrace();
        }*/
        //Schedule.getInstance().getRoom("LA134").getTarget().draw(g2d);

        //DRAW FPS COUNTER
        g2d.setTransform(new AffineTransform());
        g2d.setColor(Color.GREEN);
        g2d.setFont(new Font("Arial", Font.PLAIN, 25));
        g2d.drawString(currentFPS + "", 2, 25);

        clockTime.draw(g2d, canvas);

        millis = System.nanoTime() - millis;
        if (millis / 1000000.0 > 6)
            System.out.println("Total to draw front canvas " + millis / 1000000.0 + " ms");
    }

    private Canvas createNewCanvas() {
        pane.getChildren().remove(canvas);
        this.canvas = new Canvas(canvas.getWidth(), canvas.getHeight());
        pane.getChildren().add(this.canvas);
        this.canvas.toFront();
        g = new FXGraphics2D(canvas.getGraphicsContext2D());
        return this.canvas;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public TiledMap getMap() {
        return map;
    }

    public void setUpdateBackground(boolean updateBackground) {
        this.updateBackground = updateBackground;
    }

    public Pane getPane() {
        return pane;
    }

    @Override
    public void onBeginTime() {
        Schedule.getInstance().getLessonList().forEach(System.out::println);
        Schedule.getInstance().getGroupList().forEach(g -> g.getStudents().forEach(s -> {
            s.setDoUpdate(true);
            s.setDoSpawn(true);
        }));
        Schedule.getInstance().getTeacherList().forEach(t -> {
            t.setDoUpdate(true);
            t.setDoSpawn(true);
        });
    }

    @Override
    public void onEndTime() {
        Schedule.getInstance().getGroupList().forEach(g -> g.getStudents().forEach(s -> {
            if (s.isSpawned()) {
                s.leave();
            }
            s.setDoSpawn(false);
        }));
        Schedule.getInstance().getTeacherList().forEach(t -> {
            if (t.isSpawned()) {
                t.leave();
            }
            t.setDoSpawn(false);
        });
    }

    @Override
    public void onEndOfClass(Lesson lesson) {
        lesson.setHasTask(false);
        lesson.getGroup().getStudents().forEach(s -> s.setTask(null));
        lesson.getTeacher().setTask(null);
    }
}
