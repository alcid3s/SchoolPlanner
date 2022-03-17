package data.persons;

import org.jfree.fx.FXGraphics2D;

public class Teacher extends Person {

    public Teacher(String name){
        super(name);
    }

    @Override
    public void draw(FXGraphics2D graphics) {
        graphics.drawImage(getImage()[1], graphics.getTransform(), null);
    }

    @Override
    public void update() {

    }
}
