package callbacks;

import data.Lesson;

/**
 * Interface TimerCallback
 * Handles callback for class Timer
 */

public interface TimerCallback {
    void onEndOfClass(Lesson lesson);
}
