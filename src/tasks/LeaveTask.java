package tasks;

import data.persons.Person;
import data.rooms.Exit;
import data.tilted.TiledMap;

public class LeaveTask extends Task{
    public LeaveTask(Person p) {
        super(p, null, Exit.getInstance());
    }

    @Override
    public void update(double deltaTime) {
        p.goCloserToTarget(TiledMap.getInstance().getExitTarget(), deltaTime);
    }
}
