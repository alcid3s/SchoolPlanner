package data;

import data.persons.Person;
import data.persons.Student;
import managers.Names;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Comparable<Group>, Serializable {

    private final ArrayList<Person> students;
    private String name;
    private int size;

    public Group(String name, int size) {
        this.students = new ArrayList<>();
        this.name = name;
        this.size = size;

        for (int i = 0; i < size; i++) {
            this.students.add(new Student(Names.getRandomName()));
        }
    }

    public Group(String jsonData) {
        this.students = new ArrayList<>();
        String[] values = jsonData.split(";");
        this.name = values[0];
        this.size = Integer.parseInt(values[1]);
        for (int i = 0; i < size; i++) {
            this.students.add(new Student(Names.getRandomName()));
        }
    }

    public void addNewStudent() {
        if(this.students.size() >= this.size){
            this.size++;
        }
        this.students.add(new Student(Names.getRandomName()));
    }

    public void removeStudent() {
        this.students.remove(0);
    }

    public ArrayList<Person> getStudents() {
        return students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSystemName() {
        return name + " (" + size + ")";
    }

    public int getSize() {
        return size;
    }

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

    @Override
    public String toString() {
        return name + " (" + size + ")";
    }

    @Override
    public int compareTo(Group g) {
        return this.name.compareTo(g.getName());
    }

    public String getJsonString() {
        return name + ";" + size;
    }
}
