package data.rooms;

public abstract class Room{

    private String name;
    private int capacity;

    public Room(String name, int capacity){
        this.name = name;
        this.capacity = capacity;
    }

    public String getName(){
        return this.name;
    }
}