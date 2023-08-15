package org.anelda.wizston.countdowntimer.preset;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.anelda.wizston.countdowntimer.model.Preset;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PresetListCell extends ListCell<Preset> {

    private final ImageView displayImage = new ImageView();
    private final Pane pane = new Pane();

    private final Label title;
    private final Label autoStart;
    private final Label minute;
    private final Label ends;
    private final ImageView editIconView;
    private final ImageView deleteIconView;
    private final Image editIcon;
    private final Image deleteIcon;
    private SimpleDateFormat df;

    public PresetListCell() {
        super();

        title = new Label();
        autoStart = new Label();
        minute = new Label();
        ends = new Label();
        editIconView = new ImageView();
        deleteIconView = new ImageView();
        editIcon = new Image("ellipsis.png");
        deleteIcon = new Image("2930405.png");

        df = new SimpleDateFormat("hh:mm aa"); // AM/PM format
    }


    /*view the image class to display the image*/

    @Override
    public void updateItem(Preset preset, boolean empty) {
        super.updateItem(preset, empty);


        pane.setStyle("-fx-border-radius: 0 0 13 13; -fx-border-color: #737373; -fx-border-width: 1; -fx-pref-height: 45;-fx-background-color: #181e24");


        displayImage.setPreserveRatio(true);
        displayImage.setFitWidth(40);
        if (preset == null && empty) {
            setText(null);
            setGraphic(null);
        } else {

            //hBox.setPadding(new Insets(5, 10, 0, 10));

            VBox vBox = new VBox();
            vBox.setStyle("-fx-spacing: 0; -fx-pref-width: 330;");

            title.setText(preset.title);
            title.setStyle("-fx-font-size: 13");
            vBox.getChildren().add(0, title);

            String autoStartText = preset.autoStart ? "Auto Start" : "Manuel Start";
            autoStart.setText(autoStartText);
            autoStart.setStyle("-fx-font-size: 10");
            vBox.getChildren().add(1, autoStart);


            VBox vBox2 = new VBox();
            vBox2.setStyle("-fx-spacing: 0; -fx-pref-width: 100;");

            String durationText = String.format("%s Minutes", preset.minute + (preset.hour * 60));
            minute.setText(durationText);
            minute.setStyle("-fx-font-size: 13");
            vBox2.getChildren().add(0, minute);

            Calendar now = Calendar.getInstance();
            now.add(Calendar.MINUTE, 30);

            String endsText = String.format("Ends at: %s", df.format(now.getTime()));
            ends.setText(endsText);
            ends.setStyle("-fx-font-size: 10");
            vBox2.getChildren().add(1, ends);

            deleteIconView.setImage(deleteIcon);
            deleteIconView.setFitWidth(23);
            deleteIconView.setFitHeight(23);
            deleteIconView.setOpacity(0.5);

            editIconView.setImage(editIcon);

            editIconView.setFitWidth(23);
            editIconView.setFitHeight(23);
            editIconView.setOpacity(0.5);

            editIconView.setOnMousePressed(e -> {
                // write code to change screen
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newPreset.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.getIcons().add(new Image("/logo.png"));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.UNIFIED);
                    stage.setResizable(false);
                    stage.initOwner(this.getScene().getWindow());
                    stage.setTitle("NEW PRESET");
                    stage.setScene(new Scene(root1));
                    stage.show();
                } catch (Exception er) {
                    er.printStackTrace();
                }
            });

            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setStyle("-fx-padding: 5 5 5 15;");
            hBox.getChildren().add(0, vBox);
            hBox.getChildren().add(1, vBox2);
            hBox.getChildren().add(2, editIconView);
            hBox.getChildren().add(3, deleteIconView);

            pane.getChildren().add(hBox);

//                    setText(name);
            setGraphic(pane);
        }
    }
}
