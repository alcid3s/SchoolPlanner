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

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return this.name + " (" + capacity + ")";
    }
}