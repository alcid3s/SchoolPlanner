package tasks;

import data.persons.Person;
import data.rooms.Room;
import data.rooms.object.UsableObject;

import java.util.Optional;

public class LessonTask extends Task {
    public LessonTask(Person p, Room r) {
        super(p, getObjectToUse(p,r), r);
    }

    private static UsableObject getObjectToUse(Person p, Room r) {
       Optional<UsableObject> object = r.getFreeChair(p);
        return object.orElse(null);
    }

    @Override
    public void update(double deltaTime) {
        if(usableObject == null || room == null)
            return;
        if(usableObject.isFree() && !usableObject.isUsingEvent(p) && !usableObject.getTarget().isAtTarget(p)) {
            p.goCloserToTarget(usableObject.getTarget(), deltaTime);
            usableObject.check(p);
        }
    }
}
