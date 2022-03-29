package gui;

import data.Group;
import data.Lesson;
import data.Schedule;
import data.persons.Person;
import data.rooms.Classroom;
import data.rooms.Room;

import java.time.LocalDateTime;

public class Validation {

    private static String message = "";

    public static boolean lessonIsValid(Lesson lesson) {
        return timeIsValid(lesson.getStartDate(), lesson.getEndDate())
                && sizeIsValid(lesson.getRoom(), lesson.getGroup())
                && isClassRoom(lesson.getRoom());
    }

    public static boolean sizeIsValid(Room room, Group group) {
        if (group.getSize() <= room.getCapacity()) {
            return true;
        }
        message = "Room is too small for this group";
        return false;
    }

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

    public static boolean numberIsPositive(int number) {
        if (number > 0) {
            return true;
        }
        message = "Size must be higher than 0";
        return false;
    }

    public static boolean groupIsUnique(String groupName) {
        for (Group group : Schedule.getInstance().getGroupList()) {
            if (group.getName().equals(groupName)) {
                message = "There already is a group with this name";
                return false;
            }
        }
        return true;
    }

    public static boolean teacherIsUnique(String teacherName) {
        for (Person teacher : Schedule.getInstance().getTeacherList()) {
            if (teacher.getName().equals(teacherName)) {
                message = "There already is a teacher with this name";
                return false;
            }
        }
        return true;
    }

    public static boolean nameIsValid(String name) {
        try{
            Integer.parseInt(name);
            message = "This name contains numbers";
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public static boolean teacherIsFree(Person teacher) {
        for (Lesson lesson : Schedule.getInstance().getLessonList()) {
            if (lesson.getTeacher().getName().equals(teacher.getName())) {
                message = "Teacher is bound to a lesson";
                return false;
            }
        }
        return true;
    }

    public static boolean groupIsFee(Group group) {
        for (Lesson lesson : Schedule.getInstance().getLessonList()) {
            if (lesson.getGroup().getName().equals(group.getName())) {
                message = "Group is bound to a lesson";
                return false;
            }
        }
        return true;
    }

    public static boolean timeIsValid(LocalDateTime startTime, LocalDateTime endTime) {
        int startMinute = startTime.getMinute();
        int startHour = startTime.getHour();
        int endMinute = endTime.getMinute();
        int endHour = endTime.getHour();

        if ((startHour * 100) + startMinute >= (endHour * 100) + endMinute) {
            message = "Start time should be before end time";
        } else if (startHour < 8) {
            message = "First class starts at 8:00";
        } else if ((endHour == 16 && endMinute > 0)||endHour > 16) {
            message = "Last class ends at 16:00";
        } else {
            return true;
        }
        return false;
    }

    public static boolean isClassRoom(Room room) {
        return room instanceof Classroom;
    }

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

    private static boolean timeChecker(Lesson lesson, LocalDateTime startTime, LocalDateTime endTime) {
        return (Util.timeInInt(startTime) >= Util.timeInInt(lesson.getStartDate()) && Util.timeInInt(startTime) <= Util.timeInInt(lesson.getEndDate()))
                || (Util.timeInInt(endTime) >= Util.timeInInt(lesson.getStartDate()) && Util.timeInInt(endTime) <= Util.timeInInt(lesson.getEndDate()))
                || (Util.timeInInt(startTime) <= Util.timeInInt(lesson.getStartDate()) && Util.timeInInt(endTime) >= Util.timeInInt(lesson.getEndDate()))
                || (Util.timeInInt(startTime) >= Util.timeInInt(lesson.getStartDate()) && Util.timeInInt(endTime) <= Util.timeInInt(lesson.getEndDate()));
    }

    public static String getMessage() {
        String text = message;
        message = "";
        return text;
    }
}
