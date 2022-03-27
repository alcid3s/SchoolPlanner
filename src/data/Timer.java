package data;

import java.time.LocalTime;

public class Timer implements Updatable{

    @Override
    public void update(double deltaTime) {
        if(Clock.getTime().getHour() == this.endTime.getHour() && Clock.getTime().getMinute() == this.endTime.getMinute()){
            this.callback.onEndOfClass(lesson);
        }
    }

    private TimerCallback callback;
    private LocalTime endTime;
    private Lesson lesson;

    public Timer(Lesson lesson, TimerCallback callback){
        this.endTime = lesson.getEndDate().toLocalTime();
        this.callback = callback;
        this.lesson = lesson;
    }
}
