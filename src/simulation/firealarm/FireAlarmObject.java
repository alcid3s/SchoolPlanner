package simulation.firealarm;

import org.jfree.fx.FXGraphics2D;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class FireAlarmObject {
    private BufferedImage currentSprite;
    private int tileX;
    private int tileY;

    public FireAlarmObject(int tileX,int tileY) {
        this.tileX = tileX;
        this.tileY = tileY;
    }

    public void setCurrentSprite(BufferedImage image) {
        this.currentSprite = image;
    }

    public void draw(FXGraphics2D graphics2D) {
        if(this.currentSprite == null) {
            return;
        }
        AffineTransform tx = graphics2D.getTransform();
        tx.translate(tileX * 32, tileY * 32);
        graphics2D.drawImage(currentSprite,tx,null);
    }
}
