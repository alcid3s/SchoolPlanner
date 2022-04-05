package data;

import data.persons.Person;
import data.persons.Student;
import managers.Names;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class Group
 * Creates Groups for the application
 */

public class Group implements Comparable<Group> {

    private final ArrayList<Person> students;
    private String name;
    private int size;

    /**
     * Constructor
     * @param name of the group
     * @param size of objects of type Person that are in the group
     */

    public Group(String name, int size) {
        this.students = new ArrayList<>();
        this.name = name;
        this.size = size;

        for (int i = 0; i < size; i++) {
            this.students.add(new Student(Names.getRandomName()));
        }
    }

    /**
     * Constructor
     * Extra to create groups from JSON files
     * @param jsonData from loaded files
     */

    public Group(String jsonData) {
        this.students = new ArrayList<>();
        String[] values = jsonData.split(";");
        this.name = values[0];
        this.size = Integer.parseInt(values[1]);
        for (int i = 0; i < size; i++) {
            this.students.add(new Student(Names.getRandomName()));
        }
    }

    /**
     * Method addNewStudent
     * Adds a new student to the list with Person
     */

    public void addNewStudent() {
        if(this.students.size() >= this.size){
            this.size++;
        }
        this.students.add(new Student(Names.getRandomName()));
    }

    /**
     * Method removeStudent
     * removes a student form the list with Person
     */

    public void removeStudent() {
        this.students.remove(0);
    }

    /**
     * Method getStudents
     * @return list with Person
     */

    public ArrayList<Person> getStudents() {
        return students;
    }

    /**
     * Method getName
     * @return name of the group
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
     * Method getSystemName
     * @return name of the group where the system works with
     */

    public String getSystemName() {
        return name + " (" + size + ")";
    }

    /**
     * Method getSize
     * @return size of the group
     */

    public int getSize() {
        return size;
    }

    /**
     * Method setSize
     * @param size to set to
     */

    public void setSize(int size) {
        this.size = size;
        if (size < this.students.size()) {
            while (this.size != this.students.size()) {
                removeStudent();
            }
        } else if (size > this.students.size()) {
            while (this.size != this.students.size()) {
                addNewStudent();
            }
        }
    }

    /**
     * Inherited method toString
     * @return string of the object
     */

    @Override
    public String toString() {
        return name + " (" + size + ")";
    }

    /**
     * Implemented Method compareTo
     * @param g group to compare with
     * @return result of comparison
     */

    @Override
    public int compareTo(Group g) {
        return this.name.compareTo(g.getName());
    }

    /**
     * Method getJsonString
     * @return string that will be written in JSON files
     */

    public String getJsonString() {
        return name + ";" + size;
    }
}
