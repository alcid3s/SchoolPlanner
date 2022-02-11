package data;

import data.rooms.Room;
import data.persons.Teacher;

import java.time.LocalDateTime;

public class Lesson{

    private Room room;
    private Teacher teacher;
    private Group group;
    private String startDate;
    private String endDate;
    private String name;

    public Lesson(String name, Room room, Teacher teacher, Group group, String startDate, String endDate){
        this.room = room;
        this.teacher = teacher;
        this.group = group;
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room){
        this.room = room;
    }

    public Teacher getTeacher() {
        return teacher;
    }
    public void setTeacher(Teacher teacher){
        this.teacher = teacher;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group){
        this.group = group;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String time){
        this.startDate = time;
    }

    public String getEndDate(){
        return endDate;
    }

    public void setEndDate(String time){
        this.endDate = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name + "\t\t" +
                this.room + "\t\t" +
                this.teacher + "\t\t" +
                this.group + "\t\t" +
                this.startDate + "\t\t" +
                this.endDate;
    }
}
