package tasks;

import data.persons.Person;
import data.rooms.Classroom;
import data.rooms.Room;
import data.rooms.object.UsableObject;

import java.util.Optional;
import java.util.Random;

public class TeachTask extends Task {
    private boolean gotToRoom;
    private double timer;
    private Random random = new Random();

    public TeachTask(Person p, Room r) {
        super(p, getObjectToUse(p, r), r);
        gotToRoom = false;
        timer = 10 + random.nextInt(20);
    }

    private static UsableObject getObjectToUse(Person p, Room r) {
        Optional<UsableObject> object = r.getFreeChair(p);
        return object.orElse(null);
    }

    @Override
    public void update(double deltaTime) {
        if (room == null || usableObject == null) {
            p.setTask(null);
            return;
        }
        usableObject.check(p);
        if (usableObject.isUsingEvent(p) && !usableObject.getTarget().isExactAtTarget(p)) {
            p.moveToExactLocation(usableObject.getTarget(), deltaTime);
        }

        if (usableObject.isUsingEvent(p)) {
            p.direction = usableObject.getFacingWhenUsing().getDirection();
            timer -= deltaTime;
        }
        if(timer <= 0) {
            timer = 2 + random.nextInt(5);
            usableObject.getUsers().remove(p);
            usableObject = getNewUsableObject();
        }
        if (usableObject.isFree() && !usableObject.isUsingEvent(p) && !usableObject.getTarget().isAtTarget(p)) {
            p.goCloserToTarget(usableObject.getTarget(), deltaTime);
        } else {
            if (!usableObject.isFree() && !usableObject.isUsingEvent(p)) {
                Optional<UsableObject> objectOptional = room.getFreeChair(p);
                if (objectOptional.isPresent()) {
                    usableObject = objectOptional.get();
                } else {
                    p.setTask(null);
                }
            }
        }
    }

    private UsableObject getNewUsableObject() {
        if(room instanceof Classroom) {
            Classroom c = (Classroom) room;
            return c.getFreeSpot();
        }
        return null;

    }
}
