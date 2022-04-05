package managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Class Names
 * Handles a random name generator for students
 */

public class Names {

    private static List<String> names;

    /**
     * Static
     * Initialises the file and puts all names in a list
     */

    static{
        names = new LinkedList<>();
        try {
            File file = new File("resources/Names.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine();
                names.add(name);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Static method getRandomName
     * @return a random name
     */

    public static String getRandomName() {
        return names.get(new Random().nextInt(names.size() - 1));
    }
}
