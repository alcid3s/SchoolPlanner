package simulation.firealarm;

import data.Clock;
import data.Schedule;
import data.persons.Person;
import data.persons.Student;
import data.tiled.TiledImageLayer;
import data.tiled.TiledMap;
import gui.tabs.SimulationTab;
import org.jfree.fx.FXGraphics2D;
import tasks.LeaveTask;
import tasks.TriggerFireAlarmTask;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class FireAlarm {
    private final BufferedImage[] sprites;
    private boolean on;
    private final ArrayList<FireAlarmObject> objects;
    private final ArrayList<FireAlarmTrigger> fireAlarmTriggers;
    private final double time = 0.1;
    private double currentTime = time;
    private int currentSprite = 0;
    private final AlarmSound sound;

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

    public boolean isOn() {
        return on;
    }

    public void toggle() {
        if(this.isOn()) {
            this.on = false;
            this.sound.stop();
            stop();
        } else {
            start();
        }
    }

    public void setOn(boolean on) {
        if(on) {
            this.on = true;
            this.sound.play();
        } else {
            this.on = false;
        }
    }

    public void start() {
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
            setOn(false);
        }
    }

    public void execute() {
        List<Person> people = Schedule.getInstance().getAllPersons();
        people.forEach(Person::leave);

    }

    public void stop() {
        List<Person> people = Schedule.getInstance().getAllPersons();
        people.forEach(person -> {
            if(Clock.getTime().getHour() < 16){
                person.setDoSpawn(true);
            }
            person.setTask(person.getPreviousTask());
        });

    }

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

    public void draw(FXGraphics2D g2d) {
        if(this.on) {
            objects.forEach(o -> o.draw(g2d));
        }
    }
}
