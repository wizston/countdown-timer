package org.anelda.wizston.countdowntimer.preset;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.anelda.wizston.countdowntimer.model.Preset;
import org.anelda.wizston.countdowntimer.model.dao.PresetDao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PresetListCell extends ListCell<Preset> {
    private final Pane pane = new Pane();
    private final Label title;
    private final Label autoStart;
    private final Label minute;
    private final Label ends;
    private final ImageView showIconView, editIconView, deleteIconView;
    private final Image editIcon, showIcon, deleteIcon;
    private Button editButton, deleteButton;
    private SimpleDateFormat df;

    public PresetListCell() {
        super();

        title = new Label();
        autoStart = new Label();
        minute = new Label();
        ends = new Label();
        showIconView = new ImageView();
        editIconView = new ImageView();
        deleteIconView = new ImageView();
        showIcon = new Image("show-preset.png");
        editIcon = new Image("edit-preset.png");
        deleteIcon = new Image("cancel.png");


        deleteIconView.setImage(deleteIcon);
        deleteIconView.setFitWidth(20);
        deleteIconView.setFitHeight(20);
        deleteIconView.setOpacity(0.5);
        deleteButton = new Button();
        deleteButton.setStyle("-fx-padding: 0; -fx-background-color: rgba(255,255,255,0)");
        deleteButton.setGraphic(deleteIconView);
        deleteButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete this item?", ButtonType.YES, ButtonType.NO);
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                try {
                    PresetDao.deletePreset(this.getItem().id);
                    PresetDao.updatePresetList();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        showIconView.setImage(showIcon);
        showIconView.setFitWidth(23);
        showIconView.setFitHeight(23);
        showIconView.setOpacity(1);


        editIconView.setImage(editIcon);
        editIconView.setViewOrder(898);

        editIconView.setFitWidth(23);
        editIconView.setFitHeight(23);
        editIconView.setOpacity(1);


        editButton = new Button();
        editButton.setStyle("-fx-background-color: rgba(255,255,255,0)");
        editButton.setGraphic(editIconView);

        editButton.setOnMousePressed(e -> {
            // write code to change screen
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newPreset.fxml"));

                Parent root1 = (Parent) fxmlLoader.load();

                NewPreviewController newPreviewController = fxmlLoader.getController();
                newPreviewController.newPresetTitleTxt.setText(this.getItem().title);
                newPreviewController.newPresetHourTxt.setText(String.valueOf(this.getItem().hour));
                newPreviewController.newPresetMinTxt.setText(String.valueOf(this.getItem().minute));
                newPreviewController.newPresetSecTxt.setText(String.valueOf(this.getItem().sec));

                newPreviewController.saveNewPresetModalBtn.setText("UPDATE PRESET");
                newPreviewController.saveNewPresetModalBtn.setOnAction(event -> {

                    if (!newPreviewController.validateFields()) { return; }
                    try {
                        System.out.println("Updating...");
                        PresetDao.updatePreset(this.getItem().id,
                                newPreviewController.newPresetTitleTxt.getText(),
                                newPreviewController.newPresetHourTxt.getText(),
                                newPreviewController.newPresetMinTxt.getText(),
                                newPreviewController.newPresetSecTxt.getText());

                        PresetDao.updatePresetList();
                    } catch (SQLException er) {
                        throw new RuntimeException(er);
                    }


                    //Close modal
                    Stage stage = (Stage) newPreviewController.closeNewPresetModalBtn.getScene().getWindow();
                    stage.close();

                });

                Stage stage = new Stage();
                stage.getIcons().add(new Image("/logo.png"));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNIFIED);
                stage.setResizable(false);
                stage.initOwner(this.getScene().getWindow());
                stage.setTitle("Edit PRESET");
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (Exception er) {
                er.printStackTrace();
            }
        });

        df = new SimpleDateFormat("hh:mm aa"); // AM/PM format
    }


    /*view the image class to display the image*/

    @Override
    public void updateItem(Preset preset, boolean empty) {
        super.updateItem(preset, empty);


        pane.setStyle("-fx-border-radius: 0 0 13 13; -fx-border-color: #737373; -fx-border-width: 1; -fx-pref-height: 45;-fx-background-color: #181e24");


//        displayImage.setPreserveRatio(true);
//        displayImage.setFitWidth(40);
        if (preset == null && empty) {
            setText(null);
            setGraphic(null);
        } else {

            //hBox.setPadding(new Insets(5, 10, 0, 10));

            VBox vBox = new VBox();
            vBox.setStyle("-fx-spacing: 0; -fx-pref-width: 285;");

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

            HBox hBox = new HBox();
            hBox.setSpacing(3);
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setStyle("-fx-padding: 5 5 5 5;");
            hBox.getChildren().add(0, vBox);
            hBox.getChildren().add(1, vBox2);
            hBox.getChildren().add(2, showIconView);
            hBox.getChildren().add(3, editButton);
            hBox.getChildren().add(4, deleteButton);

            pane.getChildren().add(hBox);

//                    setText(name);
            setGraphic(pane);
        }
    }
}
