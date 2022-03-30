package gui.tabs;

import data.*;
import callbacks.ClockCallback;
import callbacks.TimerCallback;
import callbacks.Updatable;
import data.persons.Person;
import data.tiled.TiledMap;
import gui.GUI;
import javafx.scene.input.KeyCode;
import managers.Camera;
import simulation.firealarm.AlarmSound;
import simulation.firealarm.FireAlarm;
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
    private final FireAlarm fireAlarm;
    private final AlarmSound sound;

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
        this.sound = new AlarmSound("resources/bell.wav", false);

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
                if (event.getCode() == KeyCode.B) {
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
            if (hour == Clock.getTime().getHour() && minute <= Clock.getTime().getMinute() && !lesson.getHasTask()) {
                lesson.setHasTask(true);
                timers.add(new Timer(lesson, this));
                lesson.getGroup().getStudents().forEach(s ->{
                    if(fireAlarm.isOn()){
                        s.setPreviousTask(new LessonTask(s, lesson.getRoom()));
                    }else{
                        s.setTask(new LessonTask(s, lesson.getRoom()));
                    }
                });
                if(fireAlarm.isOn()){
                    lesson.getTeacher().setPreviousTask(new LessonTask(lesson.getTeacher(), lesson.getRoom()));
                }else{
                    lesson.getTeacher().setTask(new LessonTask(lesson.getTeacher(), lesson.getRoom()));
                }
            }
        });
        this.timers.forEach(t -> t.update(deltaTime));
        fireAlarm.update(deltaTime);
    }

    private void drawBackground(FXGraphics2D g) {
        g.setTransform(new AffineTransform());
        g.setBackground(Color.BLACK);
        g.setClip(null);
        g.clearRect(0, 0, (int) this.canvas.getWidth(), (int) this.canvas.getHeight());

        g.setTransform(camera.getTransform());

        map.draw(g);

        updateBackground = false;
    }

    @Override
    public void draw(FXGraphics2D g2d) {
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

        g2d.setTransform(new AffineTransform());
        g2d.setColor(Color.GREEN);
        g2d.setFont(new Font("Arial", Font.PLAIN, 25));
        g2d.drawString(currentFPS + "", 2, 25);

        clockTime.draw(g2d, canvas);
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
        this.sound.stop();
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
        this.sound.play();
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
        lesson.getGroup().getStudents().forEach(s -> {
            s.setTask(null);
            s.setPreviousTask(null);
        });
        lesson.getTeacher().setTask(null);
        lesson.getTeacher().setPreviousTask(null);
    }
}
