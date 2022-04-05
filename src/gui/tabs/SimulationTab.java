package gui.tabs;

import callbacks.ClockCallback;
import callbacks.TimerCallback;
import callbacks.Timeable;
import data.*;
import data.persons.Person;
import data.tiled.TiledMap;
import gui.GUI;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import managers.Camera;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.Resizable;
import simulation.firealarm.AlarmSound;
import simulation.firealarm.FireAlarm;
import tasks.LessonTask;
import tasks.TeachTask;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Class SimulationTab
 * Builds the simulation tab and all included functions for the GUI
 */
public class SimulationTab extends Tab implements Resizable, ClockCallback, TimerCallback{
    private final FXGraphics2D gBackground;
    private final TiledMap map;
    private final Camera camera;
    private final List<Group> groupList;
    private final Pane pane;
    private final List<Timeable> timers;
    private final FireAlarm fireAlarm;
    private final AlarmSound sound;
    private final Clock clockTime;
    private Canvas canvas;
    private FXGraphics2D g;
    private boolean updateBackground;
    private double timer = 0.5;
    private long lastFPSCheck = 0;
    private int currentFPS = 0;
    private int totalFrames = 0;

    /**
     * Constructor SimulationTab
     * Builds simulation
     */
    public SimulationTab(){
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
            if(GUI.getTabPane().getSelectionModel().getSelectedItem().equals(this)){
                if(event.getCode() == KeyCode.B){
                    fireAlarm.toggle();
                }
            }
        });

        new AnimationTimer(){
            long last = -1;

            @Override
            public void handle(long now){
                if(last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g);
            }
        }.start();
        draw(g);
    }

    /**
     * Method update
     * Updates simulation
     * @param deltaTime increase in time
     */
    private void update(double deltaTime){
        if(timer > -0.1){
            timer -= deltaTime;
        }

        for(Person p : Schedule.getInstance().getAllPersons()){
            if(!p.isSpawned() && !fireAlarm.isOn()){
                if(timer <= 0){
                    p.spawn(map.getStudentSpawn());
                    timer = 0.5;
                }
            }else{
                p.update(deltaTime);
            }
        }

        Schedule.getInstance().getRoomList().forEach(room -> room.update(deltaTime));

        Schedule.getInstance().getLessonList().forEach(lesson -> {
            int hour = lesson.getStartDate().getHour();
            int minute = lesson.getStartDate().getMinute();
            if(hour == Clock.getTime().getHour() && minute <= Clock.getTime().getMinute() && !lesson.getHasTask()){
                lesson.setHasTask(true);
                timers.add(new Timer(lesson, this));
                lesson.getGroup().getStudents().forEach(s -> {
                    if(fireAlarm.isOn()){
                        s.setPreviousTask(new LessonTask(s, lesson.getRoom()));
                    }else{
                        s.setTask(new LessonTask(s, lesson.getRoom()));
                    }
                });
                if(fireAlarm.isOn()){
                    lesson.getTeacher().setPreviousTask(new TeachTask(lesson.getTeacher(), lesson.getRoom()));
                }else{
                    lesson.getTeacher().setTask(new TeachTask(lesson.getTeacher(), lesson.getRoom()));
                }
            }
        });
        this.timers.forEach(t -> t.update(deltaTime));
        fireAlarm.update(deltaTime);
    }

    /**
     * Method drawBackground
     * Draws background
     * @param graphics graphics on which to draw
     */
    private void drawBackground(FXGraphics2D graphics){
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.BLACK);
        graphics.setClip(null);
        graphics.clearRect(0, 0, (int) this.canvas.getWidth(), (int) this.canvas.getHeight());

        graphics.setTransform(camera.getTransform());

        map.draw(graphics);

        updateBackground = false;
    }

    /**
     * Method draw
     * Draws actual graphics
     * @param graphics graphics on which to draw
     */
    @Override
    public void draw(FXGraphics2D graphics){
        totalFrames++;
        if(System.nanoTime() > lastFPSCheck + 1000000000){
            lastFPSCheck = System.nanoTime();
            currentFPS = totalFrames;
            totalFrames = 0;
        }
        if(updateBackground){
            drawBackground(gBackground);
        }

        canvas = createNewCanvas();
        graphics = g;
        graphics.setTransform(camera.getTransform());

        for(Group group : groupList){
            for(Person student : group.getStudents()){
                student.draw(graphics);
            }
        }

        for(Person teacher : Schedule.getInstance().getTeacherList()){
            teacher.draw(graphics);
        }
        fireAlarm.draw(graphics);

        graphics.setTransform(new AffineTransform());
        graphics.setColor(Color.GREEN);
        graphics.setFont(new Font("Arial", Font.PLAIN, 25));
        graphics.drawString(currentFPS + "", 2, 25);

        clockTime.draw(graphics, canvas);
    }

    /**
     * Method createNewCanvas
     * Creates new canvas
     * @return canvas
     */
    private Canvas createNewCanvas(){
        pane.getChildren().remove(canvas);
        this.canvas = new Canvas(canvas.getWidth(), canvas.getHeight());
        pane.getChildren().add(this.canvas);
        this.canvas.toFront();
        g = new FXGraphics2D(canvas.getGraphicsContext2D());
        return this.canvas;
    }

    /**
     * Method getCanvas
     * Gets canvas
     * @return canvas
     */
    public Canvas getCanvas(){
        return canvas;
    }

    /**
     * Method getMap
     * Gets map
     * @return map
     */
    public TiledMap getMap(){
        return map;
    }

    /**
     * Method setUpdateBackground
     * Set whether to update background
     * @param updateBackground boolean to check if background needs to be updated
     */
    public void setUpdateBackground(boolean updateBackground){
        this.updateBackground = updateBackground;
    }

    /**
     * Method getPane
     * Gets pane
     * @return pane
     */
    public Pane getPane(){
        return pane;
    }

    /**
     * Method onBeginTime
     * Starts all objects
     */
    @Override
    public void onBeginTime(){
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

    /**
     * Method onEndTime
     * Stops all objects
     */
    @Override
    public void onEndTime(){
        this.sound.play();
        Schedule.getInstance().getGroupList().forEach(g -> g.getStudents().forEach(s -> {
            if(s.isSpawned()){
                s.leave();
            }
            s.setDoSpawn(false);
        }));
        Schedule.getInstance().getTeacherList().forEach(t -> {
            if(t.isSpawned()){
                t.leave();
            }
            t.setDoSpawn(false);
        });
    }

    /**
     * Method onEndOfClass
     * Ends a lesson
     * @param lesson lesson to end
     */
    @Override
    public void onEndOfClass(Lesson lesson){
        lesson.setHasTask(false);
        lesson.getGroup().getStudents().forEach(s -> {
            s.setTask(null);
            s.setPreviousTask(null);
        });
        lesson.getTeacher().setTask(null);
        lesson.getTeacher().setPreviousTask(null);
    }
}
