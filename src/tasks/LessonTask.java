package tasks;

import data.persons.Person;
import data.rooms.Room;
import data.rooms.object.UsableObject;

import java.util.Optional;

public class LessonTask extends Task {
    public LessonTask(Person p, Room r) {
        super(p, getObjectToUse(p, r), r);
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
}
