package data;

import callbacks.ClockCallback;
import callbacks.Timeable;
import javafx.scene.canvas.Canvas;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Class Clock
 * Creates a clock that keeps track of the time
 */

public class Clock implements Timeable {
    private int speed;
    private boolean check;
    private static LocalTime time;
    private final DateTimeFormatter formatter;
    private final ClockCallback callback;

    /**
     * Constructor
     * @param callback to give when a certain requirement has been reached
     */

    public Clock(ClockCallback callback) {
        check = false;
        resetTime();
        speed = 1;
        formatter = DateTimeFormatter.ofPattern("HH:mm");
        this.callback = callback;
    }

    /**
     * Static method GetTime
     * @return current time
     */

    public static LocalTime getTime() {
        return time;
    }

    /**
     * Method draw
     * @param g2d graphical context that needs to draw the clock
     * @param canvas where the clock needs to be drawn on
     */

    public void draw(FXGraphics2D g2d, Canvas canvas) {
        AffineTransform transform = g2d.getTransform();
        g2d.setTransform(new AffineTransform());
        g2d.setColor(Color.YELLOW);
        g2d.drawString(formatter.format(time), (int) canvas.getWidth() - 80, 20);
        g2d.setTransform(transform);
    }

    /**
     * Static method resetTime
     * Method that resets the time of the clock
     */

    public static void resetTime(){
        time = LocalTime.of(6, 59, 0);
    }

    /**
     * Implemented method update
     * @param deltaTime to take to update
     */

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

