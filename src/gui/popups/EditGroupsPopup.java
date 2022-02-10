package gui.popups;

import data.Group;
import data.Schedule;
import data.persons.Teacher;
import gui.Util;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class EditGroupsPopup extends Stage {

    public EditGroupsPopup() {
        setTitle("Edit groups");
        Schedule schedule = Schedule.getInstance();
        ListView listView = new ListView();
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        ArrayList<String> groupsShow = new ArrayList<>();
        schedule.getGroupList().forEach(g -> groupsShow.add(g.toString()));
        listView.getItems().addAll(groupsShow);
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(listView);

        Button create = Util.getDefaultButton("Create group", 50, 100);
        Button remove = Util.getDefaultButton("Remove group", 50, 100);
        Button edit = Util.getDefaultButton("Edit group", 50, 100);

        borderPane.setBottom(new HBox(create, remove, edit));
        create.setOnAction(e -> {
            new CreateGroupPopup().show();
            close();
        });
        remove.setOnAction(e -> {
            if(listView.getSelectionModel().getSelectedItems().size() > 0) {
                int selected = listView.getSelectionModel().getSelectedIndex();
                String teacherName = (String) listView.getItems().get(selected);
                Group selectedGroup = schedule.getGroup(teacherName);
                if(selectedGroup != null) {
                    listView.getItems().remove(selected);
                    schedule.removeGroup(selectedGroup);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Could not find group").show();
                }
            }
        });
        edit.setOnAction(e -> {

        });
    }

}
