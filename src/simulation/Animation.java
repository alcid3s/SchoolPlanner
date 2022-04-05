package simulation;

import data.persons.Facing;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Class Animation
 * Creates animations for animated objects
 */

public class Animation implements Serializable {

    private final HashMap<Facing, BufferedImage[]> images;
    private Facing currentFacing;
    private int animationCount;
    private double currentAnimationDelay;
    private final int animationPerState;

    /**
     * Constructor
     * @param animationPerState
     */

    public Animation(int animationPerState) {
        currentFacing = Facing.STATIONARY;
        images = new HashMap<>();
        animationCount = 0;
        currentAnimationDelay = 0;
        this.animationPerState = animationPerState;
    }

    /**
     * Method setFacing
     * Sets the direction facing per image
     * @param facing
     * @param list
     */

    public void setFacing(Facing facing, BufferedImage[] list) {
        images.put(facing, list);
    }

    /**
     * Method getImage
     * @return image of facing
     */

    public BufferedImage getImage() {
        return images.get(currentFacing)[animationCount];
    }

    /**
     * Method update
     * @param deltaTime to take to update
     * @param facing to change to
     */

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
