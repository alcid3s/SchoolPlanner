package data;

import data.persons.Person;
import data.persons.Student;

import java.util.ArrayList;

public class Group implements Comparable{

    private ArrayList<Person> students;
    private String name;
    private String systemName;
    private int size;

    public Group(String name, int size){
        this.students = new ArrayList<>();
        this.name = name;
        this.size = size;
        this.systemName = name + " (" + size + ")";

        for(int i = 0; i < size; i++){
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
        this.systemName = name + " (" + size + ")";
        Schedule.getInstance().sort();
    }

    public String getSystemName(){
        return this.systemName;
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
}
