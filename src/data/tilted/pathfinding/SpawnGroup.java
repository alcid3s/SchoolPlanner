package data.tilted.pathfinding;

import data.Group;
import data.persons.Person;
import javafx.scene.canvas.Canvas;
import org.jfree.fx.FXGraphics2D;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class SpawnGroup {
    private Image[] persons = new BufferedImage[2];
    private BufferedImage image;
    private Group group;
    private FXGraphics2D graphics;
    private AffineTransform tx;

    public SpawnGroup(FXGraphics2D graphics) {
        this.graphics = graphics;
        this.tx = graphics.getTransform();
        setPerson();
    }

    public void setPerson() {
        try {
            this.image = ImageIO.read(getClass().getClassLoader().getResource("Person.png"));
            this.graphics.drawImage(this.image, this.tx, null);
            for (int i = 0; i <= 1; i++) {
                this.persons[i] = this.image.getSubimage(32 * i, 0, 32, 32);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void update(){

    }

    private void draw(){
        graphics.drawImage(this.persons[0], this.tx, null);
    }

    public void spawnGroup(Group group){

        for (Person student : group.getStudents()) {
            draw();
        }
    }
}
