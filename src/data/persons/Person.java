package data.persons;

import data.Schedule;

import java.io.Serializable;

public abstract class Person implements Comparable, Serializable {
    private String name;

    public Person(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
        Schedule.getInstance().sort();
    }

    @Override
    public String toString(){
        return this.name;
    }

    @Override
    public int compareTo(Object o){
        return this.toString().compareTo(o.toString());
    }

    public String getJsonString() {
        return name;
    }
}
