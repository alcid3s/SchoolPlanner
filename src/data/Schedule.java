package data;

import data.persons.Person;
import data.persons.Teacher;
import data.rooms.Room;
import gui.FileManager;
import gui.Util;
import gui.tabs.ScheduleTab;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class Schedule implements Serializable {
    private static Schedule schedule;

    private ArrayList<Lesson> lessonList;
    private ArrayList<Group> groupList;
    private ArrayList<Person> teacherList;
    private final ArrayList<Room> roomList;

    public Schedule() {
        this.lessonList = new ArrayList<>();
        this.groupList = new ArrayList<>();
        this.teacherList = new ArrayList<>();
        this.roomList = new ArrayList<>();
        sort();
    }

    /**
     * Gives the instance of schedule. Don't create a new schedule!
     *
     * @return the schedule.
     */

    public static Schedule getInstance() {
        if (schedule == null) {
            schedule = new Schedule();
        }
        return schedule;
    }

    /**
     * can add a lesson
     *
     * @param lesson to be added.
     */
    public void addLesson(Lesson lesson) {
        lessonList.add(lesson);
        sort();
    }

    /**
     * Can add a group
     *
     * @param group to be added.
     */
    public void addGroup(Group group) {
        this.groupList.add(group);
        sort();
    }

    public void addRoom(Room room) {
        this.roomList.add(room);
        sort();
    }

    /**
     * Returns the lesson with specific name.
     *
     * @param name of the lesson
     * @return the lesson with specific name or null if no lesson found.
     */
    public Lesson getLesson(String name) {
        for (Lesson l : lessonList) {
            if (l.getName().equals(name)) {
                return l;
            }
        }
        return null;
    }

    /**
     * returns the group with specific name.
     *
     * @param name of the group
     * @return the group with specific name or null if no group found.
     */
    public Group getGroup(String name) {
        for (Group g : groupList) {
            if (g.getName().equals(name) || g.getSystemName().equals(name))
                return g;
        }
        return null;
    }

    /**
     * returns the room with specific name.
     *
     * @param name of the room
     * @return the room with specific name or null if no room found
     */
    public Room getRoom(String name) {
        for (Room room : this.roomList) {
            if (room.getName().equals(name) || room.getSystemName().equals(name)) {
                return room;
            }
        }
        return null;
    }

    /**
     * returns the teacher with specific name.
     *
     * @param name of the teacher
     * @return the teacher with specific name or null if no group found.
     */
    public Person getTeacher(String name) {
        for (Person t : teacherList) {
            if (t.getName().equals(name))
                return t;
        }
        return null;
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

    public ArrayList<Room> getRoomList() {
        return this.roomList;
    }

    public void addTeacher(Teacher teacher) {
        this.teacherList.add(teacher);
        sort();
    }

    public boolean removeTeacher(Person teacher) {
        return this.teacherList.remove(teacher);
    }

    public void removeLesson(Lesson lesson) {
        this.lessonList.remove(lesson);
    }

    public void removeGroup(Group group) {
        groupList.remove(group);
    }

    public void sort() {
        Collections.sort(this.lessonList);
        Collections.sort(this.roomList);
        Collections.sort(this.groupList);
        Collections.sort(this.teacherList);
    }

    public void setExample() {
        groupList.add(new Group("Proftaak B", 20));
        groupList.add(new Group("Proftaak A", 20));
        groupList.add(new Group("Proftaak C", 20));
        groupList.add(new Group("Proftaak D", 20));
        groupList.add(new Group("Proftaak E", 20));
        teacherList.add(new Teacher("Pieter"));
        teacherList.add(new Teacher("Edwin"));
        teacherList.add(new Teacher("Etienne"));
        lessonList.add(new Lesson("WIS", getRoom("LA134"), getTeacher("Pieter"), getGroup("Proftaak B"), Util.makeTime("9", "00"), Util.makeTime("15", "30")));
        lessonList.add(new Lesson("OGP", getRoom("CollegeHall"), getTeacher("Edwin"), getGroup("Proftaak A"), Util.makeTime("10", "00"), Util.makeTime("12", "00")));
        lessonList.add(new Lesson("OGP1", getRoom("LA124"), getTeacher("Etienne"), getGroup("Proftaak A"), Util.makeTime("12", "05"), Util.makeTime("16", "00")));
    }

    public boolean save(File file) {
        Optional<String> optionalExtension = getExtensionByStringHandling(file.getName());
        if (!optionalExtension.isPresent())
            return false;
        switch (optionalExtension.get().toLowerCase()) {
            case "json":
                return FileManager.saveJson(file, this);
            case "rooster":
                try {
                    if (file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();

                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                    objectOutputStream.writeObject(this);
                    objectOutputStream.close();
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
        }
        return false;
    }

    public boolean load(File file) {
        Optional<String> optionalExtension = getExtensionByStringHandling(file.getName());
        if (!optionalExtension.isPresent())
            return false;
        switch (optionalExtension.get().toLowerCase()) {
            case "json":
                return FileManager.loadJson(file, this);
            case "rooster":
                try {
                    FileInputStream f = new FileInputStream(file);
                    ObjectInputStream s = new ObjectInputStream(f);
                    Schedule newSchedule = (Schedule) s.readObject();
                    this.schedule.setLessonList(newSchedule.getLessonList());
                    this.schedule.setTeacherList(newSchedule.getTeacherList());
                    this.schedule.setGroupList(newSchedule.getGroupList());
                    Clock.resetTime();
                    ScheduleTab.refreshCanvas();
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
        }
        return false;
    }

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    @Override
    public String toString() {
        return lessonList.toString() + "\n" + teacherList.toString() + " \n" + groupList.toString() + "\n" + roomList.toString();
    }

    public void setLessonList(ArrayList<Lesson> lessonList) {
        this.lessonList = lessonList;
    }

    public void setGroupList(ArrayList<Group> groupList) {
        this.groupList = groupList;
    }

    public void setTeacherList(ArrayList<Person> teacherList) {
        this.teacherList = teacherList;
    }

    public void clearAllLists() {
        this.lessonList.clear();
        this.groupList.clear();
        this.teacherList.clear();
    }
}
