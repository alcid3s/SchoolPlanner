package data.persons;

import simulation.Animation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Class Teacher
 * Creates an object Teacher that inherits Person
 */

public class Teacher extends Person {

    /**
     * Constructor
     * @param name to give to the object
     */

    public Teacher(String name) {
        super(name, getAnimation());
    }

    /**
     * Private static method getAnimation
     * Auto generates the animation for a Person of type Student
     * @return the animation
     */

    private static Animation getAnimation() {
        final int size = 32;
        Animation animation = new Animation(3);
        ArrayList<Facing> faces = new ArrayList<>(Arrays.asList(Facing.SOUTH, Facing.WEST, Facing.EAST, Facing.NORTH));
        try {
            BufferedImage totalImage = ImageIO.read(Objects.requireNonNull(Student.class.getClassLoader().getResource("NPCs.png")));
            for (int y = 4; y < 8; y++) {
                BufferedImage[] sprites = new BufferedImage[3];
                for (int x = 6; x < 9; x++) {
                    sprites[x-6] = totalImage.getSubimage(x * size, y * size, size, size);
                }
                animation.setFacing(faces.get(y-4), sprites);
                if(faces.get(y-4).equals(Facing.SOUTH)) {
                    animation.setFacing(Facing.STATIONARY, sprites);
                }
            }
            return animation;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
