package gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;

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

    public static int getHour(String string){
        try{
            return Integer.parseInt(string.substring(0,2));
        }catch(Exception e){
            new Alert(Alert.AlertType.ERROR, "TIME INVALID");
            return 0;
        }
    }

    public static int getMinute(String minutes){
        try{
            return Integer.parseInt(minutes.substring(3,5));
        }catch(Exception e){
            new Alert(Alert.AlertType.ERROR, "TIME INVALID");
            return 0;
        }
    }
}
