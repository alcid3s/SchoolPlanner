package tasks;

import data.persons.Person;
import simulation.firealarm.FireAlarmTrigger;

/**
 * Class TriggerFireAlarm
 * Creates the Task to trigger a FireAlarm
 */

public class TriggerFireAlarmTask extends Task {
    private FireAlarmTrigger trigger;

    /**
     * Constructor TriggerFireAlarm
     * Creates a FireAlarmTask for Person
     * @param p for the Task
     * @param trigger for the Task
     */

    public TriggerFireAlarmTask(Person p, FireAlarmTrigger trigger) {
        super(p, null,null);
        this.trigger = trigger;
    }

    /**
     * Method update
     * Updates location for Person to go to FireAlarm and use it
     * @param deltaTime to do update
     */

    @Override
    public void update(double deltaTime) {
        p.goCloserToTarget(trigger.getTarget(), deltaTime);
        if(trigger.isInsideUsableRange((int)p.getPosition().getX(), (int)p.getPosition().getY())) {
            trigger.getTriggerCallback().callback(p);
        }
    }
}
