package data;

import data.persons.Person;
import data.persons.*;
import data.rooms.Room;
import managers.FileManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Class Schedule
 * Creates object that combines all attributes in the application
 */

public class Schedule {
    private static Schedule schedule;
    private ArrayList<Lesson> lessonList;
    private ArrayList<Group> groupList;
    private ArrayList<Person> teacherList;
    private final ArrayList<Room> roomList;

    /**
     * Constructor
     */

    public Schedule() {
        this.lessonList = new ArrayList<>();
        this.groupList = new ArrayList<>();
        this.teacherList = new ArrayList<>();
        this.roomList = new ArrayList<>();
    }

    /**
     * Static method getInstance
     * Gives the instance of schedule. Don't create a new schedule!
     * @return the schedule
     */

    public static Schedule getInstance() {
        if (schedule == null) {
            schedule = new Schedule();
        }
        return schedule;
    }

    /**
     * Method addLesson
     * can add a lesson
     * @param lesson to be added
     */

    public void addLesson(Lesson lesson) {
        lessonList.add(lesson);
    }

    /**
     * Method addGroup
     * Can add a group
     * @param group to be added.
     */

    public void addGroup(Group group) {
        this.groupList.add(group);
    }

    public void addRoom(Room room) {
        this.roomList.add(room);
    }

    /**
     * Method getGroup
     * returns the group with specific name.
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
     * Method getRoom
     * returns the room with specific name.
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
     * Method getTeacher
     * returns the teacher with specific name.
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

    /**
     * Method getLessonList
     * @return list with lessons
     */

    public ArrayList<Lesson> getLessonList() {
        return lessonList;
    }

    /**
     * Method getGroupList
     * @return list with groups
     */

    public ArrayList<Group> getGroupList() {
        return groupList;
    }

    /**
     * Method getTeacher
     * @return list with teachers
     */

    public ArrayList<Person> getTeacherList() {
        return teacherList;
    }

    /**
     * Method getRoomList
     * @return list with rooms
     */

    public ArrayList<Room> getRoomList() {
        return this.roomList;
    }

    /**
     * Method addTeacher
     * @param teacher to be added to list with teachers
     */

    public void addTeacher(Teacher teacher) {
        this.teacherList.add(teacher);
    }

    /**
     * Method removeTeacher
     * @param teacher to be removed from list with teachers
     */

    public void removeTeacher(Person teacher) {
        this.teacherList.remove(teacher);
    }

    /**
     * Method removeLesson
     * @param lesson to be removed from list with lessons
     */

    public void removeLesson(Lesson lesson) {
        this.lessonList.remove(lesson);
    }

    /**
     * Method removeGroup
     * @param group to be removed from list with groups
     */

    public void removeGroup(Group group) {
        groupList.remove(group);
    }

    /**
     * Method sort
     * sorts all lists on natural order
     */

    public void sort() {
        Collections.sort(this.lessonList);
        Collections.sort(this.roomList);
        Collections.sort(this.groupList);
        Collections.sort(this.teacherList);
    }

    /**
     * Method save
     * @param file that needs to be saved
     * @return boolean that checks if saving was successful
     */

    public boolean save(File file) {
        Optional<String> optionalExtension = getExtensionByStringHandling(file.getName());
        if (!optionalExtension.isPresent())
            return false;
        switch (optionalExtension.get().toLowerCase()) {
            case "json":
                return FileManager.saveJson(file, this);
        }
        return false;
    }

    /**
     * Method getAllPersons
     * @return list with all persons
     */

    public List<Person> getAllPersons() {
        List<Person> people = new ArrayList<>(Schedule.getInstance().getTeacherList());
        Schedule.getInstance().getGroupList().forEach(g -> {people.addAll(g.getStudents());});
        return people;
    }

    /**
     * Method load
     * @param file to load in
     * @return boolean that checks if loading was successful
     */

    public boolean load(File file) {
        Optional<String> optionalExtension = getExtensionByStringHandling(file.getName());
        if (!optionalExtension.isPresent())
            return false;
        switch (optionalExtension.get().toLowerCase()) {
            case "json":
                return FileManager.loadJson(file, this);
        }
        return false;
    }

    /**
     * Method getExtensionByStringHandling
     * @param filename
     * @return
     */

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    /**
     * Inherited method toString
     * @return string of object
     */

    @Override
    public String toString() {
        return lessonList.toString() + "\n" + teacherList.toString() + " \n" + groupList.toString() + "\n" + roomList.toString();
    }

    /**
     * Method setLessonList
     * @param lessonList to set all lessons to
     */

    public void setLessonList(ArrayList<Lesson> lessonList) {
        this.lessonList = lessonList;
    }

    /**
     * Method setGroupList
     * @param groupList to set all groups to
     */

    public void setGroupList(ArrayList<Group> groupList) {
        this.groupList = groupList;
    }

    /**
     * Method setTeacherList
     * @param teacherList to set all teachers to
     */

    public void setTeacherList(ArrayList<Person> teacherList) {
        this.teacherList = teacherList;
    }

    /**
     * Method clearAllLists
     * clears all lists in the object
     */

    public void clearAllLists() {
        this.lessonList.clear();
        this.groupList.clear();
        this.teacherList.clear();
    }
}
