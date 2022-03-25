package data;

import javafx.scene.canvas.Canvas;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Clock {
    private int speed;
    private LocalTime time;
    private DateTimeFormatter formatter;

    public Clock() {
        time = LocalTime.of(8, 0,0);
        speed = 1;
        formatter = DateTimeFormatter.ofPattern("HH:mm");

    }


    public void draw(FXGraphics2D g2d, Canvas canvas) {
        AffineTransform transform = g2d.getTransform();
        g2d.setTransform(new AffineTransform());
        g2d.setColor(Color.YELLOW);
        g2d.drawString(formatter.format(time), (int) canvas.getWidth()-80, 20);
        g2d.setTransform(transform);
    }

    public void update(double deltaTime) {
        time = time.plusSeconds((int) (speed * deltaTime * 150));

    }


}
