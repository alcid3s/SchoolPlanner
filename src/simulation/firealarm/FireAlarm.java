package simulation.firealarm;

import data.*;
import data.Schedule;
import data.persons.Person;
import data.persons.Student;
import data.tiled.TiledImageLayer;
import data.tiled.TiledMap;
import gui.tabs.SimulationTab;
import org.jfree.fx.FXGraphics2D;
import tasks.IdleTask;
import tasks.LeaveTask;
import tasks.TriggerFireAlarmTask;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Class FireAlarm
 * Creates a fire alarm system.
 */
public class FireAlarm {
    private final BufferedImage[] sprites;
    private boolean on;
    private boolean starting;
    private final ArrayList<FireAlarmObject> objects;
    private final ArrayList<FireAlarmTrigger> fireAlarmTriggers;
    private final double time = 0.1;
    private double currentTime = time;
    private int currentSprite = 0;
    private final AlarmSound sound;

    /**
     * Constructor FireAlarm
     * @param tab of the simulation.
     */
    public FireAlarm(SimulationTab tab) {
        sound = new AlarmSound("resources/alarm.wav", true);
        this.sprites = getImages();
        this.objects = new ArrayList<>();
        TiledMap map = tab.getMap();
        TiledImageLayer imageLayer = map.getFireAlarmLayer();
        this.fireAlarmTriggers = new ArrayList<>();
        for (int i = 0; i < imageLayer.getValues().length; i++) {
            for (int j = 0; j < imageLayer.getValues()[i].length; j++) {
                if(imageLayer.getValues()[i][j] == 2889) {
                    objects.add(new FireAlarmObject(i,j));
                } else if(imageLayer.getValues()[i][j] == 2890) {
                    fireAlarmTriggers.add(new FireAlarmTrigger(new Point(i, j), map.getCollisionLayer(), p -> {
                        execute();
                        setOn(true);
                    }));
                }
            }
        }
        on = false;
    }

    /**
     * Method getImages
     * Method to receive the images of the fire alarm.
     * The images contains the animation for the fire alarm.
     * @return the images of the fire alarm.
     */

    private BufferedImage[] getImages() {
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(Student.class.getClassLoader().getResource("firealarmanimation.png")));
            int totalX = 5;
            int totalY = 6;
            int size = 32;

            BufferedImage[] sprites = new BufferedImage[totalX * totalY];
            int counter = 0;
            for(int y = 0; y < totalY; y++) {
                for(int x = 0; x < totalX; x++) {
                    sprites[counter] = image.getSubimage(x * size, y * size, size, size);
                    counter++;
                }
            }
            return sprites;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method isOn
     * @return value of private attribute on
     */

    public boolean isOn() {
        return on;
    }

    /**
     * Method toggle
     * Toggles the fire alarm between on and off
     */

    public void toggle() {
        if(this.isOn() || this.starting) {
            this.on = false;
            this.starting = false;
            this.sound.stop();
            stop();
        } else {
            start();
        }
    }

    /**
     * Method setOn
     * @param on to set to
     */

    public void setOn(boolean on) {
        if(on) {
            this.on = true;
            this.sound.play();
        } else {
            this.on = false;
        }
    }

    /**
     * Method start
     * Starts the fire alarm sequence
     */

    public void start() {
        this.starting = true;
        List<Person> people = Schedule.getInstance().getAllPersons();
        people.removeIf(person -> !person.isSpawned());
        Collections.shuffle(people);
        if(people.size() > 0) {
            Person p = people.get(0);
            int shortest = 999;
            FireAlarmTrigger trigger = null;
            for (FireAlarmTrigger fireAlarmTrigger : fireAlarmTriggers) {
                if (fireAlarmTrigger.getTarget().getSteps(p.getPosition()) < shortest) {
                    trigger = fireAlarmTrigger;
                    shortest = fireAlarmTrigger.getTarget().getSteps(p.getPosition());
                }
            }
            p.setTask(new TriggerFireAlarmTask(p, trigger));

        } else {
            this.starting = false;
            setOn(false);
        }
    }

    /**
     * Method execute
     * Makes all objects of type Person leave the school
     */

    public void execute() {
        List<Person> people = Schedule.getInstance().getAllPersons();
        people.forEach(Person::leave);

    }

    /**
     * Method stop
     * Stops the fire alarm sequence
     */

    public void stop() {
        List<Person> people = Schedule.getInstance().getAllPersons();
        people.forEach(person -> {
            if(Clock.getTime().getHour() < 16) {
                person.setDoSpawn(true);
            }
            person.setTask(person.getPreviousTask());
            if(person.getTask() instanceof TriggerFireAlarmTask) {
                person.setTask(new IdleTask(person));
            }
        });

    }

    /**
     * Method update
     * @param deltaTime to take to update
     */

    public void update(double deltaTime) {
        if(this.on) {
            Schedule.getInstance().getAllPersons().forEach(p -> {
                if(!(p.getTask() instanceof LeaveTask)) {
                    p.setTask(new LeaveTask(p));
                }
            });
            currentTime -= deltaTime;
            if(currentTime <= 0) {
                currentTime = time;
                if (sprites.length <= currentSprite + 1) {
                    currentSprite = 0;
                } else {
                    currentSprite++;
                }
                for (FireAlarmObject object : objects) {
                    object.setCurrentSprite(sprites[currentSprite]);
                }
            }
        }
    }

    /**
     * Method draw
     * Draws all objects related to FireAlarm
     * @param g2d graphical context that needs to draw
     */

    public void draw(FXGraphics2D g2d) {
        if(this.on) {
            objects.forEach(o -> o.draw(g2d));
        }
    }
}
