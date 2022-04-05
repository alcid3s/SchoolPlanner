package managers;

import data.Group;
import data.Lesson;
import data.Schedule;
import data.persons.Person;
import data.rooms.Classroom;
import data.rooms.Room;

import java.time.LocalDateTime;

/**
 * Class Validation
 * Validator that does different checks for the ScheduleTab
 */

public class Validation {

    private static String message = "";

    /**
     * Static method lessonIsValid
     * Helper method that returns the value of different chekcs that apply to a lesson
     * @param lesson that needs to be checked
     * @return the result of the check
     */

    public static boolean lessonIsValid(Lesson lesson) {
        return timeIsValid(lesson.getStartDate(), lesson.getEndDate())
                && sizeIsValid(lesson.getRoom(), lesson.getGroup())
                && isClassRoom(lesson.getRoom());
    }

    /**
     * Static method sizeIsValid
     * Compares the size of a room to the size of a group
     * @param room that needs to be compared
     * @param group that needs to be compared
     * @return the result of the comparison
     */

    public static boolean sizeIsValid(Room room, Group group) {
        if (group.getSize() <= room.getCapacity()) {
            return true;
        }
        message = "Room is too small for this group";
        return false;
    }

    /**
     * Static method sizeIsValid
     * Compares the size of a room to the new given size of a group
     * @param group that gets a new size
     * @param newSize the new size of the group
     * @return the result of the comparison
     */

