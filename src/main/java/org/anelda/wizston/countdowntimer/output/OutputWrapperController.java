package org.anelda.wizston.countdowntimer.output;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    public Label overTimeLabel;
    @FXML
    public Label labelShowingMessage;
    @FXML
    public Label labelTitle;
    @FXML
    public AnchorPane bottomAnchorPane;

    FadeTransition fadeTransition;

    public final ObjectProperty<Font> liveTimerFontTracking = new SimpleObjectProperty<Font>(Font.getDefault());
    public final ObjectProperty<Color> colorProp = new SimpleObjectProperty<>(Color.valueOf("#e5e5e5"));
    public final ObjectProperty<Font> messageFontTracking = new SimpleObjectProperty<Font>(Font.getDefault());
    public final ObjectProperty<Font> currentTimeFontTracking = new SimpleObjectProperty<Font>(Font.getDefault());
    public final ObjectProperty<Font> overTimeFontTracking = new SimpleObjectProperty<Font>(Font.getDefault());
    public final ObjectProperty<Font> titleFontTracking = new SimpleObjectProperty<Font>(Font.getDefault());


    private int[] init;

    private final SimpleBooleanProperty showOvertime = new SimpleBooleanProperty(true);

    public void initModel(DataModel model) {
        if (Objects.nonNull(this.model)) {
            throw new IllegalStateException("Model can only be initialized once");
        }

        this.model = model;

        currentTimeLabel.textProperty().bind(model.getCurrentMoment().currentTimeValue);
        currentTimeFontTracking.set(Font.font(26));
        currentTimeLabel.fontProperty().bind(currentTimeFontTracking);

        overTimeFontTracking.set(Font.font("Calibri Bold", 26));
        overTimeLabel.fontProperty().bind(overTimeFontTracking);

        fadeTransition = new FadeTransition(Duration.seconds(1.0), labelLiveTimer);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.3);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
//        fadeTransition.play();

        labelLiveTimer.textProperty().bind(model.getCurrentMoment().outputTimeValue);
        overTimeLabel.textProperty().bind(model.getCurrentMoment().overTimeValue);
        liveTimerFontTracking.set(Font.font("Calibri Bold", 114));
        labelLiveTimer.fontProperty().bind(liveTimerFontTracking);
        model.getCurrentMoment().colorProp.set(Color.RED);
        labelLiveTimer.textFillProperty().bind(model.getCurrentMoment().colorProp);


        labelShowingMessage.textProperty().bind(model.getCurrentMoment().alertMessage);
        messageFontTracking.set(Font.font(26));
        labelShowingMessage.fontProperty().bind(messageFontTracking);
        labelShowingMessage.wrapTextProperty().set(true);

        labelTitle.textProperty().bind(model.getCurrentMoment().timerTitle);
        titleFontTracking.set(Font.font(19));
        labelTitle.fontProperty().bind(titleFontTracking);

        overTimeLabel.visibleProperty().bind(model.getCurrentPreference().showOvertimeProperty());

        startClock();
        startTimer();
    }

    public void startTimer() {

        init = new int[]{model.getCurrentMoment().getHour(), model.getCurrentMoment().getMinute(), model.getCurrentMoment().getSecond()};

        model.getCurrentMoment().setTimeElapsed(false);

        if(!this.model.getCurrentMoment().isTimerRunning()) { //Added an if statement to switch on "running"
//            if (this.model.getCurrentMoment().getHour() > 0 || this.model.getCurrentMoment().getMinute() > 0) {


                KeyFrame keyframe = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {

                        int hour = model.getCurrentMoment().getHour();
                        int minute = model.getCurrentMoment().getMinute();
                        int second = model.getCurrentMoment().getSecond();

                        if (model.getCurrentMoment().isTimeElapsed() && model.getCurrentPreference().isCountUpOverTimeEnabled()) {

                            int otHour = model.getCurrentMoment().getOtHour();
                            int otMinute = model.getCurrentMoment().getOtMinute();
                            int otSecond = model.getCurrentMoment().getOtSecond();

                            if (otMinute >= 59) {
                                model.getCurrentMoment().otHourProperty().setValue(otHour + 1);
                                model.getCurrentMoment().otMinuteProperty().setValue(0);
                                model.getCurrentMoment().otSecondProperty().setValue(0);
                            }


                            if (otSecond >= 59) {
                                model.getCurrentMoment().otMinuteProperty().setValue(otMinute + 1);
                                model.getCurrentMoment().otSecondProperty().setValue(0);
                            }

                            if (otSecond < 59) {
                                model.getCurrentMoment().otSecondProperty().setValue(otSecond + 1);
                            }


                            model.getCurrentMoment().overTimeValueProperty().set(String.format("⚠\uFE0FOvertime: %d:%02d:%02d",
                                    model.getCurrentMoment().getOtHour(),
                                    model.getCurrentMoment().getOtMinute(),
                                    model.getCurrentMoment().getOtSecond())
                            );
                        } else {

                            if (second < 0) {
                                model.getCurrentMoment().secondProperty().setValue(59);
                            }

                            boolean isHoursZero = hour == 0;
                            boolean isSecondsZero = second == 0;
                            boolean isMinutesZero = minute == 0 && isSecondsZero;
                            boolean timeToChangeBackground = hour == 0 && minute < 5;


                            if (isMinutesZero && hour > 0) {
                                model.getCurrentMoment().hourProperty().setValue(hour - 1);
                                model.getCurrentMoment().minuteProperty().setValue(59);
                                model.getCurrentMoment().secondProperty().setValue(59);
                            }

                            if (isSecondsZero) {
                                model.getCurrentMoment().minuteProperty().setValue(minute - 1);
                                model.getCurrentMoment().secondProperty().setValue(59);
                            }

                            if (isHoursZero && isMinutesZero) {
                                if (!model.getCurrentPreference().isCountUpOverTimeEnabled()) {
                                    model.getCurrentMoment().timeline.stop();
                                }
                                model.getCurrentMoment().minuteProperty().setValue(0);
                                model.getCurrentMoment().secondProperty().setValue(0);
                                model.getCurrentMoment().setTimeElapsed(true);
                            }

                            if (second > 0) {
                                model.getCurrentMoment().secondProperty().setValue(second - 1);
                            }

                            if (timeToChangeBackground) {
                                colorProp.setValue(Color.RED);
                                model.getCurrentMoment().colorPropProperty().set(Color.RED);
                            } else {
                                model.getCurrentMoment().colorPropProperty().set(Color.valueOf("#e5e5e5"));
                            }
                        }

                        model.getCurrentMoment().outputTimeValueProperty().set(String.format("%d:%02d:%02d",
                                model.getCurrentMoment().getHour(),
                                model.getCurrentMoment().getMinute(),
                                model.getCurrentMoment().getSecond())
                        );

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
//            } else {
//                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have not entered a time!");
//                alert.showAndWait();
//            }
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

        model.getCurrentMoment().timeElapsedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                fadeTransition.play();
            } else {
                labelLiveTimer.opacityProperty().set(1.0);
                fadeTransition.stop();
            }
            if (newValue && model.currentPreferenceProperty().get().countUpOverTimeProperty().get()) {
                model.getCurrentPreference().showOvertimeProperty().set(true);
            } else {
                model.getCurrentPreference().showOvertimeProperty().set(false);
            }
        });
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
