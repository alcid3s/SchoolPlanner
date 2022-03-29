package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.*;

public class Names {
    private static Names namesInstance;
    private List<String> names;

    public static Names getInstance() {
        if (namesInstance == null) {
            namesInstance = new Names();
        }
        return namesInstance;
    }

    public Names() {
        names = new LinkedList<>();
        try {
            File file = new File(getClass().getResource("/Names.txt").toURI());
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine();
                names.add(name);
            }
        } catch (FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public String getRandomName() {
        return names.get(new Random().nextInt(names.size() - 1));
    }
}
