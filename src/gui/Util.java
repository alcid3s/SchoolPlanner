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
        b.setPrefSize(width, height);
        return b;
    }

    public static LocalDateTime makeTime(String hours, String minutes) {
        int hoursInt = 0;
        int minutesInt = 0;
        try {
            hoursInt = Integer.parseInt(hours);
            minutesInt = Integer.parseInt(minutes);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), hoursInt, minutesInt);
    }

    public static ObservableList<String> getHoursInList() {
        ArrayList<String> hourList = new ArrayList<>();
        for (int i = 8; i < 17; i++) {
            hourList.add("" + i);
        }
        return FXCollections.observableList(hourList);
    }

    public static ObservableList<String> getMinutesInList() {
        ArrayList<String> minuteList = new ArrayList<>();
        for (int i = 0; i < 60; i += 5) {
            if (i < 10) {
                minuteList.add("0" + i);
            } else {
                minuteList.add("" + i);
            }
        }
        return FXCollections.observableList(minuteList);
    }

    public static int timeInInt(LocalDateTime time) {
        return (time.getHour() * 100) + time.getMinute();
    }

    public static String timeInString(int time) {
        if (time < 10) {
            return "0" + time;
        }
        return "" + time;
    }
}
