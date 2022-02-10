import data.Person;
import data.persons.Student;

import java.util.ArrayList;

public class Group{

    private ArrayList<Person> studentList;
    private String name;

    public Group(String name, int capacity){
        this.studentList = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            this.studentList.add(new Student(Student.getRandomName()));
        }
        this.name = name;
    }

    public void printGroup(){
        for(Person student : this.studentList){
            System.out.println(student.getName());
        }
    }

}
