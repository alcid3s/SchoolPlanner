package gui.popups;

import data.Lesson;
import data.Schedule;
import gui.Util;
import gui.tabs.ScheduleTab;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class EditLessonsPopup extends Stage {

    public EditLessonsPopup() {
        setTitle("Edit Lessons");
        Schedule schedule = Schedule.getInstance();
        ListView<Lesson> listView = new ListView<>();
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listView.getItems().addAll(schedule.getLessonList());
        BorderPane pane = new BorderPane();
        pane.setTop(listView);

        Button createLesson = Util.getDefaultButton("Create new lesson", 50, 150);
        createLesson.setOnAction(e -> {
            new CreateLessonPopup().show();
            close();
        });

        Button removeLesson = Util.getDefaultButton("Remove lesson", 50, 150);
        removeLesson.setOnAction(e -> {
            if (listView.getSelectionModel().getSelectedItems().size() > 0) {
                int selected = listView.getSelectionModel().getSelectedIndex();
                Lesson selectedLesson = listView.getItems().get(selected);
                if (selectedLesson != null) {
                    listView.getItems().remove(selected);
                    schedule.removeLesson(selectedLesson);
                    ScheduleTab.refreshCanvas();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Could not find this lesson").show();
                }
            }
        });

        Button editLesson = Util.getDefaultButton("Edit Lesson", 50, 150);
        editLesson.setOnAction(e -> {
            if (listView.getSelectionModel().getSelectedItems().size() > 0) {
                int selected = listView.getSelectionModel().getSelectedIndex();
                Lesson selectedLesson = listView.getItems().get(selected);
                if (selectedLesson != null) {
                    new EditLessonAttributesPopup(selectedLesson).show();
                    close();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Could not find this lesson").show();
                }
            }
        });

        Button close = Util.getDefaultButton("Close", 50, 150);
        close.setOnAction(e -> close());

        HBox box = new HBox(createLesson, removeLesson, editLesson, close);
        pane.setBottom(box);
        Scene scene = new Scene(pane);
        setScene(scene);
    }
}
