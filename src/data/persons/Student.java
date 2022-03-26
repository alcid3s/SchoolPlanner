package data.persons;


import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import java.util.Random;
import java.util.Scanner;

public class Student extends Person {
    private Point direction;

    public Student(String name) {
        super(name, getImages());
    }

    private static BufferedImage[] getImages() {
        try {
            BufferedImage totalImage = ImageIO.read(Objects.requireNonNull(Student.class.getClassLoader().getResource("NPCs.png")));
            BufferedImage[] sprites = new BufferedImage[48 * 3];
            int counter = 0;
            for (int y = 0; y < 4; y++) {
                for (int x = 0; x < 3; x++) {
                    sprites[counter] = totalImage.getSubimage(x * 48,y * 48,48, 48);
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
        if(isSpawned()) {
            if (getSprites() != null) {
                AffineTransform tx = graphics.getTransform();
                tx.translate(getPosition().getX() - 16, getPosition().getY() - 16);

                // System.out.println(this.direction);
                if(this.direction != null) {
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
                }else{
                    drawAnimation(graphics, tx, 5);
                }
            }
        }
    }



    @Override
    public void update(double deltaTime) {
        if(isSpawned()) {
            if(target != null) {
                int tileX = (int) Math.floor(getPosition().getX() / 32);
                int tileY = (int) Math.floor(getPosition().getY() / 32);
                if(!target.isAtTarget(tileX, tileY)) {
                    this.direction = target.getDirection(tileX, tileY);
                    if(direction.x != 0 || direction.y != 0) {
                        Point2D neededToMove = calculateMovement(direction, tileX,tileY);
                        move(neededToMove, deltaTime);
                    }
                } else {
                    target = null;
                    this.direction = null;
                }
            }
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
