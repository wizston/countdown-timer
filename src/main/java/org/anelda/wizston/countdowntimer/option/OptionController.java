package org.anelda.wizston.countdowntimer.option;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import org.controlsfx.control.ToggleSwitch;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.anelda.wizston.countdowntimer.HelloApplication;
import org.anelda.wizston.countdowntimer.model.DataModel;
import org.anelda.wizston.countdowntimer.output.OutputWrapperController;

import java.io.IOException;

public class OptionController {

    @FXML
    public ComboBox displaysComboBox;

    @FXML
    public Button setActiveDisplayBtn;

    @FXML
    public ComboBox renderOptionChoiceBox;

    @FXML
    public ToggleSwitch showSecondaryScreenToggleSwitch;


    public Stage primaryStage2 = new Stage();

    private DataModel model;

    ListView<String> list = new ListView<String>();
    ObservableList<String> data = FXCollections.observableArrayList();

    public void initModel(DataModel model) throws IOException {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;

        int i = 1;
        for (Screen screen: Screen.getScreens()) {
            data.add(String.format("Display %s (Width: %s, Height: %s", i, screen.getBounds().getWidth(), screen.getBounds().getHeight()));
            i++;
        }

//        ObservableList<Screen> screens = Screen.getScreens();

        ObservableList<Screen> screens = Screen.getScreens();//Get list of Screens
        externalDisplay(true, screens.size() - 1);

        // Create a combo box
        displaysComboBox.setItems(data);
        displaysComboBox.getSelectionModel().selectLast();

        showSecondaryScreenToggleSwitch.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                renderOptionChoiceBox.setDisable(true);
                displaysComboBox.setDisable(true);
            } else {
                renderOptionChoiceBox.setDisable(false);
                displaysComboBox.setDisable(false);
            }
        });

        setActiveDisplayBtn.setOnAction((ActionEvent event) -> {
            if (showSecondaryScreenToggleSwitch.isSelected()) {
                primaryStage2.setFullScreen(false);
                primaryStage2.close();
            }

            primaryStage2.setFullScreen(false);
            primaryStage2.close();

            if (showSecondaryScreenToggleSwitch.isSelected()) {
                try {
                    boolean fullScreen = renderOptionChoiceBox.getSelectionModel().getSelectedItem().equals("Full Screen");
                    this.externalDisplay(fullScreen, displaysComboBox.getSelectionModel().getSelectedIndex());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        ObservableList<String> options = FXCollections.observableArrayList("Full Screen","Window");

        renderOptionChoiceBox.setItems(options);
        renderOptionChoiceBox.getSelectionModel().selectFirst();
    }



    public void externalDisplay(Boolean fullScreen, Integer activeScreenIndex) throws IOException {

        FXMLLoader mainOutputLoader = new FXMLLoader(HelloApplication.class.getResource("output/outputWrapper.fxml"));

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
                mainOutputController.liveTimerFontTracking.set(Font.font(newWidth.doubleValue() / 5));
                mainOutputController.messageFontTracking.set(Font.font(newWidth.doubleValue() / 20));
                mainOutputController.currentTimeFontTracking.set(Font.font(newWidth.doubleValue() / 40));
                mainOutputController.titleFontTracking.set(Font.font(newWidth.doubleValue() / 40));
            }
        });

        Scene scene2 = new Scene(mainOutputNode);

        ObservableList<Screen> screens = Screen.getScreens();//Get list of Screens

        primaryStage2.setScene(scene2);
        primaryStage2.setTitle("Fullscreen - Wizston:Countdown Timer 0.1");

        if (Boolean.TRUE.equals(fullScreen) && screens.size() > activeScreenIndex) {
            Rectangle2D bounds = screens.get(activeScreenIndex).getVisualBounds();
            primaryStage2.setX(bounds.getMinX());
            primaryStage2.setY(bounds.getMinY());
            primaryStage2.setFullScreen(true);
            primaryStage2.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        }

        primaryStage2.getIcons().add(new Image("/logo.png"));
        primaryStage2.show();
    }
}
