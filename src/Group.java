import data.persons.Person;

import java.util.ArrayList;

public class Group{

    private ArrayList<Person> students;
    private String name;
    private int size;

    public Group(String name, int size){
        this.students = new ArrayList<>();
        this.name = name;
        this.size = size;
    }

    public ArrayList<Person> getStudents() {
        return students;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }
}
