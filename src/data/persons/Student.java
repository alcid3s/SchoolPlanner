package data.persons;

import data.Animation;
import tasks.IdleTask;
import tasks.Task;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import java.util.Random;
import java.util.Scanner;
import java.util.*;

public class Student extends Person {
    private static HashMap<Facing, BufferedImage[]> images;

    public Student(String name) {
        super(name, getAnimation());
    }

    private static Animation getAnimation() {
        Animation animation = new Animation(3);
        if(images != null) {
            images.forEach(animation::setFacing);
            return animation;
        }
        images = new HashMap<>();
        final int size = 32;
        ArrayList<Facing> faces = new ArrayList<>(Arrays.asList(Facing.SOUTH, Facing.WEST, Facing.EAST, Facing.NORTH));
        try {
            BufferedImage totalImage = ImageIO.read(Objects.requireNonNull(Student.class.getClassLoader().getResource("NPCs.png")));
            for (int y = 0; y < 4; y++) {
                BufferedImage[] sprites = new BufferedImage[3];
                for (int x = 0; x < 3; x++) {
                    sprites[x] = totalImage.getSubimage(x * size, y * size, size, size);
                }
                animation.setFacing(faces.get(y), sprites);
                images.put(faces.get(y), sprites);
                if(faces.get(y).equals(Facing.SOUTH)) {
                    animation.setFacing(Facing.STATIONARY, sprites);
                    images.put(Facing.STATIONARY, sprites);
                }
            }
            return animation;
        } catch (IOException e) {
            e.printStackTrace();
            images = null;
            return null;
        }
    }

    @Override
    public void update(double deltaTime) {
        if(doUpdate){
            if(task == null) {
                task = new IdleTask(this);
            }
            if(direction != null) {
                if(task.isPlayerUsingObject()) {
                    animation.update(0, Facing.getFacing(direction));
                } else {
                    animation.update(deltaTime, Facing.getFacing(direction));
                }
            }
            task.update(deltaTime);
        }
    }

}
