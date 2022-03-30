package callbacks;

import data.Lesson;

public interface TimerCallback {
    void onEndOfClass(Lesson lesson);
}
