package tasks;

import data.persons.Person;
import data.rooms.Exit;
import data.tiled.TiledMap;

/**
 * Class LeaveTask
 * Creates task for Person to leave the school
 */

public class LeaveTask extends Task {

    /**
     * Constructor LeaveTask
     * Creates a LeaveTask for Person
     * @param p for the Task
     */

    public LeaveTask(Person p) {
        super(p, null, Exit.getInstance());
    }

    /**
     * Method update
     * Updates location for Person to go to exit
     * @param deltaTime to do update
     */

    @Override
    public void update(double deltaTime) {
        p.goCloserToTarget(TiledMap.getInstance().getExitTarget(), deltaTime);
    }
}
