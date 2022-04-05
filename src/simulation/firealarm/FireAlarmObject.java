package simulation.firealarm;

import org.jfree.fx.FXGraphics2D;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Class FireAlarmObject
 * Creates objects related to the FireAlarm
 */

public class FireAlarmObject {

    private BufferedImage currentSprite;
    private final int tileX;
    private final int tileY;

    /**
     * Constructor
     * @param tileX x-position of the object
     * @param tileY y-position of the object
     */

    public FireAlarmObject(int tileX,int tileY) {
        this.tileX = tileX;
        this.tileY = tileY;
    }

    /**
     * Method setCurrentSprite
     * @param image to set to
     */

    public void setCurrentSprite(BufferedImage image) {
        this.currentSprite = image;
    }

    /**
     * Method draw
     * Draws the object on the canvas
     * @param graphics2D graphical context that needs to draw
     */

    public void draw(FXGraphics2D graphics2D) {
        if(this.currentSprite == null) {
            return;
        }
        AffineTransform tx = graphics2D.getTransform();
        tx.translate(tileX * 32, tileY * 32);
        graphics2D.drawImage(currentSprite,tx,null);
    }
}
