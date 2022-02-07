package org.anelda.isakacountdowntimer.preset;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.anelda.isakacountdowntimer.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PresetListCell extends ListCell<Preset> {

    private final ImageView displayImage = new ImageView();
    private final Pane pane = new Pane();

    public PresetListCell() {

    }


    /*view the image class to display the image*/

    @Override
    public void updateItem(Preset preset, boolean empty) {
        super.updateItem(preset, empty);


        pane.setStyle("-fx-border-radius: 0 0 13 13; -fx-border-color: #737373; -fx-border-width: 1; -fx-padding: 10 5 0 5; -fx-pref-height: 45;-fx-background-color: #181e24");


        displayImage.setPreserveRatio(true);
        displayImage.setFitWidth(40);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {

            HBox hBox = new HBox();
            hBox.setPadding(new Insets(5, 10, 0, 10));

            VBox vBox = new VBox();
            vBox.setStyle("-fx-spacing: 0; -fx-pref-width: 350");

            Label title = new Label(preset.title);
            title.setStyle("-fx-font-size: 13");
            vBox.getChildren().add(0, title);

            String AutoStartText = preset.autoStart ? "Auto Start" : "Manuel Start";
            Label autoStart = new Label("Auto Start");
            autoStart.setStyle("-fx-font-size: 10");
            vBox.getChildren().add(1, autoStart);


            VBox vBox2 = new VBox();
            vBox2.setStyle("-fx-spacing: 0; -fx-pref-width: 100");

            String durationText = String.format("%s Minutes", preset.durationInMinute);
            Label minute = new Label(durationText);
            minute.setStyle("-fx-font-size: 13");
            vBox2.getChildren().add(0, minute);

            Calendar now = Calendar.getInstance();
            now.add(Calendar.MINUTE, 30);
            // AM/PM format
            SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");

            String endsText = String.format("Ends at: %s", df.format(now.getTime()));
            Label ends = new Label(endsText);
            ends.setStyle("-fx-font-size: 10");
            vBox2.getChildren().add(1, ends);

            hBox.getChildren().add(0, vBox);
            hBox.getChildren().add(1, vBox2);
            hBox.getChildren().add(2, new ImageView(new Image("editPreset.png")));

            pane.getChildren().add(hBox);

//                    setText(name);
            setGraphic(pane);
        }
    }
}
