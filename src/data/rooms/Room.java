package data.rooms;

public abstract class Room{

    private String name;
    private int capacity;
    private String systemName;

    public Room(String name, int capacity){
        this.name = name;
        this.capacity = capacity;
        this.systemName = name + " (" + capacity + ")";
    }

    public String getName(){
        return this.name;
    }

    public String getSystemName(){
        return this.systemName;
    }

    public int getCapacity(){
        return this.capacity;
    }

    @Override
    public String toString(){
        return this.name + " (" + this.capacity + ")";
    }
}