package data;

import data.persons.Person;

import java.util.ArrayList;

public class Group{

    private ArrayList<Person> students;
    private String name;
    private String systemName;
    private int size;

    public Group(String name, int size){
        this.students = new ArrayList<>();
        this.name = name;
        this.size = size;
        this.systemName = name + " (" + size + ")";
    }

    public ArrayList<Person> getStudents() {
        return students;
    }

    public String getName() {
        return name;
    }

    public String getSystemName(){
        return this.systemName;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return name + " (" + size + ")";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
