package managers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Class Util
 * Creates utilities that can be used across all classes
 */

public class Util {

    /**
     * Static method getDefaultButton
     * Creates a generalised format of a button
     * @param name to put on the button
     * @param height of the button
     * @param width of the button
     * @return the button
     */

    public static Button getDefaultButton(String name, int height, int width) {
        Button b = new Button(name);
        b.setPrefSize(width, height);
        return b;
    }

    /**
     * Static method makeTime
     * Creates an object of LocalDateTime
     * @param hours to put in the object
     * @param minutes to put in the object
     * @return a new LocalDateTime
     */

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

    /**
     * Static method getHoursInList
     * @return a list with all hours in a school day
     */

    public static ObservableList<String> getHoursInList() {
        ArrayList<String> hourList = new ArrayList<>();
        for (int i = 8; i < 17; i++) {
            hourList.add("" + i);
        }
        return FXCollections.observableList(hourList);
    }

    /**
     * Static method getHoursInList
     * @return a list with all minutes
     */

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

    /**
     * Static method timeInInt
     * Converts time in LocalDateTime to an integer
     * @param time that needs to be converted
     * @return the converted integer
     */

    public static int timeInInt(LocalDateTime time) {
        return (time.getHour() * 100) + time.getMinute();
    }

    /**
     * Static method timeInString
     * Converts a time in an integer to a time in a string with an additional 0
     * @param time that needs to be converted
     * @return the converted string
     */

    public static String timeInString(int time) {
        if (time < 10) {
            return "0" + time;
        }
        return "" + time;
    }
}
