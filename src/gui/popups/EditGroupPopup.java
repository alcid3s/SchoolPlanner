package gui.popups;

import data.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditGroupPopup extends Stage {
    private Group group;

    public EditGroupPopup(Group group) {
        this.group = group;
        setTitle("Edit Group");
        Label name = new Label(" Name: ");
        Label size = new Label("Group Size: ");
        TextField nameField = new TextField();
        TextField sizeField = new TextField();

        VBox vBox = new VBox();
        vBox.getChildren().addAll(new HBox(name,nameField), new HBox(size,sizeField));
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(vBox);


        Scene scene = new Scene(borderPane);
        setScene(scene);

    }
}
