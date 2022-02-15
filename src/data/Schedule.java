package data;

import data.persons.Person;
import data.persons.Teacher;
import data.rooms.Room;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Schedule {
    private static Schedule schedule;

    private ArrayList<Lesson> lessonList;
    private ArrayList<Group> groupList;
    private ArrayList<Person> teacherList;
    private ArrayList<Room> roomList;

    public Schedule() {
        this.lessonList = new ArrayList<>();
        this.groupList = new ArrayList<>();
        this.teacherList = new ArrayList<>();
        this.roomList = AllRooms.AllRooms();
        setExample();
    }

    /**
     * can add a lesson
     * @param lesson to be added.
     */
    public void addLesson(Lesson lesson) {
        lessonList.add(lesson);
        System.out.println(lessonList);
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
            if(g.getName().equals(name) || g.getSystemName().equals(name))
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
            if(room.getName().equals(name) || room.getSystemName().equals(name)){
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
    public Person getTeacher(String name) {
        for(Person t : teacherList) {
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

    public ArrayList<Person> getTeacherList() {
        return teacherList;
    }

    public ArrayList<Room> getRoomList(){
        return this.roomList;
    }

    public void addTeacher(Person teacher) {
        this.teacherList.add(teacher);
    }

    public boolean removeTeacher(Person teacher) {
        return this.teacherList.remove(teacher);
    }

    public void removeLesson(Lesson lesson){
        this.lessonList.remove(lesson);
    }

    public void removeGroup(Group group) {
        groupList.remove(group);
    }

    private void setExample(){
        groupList.add(new Group("Proftaak B", 30));
        groupList.add(new Group("Proftaak A", 25));
        teacherList.add(new Teacher("Pieter"));
        teacherList.add(new Teacher("Edwin"));
        teacherList.add(new Teacher("Etienne"));
        lessonList.add(new Lesson("WK", getRoom("LD111"), getTeacher("Pieter"), getGroup("Proftaak B"), LocalDateTime.now(), LocalDateTime.now()));
        lessonList.add(new Lesson("OGP", getRoom("LA134"), getTeacher("Edwin"), getGroup("Proftaak A"), LocalDateTime.now(), LocalDateTime.now()));
    }
}
