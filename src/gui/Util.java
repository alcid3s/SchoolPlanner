package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Util {

    /*
    Creates default button with a name, height and width.
     */
    public static Button getDefaultButton(String name, int height, int width) {
        Button b = new Button(name);
        b.setPrefHeight(height);
        b.setPrefWidth(width);
        return b;
    }

    public static LocalDateTime makeTime(String hours, String minutes){
        return LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), Integer.parseInt(hours), Integer.parseInt(minutes));
    }

    public static ObservableList<String> getHoursInList(){
        ArrayList<String> hourList = new ArrayList<>();
        for(int i = 8; i < 19; i++) {
            hourList.add("" + i);
        }
        return FXCollections.observableList(hourList);
    }

    public static ObservableList<String> getMinutesInList(){
        ArrayList<String> minuteList = new ArrayList<>();
        for(int i = 0; i < 60; i += 5) {
            if(i < 10){
                minuteList.add("0" + i);
            }else{
                minuteList.add("" + i);
            }
        }
        return FXCollections.observableList(minuteList);
    }
}
