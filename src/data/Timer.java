package data;

import java.time.LocalTime;

public class Timer implements Updatable{

    @Override
    public void update(double deltaTime) {
        this.currentTime = this.clock.getTime();
        if(this.currentTime.getHour() == this.endTime.getHour() && this.currentTime.getMinute() == this.endTime.getMinute()){
            this.callback.onEndOfClass(this);
        }
    }

    private TimerCallback callback;
    private LocalTime currentTime;
    private LocalTime endTime;
    private String name;
    private Clock clock;

    public Timer(String name, LocalTime endTime,TimerCallback callback, Clock clock){
        this.currentTime = clock.getTime();
        this.endTime = endTime;
        this.callback = callback;
        this.name = name;
        this.clock = clock;
    }

    public String getName(){
        return this.name;
    }
}
