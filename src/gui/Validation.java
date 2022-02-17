package gui;

import data.Group;
import data.Lesson;
import data.Schedule;
import data.persons.Person;
import data.rooms.Classroom;
import data.rooms.Room;

import java.time.LocalDateTime;

public class Validation{

    private static String message = "";

    public static boolean lessonIsValid(Lesson lesson){
        if(sizeIsValid(lesson.getRoom(), lesson.getGroup())
                && timeIsValid(lesson.getStartDate(), lesson.getEndDate())
                && scheduleIsAvailable(lesson.getStartDate(), lesson.getEndDate(), lesson.getTeacher(), lesson.getRoom(), lesson.getGroup())
                && isClassRoom(lesson.getRoom())){
            return true;
        }else{
            return false;
        }
    }

    public static boolean sizeIsValid(Room room, Group group){
        if(group.getSize() <= room.getCapacity()){
            return true;
        }
        message = "Room is too small for this group";
        return false;
    }

    public static boolean teacherIsFree(Person teacher){
        for(Lesson lesson : Schedule.getInstance().getLessonList()){
            if(lesson.getTeacher().getName().equals(teacher.getName())){
                message = "Teacher is bound to a lesson";
                return false;
            }
        }
        return true;
    }

    public static boolean groupIsFee(Group group){
        for(Lesson lesson : Schedule.getInstance().getLessonList()){
            if(lesson.getGroup().getName().equals(group.getName())){
                message = "Group is bound to a lesson";
                return false;
            }
        }
        return true;
    }

    public static boolean timeIsValid(LocalDateTime startTime, LocalDateTime endTime){
        int startMinute = startTime.getMinute();
        int startHour = startTime.getHour();
        int endMinute = endTime.getMinute();
        int endHour = endTime.getHour();

        if((startHour * 100) + startMinute >= (endHour * 100) + endMinute){
            message = "Start time should be before end time";
        }else if(startHour == 8 && startMinute < 45){
            message = "First class starts at 8:45";
        }else if(endHour == 18 && endMinute > 0){
            message = "Last class ends at 18:00";
        }else if(endHour > 18 || startHour < 8){
            message = "Lesson out of bounds";
        }else{
            return true;
        }
        return false;
    }

    public static boolean isClassRoom(Room room){
        if(room instanceof Classroom){
            return true;
        }
        message = "Lesson must be given in a classroom";
        return false;
    }

    public static boolean scheduleIsAvailable(LocalDateTime startTime, LocalDateTime endTime, Person teacher, Room room, Group group){
        for(Lesson lesson : Schedule.getInstance().getLessonList()){
            if(timeChecker(lesson, startTime, endTime)){
                if(lesson.getTeacher().getName().equals(teacher.getName())){
                    message = "Teacher is not available";
                    return false;
                }else if(lesson.getRoom().getName().equals(room.getName())){
                    message = "Room is not available";
                    return false;
                }else if(lesson.getGroup().getName().equals(group.getName())){
                    message = "Group is not available";
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean scheduleIsAvailable(LocalDateTime startTime, LocalDateTime endTime, Lesson newLesson){
        for(Lesson lesson : Schedule.getInstance().getLessonList()){
            if(timeChecker(lesson, startTime, endTime) && lesson != newLesson){
                if(newLesson.getTeacher().getName().equals(lesson.getTeacher().getName())){
                    message = "Teacher is not available";
                    return false;
                }else if(newLesson.getRoom().getName().equals(lesson.getRoom().getName())){
                    message = "Room is not available";
                    return false;
                }else if(newLesson.getGroup().getName().equals(lesson.getGroup().getName())){
                    message = "Group is not available";
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean scheduleIsAvailable(LocalDateTime startTime, LocalDateTime endTime, Object object, Object previousObject){
        for(Lesson lesson : Schedule.getInstance().getLessonList()){
            if(timeChecker(lesson, startTime, endTime)){
                if(lesson.getTeacher().getName().equals(object.toString()) && !lesson.getTeacher().getName().equals(previousObject.toString())){
                    message = "Teacher is not available";
                    return false;
                }else if(lesson.getRoom().getSystemName().equals(object.toString()) && !lesson.getRoom().getSystemName().equals(previousObject.toString())){
                    message = "Room is not available";
                    return false;
                }else if(lesson.getGroup().getSystemName().equals(object.toString()) && !lesson.getGroup().getSystemName().equals(previousObject.toString())){
                    message = "Group is not available";
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean timeChecker(Lesson lesson, LocalDateTime startTime, LocalDateTime endTime){
        if((Util.timeInInt(startTime) >= Util.timeInInt(lesson.getStartDate()) && Util.timeInInt(startTime) <= Util.timeInInt(lesson.getEndDate()))
                || (Util.timeInInt(endTime) >= Util.timeInInt(lesson.getStartDate()) && Util.timeInInt(endTime) <= Util.timeInInt(lesson.getEndDate()))
                || (Util.timeInInt(startTime) <= Util.timeInInt(lesson.getStartDate()) && Util.timeInInt(endTime) >= Util.timeInInt(lesson.getEndDate()))
                || (Util.timeInInt(startTime) >= Util.timeInInt(lesson.getStartDate()) && Util.timeInInt(endTime) <= Util.timeInInt(lesson.getEndDate()))){
            return true;
        }
        return false;
    }

    public static String getMessage(){
        String text = message;
        message = "";
        return text;
    }
}
