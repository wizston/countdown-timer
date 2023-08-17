package org.anelda.wizston.countdowntimer.preset;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.anelda.wizston.countdowntimer.model.DataModel;
import org.anelda.wizston.countdowntimer.model.Preset;
import org.anelda.wizston.countdowntimer.model.dao.PresetDao;
import org.anelda.wizston.countdowntimer.preview.PreviewController;

public class PresetController {

    @FXML
    public Label labelTest2;

    @FXML
    public VBox presetListVBox;

    private final SimpleStringProperty hourValue = new SimpleStringProperty("5");

    private DataModel model;

    ObservableList<Preset> presets;

    public void initModel(DataModel model) throws Exception {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model ;
        hourValue.bind(this.model.getCurrentMoment().currentTimeValueProperty());
        labelTest2.textProperty().bind(hourValue);
        start();
    }

    public void start() {
        StackPane stackpane = new StackPane();

        presets = PresetDao.getPresets();

        ListView<Preset> listViewReference = new ListView<>(presets);
        listViewReference.setCellFactory(list -> new PresetListCell());
        listViewReference.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
        {
            if (newSelection != null) {
                this.model.getCurrentMoment().previewTimerTitleProperty().setValue(String.valueOf(newSelection.title));
                this.model.getCurrentMoment().previewTimerTitleProperty().setValue(String.valueOf(newSelection.title));
                this.model.getCurrentMoment().previewTimeHourProperty().setValue(newSelection.hour);
                this.model.getCurrentMoment().previewTimeMinProperty().setValue(newSelection.minute);
                this.model.getCurrentMoment().previewTimeSecProperty().setValue(newSelection.sec);
                setPreview();
            }
        });

        stackpane.getChildren().add(listViewReference);

        StackPane.setMargin(listViewReference, new Insets(0,8,8,8));
        presetListVBox.getChildren().add(stackpane);
        presetListVBox.setStyle("-fx-background-color: #181e24");
        presetListVBox.setFillWidth(true);
//        presetListVBox.setSpacing(5);
    }

    public void setPreview() {
        PreviewController.updatePreviewValues(model);
    }

    public void openNewPresetModal() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newPreset.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/logo.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNIFIED);
            stage.setResizable(false);
            stage.initOwner(presetListVBox.getScene().getWindow());
            stage.setTitle("NEW PRESET");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception er) {
            er.printStackTrace();
        }
    }


}
