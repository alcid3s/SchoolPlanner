package data.tilted.pathfinding;

import data.Group;
import data.persons.Person;
import javafx.scene.canvas.Canvas;
import org.jfree.fx.FXGraphics2D;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class SpawnGroup {
    private Image[] persons = new BufferedImage[2];
    private BufferedImage image;
    private Group group;
    private Canvas canvas;
    private AffineTransform tx;

    public SpawnGroup(Canvas canvas) {

    }

    public SpawnGroup(javafx.scene.canvas.Canvas canvas, AffineTransform tx) {
        this.canvas = canvas;
        this.tx = tx;
        setPerson();
    }

    public void setPerson() {
        for (int i = 0; i <= 1; i++) {
            this.persons[i] = this.image.getSubimage(32 * i, 0, 32, 32);
        }
    }

    private void update(){

    }

    private void draw(Person person){
        Graphics2D graphics = this.canvas.getGraphicsContext2D();
        graphics.drawImage(this.image, this.tx, null);
    }

    public void spawnGroup(Group group){
        this.group = group;

        for (Person student : group.getStudents()) {
            draw(student);
        }
    }
}
