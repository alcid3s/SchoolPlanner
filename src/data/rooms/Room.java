package data.rooms;

import data.Target;

import java.io.Serializable;

public abstract class Room implements Comparable, Serializable {

    private String name;
    private int capacity;
    private Target target;


    public Room(String name, int capacity){
        this.name = name;
        this.capacity = capacity;
    }

    public String getName(){
        return this.name;
    }

    public String getSystemName(){
        return name + " (" + capacity + ")";
    }

    public int getCapacity(){
        return this.capacity;
    }

    @Override
    public String toString(){
        return this.name + " (" + this.capacity + ")";
    }

    @Override
    public int compareTo(Object o){
        return this.toString().compareTo(o.toString());
    }
}