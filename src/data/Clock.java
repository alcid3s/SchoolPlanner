package data;

import javafx.scene.canvas.Canvas;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Clock implements Updatable{
    private int speed;
    private boolean check;
    private LocalTime time;
    private DateTimeFormatter formatter;
    private ClockCallback callback;

    public Clock(ClockCallback callback){
        check = true;
        time = LocalTime.of(5, 45,0);
        speed = 1;
        formatter = DateTimeFormatter.ofPattern("HH:mm");
        this.callback = callback;
    }

    public LocalTime getTime(){
        return time;
    }

    public void draw(FXGraphics2D g2d, Canvas canvas) {
        AffineTransform transform = g2d.getTransform();
        g2d.setTransform(new AffineTransform());
        g2d.setColor(Color.YELLOW);
        g2d.drawString(formatter.format(time), (int) canvas.getWidth()-80, 20);
        g2d.setTransform(transform);
    }

    @Override
    public void update(double deltaTime) {
        if(time.getHour() >= 8 && time.getHour() < 16 && !check){
            check = true;
            callback.onBeginTime();
        }else if((time.getHour() >= 16 || time.getHour() < 8) && check){
            check = false;
            callback.onEndTime();
        }

        time = time.plusSeconds((int) (speed * deltaTime * 150));
    }
}

