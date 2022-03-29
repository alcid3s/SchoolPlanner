package data.persons;

import data.Animation;
import tasks.IdleTask;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class Student extends Person {
    private static Random random = new Random();
    private static ArrayList<HashMap<Facing, BufferedImage[]>> images;

    public Student(String name) {
        super(name, getAnimation());
    }

    private static Animation getAnimation() {
        Animation animation = new Animation(3);
        if(images == null) {
            images = new ArrayList<>();
            final int size = 32;
            ArrayList<Facing> faces = new ArrayList<>(Arrays.asList(Facing.SOUTH, Facing.WEST, Facing.EAST, Facing.NORTH));

            for (int i = 0; i < 4; i++) {
                HashMap<Facing, BufferedImage[]> temp = new HashMap<>();

                try {
                    BufferedImage totalImage = ImageIO.read(Objects.requireNonNull(Student.class.getClassLoader().getResource("NPCs.png")));
                    for (int y = 0; y < 4; y++) {
                        BufferedImage[] sprites = new BufferedImage[3];
                        for (int x = 0; x < 3; x++) {
                            sprites[x] = totalImage.getSubimage(x * size + (i * (32 * 3)), y * size, size, size);
                        }

                        animation.setFacing(faces.get(y), sprites);
                        temp.put(faces.get(y), sprites);
                        if (faces.get(y).equals(Facing.SOUTH)) {
                            animation.setFacing(Facing.STATIONARY, sprites);
                            temp.put(Facing.STATIONARY, sprites);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                images.add(temp);
            }
        }
        images.get(random.nextInt(images.size())).forEach(animation::setFacing);
        return animation;
    }

    @Override
    public void update(double deltaTime) {
        if (doUpdate) {
            if (task == null) {
                task = new IdleTask(this);
            }
            if (direction != null) {
                if (task.isPlayerUsingObject()) {
                    animation.update(0, Facing.getFacing(direction));
                } else {
                    animation.update(deltaTime, Facing.getFacing(direction));
                }
            }
            task.update(deltaTime);
        }
    }

}
