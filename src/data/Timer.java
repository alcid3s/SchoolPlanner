package data;

import callbacks.TimerCallback;
import callbacks.Timeable;

import java.time.LocalTime;

/**
 * Class Timer
 * Creates a timer that counts the time based on a begin time and the current time from CLock
 */

public class Timer implements Timeable {

    private final TimerCallback callback;
    private final LocalTime endTime;
    private final Lesson lesson;

    /**
     * Constructor
     * @param lesson that uses the timer to time the schedule with
     * @param callback to give when a certain requirement has been reached
     */

    public Timer(Lesson lesson, TimerCallback callback) {
        this.endTime = lesson.getEndDate().toLocalTime();
        this.callback = callback;
        this.lesson = lesson;
    }

    /**
     * Implemented method update
     * @param deltaTime to take to update
     */

    @Override
    public void update(double deltaTime) {
        if (Clock.getTime().getHour() == this.endTime.getHour() && Clock.getTime().getMinute() == this.endTime.getMinute()) {
            this.callback.onEndOfClass(lesson);
        }
    }
}
