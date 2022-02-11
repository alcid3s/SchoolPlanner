package data;

import data.persons.Teacher;
import data.rooms.Room;

import java.util.ArrayList;

public class Schedule {
    private static Schedule schedule;

    private ArrayList<Lesson> lessonList;
    private ArrayList<Group> groupList;
    private ArrayList<Teacher> teacherList;
    private ArrayList<Room> roomList;

    public Schedule() {
        this.lessonList = new ArrayList<>();
        this.groupList = new ArrayList<>();
        this.teacherList = new ArrayList<>();
        this.roomList = new ArrayList<>();
    }

    /**
     * can add a lesson
     * @param lesson to be added.
     */
    public void addLesson(Lesson lesson) {
        lessonList.add(lesson);
    }

    /**
     * Can add a group
     * @param group to be added.
     */
    public void addGroup(Group group) {
        this.groupList.add(group);
    }

    public void addRoom(Room room){
        this.roomList.add(room);
    }

    /**
     * Returns the lesson with specific name.
     * @param name of the lesson
     * @return the lesson with specific name or null if no lesson found.
     */
    public Lesson getLesson(String name) {
        for(Lesson l : lessonList) {
            if(l.getName().equals(name)) {
                return l;
            }
        }
        return null;
    }

    /**
     * returns the group with specific name.
     * @param name of the group
     * @return the group with specific name or null if no group found.
     */
    public Group getGroup(String name) {
        for(Group g : groupList) {
            if(g.getName().equals(name))
                return g;
        }
        return null;
    }

    /**
     * returns the room with specific name.
     * @param name of the room
     * @return the room with specific name or null if no room found
     */
    public Room getRoom(String name){
        for(Room room : this.roomList){
            if(room.getName().equals(name)){
                return room;
            }
        }
        return null;
    }

    /**
     * returns the teacher with specific name.
     * @param name of the teacher
     * @return the teacher with specific name or null if no group found.
     */
    public Teacher getTeacher(String name) {
        for(Teacher t : teacherList) {
            if(t.getName().equals(name))
                return t;
        }
        return null;
    }

    /**
     * Gives the instance of schedule. Don't create a new schedule!
     * @return the schedule.
     */

    public static Schedule getInstance() {
        if(schedule == null) {
            schedule = new Schedule();
        }
        return schedule;
    }

    public ArrayList<Lesson> getLessonList() {
        return lessonList;
    }

    public ArrayList<Group> getGroupList() {
        return groupList;
    }

    public ArrayList<Teacher> getTeacherList() {
        return teacherList;
    }

    public void addTeacher(Teacher teacher) {
        this.teacherList.add(teacher);
    }

    public boolean removeTeacher(Teacher teacher) {
        return this.teacherList.remove(teacher);
    }

    public void removeLesson(Lesson lesson){
        this.lessonList.remove(lesson);
    }

    public void removeGroup(Group group) {
        groupList.remove(group);
    }
}
