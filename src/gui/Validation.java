package gui;

public class Validation{

    public static boolean timeIsValid(String startTime, String endTime){
        if(startTime.length() >= 4 && startTime.length() <= 5 && endTime.length() >= 4 && endTime.length() <= 5){
            if(Util.getHour(startTime) < 0){
                System.out.println("yes");
            }
        }
        return false;
    }
}
