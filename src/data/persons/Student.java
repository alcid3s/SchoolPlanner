package data.persons;

import data.Schedule;
import data.rooms.Room;
import data.rooms.object.UsableObject;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

public class Student extends Person {

    public Student(String name) {
        super(name, getImages());
        Room r = Schedule.getInstance().getRoomList().get(new Random().nextInt(Schedule.getInstance().getRoomList().size()));
        //Room r = Schedule.getInstance().getRoom("Xplora4Kamer");
        Optional<UsableObject> object = r.getFreeChair(this);
/*
        if(object.isPresent()) {
            target = object.get().getTarget();
            System.out.println("Student -> Found Target! (Room: " + r.getName() + ")");
            target.print();
        } else {
            System.out.println("Student -> Target not found! (Room: " + r.getName() + ")");
        }
*/
    }

    private static BufferedImage[] getImages() {
        try {
            BufferedImage totalImage = ImageIO.read(Objects.requireNonNull(Student.class.getClassLoader().getResource("NPCs.png")));
            BufferedImage[] sprites = new BufferedImage[48 * 3];
            int counter = 0;
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 3; x++) {
                    sprites[counter] = totalImage.getSubimage(x * 48, y * 48, 48, 48);
                    counter++;
                }
            }
            return sprites;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void draw(FXGraphics2D graphics) {
        if (isSpawned()) {
            if (getSprites() != null) {
                AffineTransform tx = graphics.getTransform();
                tx.translate(getPosition().getX() - 16, getPosition().getY() - 16);

                // System.out.println(this.direction);
                if (this.direction != null) {
                    if (this.direction.x == 0 && this.direction.y == -1) {
                        drawAnimation(graphics, tx, 1);
                    } else if (this.direction.x == 1 && this.direction.y == 0) {
                        drawAnimation(graphics, tx, 2);
                    } else if (this.direction.x == -1 && this.direction.y == 0) {
                        drawAnimation(graphics, tx, 3);
                    } else if (this.direction.x == 0 && this.direction.y == 1) {
                        drawAnimation(graphics, tx, 4);
                    } else {
                        drawAnimation(graphics, tx, 5);
                    }
                } else {
                    drawAnimation(graphics, tx, 5);
                }
            }
        }
    }

    private void drawAnimation(FXGraphics2D graphics, AffineTransform tx, int state) {
        if (state == 1 && this.animationCounter >= 11 || state == 1 && this.animationCounter < 9) {
            this.animationCounter = 9;
        } else if (state == 2 && this.animationCounter >= 8 || state == 2 && this.animationCounter < 6) {
            this.animationCounter = 6;
        } else if (state == 3 && this.animationCounter >= 5 || state == 3 && this.animationCounter < 3) {
            this.animationCounter = 3;
        } else if (state == 4 && this.animationCounter >= 2 || state == 4 && this.animationCounter < 0) {
            this.animationCounter = 0;
        } else if (state == 5) {
            this.animationCounter = 0;
        }
        graphics.drawImage(getSprites()[animationCounter], tx, null);
        animationCounter++;
    }

    @Override
    public void update(double deltaTime) {

    }


    //TODO Make more Efficient. Singleton Class which reads the names 1 time and not again for every student.
    public static String getRandomName() {
        File file = new File("src/Names.txt");
        Random random = new Random();
        int pos = random.nextInt(1081);
        try {
            Scanner scanner = new Scanner(file);

            for (int i = 0; i < pos - 1; i++) {
                scanner.nextLine();
            }
            String name = scanner.nextLine();
            scanner.close();

            return name;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
