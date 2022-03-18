package data.persons;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Student extends Person {

    public Student(String name) {
        super(name, getImages());

    }

    private static BufferedImage[] getImages() {
        try {
            BufferedImage totalImage = ImageIO.read(Objects.requireNonNull(Student.class.getClassLoader().getResource("student.png")));
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
        if(isSpawned()) {
            if (getSprites() != null) {
                AffineTransform tx = graphics.getTransform();
                tx.translate(getPosition().getX(), getPosition().getY());
                graphics.drawImage(getSprites()[0], tx, null);
            }
        }
    }

    @Override
    public void update(double deltaTime) {
        if(isSpawned()){
            setPosition(new Point2D.Double(getPosition().getX(), getPosition().getY() - 200 * deltaTime));
        }
    }

    public static String getRandomName(){
        File file = new File("src/Names.txt");
        Random random = new Random();
        int pos = random.nextInt(1081);

        try {
            Scanner scanner = new Scanner(file);

            for(int i = 0; i < pos - 1; i++){
                scanner.nextLine();
            }
            String name = scanner.nextLine();
            scanner.close();

            return name;
        } catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
