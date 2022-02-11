package gui;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Validation{

        public static boolean sizeIsValid(int capacity, int size){
        if (size > capacity ){
            return false;
        }else{
            return true;
        }
    }

    public static boolean timeIsValid(LocalTime startTime, LocalTime endTime){
        int startMinute = startTime.getMinute();
        int startHour = startTime.getHour();
        int endMinute = endTime.getMinute();
        int endHour = endTime.getHour();
        if ((startHour*100) + startMinute > (endHour*100) + endMinute){
            return false;
        }if (startHour > 23||startHour < 0||endHour > 23||endHour < 0){
            return false;
        }if (startMinute > 59||startMinute < 0||endMinute > 59||endMinute < 0){
            return false;
        }
        return true;
    }

    
}
