package data;

import data.persons.Person;
import data.rooms.Room;
import gui.tabs.DrawState;
import gui.tabs.ScheduleTab;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Class Lesson
 * Creates a lesson in the application
 */

public class Lesson implements Comparable<Lesson> {

    private Room room;
    private Person teacher;
    private Group group;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String name;
    private boolean hasTask;

    /**
     * Constructor
     * @param name of the lesson
     * @param room where the lesson needs to be given in
     * @param teacher that needs to give the lesson
     * @param group that needs to follow the lesson
     * @param startDate start time of the lesson
     * @param endDate end time of the lesson
     */

    public Lesson(String name, Room room, Person teacher, Group group, LocalDateTime startDate, LocalDateTime endDate) {
        this.room = room;
        this.teacher = teacher;
        this.group = group;
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
    }

    /**
     * Constructor
     * Extra to make object of lesson from a JSON file
     * @param jsonData from loaded file
     */

    public Lesson(String jsonData) {
        String[] values = jsonData.split(";");
        this.name = values[0];
        this.room = Schedule.getInstance().getRoom(values[1]);
        this.teacher = Schedule.getInstance().getTeacher(values[2]);
        this.group = Schedule.getInstance().getGroup(values[3]);
        this.startDate = LocalDateTime.parse(values[4]);
        this.endDate = LocalDateTime.parse(values[5]);
    }

    /**
     * Method getRoom
     * @return room of lesson
     */

    public Room getRoom() {
        return room;
    }

    /**
     * Method setRoom
     * @param room to set to
     */

    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Method getTeacher
     * @return teacher of lesson
     */

    public Person getTeacher() {
        return teacher;
    }

    /**
     * Method setTeacher
     * @param teacher to set to
     */

    public void setTeacher(Person teacher) {
        this.teacher = teacher;
    }

    /**
     * Method getGroup
     * @return group of lesson
     */

    public Group getGroup() {
        return group;
    }

    /**
     * Method setGroup
     * @param group to set to
     */

    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * Method getStartDate
     * @return start time of lesson
     */

    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Method setStartDate
     * @param time to set to
     */

    public void setStartDate(LocalDateTime time) {
        this.startDate = time;
    }

    /**
     * Method getEndDate
     * @return end time of lesson
     */

    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Method setEndDate
     * @param time to set to
     */

    public void setEndDate(LocalDateTime time) {
        this.endDate = time;
    }

    /**
     * Method getName
     * @return name of lesson
     */

    public String getName() {
        return name;
    }

    /**
     * Method setName
     * @param name to set to
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method setHasTask
     * @param set to change boolean variable to
     */

    public void setHasTask(boolean set) {
        this.hasTask = set;
    }

    /**
     * Method getHasTask
     * @return boolean variable that checks if tasks are enabled
     */

    public boolean getHasTask() {
        return this.hasTask;
    }

    /**
     * Inherited method toString
     * @return string of object
     */

    @Override
    public String toString() {
        String startDate;
        String endDate;

        if (this.startDate.getMinute() < 10) {
            startDate = this.startDate.getHour() + ":0" + this.startDate.getMinute();
        } else {
            startDate = this.startDate.getHour() + ":" + this.startDate.getMinute();
        }

        if (this.endDate.getMinute() < 10) {
            endDate = this.endDate.getHour() + ":0" + this.endDate.getMinute();
        } else {
            endDate = this.endDate.getHour() + ":" + this.endDate.getMinute();
        }

        return this.name + "\t\t\t" +
                this.room.getName() + "\t\t\t" +
                this.teacher + "\t\t\t" +
                this.group.getName() + "\t\t\t" +
                startDate + "\t\t\t" +
                endDate;
    }

    /**
     * Implemented method compareTo
     * @param l lesson to compare with
     * @return result of comparison
     */

    @Override
    public int compareTo(Lesson l) {
        if(ScheduleTab.getState() == DrawState.GROUP){
            return this.group.getName().compareTo(l.getGroup().getName());
        }else if(ScheduleTab.getState() == DrawState.TEACHER){
            return this.teacher.getName().compareTo(l.getTeacher().getName());
        }else{
            return this.room.getName().compareTo(l.getRoom().getName());
        }
    }

    /**
     * Method getJsonString
     * @return string to use for writing JSON files
     */

    public String getJsonString() {
        return name + ";" + room.getName() + ";" + teacher.getName() + ";" + group.getName() + ";" + startDate.toString() + ";" + endDate.toString();
    }

    /**
     * Method notNull
     * @return check if lesson is not null
     */

    public boolean notNull() {
        return this.room != null && this.teacher != null && this.group != null;
    }
}
