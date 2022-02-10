import data.persons.Student;
import data.persons.Person;

import java.util.ArrayList;

public class Group{

    private ArrayList<Person> studentList;
    private String name;
    private int size;

    public Group(String name, int size){
        this.name = name;
        this.size = size;
        this.studentList = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            this.studentList.add(new Student(Student.getRandomName()));
        }
    }
    public ArrayList<Person> getStudents() {
        return studentList;
    }
    public String getName() {
        return name;
    }
    public int getSize() {
        return size;
    }
}
