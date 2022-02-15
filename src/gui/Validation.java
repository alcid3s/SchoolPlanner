package gui;

import data.Group;
import data.Lesson;
import data.Schedule;
import data.persons.Person;
import data.rooms.Room;
import data.rooms.Classroom;

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
        message = "Room is too small for this group\n";
        return false;
    }

    public static boolean timeIsValid(LocalDateTime startTime, LocalDateTime endTime){
        int startMinute = startTime.getMinute();
        int startHour = startTime.getHour();
        int endMinute = endTime.getMinute();
        int endHour = endTime.getHour();

        if((startHour*100) + startMinute >= (endHour*100) + endMinute){
            message = "Start time should be before end time\n";
        }else if(startHour == 8 && startMinute < 45){
            message = "First class starts at 8:45\n";
        }else if(endHour == 18 && endMinute > 0){
            message = "Last class ends at 18:00\n";
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
            int startTimeNewLesson = (startTime.getHour()*100) + startTime.getMinute();
            int startTimeOldLesson = (lesson.getStartDate().getHour()*100) + lesson.getStartDate().getMinute();
            int endTimeNewLesson = (endTime.getHour()*100) + endTime.getMinute();
            int endTimeOldLesson = (lesson.getEndDate().getHour()*100) + lesson.getEndDate().getMinute();

            if((startTimeNewLesson >= startTimeOldLesson && startTimeNewLesson <= endTimeOldLesson)
                    ||(endTimeNewLesson >= startTimeOldLesson && endTimeNewLesson <= endTimeOldLesson)
                    ||(startTimeNewLesson <= startTimeOldLesson && endTimeNewLesson >= endTimeOldLesson)
                    ||(startTimeNewLesson >= startTimeOldLesson && endTimeNewLesson <= endTimeOldLesson)){
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

    public static boolean scheduleIsAvailable(LocalDateTime startTime, LocalDateTime endTime, Object object, Object previousObject){
        for(Lesson lesson : Schedule.getInstance().getLessonList()){
            if((Util.timeInInt(startTime) >= Util.timeInInt(lesson.getStartDate()) && Util.timeInInt(startTime) <= Util.timeInInt(lesson.getEndDate()))
                    ||(Util.timeInInt(endTime) >= Util.timeInInt(lesson.getStartDate()) && Util.timeInInt(endTime) <= Util.timeInInt(lesson.getEndDate()))
                    ||(Util.timeInInt(startTime) <= Util.timeInInt(lesson.getStartDate()) && Util.timeInInt(endTime) >= Util.timeInInt(lesson.getEndDate()))
                    ||(Util.timeInInt(startTime) >= Util.timeInInt(lesson.getStartDate()) && Util.timeInInt(endTime) <= Util.timeInInt(lesson.getEndDate()))){
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

    public static String getMessage(){
        String text = message;
        message = "";
        return text;
    }
}
