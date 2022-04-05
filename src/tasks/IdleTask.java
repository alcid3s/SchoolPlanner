package tasks;

import data.persons.Person;
import data.persons.Student;
import data.persons.Teacher;
import data.rooms.*;
import data.rooms.object.UsableObject;

import java.util.Optional;
import java.util.Random;

/**
 * Class IdleTask
 *Creates things to do for persons without tasks
 */

public class IdleTask extends Task {
    private double timer;
    private double maxTimeToMove;

    /**
     * Constructor IdleTask
     * Creates a IdleTask for Person
     * @param p for the Task
     */

    public IdleTask(Person p) {
        super(p, null, null);
    }

    /**
     * Creates new tasks for Person to do
     */

    private void createNewTask() {
        Random random = new Random();
        timer = 10 + random.nextInt(20);
        maxTimeToMove = 30;

        int value = random.nextInt(11);
        if (value <= 6 && p instanceof Student) {
            getRoomAndObject(Xplora.class);
        } else if (value <= 9 && p instanceof Student) {
            getRoomAndObject(Canteen.class);
        } else if (value <= 9 && p instanceof Teacher) {
            getRoomAndObject(TeacherRoom.class);
        } else {
            getRoomAndObject(Toilet.class);
            timer = random.nextInt(8);
        }
    }

    /**
     * Method getRoomAndObject
     * Choses a Room and Object for Person to go to
     * @param c for Class from Room
     */

    private void getRoomAndObject(Class<? extends Room> c) {
        int i = 0;
        room = null;
        usableObject = null;
        while (room == null || usableObject == null) {
            i++;
            room = Room.getRandomRoom(c);
            if (room != null) {
                usableObject = room.getFreeChair(p).orElse(null);
            }
            if (i > 10) {
                break;
            }
        }
    }

    /**
     * Method update
     * Updates task for Person
     * @param deltaTime to do update
     */

    @Override
    public void update(double deltaTime) {
        if (room == null || usableObject == null) {
            createNewTask();
            return;
        }
        maxTimeToMove -= deltaTime;
        usableObject.check(p);
        if (usableObject.isUsingEvent(p) && !usableObject.getTarget().isExactAtTarget(p)) {
            p.moveToExactLocation(usableObject.getTarget(), deltaTime);
        }

        if (usableObject.isUsingEvent(p)) {
            timer -= deltaTime;
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
                    createNewTask();
                }
            }
        }

        if (timer <= 0 || maxTimeToMove <= 0) {
            createNewTask();
        }
    }
}
