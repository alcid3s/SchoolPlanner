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

    public Teacher(String name) {
        super(name, getImages());
    }

    private static BufferedImage[] getImages() {
        try {
            BufferedImage totalImage = ImageIO.read(Objects.requireNonNull(Teacher.class.getClassLoader().getResource("NPCs.png")));
            BufferedImage[] sprites = new BufferedImage[48 * 3];
            int counter = 0;
            for (int y = 4; y < 8; y++) {
                for (int x = 6; x < 9; x++) {
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
                if(this.direction != null){
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
        if(isSpawned()){
            if(target != null){
                int x = (int)Math.floor(getPosition().getX() / 32);
                int y = (int)Math.floor(getPosition().getY() / 32);
                if(!target.isAtTarget(x, y)){
                    this.direction = target.getDirection(x,y);
                    if(direction.x != 0 || direction.y != 0){
                        Point2D neededToMove = calculateMovement(direction, x, y);
                        move(neededToMove, deltaTime);
                    }
                }else {
                    target = null;
                    this.direction = null;
                }
            }
        }
    }
}
