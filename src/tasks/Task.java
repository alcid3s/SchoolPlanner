package tasks;

import data.persons.Person;
import data.rooms.Room;
import data.rooms.object.UsableObject;

public abstract class Task {
    protected Person p;
    protected UsableObject usableObject;
    protected Room room;

    public Task(Person p, UsableObject usableObject, Room r) {
      this.p = p;
      this.usableObject = usableObject;
      this.room = r;
    }


    public abstract void update(double deltaTime);

    public boolean isPlayerUsingObject() {
        if(usableObject != null)
            return usableObject.isUsingEvent(p);
        return false;
    }

    public Person getP() {
        return p;
    }

    public UsableObject getUsableObject() {
        return usableObject;
    }

    public Room getRoom() {
        return room;
    }
}
