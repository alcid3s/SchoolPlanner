package data;

import callbacks.ClockCallback;
import callbacks.Updatable;
import javafx.scene.canvas.Canvas;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Clock implements Updatable {
    private int speed;
    private boolean check;
    private static LocalTime time;
    private final DateTimeFormatter formatter;
    private final ClockCallback callback;

    public Clock(ClockCallback callback) {
        check = false;
        resetTime();
        speed = 1;
        formatter = DateTimeFormatter.ofPattern("HH:mm");
        this.callback = callback;
    }

    public static LocalTime getTime() {
        return time;
    }

    public void draw(FXGraphics2D g2d, Canvas canvas) {
        AffineTransform transform = g2d.getTransform();
        g2d.setTransform(new AffineTransform());
        g2d.setColor(Color.YELLOW);
        g2d.drawString(formatter.format(time), (int) canvas.getWidth() - 80, 20);
        g2d.setTransform(transform);
    }

    public static void resetTime(){
        time = LocalTime.of(15, 50, 0);
    }

    @Override
    public void update(double deltaTime) {
        if (time.getHour() >= 7 && time.getHour() < 16 && !check) {
            speed = 1;
            check = true;
            callback.onBeginTime();
        } else if ((time.getHour() >= 16 || time.getHour() < 7) && check) {
            check = false;
            callback.onEndTime();
        } else if (time.getHour() >= 16 && time.getMinute() > 30) {
            speed = 10;
        }

        time = time.plusSeconds((int) (speed * deltaTime * 100));
    }
}

