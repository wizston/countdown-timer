package org.anelda.wizston.countdowntimer.output;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.anelda.wizston.countdowntimer.model.DataModel;

import java.time.LocalTime;
import java.util.Objects;

public class OutputWrapperController {

    private DataModel model;

    @FXML
    public Label labelLiveTimer;
    @FXML
    public Label currentTimeLabel;
    @FXML
    public Label labelShowingMessage;
    @FXML
    public Label labelTitle;

    public final ObjectProperty<Font> liveTimerFontTracking = new SimpleObjectProperty<Font>(Font.getDefault());
    public final ObjectProperty<Color> colorProp = new SimpleObjectProperty<>(Color.valueOf("#e5e5e5"));
    public final ObjectProperty<Font> messageFontTracking = new SimpleObjectProperty<Font>(Font.getDefault());
    public final ObjectProperty<Font> currentTimeFontTracking = new SimpleObjectProperty<Font>(Font.getDefault());
    public final ObjectProperty<Font> titleFontTracking = new SimpleObjectProperty<Font>(Font.getDefault());


    private int[] init;

    private final SimpleStringProperty hourValue = new SimpleStringProperty("5");

    public void initModel(DataModel model) {
        if (Objects.nonNull(this.model)) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;

        currentTimeLabel.textProperty().bind(model.getCurrentMoment().currentTimeValue);
        currentTimeFontTracking.set(Font.font(26));
        currentTimeLabel.fontProperty().bind(currentTimeFontTracking);

        labelLiveTimer.textProperty().bind(model.getCurrentMoment().outputTimeValue);
        liveTimerFontTracking.set(Font.font(114));
        labelLiveTimer.fontProperty().bind(liveTimerFontTracking);
        model.getCurrentMoment().colorProp.set(Color.RED);
        labelLiveTimer.textFillProperty().bind(model.getCurrentMoment().colorProp);


        labelShowingMessage.textProperty().bind(model.getCurrentMoment().alertMessage);
        messageFontTracking.set(Font.font(26));
        labelShowingMessage.fontProperty().bind(messageFontTracking);

        labelTitle.textProperty().bind(model.getCurrentMoment().timerTitle);
        titleFontTracking.set(Font.font(19));
        labelTitle.fontProperty().bind(titleFontTracking);

        startClock();
        startTimer();
    }

    public void startTimer() {
        init = new int[]{model.getCurrentMoment().getHour(), model.getCurrentMoment().getMinute(), model.getCurrentMoment().getSecond()};

        if(!this.model.getCurrentMoment().isTimerRunning()) { //Added an if statement to switch on "running"
            if (this.model.getCurrentMoment().getHour() > 0 || this.model.getCurrentMoment().getMinute() > 0) {


                KeyFrame keyframe = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {

                        if (model.getCurrentMoment().getSecond() < 0) {
                            model.getCurrentMoment().secondProperty().setValue(59);
                        }

                        boolean isHoursZero = model.getCurrentMoment().getHour() == 0;
                        boolean isSecondsZero = model.getCurrentMoment().getSecond() == 0;
                        boolean isMinutesZero = model.getCurrentMoment().getMinute() == 0 && isSecondsZero;
                        boolean timeToChangeBackground = model.getCurrentMoment().getHour() == 0 && model.getCurrentMoment().getMinute() < 5;

                        if (isMinutesZero && model.getCurrentMoment().getHour() > 0) {
                            int sec  = model.getCurrentMoment().getHour();
                            sec--;
                            model.getCurrentMoment().hourProperty().setValue(sec);
                            model.getCurrentMoment().minuteProperty().setValue(60);
                            model.getCurrentMoment().secondProperty().setValue(60);
                        }

                        if (isSecondsZero) {
                            model.getCurrentMoment().minuteProperty().setValue(model.getCurrentMoment().getMinute() - 1);
                            model.getCurrentMoment().secondProperty().setValue(60);
                        }

                        if (isHoursZero && isMinutesZero) {
                            model.getCurrentMoment().timeline.stop();
                            model.getCurrentMoment().minuteProperty().setValue(0);
                            model.getCurrentMoment().secondProperty().setValue(0);
                        }

                        if(model.getCurrentMoment().getSecond() > 0) {
                            model.getCurrentMoment().secondProperty().setValue(model.getCurrentMoment().getSecond() - 1);
                        }

                        if (model.getCurrentMoment().getHour() == 0 && model.getCurrentMoment().getMinute() < 3) {
                            colorProp.setValue(Color.RED);
                            model.getCurrentMoment().colorPropProperty().set(Color.RED);
                        } else {
                            model.getCurrentMoment().colorPropProperty().set(Color.valueOf("#e5e5e5"));
                        }
                        model.getCurrentMoment().outputTimeValueProperty().set(String.format("%d:%02d:%02d", model.getCurrentMoment().getHour(), model.getCurrentMoment().getMinute(), model.getCurrentMoment().getSecond()));
                        //labelLiveTimer.setText(String.format("%d:%02d:%02d", startTimeHour, startTimeMin, startTimeSec));

                    }
                });
//                labelLiveTimer.textFillProperty().set(Color.valueOf("#e5e5e5"));
                //startTimeSec = 60; // Change to 60!
                //startTimeMin = min - 1;
                model.getCurrentMoment().timeline.setCycleCount(Timeline.INDEFINITE);
                model.getCurrentMoment().timeline.getKeyFrames().add(keyframe);
                model.getCurrentMoment().timeline.playFromStart();
                this.model.getCurrentMoment().timerRunningProperty().set(true);
            } else {
//                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have not entered a time!");
//                alert.showAndWait();
            }
        }else {
//            if (model.getCurrentMoment().getHour() > 0 || model.getCurrentMoment().getMinute() > 0) {
//                colorProp.setValue(Color.RED);
//                labelLiveTimer.textFillProperty().bind(colorProp);
//
////                labelLiveTimer.textFillProperty().set(Color.valueOf("#e5e5e5"));
//                model.getCurrentMoment().timeline.play();
//            }


            //timeline.play(); //Playes from current position in the direction indicated by rate.

            if (model.getCurrentMoment().getHour() == 0 && model.getCurrentMoment().getMinute() < 5) {
                model.getCurrentMoment().colorPropProperty().set(Color.RED);
            } else {
                model.getCurrentMoment().colorPropProperty().set(Color.valueOf("#e5e5e5"));
            }
        }

    }

    public void stopTimer() {
        model.getCurrentMoment().timeline.pause();
    }

    public void resetTimer() {
        model.getCurrentMoment().timeline.stop();
        model.getCurrentMoment().hourProperty().setValue(init[0]);
        model.getCurrentMoment().minuteProperty().setValue(init[1]);
        model.getCurrentMoment().secondProperty().setValue(init[2]);
        model.getCurrentMoment().outputTimeValueProperty().set(String.format("%d:%02d:%02d", model.getCurrentMoment().getHour(), model.getCurrentMoment().getMinute(), model.getCurrentMoment().getSecond()));
        model.getCurrentMoment().timeline.play();
    }

    public void clearMessage() {
        model.getCurrentMoment().alertMessage.set("");
    }

    public void startClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            model.getCurrentMoment().currentTimeValueProperty().setValue(String.format("%d:%02d:%02d", currentTime.getHour(), currentTime.getMinute(), currentTime.getSecond()));
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}
