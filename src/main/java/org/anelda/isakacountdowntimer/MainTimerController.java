package org.anelda.isakacountdowntimer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainTimerController implements Initializable {

    public VBox alternatePaneComponent;
    public Label welcomeText;

    @FXML
    private HelloController alternatePaneComponentController;

    @FXML
    private Label labelPreviewTimer;
    @FXML
    private Label labelLiveTimer;

    @FXML
    private TextField txtPreviewMessage;

    @FXML
    private Label labelShowingMessage;

    @FXML
    private Button btnStart;

    private SimpleStringProperty timeValue = new SimpleStringProperty("00:00:00");

    private final Timeline timeline = new Timeline();

    private int min;
    private int startTimeSec = 5, startTimeMin = 1, startTimeHour = 1;
    private int[] init = {startTimeHour, startTimeMin, startTimeSec};
    private int[] activePreset = {startTimeHour, startTimeMin, startTimeSec};
    private Parent borderPane;
    public BorderPane timeBorderPane;
    private boolean isRunning = false;

    private void init() throws IOException {
        startTimer();
        ///startOnDisplay();
        externalDisplay();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            init();
            labelLiveTimer.textProperty().bind(timeValue);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startTimer() {

//            Image imageDecline = new Image(MainTimerController.class.getResourceAsStream("/images/R.png"));
//            ImageView iv = new ImageView(imageDecline);
//            btnStart.setGraphic(iv);
        if(!isRunning) { //Added a if statement to switch on "running"
            if (startTimeHour > 0 || startTimeMin > 0) {
                labelLiveTimer.setTextFill(Color.valueOf("#e5e5e5"));

                KeyFrame keyframe = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (startTimeSec < 0) {
                            startTimeSec = 59;
                        }

                        startTimeSec--;
                        boolean isSecondsZero = startTimeSec == 0;
                        boolean isMinutesZero = startTimeMin == 0 && isSecondsZero;
                        boolean timeToChangeBackground = startTimeHour == 0 && startTimeSec == 0 && startTimeMin == 0;

                        if (isMinutesZero && startTimeHour > 0) {
                            startTimeHour--;
                            startTimeMin = 60;
                            startTimeSec = 60;
                        }

                        if (isSecondsZero) {
                            startTimeMin--;
                            startTimeSec = 60;
                        }

                        if (timeToChangeBackground) {
                            timeline.stop();
                            startTimeMin = 0;
                            startTimeSec = 0;
                            labelLiveTimer.setTextFill(Color.RED);

                        }

                        alternatePaneComponentController.timeValue.set(String.format("%d:%02d:%02d", startTimeHour, startTimeMin, startTimeSec));
                        timeValue.set(String.format("%d:%02d:%02d", startTimeHour, startTimeMin, startTimeSec));
                        //labelLiveTimer.setText(String.format("%d:%02d:%02d", startTimeHour, startTimeMin, startTimeSec));

                    }
                });
                labelLiveTimer.setTextFill(Color.valueOf("#e5e5e5"));
                //startTimeSec = 60; // Change to 60!
                //startTimeMin = min - 1;
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.getKeyFrames().add(keyframe);
                timeline.playFromStart();
                isRunning = true;
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have not entered a time!");
                alert.showAndWait();
            }
        }else {
            if (startTimeHour > 0 || startTimeMin > 0) {
                labelLiveTimer.setTextFill(Color.valueOf("#e5e5e5"));
                timeline.play();
            }
            //timeline.play(); //Playes from current position in in the direction indicated by rate.
        }

    }

//    public void setTimer() {
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(Main.class.getResource("newTimeDialog.fxml"));
//            Parent newTimeBorderPane = (BorderPane) loader.load();
//            borderPane = newTimeBorderPane;
//            Scene scene = new Scene(newTimeBorderPane);
//            Stage primaryStage = new Stage();
//            primaryStage.setScene(scene);
//            primaryStage.showAndWait();
//            if (!primaryStage.isShowing()) {
//                min = newTimeController.getMin();
//                labelLiveTimer.setText(" " + min);
//            } else {
//
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }

    public void stopTimer() {
        timeline.pause();
    }

    public void resetTimer() {
        timeline.stop();
        startTimeHour = init[0];
        startTimeMin = init[1];
        startTimeSec = init[2];
        timeValue.set(String.format("%d:%02d:%02d", startTimeHour, startTimeMin, startTimeSec));
    }

    public void incrementHour() {
        activePreset[0]++;
        setPreview();
    }

    public void decrementHour() {
        activePreset[0]--;
        setPreview();
    }

    public void incrementMinute() {
        activePreset[1]++;
        setPreview();
    }

    public void decrementMinute() {
        activePreset[1]--;
        setPreview();
    }

    public void setPreview() {
        activePreset[2] = 0;
        labelPreviewTimer.setText(String.format("%d:%02d:%02d", activePreset[0], activePreset[1], activePreset[2]));
    }

    public void setShowing() {
        timeline.stop();
        startTimeHour = activePreset[0];
        startTimeMin = activePreset[1];
        startTimeSec = activePreset[2];

        init = activePreset.clone();

        timeValue.set(String.format("%d:%02d:%02d", startTimeHour, startTimeMin, startTimeSec));
    }

    public void showMessage() {

        labelShowingMessage.setText(txtPreviewMessage.getText());
        alternatePaneComponentController.welcomeText.setText(txtPreviewMessage.getText());
    }

    public void clearMessage() {
        labelShowingMessage.setText("");
    }

    public void startOnDisplay()
    {
        Stage primaryStage = new Stage();
        ObservableList<Screen> screens = Screen.getScreens();//Get list of Screens
        Button btn = new Button();
        btn.setText("Full Screen - Screen 1");
        btn.setOnAction((ActionEvent event) -> {
            Rectangle2D bounds = screens.get(0).getVisualBounds();
            primaryStage.setX(bounds.getMinX());
            primaryStage.setY(bounds.getMinY());
            primaryStage.setFullScreen(true);
            //primaryStage.setWidth(bounds.getWidth());
            //primaryStage.setHeight(bounds.getHeight());
        });

        Button btn2 = new Button();
        btn2.setText("Full Screen - Screen 2");
        btn2.setOnAction((ActionEvent event) -> {
            if (screens.size() > 0) {
                Rectangle2D bounds = screens.get(1).getVisualBounds();
                primaryStage.setX(bounds.getMinX());
                primaryStage.setY(bounds.getMinY());
                primaryStage.setFullScreen(true);
                //primaryStage.setWidth(bounds.getWidth());
                //primaryStage.setHeight(bounds.getHeight());
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(new VBox(btn, btn2));

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void externalDisplay() throws IOException {

        Stage primaryStage2 = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        primaryStage2.setScene(scene);

        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        primaryStage2.setX(bounds.getMinX());
        primaryStage2.setY(bounds.getMinY());
        primaryStage2.setFullScreen(true);
        primaryStage2.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage2.show();


        Stage primaryStage = new Stage();
        StackPane root = new StackPane();
        root.getChildren().add(scene.getRoot());

        Scene scenea = new Scene(root, 300, 250);

        primaryStage.setScene(scenea);
        primaryStage.show();
    }
}
