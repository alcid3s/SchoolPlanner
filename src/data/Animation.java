package data;

import data.persons.Facing;

import java.awt.image.BufferedImage;
import java.lang.management.BufferPoolMXBean;
import java.util.ArrayList;
import java.util.HashMap;

public class Animation {
    private final double animationDelay = 0.300; // Animation delay in seconds
    private HashMap<Facing, BufferedImage[]> images;
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
        images.put(facing,list);
    }

    public BufferedImage getImage() {
        return images.get(currentFacing)[animationCount];
    }

    public void update(double deltaTime, Facing facing) {
        if(!currentFacing.equals(facing)) {
            currentFacing = facing;
            animationCount = 0;
        } else {
            currentAnimationDelay += deltaTime;
            if(currentAnimationDelay > animationDelay) {
                currentAnimationDelay = 0;
                if(animationPerState - 1 <= animationCount) {
                    animationCount = 0;
                } else {
                    animationCount++;
                }
            }
        }
    }
}
