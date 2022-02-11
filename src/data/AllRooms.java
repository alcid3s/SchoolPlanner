package data;

import data.rooms.*;

import java.util.ArrayList;

public class AllRooms {

    public static ArrayList<Room> AllRooms(){
        ArrayList<Room> allRooms = new ArrayList<Room>();
        allRooms.add(new Xplora("XPlora", 200));
        allRooms.add(new Canteen("Canteen", 60));
        allRooms.add(new TeacherRoom("Teachersroom", 20));
        allRooms.add(new Classroom("LA134", 25));
        allRooms.add(new Classroom("LA136", 25));
        allRooms.add(new Classroom("LD111", 35));
        allRooms.add(new Classroom("LD112", 35));
        allRooms.add(new Classroom("LD128", 40));
        allRooms.add(new Classroom("LA523", 40));
        allRooms.add(new Classroom("LA029", 70));

        return allRooms;
    }
}
