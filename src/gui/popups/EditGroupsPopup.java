package gui.popups;

import data.Group;
import data.Schedule;
import gui.Util;
import gui.Validation;
import gui.tabs.ScheduleTab;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class EditGroupsPopup extends Stage {

    /**
     * This is the main popup to edit groups, remove a group, create a new group or edit a group
     */
    public EditGroupsPopup() {
        setTitle("Edit groups");
        Schedule schedule = Schedule.getInstance();
        ListView<Group> listView = new ListView<Group>();
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        for (Group group : schedule.getGroupList()) {
            listView.getItems().add(group);
        }
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(listView);

        Button create = Util.getDefaultButton("Create group", 50, 100);
        Button remove = Util.getDefaultButton("Remove group", 50, 100);
        Button edit = Util.getDefaultButton("Edit group", 50, 100);
        Button close = Util.getDefaultButton("Close", 50, 100);

        close.setOnAction(e -> close());

        borderPane.setBottom(new HBox(create, remove, edit, close));
        create.setOnAction(e -> {
            new CreateGroupPopup().show();
            close();
        });

        remove.setOnAction(e -> {
            if (listView.getSelectionModel().getSelectedItems().size() > 0) {
                int selected = listView.getSelectionModel().getSelectedIndex();
                Group selectedGroup = listView.getItems().get(selected);
                if (selectedGroup != null) {
                    if (Validation.groupIsFee(selectedGroup)) {
                        listView.getItems().remove(selected);
                        schedule.removeGroup(selectedGroup);
                        ScheduleTab.refreshCanvas();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Could not delete group because it is in use. (" + selectedGroup + ")").show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Could not find group").show();
                }
            }
        });

        edit.setOnAction(e -> {
            if (listView.getSelectionModel().getSelectedItems().size() > 0) {
                int selected = listView.getSelectionModel().getSelectedIndex();
                Group selectedGroup = listView.getItems().get(selected);
                if (selectedGroup != null) {
                    new EditGroupAttributesPopup(selectedGroup).show();
                    close();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Could not find group.").show();
                }
            }
        });
        Scene scene = new Scene(borderPane);
        setScene(scene);
    }


}
