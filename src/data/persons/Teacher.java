package data.persons;

public class Teacher extends Person{

    public Teacher(String name){
        super(name);
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
