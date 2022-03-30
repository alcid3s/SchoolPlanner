package managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Names {
    private static List<String> names;

    public static void init(){
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

    public static String getRandomName() {
        return names.get(new Random().nextInt(names.size() - 1));
    }
}
