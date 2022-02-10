package gui.popups;

import data.Group;
import javafx.stage.Stage;

public class EditGroupPopup extends Stage {
    private Group group;

    public EditGroupPopup(Group group) {
        this.group = group;
        setTitle("Edit Group");
    }
}
