package data.persons;

import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Teacher extends Person {

    public Teacher(String name){
        super(name, getImages());
    }

    private static BufferedImage[] getImages() {
        try {
            BufferedImage totalImage = ImageIO.read(Objects.requireNonNull(Student.class.getClassLoader().getResource("teacher.png")));
            BufferedImage[] sprites = new BufferedImage[totalImage.getWidth()/32];
            sprites[0] = totalImage.getSubimage(0,0,32,32);
            return sprites;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void draw(FXGraphics2D graphics) {
        graphics.drawImage(getSprites()[1], graphics.getTransform(), null);
    }

    @Override
    public void update(double deltaTime) {

    }
}
