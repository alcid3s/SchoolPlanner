package tasks;

import data.persons.Person;
import data.rooms.Canteen;
import data.rooms.Room;
import data.rooms.Xplora;
import data.rooms.object.UsableObject;

import java.util.Optional;
import java.util.Random;

public class IdleTask extends Task {
    private double timer;

    public IdleTask(Person p) {
        super(p, null, null);
    }

    private void createNewTask() {
        Random random = new Random();
        timer = 30 + random.nextInt(20);

        switch (random.nextInt(2)) {
            case 0:
                getRoomAndObject(Xplora.class);
                break;
            case 1:
                getRoomAndObject(Canteen.class);
                break;
        }
    }

    private void getRoomAndObject(Class<? extends Room> c) {
        int i = 0;
        room = null;
        usableObject = null;
        while(room == null || usableObject == null) {
            i++;
            room = Room.getRandomRoom(c);
            if(room != null) {
                usableObject = room.getFreeChair(p).orElse(null);
            }
            if(i > 10) {
                break;
            }
        }
    }

    @Override
    public void update(double deltaTime) {
        if(room == null || usableObject == null) {
            createNewTask();
        }
        timer-= deltaTime;
        usableObject.check(p);
        if(usableObject.isFree() && !usableObject.isUsingEvent(p) && !usableObject.getTarget().isAtTarget(p)) {
            p.goCloserToTarget(usableObject.getTarget(), deltaTime);
        } else {
            if(!usableObject.isFree() && !usableObject.isUsingEvent(p)) {
                Optional<UsableObject> objectOptional = room.getFreeChair(p);
                if(objectOptional.isPresent()) {
                    usableObject = objectOptional.get();
                } else {
                    createNewTask();
                }
            }
        }
        if(timer <= 0) {
            createNewTask();
        }
    }
}
