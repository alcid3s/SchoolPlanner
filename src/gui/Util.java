package gui;

import javafx.scene.control.Button;

import java.time.LocalDateTime;

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
}
