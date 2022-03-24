package data;

import java.util.*;

public class Names {
    private static Names namesInstance;
    private ArrayList<String> names;

    public static Names getInstance() {
        if(namesInstance == null) {
            namesInstance = new Names();
        }
        return namesInstance;
    }

    public Names() {
        names = new ArrayList<>();
        //TODO Read Names file, add every name to names arraylist.

    }


    public String getRandomName() {
        return names.get(new Random().nextInt(names.size()-1));
    }
}