    public static boolean sizeIsValid(Group group, int newSize) {
        for (Lesson lesson : Schedule.getInstance().getLessonList()) {
            if (lesson.getGroup() == group) {
                if (newSize > lesson.getRoom().getCapacity()) {
                    message = "Room is too small for this group";
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Static method numberIsPositive
     * Checks if a number is positive
     * @param number to check
     * @return result of the check
     */

    public static boolean numberIsPositive(int number) {
        if (number > 0) {
            return true;
        }
        message = "Size must be higher than 0";
        return false;
    }

    /**
     * Static method groupIsUnique
     * Checks if the name of a group is original
     * @param groupName that needs to be checked
     * @return the result of the check
     */

    public static boolean groupIsUnique(String groupName) {
        for (Group group : Schedule.getInstance().getGroupList()) {
            if (group.getName().equals(groupName)) {
                message = "There already is a group with this name";
                return false;
            }
        }
        return true;
    }

    /**
     * Static method teacherIsUnique
     * Checks if the name of a teacher is original
     * @param teacherName that needs to be checked
     * @return the result of the check
     */

    public static boolean teacherIsUnique(String teacherName) {
        for (Person teacher : Schedule.getInstance().getTeacherList()) {
            if (teacher.getName().equals(teacherName)) {
                message = "There already is a teacher with this name";
                return false;
            }
        }
        return true;
    }

    /**
     * Static method nameIsValid
     * Checks if a name only contains letters
     * @param name to check
     * @return the result of the check
     */

    public static boolean nameIsValid(String name) {
        try {
            Integer.parseInt(name);
            message = "This name contains numbers";
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Static method teacherIsFree
     * Checks if the teacher is bound to a lesson
     * @param teacher to check
     * @return the result of the check
     */

    public static boolean teacherIsFree(Person teacher) {
        for (Lesson lesson : Schedule.getInstance().getLessonList()) {
            if (lesson.getTeacher().getName().equals(teacher.getName())) {
                message = "Teacher is bound to a lesson";
                return false;
            }
        }
        return true;
    }

    /**
     * Static method groupIsFree
     * Checks if a group is bound to a lesson
     * @param group to check
     * @return the result of the check
     */

    public static boolean groupIsFee(Group group) {
        for (Lesson lesson : Schedule.getInstance().getLessonList()) {
            if (lesson.getGroup().getName().equals(group.getName())) {
                message = "Group is bound to a lesson";
                return false;
            }
        }
        return true;
    }

    /**
     * Static method timeIsValid
     * Checks if the time fits in the bounds of the simulation time
     * @param startTime to check
     * @param endTime to check
     * @return the result of the check
     */

    public static boolean timeIsValid(LocalDateTime startTime, LocalDateTime endTime) {
        int startMinute = startTime.getMinute();
        int startHour = startTime.getHour();
        int endMinute = endTime.getMinute();
        int endHour = endTime.getHour();

        if ((startHour * 100) + startMinute >= (endHour * 100) + endMinute) {
            message = "Start time should be before end time";
        } else if (startHour < 8) {
            message = "First class starts at 8:00";
        } else if ((endHour == 16 && endMinute > 0) || endHour > 16) {
            message = "Last class ends at 16:00";
        } else {
            return true;
        }
        return false;
    }

    /**
     * Static method isClassRoom
     * Checks if the given room is a classroom
     * @param room that needs to be checked
     * @return the result of the check
     */

    public static boolean isClassRoom(Room room) {
        return room instanceof Classroom;
    }

    /**
     * Static method scheduleIsAvailable
     * Checks if attributes from a lesson fit in the schedule
     * @param startTime of the lesson
     * @param endTime of the lesson
     * @param teacher to check
     * @param room to check
     * @param group to check
     * @return the result of the check
     */

    public static boolean scheduleIsAvailable(LocalDateTime startTime, LocalDateTime endTime, Person teacher, Room room, Group group) {
        for (Lesson lesson : Schedule.getInstance().getLessonList()) {
            if (timeChecker(lesson, startTime, endTime)) {
                if (lesson.getTeacher().getName().equals(teacher.getName())) {
                    message = "Teacher is not available";
                    return false;
                } else if (lesson.getRoom().getName().equals(room.getName())) {
                    message = "Room is not available";
                    return false;
                } else if (lesson.getGroup().getName().equals(group.getName())) {
                    message = "Group is not available";
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Static method scheduleIsAvailable
     * Checks if a lesson fits in the current schedule
     * @param startTime of the lesson
     * @param endTime of the lesson
     * @param newLesson to ckeck
     * @return the result of the check
     */

    public static boolean scheduleIsAvailable(LocalDateTime startTime, LocalDateTime endTime, Lesson newLesson) {
        for (Lesson lesson : Schedule.getInstance().getLessonList()) {
            if (timeChecker(lesson, startTime, endTime) && lesson != newLesson) {
                if (newLesson.getTeacher().getName().equals(lesson.getTeacher().getName())) {
                    message = "Teacher is not available";
                    return false;
                } else if (newLesson.getRoom().getName().equals(lesson.getRoom().getName())) {
                    message = "Room is not available";
                    return false;
                } else if (newLesson.getGroup().getName().equals(lesson.getGroup().getName())) {
                    message = "Group is not available";
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Static method scheduleIsAvailable
     * Checks if an object of a lesson fits in the current schedule
     * @param startTime of the lesson
     * @param endTime of the lesson
     * @param object to check
     * @param previousObject to check
     * @return the result of the check
     */

    public static boolean scheduleIsAvailable(LocalDateTime startTime, LocalDateTime endTime, Object object, Object previousObject) {
        for (Lesson lesson : Schedule.getInstance().getLessonList()) {
            if (timeChecker(lesson, startTime, endTime)) {
                if (lesson.getTeacher().getName().equals(object.toString()) && !lesson.getTeacher().getName().equals(previousObject.toString())) {
                    message = "Teacher is not available";
                    return false;
                } else if (lesson.getRoom().getSystemName().equals(object.toString()) && !lesson.getRoom().getSystemName().equals(previousObject.toString())) {
                    message = "Room is not available";
                    return false;
                } else if (lesson.getGroup().getSystemName().equals(object.toString()) && !lesson.getGroup().getSystemName().equals(previousObject.toString())) {
                    message = "Group is not available";
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Private static method timeChecker
     * Checks if given times are valid
     * @param lesson to check
     * @param startTime to check
     * @param endTime to check
     * @return the result of the check
     */

    private static boolean timeChecker(Lesson lesson, LocalDateTime startTime, LocalDateTime endTime) {
        return (Util.timeInInt(startTime) >= Util.timeInInt(lesson.getStartDate()) && Util.timeInInt(startTime) <= Util.timeInInt(lesson.getEndDate()))
                || (Util.timeInInt(endTime) >= Util.timeInInt(lesson.getStartDate()) && Util.timeInInt(endTime) <= Util.timeInInt(lesson.getEndDate()))
                || (Util.timeInInt(startTime) <= Util.timeInInt(lesson.getStartDate()) && Util.timeInInt(endTime) >= Util.timeInInt(lesson.getEndDate()))
                || (Util.timeInInt(startTime) >= Util.timeInInt(lesson.getStartDate()) && Util.timeInInt(endTime) <= Util.timeInInt(lesson.getEndDate()));
    }

    /**
     * Static method getMessage
     * @return the message of the latest error
     */

    public static String getMessage() {
        String text = message;
        message = "";
        return text;
    }
}
