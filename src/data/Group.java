package data;

import data.persons.Person;
import data.persons.Student;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Comparable, Serializable {

    private ArrayList<Person> students;
    private String name;
    private int size;

    public Group(String name, int size){
        this.students = new ArrayList<>();
        this.name = name;
        this.size = size;

        for(int i = 0; i < size; i++) {
            this.students.add(new Student(Names.getInstance().getRandomName()));
        }
    }

    public Group(String jsonData) {
        this.students = new ArrayList<>();
        String[] values = jsonData.split(";");
        this.name = values[0];
        this.size = Integer.parseInt(values[1]);
        for(int i = 0; i < size; i++) {
            this.students.add(new Student(Student.getRandomName()));
        }
    }

    public void addNewStudent(){
        this.size++;
        this.students.add(new Student(Student.getRandomName()));
    }

    public ArrayList<Person> getStudents(){
        return students;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
        Schedule.getInstance().sort();
    }

    public String getSystemName() {
        return  name + " (" + size + ")";
    }

    public int getSize(){
        return size;
    }

    public void setSize(int size){
        this.size = size;
    }

    @Override
    public String toString(){
        return name + " (" + size + ")";
    }

    @Override
    public int compareTo(Object o){
        return this.toString().compareTo(o.toString());
    }

    public String getJsonString() {
        return name + ";" + size;
    }
}
