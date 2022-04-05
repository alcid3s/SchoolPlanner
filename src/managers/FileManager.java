package managers;

import data.Schedule;
import data.*;
import data.Lesson;
import data.persons.Teacher;
import gui.tabs.ScheduleTab;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Class FileManager
 * Handles JSON object streams
 */

public class FileManager implements Serializable {

    /**
     * Static method saveJson
     * @param file that needs to be saved
     * @param schedule root that gives objects that need to be saved
     * @return boolean that checks if saving was successful
     */

    public static boolean saveJson(File file, Schedule schedule) {
        try {
            file.delete();
            file.createNewFile();
            JsonArrayBuilder teacherList = Json.createArrayBuilder();
            JsonArrayBuilder groupList = Json.createArrayBuilder();
            JsonArrayBuilder lessonList = Json.createArrayBuilder();
            schedule.getTeacherList().forEach(e -> teacherList.add(e.getJsonString()));
            schedule.getGroupList().forEach(e -> groupList.add(e.getJsonString()));
            schedule.getLessonList().forEach(e -> lessonList.add(e.getJsonString()));

            Map<String, Object> properties = new HashMap<>(1);
            properties.put(JsonGenerator.PRETTY_PRINTING, true);
            JsonWriterFactory jf = Json.createWriterFactory(properties);
            JsonObject jsonObject = Json.createObjectBuilder()
                    .add("teachers", teacherList)
                    .add("groups", groupList)
                    .add("lessons", lessonList)
                    .build();
            FileWriter fileWriter = new FileWriter(file);
            JsonWriter jsonWriter = jf.createWriter(fileWriter);
            jsonWriter.writeObject(jsonObject);
            jsonWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Static method loadJson
     * @param file that needs to be loaded
     * @param schedule root that receives objects that are loaded in
     * @return boolean that checks if loading was successful
     */

    public static boolean loadJson(File file, Schedule schedule) {
        try {
            InputStream input = new FileInputStream(file);
            JsonReader jsonReader = Json.createReader(input);
            JsonObject jsonObject = jsonReader.readObject();
            schedule.clearAllLists();
            jsonObject.getJsonArray("teachers").forEach(teacher -> schedule.addTeacher(new Teacher(teacher.toString().replaceAll("\"", ""))));
            jsonObject.getJsonArray("groups").forEach(group -> schedule.addGroup(new Group(group.toString().replaceAll("\"", ""))));
            jsonObject.getJsonArray("lessons").forEach(lesson -> schedule.addLesson(new Lesson(lesson.toString().replaceAll("\"", ""))));
            ScheduleTab.refreshCanvas();
            Clock.resetTime();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
