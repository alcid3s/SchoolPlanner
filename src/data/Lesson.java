package data;

import data.rooms.Room;
import data.persons.Teacher;

import java.time.LocalDateTime;

public class Lesson{

    private Room room;
    private Teacher teacher;
    private Group group;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String name;

    public Lesson(String name, Room room, Teacher teacher, Group group, LocalDateTime startDate, LocalDateTime endDate){
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

    public LocalDateTime getStartDate(){
        return startDate;
    }

    public void setStartDate(LocalDateTime time){
        this.startDate = time;
    }

    public LocalDateTime getEndDate(){
        return endDate;
    }

    public void setEndDate(LocalDateTime time){
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
        String startDate;
        String endDate;

        if(this.startDate.getMinute() < 10){
            startDate = this.startDate.getHour() + ":0" + this.startDate.getMinute();
        }else{
            startDate = this.startDate.getHour() + ":" + this.startDate.getMinute();
        }

        if(this.endDate.getMinute() < 10){
            endDate = this.endDate.getHour() + ":0" + this.endDate.getMinute();
        }else{
            endDate = this.endDate.getHour() + ":" + this.endDate.getMinute();
        }

        return this.name + "\t\t" +
                this.room.getName() + "\t\t" +
                this.teacher + "\t\t" +
                this.group.getName() + "\t\t" +
                startDate + "\t\t" +
                endDate;
    }
}
