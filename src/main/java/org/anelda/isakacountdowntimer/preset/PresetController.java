package org.anelda.isakacountdowntimer.preset;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.anelda.isakacountdowntimer.HelloApplication;
import org.anelda.isakacountdowntimer.database.DatabaseConnection;
import org.anelda.isakacountdowntimer.model.DataModel;
import org.anelda.isakacountdowntimer.output.OutputController;

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

        BorderPane root = new BorderPane();

        FXMLLoader mainOutputLoader = new FXMLLoader(HelloApplication.class.getResource("output/output.fxml"));
        mainOutputLoader.setController(new OutputController());
        Node mainOutputNode = mainOutputLoader.load();

        AnchorPane.setTopAnchor(mainOutputNode, 0.0);
        AnchorPane.setRightAnchor(mainOutputNode, 0.0);
        AnchorPane.setLeftAnchor(mainOutputNode, 0.0);
        AnchorPane.setBottomAnchor(mainOutputNode, 0.0);

        OutputController mainOutputController = mainOutputLoader.getController();
        mainOutputController.initModel(model);

        root.setCenter(mainOutputNode);

        Stage primaryStage2 = new Stage();
        Scene scene2 = new Scene(new AnchorPane(root));

        primaryStage2.setScene(scene2);

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
