package data;

import gui.tabs.ScheduleTab;

import java.util.ArrayList;

public class Schedule {
    private static Schedule schedule;

    private ArrayList<Lesson> lessons;
    private ArrayList<Group> groups;

    public Schedule() {
        lessons = new ArrayList<>();
        groups = new ArrayList<>();
    }

    /**
     * can add a lesson
     * @param lesson to be added.
     */
    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    /**
     * Can add a group
     * @param group to be added.
     */
    public void addGroup(Group group) {
        groups.add(group);
    }

    /**
     * Returns the lesson with specific name.
     * @param name of the lesson
     * @return the lesson with specific name or null if no lesson found.
     */
    public Lesson getLesson(String name) {
        for(Lesson l : lessons) {
            if(l.getName().equals(name))
                return l;
        }
        return null;
    }

    /**
     * returns the group with specific name.
     * @param name of the group
     * @return the group with specific name or null if no group found.
     */
    public Group getGroup(String name) {
        for(Group g : groups) {
            if(g.getName().equals(name))
                return g;
        }
        return null;
    }

    /**
     * Gives the instance of schedule. Don't create a new schedule!
     * @return the schedule.
     */
    public static Schedule getInstance() {
        if(schedule == null)
            schedule = new Schedule();
        return schedule;
    }
}
