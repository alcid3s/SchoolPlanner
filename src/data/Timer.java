package data;

import callbacks.TimerCallback;
import callbacks.Updatable;

import java.time.LocalTime;

public class Timer implements Updatable {

    @Override
    public void update(double deltaTime) {
        if (Clock.getTime().getHour() == this.endTime.getHour() && Clock.getTime().getMinute() == this.endTime.getMinute()) {
            this.callback.onEndOfClass(lesson);
        }
    }

    private final TimerCallback callback;
    private final LocalTime endTime;
    private final Lesson lesson;

    public Timer(Lesson lesson, TimerCallback callback) {
        this.endTime = lesson.getEndDate().toLocalTime();
        this.callback = callback;
        this.lesson = lesson;
    }
}
