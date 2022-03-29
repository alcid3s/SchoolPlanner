package tasks;

import data.persons.Person;
import simulation.firealarm.FireAlarmTrigger;

public class TriggerFireAlarmTask extends Task {
    private FireAlarmTrigger trigger;


    public TriggerFireAlarmTask(Person p, FireAlarmTrigger trigger) {
        super(p, null,null);
        this.trigger = trigger;
    }

    @Override
    public void update(double deltaTime) {
        p.goCloserToTarget(trigger.getTarget(), deltaTime);
        if(trigger.isInsideUsableRange((int)p.getPosition().getX(), (int)p.getPosition().getY())) {
            trigger.getTriggerCallback().callback(p);
        }
    }
}
