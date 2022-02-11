package org.anelda.wizston.countdowntimer.preset;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.anelda.wizston.countdowntimer.HelloApplication;
import org.anelda.wizston.countdowntimer.database.DatabaseConnection;
import org.anelda.wizston.countdowntimer.model.DataModel;
import org.anelda.wizston.countdowntimer.output.OutputWrapperController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PresetController {

    @FXML
    public Label labelTest2;

    @FXML
    public VBox presetListVBox;

    private final SimpleStringProperty hourValue = new SimpleStringProperty("5");

    private DataModel model;


    public void initModel(DataModel model) throws Exception {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model ;

        externalDisplay();

        hourValue.bind(this.model.getCurrentMoment().currentTimeValueProperty());
        labelTest2.textProperty().bind(hourValue);
        starta();
    }


    public void externalDisplay() throws IOException {
        externalDisplay(false);
    }

    public void externalDisplay(Boolean fullScreen) throws IOException {

        FXMLLoader mainOutputLoader = new FXMLLoader(HelloApplication.class.getResource("output/outputWrapper.fxml"));
//        mainOutputLoader.setController(new OutputWrapperController());

        OutputWrapperController outputWrapperController = new OutputWrapperController();
        Callback<Class<?>, Object> controllerFactory = type -> outputWrapperController;

        mainOutputLoader.setControllerFactory(controllerFactory);

        AnchorPane mainOutputNode = mainOutputLoader.load();

        AnchorPane.setTopAnchor(mainOutputNode, 0.0);
        AnchorPane.setRightAnchor(mainOutputNode, 0.0);
        AnchorPane.setLeftAnchor(mainOutputNode, 0.0);
        AnchorPane.setBottomAnchor(mainOutputNode, 0.0);

        OutputWrapperController mainOutputController = mainOutputLoader.getController();
        mainOutputController.initModel(this.model);

        mainOutputNode.widthProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth)
            {
                mainOutputController.fontTracking.set(Font.font(newWidth.doubleValue() / 5));
            }
        });

        Stage primaryStage2 = new Stage();
        Scene scene2 = new Scene(mainOutputNode);

        primaryStage2.setScene(scene2);
        primaryStage2.setTitle("Fullscreen - Wizston:Countdown Timer 0.1");

        if (fullScreen) {
            primaryStage2.setFullScreen(true);
            primaryStage2.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        }

        primaryStage2.show();
    }



    public void starta() throws Exception {

        StackPane stackpane = new StackPane();

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getDatabaseConnection();

        String presetQuery = "SELECT id, title, durationInMinutes, autoStart from presets";

        Statement statement = connection.createStatement();
        ResultSet presets = statement.executeQuery(presetQuery);

        ObservableList<Preset> data = FXCollections.observableArrayList();

        while (presets.next()) {
            Integer id = presets.getInt("id");
            String title = presets.getString("title");
            Integer durationInMinutes = presets.getInt("durationInMinutes");
            Boolean autoStart = presets.getBoolean("autoStart");

            data.add(new Preset(id, title, durationInMinutes, autoStart));
        }

        /* create list object */
        ListView<Preset> listViewReference = new ListView<>(data);

        listViewReference.setCellFactory(new Callback<ListView<Preset>, ListCell<Preset>>() {
            @Override
            public ListCell<Preset> call(ListView<Preset> list) {
                return new PresetListCell();
            }
        });
        stackpane.getChildren().add(listViewReference);

        // Create an 8 pixel margin around a listview in the stackpane
        StackPane.setMargin(listViewReference, new Insets(8,8,8,8));
        /* creating vertical box to add item objects */
        presetListVBox.getChildren().add(stackpane);
        presetListVBox.setStyle("-fx-background-color: #181e24");
        presetListVBox.setFillWidth(true);
        presetListVBox.setSpacing(40);
    }
}
