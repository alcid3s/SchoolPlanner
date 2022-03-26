package data.persons;

import data.Animation;
import org.jfree.fx.FXGraphics2D;
import tasks.IdleTask;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Teacher extends Person {
    private Point direction = new Point(1, 1);
    private int animationCounter = 0;
    private static final int size = 32;

    public Teacher(String name) {
        super(name, getAnimation());
    }

    private static Animation getAnimation() {
        final int size = 32;
        Animation animation = new Animation(3);
        ArrayList<Facing> faces = new ArrayList<>(Arrays.asList(Facing.SOUTH, Facing.WEST, Facing.EAST, Facing.NORTH));
        try {
            BufferedImage totalImage = ImageIO.read(Objects.requireNonNull(Student.class.getClassLoader().getResource("NPCs.png")));
            //BufferedImage[] sprites = new BufferedImage[size * 3];
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

    @Override
    public void draw(FXGraphics2D graphics) {
        if (isSpawned()) {
            AffineTransform tx = graphics.getTransform();
            tx.translate(getPosition().getX() - (size / 2), getPosition().getY() - (size / 2));
            graphics.drawImage(getAnimation().getImage(), tx, null);
        }
    }

    @Override
    public void update(double deltaTime) {
        if(task == null) {
            task = new IdleTask(this);
        }
        getAnimation().update(deltaTime, Facing.getFacing(direction));
        task.update(deltaTime);
    }
}
