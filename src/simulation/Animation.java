package simulation;

import data.persons.Facing;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Animation {
    private final HashMap<Facing, BufferedImage[]> images;
    private Facing currentFacing;
    private int animationCount;
    private double currentAnimationDelay;
    private final int animationPerState;

    public Animation(int animationPerState) {
        currentFacing = Facing.STATIONARY;
        images = new HashMap<>();
        animationCount = 0;
        currentAnimationDelay = 0;
        this.animationPerState = animationPerState;
    }

    public void setFacing(Facing facing, BufferedImage[] list) {
        images.put(facing, list);
    }

    public BufferedImage getImage() {
        return images.get(currentFacing)[animationCount];
    }

    public void update(double deltaTime, Facing facing) {
        if (currentFacing != facing && facing != null) {
            currentFacing = facing;
            animationCount = 0;
        } else {
            currentAnimationDelay += deltaTime;
            double animationDelay = 0.300;
            if (currentAnimationDelay > animationDelay) {
                currentAnimationDelay = 0;
                if (animationPerState - 1 <= animationCount) {
                    animationCount = 0;
                } else {
                    animationCount++;
                }
            }
        }
    }
}
