package data.persons;

import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Teacher extends Person {
    private Point direction = new Point(1, 1);
    private int animationCounter = 0;
    private static final int size = 32;

    public Teacher(String name) {
        super(name, getImages());
    }

    private static BufferedImage[] getImages() {
        try {
            BufferedImage totalImage = ImageIO.read(Objects.requireNonNull(Student.class.getClassLoader().getResource("NPCs.png")));
            BufferedImage[] sprites = new BufferedImage[size * 3];
            int counter = 0;
            for (int y = 4; y < 8; y++) {
                for (int x = 6; x < 9; x++) {
                    sprites[counter] = totalImage.getSubimage(x * size, y * size, size, size);
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
                tx.translate(getPosition().getX() - (size/2), getPosition().getY() - (size/2));

                // System.out.println(this.direction);
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
}
