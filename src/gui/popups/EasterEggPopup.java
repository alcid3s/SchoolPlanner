package gui.popups;

import gui.Util;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;

public class EasterEggPopup extends Stage{

    public EasterEggPopup(String path){
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setAutoPlay(true);
        MediaView view = new MediaView(player);
        int xScale = (int) (1920 / 1.5);
        int yScale = (int) (1080 / 1.5);
        view.setFitWidth(xScale);
        view.setFitHeight(yScale);
        Group root = new Group();
        root.getChildren().add(view);

        Button close = Util.getDefaultButton("Close", 25, xScale);
        close.setOnAction(e -> {
            player.stop();
            new EditTeachersPopup().show();
            close();
        });

        HBox box = new HBox(close);

        BorderPane pane = new BorderPane();
        pane.setTop(root);
        pane.setBottom(box);

        Scene scene = new Scene(pane);
        setTitle("Playing video");
        setScene(scene);
    }
}
