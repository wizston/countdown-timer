package org.anelda.wizston.countdowntimer.output;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.anelda.wizston.countdowntimer.model.DataModel;
import org.anelda.wizston.countdowntimer.services.TimerService;

import java.time.LocalTime;
import java.util.Objects;

public class OutputController {

    public DataModel model;

    @FXML
    public Button pauseTimeBtn;
    @FXML
    public Button startTimeBtn;

    @FXML
    public OutputWrapperController outputWrapperController;

    TimerService timerService;

    private int[] init;

    public void initModel(DataModel model) {
        if (Objects.nonNull(this.model)) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;
        outputWrapperController.initModel(model);
        timerService = new TimerService(model);

        pauseTimeBtn.visibleProperty().bind(model.getCurrentMoment().timerRunningProperty());
        startTimeBtn.visibleProperty().bind(model.getCurrentMoment().timerRunningProperty().not());
    }

    public void startTimer() {

        if (model.getCurrentMoment().getHour() > 0 || model.getCurrentMoment().getMinute() > 0) {
            model.getCurrentMoment().colorPropProperty().set(Color.valueOf("#e5e5e5"));
            model.getCurrentMoment().timeline.play();
            this.model.getCurrentMoment().timerRunningProperty().set(true);

            timerService.setEndTimeLabel();
        }
        //timeline.play(); //Playes from current position in in the direction indicated by rate.
    }

    public void stopTimer() {
        model.getCurrentMoment().setTimerRunning(false);
        model.getCurrentMoment().timeline.pause();
    }

    public void resetTimer() {
        int[] initTime = this.model.getCurrentMoment().initTimeValue;

        model.getCurrentMoment().timeline.stop();
        model.getCurrentMoment().hourProperty().setValue(initTime[0]);
        model.getCurrentMoment().minuteProperty().setValue(initTime[1]);
        model.getCurrentMoment().secondProperty().setValue(initTime[2]);
        model.getCurrentMoment().outputTimeValueProperty().set(String.format("%d:%02d:%02d", model.getCurrentMoment().getHour(), model.getCurrentMoment().getMinute(), model.getCurrentMoment().getSecond()));
        model.getCurrentMoment().timeline.play();

        timerService.setEndTimeLabel();
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
