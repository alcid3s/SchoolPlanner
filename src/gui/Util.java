package gui;

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
}
