package data.persons;

import data.Schedule;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

public abstract class Person implements Comparable, Serializable {
    private String name;
    private BufferedImage fullImage;
    private BufferedImage[] image = new BufferedImage[2];

    public Person(String name){
        this.name = name;
        try {
            this.fullImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("Person.png")));
            for (int i = 0; i < 2; i++) {
                this.image[i] = this.fullImage.getSubimage(32 * i, 0, 32, 32);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage[] getImage(){
        return image;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
        Schedule.getInstance().sort();
    }

    public abstract void draw(FXGraphics2D graphics);
    public abstract void update();

    @Override
    public String toString(){
        return this.name;
    }

    @Override
    public int compareTo(Object o){
        return this.toString().compareTo(o.toString());
    }

    public String getJsonString() {
        return name;
    }
}
