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
}
