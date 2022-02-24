package data.tilted;

import java.util.ArrayList;
import java.util.Arrays;

public class TiledLayer {
    private int width;
    private int height;
    private int[][] values;

    public TiledLayer(int width, int height) {
        this.width = width;
        this.height = height;
        values = new int[height][width];
    }

    public boolean addValue(int data, int width, int height) {
        if(values.length > height && values[height].length > width) {
            values[width][height] = data;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        ArrayList<String> valuesAsString = new ArrayList<>();
        for(int i = 0; i < values.length; i++) {
            for(int j = 0; j < values[i].length; j++) {
                    if(values[i][j] != 0) {
                        valuesAsString.add("(" + i + "," + j + ") " + values[i][j]);
                    }
                }
        }
        return "TiltedLayer:\n" +
                "Width: " + width + "\n" +
                "Height: " + height + "\n" +
                "Values: " + valuesAsString.toString();
    }
}
