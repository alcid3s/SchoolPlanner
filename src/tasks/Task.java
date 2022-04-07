package tasks;

import data.persons.Person;
import data.rooms.Room;
import data.rooms.object.UsableObject;

/**
 * Abstract Class Task
 * Creates Tasks to do for Persons in different Rooms
 */

public abstract class Task {
    protected Person p;
    protected UsableObject usableObject;
    protected Room room;

    /**
     * Constructor Task
     * Creates a Task for Person
     * @param p for the Task
     * @param usableObject for the Task
     * @param r for the Task
     */

    public Task(Person p, UsableObject usableObject, Room r) {
        this.p = p;
        this.usableObject = usableObject;
        this.room = r;
    }

    /**
     * Abstract method update
     * @param deltaTime to do update
     */

    public abstract void update(double deltaTime);

    /**
     * Checks if Person is using an object
     * @return Boolean for task
     */

    public boolean isPlayerUsingObject() {
        if (usableObject != null)
            return usableObject.isUsingEvent(p);
        return false;
    }

    /**
     * Method getP
     * @return p
     */

    public Person getP() {
        return p;
    }

    /**
     * Method getUsableObject
     * @return usableObject
     */

    public UsableObject getUsableObject() {
        return usableObject;
    }

    /**
     * Method getRoom
     * @return room
     */

    public Room getRoom() {
        return room;
    }
}
