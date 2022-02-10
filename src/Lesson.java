import data.rooms.Room;
import data.persons.Teacher;

import java.time.LocalDateTime;

public class Lesson{

    private Room room;
    private Teacher teacher;
    private Group group;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Lesson(Room room, Teacher teacher, Group group, LocalDateTime startDate, LocalDateTime endDate){
        this.room = room;
        this.teacher = teacher;
        this.group = group;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
