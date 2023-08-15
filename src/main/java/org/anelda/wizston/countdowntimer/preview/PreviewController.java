package org.anelda.wizston.countdowntimer.preview;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.anelda.wizston.countdowntimer.model.DataModel;
import org.anelda.wizston.countdowntimer.output.OutputController;

public class PreviewController {

    private DataModel model;

    @FXML
    public Button increaseHourBtn;
    @FXML
    public Button decreaseHourBtn;
    @FXML
    public Button increaseMinuteBtn;
    @FXML
    public Button decreaseMinuteBtn;

    @FXML
    public Button simplePreset1, simplePreset2, simplePreset3, simplePreset4;

    @FXML
    public TextField txtPreviewMessage;

    @FXML
    public Label previewTimerTitle, labelPreviewHour, labelPreviewMin, labelPreviewSec;

//    @FXML
//    public OutputController outputController;

    @FXML
    public void initialize() {
        increaseHourBtn.setOnAction(new EventHandler() {@Override public void handle(Event event) {incrementHour();} });
        decreaseHourBtn.setOnAction(new EventHandler() {@Override public void handle(Event event) {decrementHour();} });
        increaseMinuteBtn.setOnAction(new EventHandler() {@Override public void handle(Event event) {incrementMinute();} });
        decreaseMinuteBtn.setOnAction(new EventHandler() {@Override public void handle(Event event) {decrementMinute();} });

        simplePreset1.setOnAction(new EventHandler() {@Override public void handle(Event event) {setPresetTime(0, 5);} });
        simplePreset2.setOnAction((EventHandler) event -> setPresetTime(0, 10));
        simplePreset3.setOnAction((EventHandler) event -> setPresetTime(0, 25));
        simplePreset4.setOnAction((EventHandler) event -> setPresetTime(0, 45));
    }

    private void setPresetTime(int hour, int min) {

        this.model.getCurrentMoment().previewTimeHourProperty().setValue(hour);
        this.model.getCurrentMoment().previewTimeMinProperty().setValue(min);
        setPreview();
        //Reset timer tile text to blank
        model.getCurrentMoment().previewTimerTitle.set("");

        //Reset play-pause buttons
//        outputController.pauseTimeBtn.setVisible(true);
//        outputController.startTimeBtn.setVisible(false);
    }

    public void initModel(DataModel model) {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model ;

        setPreview();

        previewTimerTitle.textProperty().bind(model.getCurrentMoment().previewTimerTitle);
        labelPreviewHour.textProperty().bind(model.getCurrentMoment().previewTimeHourValue);
        labelPreviewMin.textProperty().bind(model.getCurrentMoment().previewTimeMinValue);
        labelPreviewSec.textProperty().bind(model.getCurrentMoment().previewTimeSecValue);
    }

    public void setOutputTime() {
        this.model.getCurrentMoment().setTimerTitle(this.model.getCurrentMoment().previewTimerTitle.get());
        this.model.getCurrentMoment().setHour(this.model.getCurrentMoment().getPreviewTimeHour());
        this.model.getCurrentMoment().setMinute(this.model.getCurrentMoment().getPreviewTimeMin());
        this.model.getCurrentMoment().setSecond(this.model.getCurrentMoment().getPreviewTimeSec());

        model.getCurrentMoment().setOutputTimeValue((String.format("%d:%02d:%02d", this.model.getCurrentMoment().getHour(), this.model.getCurrentMoment().previewTimeMinProperty().getValue(), this.model.getCurrentMoment().previewTimeSecProperty().getValue())));

        // set initial value for reset purpose
        this.model.getCurrentMoment().setInitTimeValue();

        model.getCurrentMoment().timeline.play();
        model.getCurrentMoment().setTimerRunning(true);

        //Reset play-pause buttons
//        outputController.resetPausePlayButtons();
    }

    public void showMessage() {
        this.model.getCurrentMoment().alertMessageProperty().set(txtPreviewMessage.getText());
    }

    public void clearMessage() {
        this.model.getCurrentMoment().alertMessageProperty().set(txtPreviewMessage.getText());
    }


    public void setPreview() {
        updatePreviewValues(model);
    }

    public static void updatePreviewValues(DataModel model) {

        model.getCurrentMoment().setPreviewTimeHourValue((String.format("%02d", model.getCurrentMoment().previewTimeHourProperty().getValue())));
        model.getCurrentMoment().setPreviewTimeMinValue((String.format("%02d", model.getCurrentMoment().previewTimeMinProperty().getValue())));
        model.getCurrentMoment().setPreviewTimeSecValue((String.format("%02d", model.getCurrentMoment().previewTimeSecProperty().getValue())));

        model.getCurrentMoment().previewTimeSecProperty().setValue(0);
    }


    public void incrementHour() {
        this.model.getCurrentMoment().previewTimeHourProperty().setValue(model.getCurrentMoment().getPreviewTimeHour() + 1);
        setPreview();
        //Reset timer tile text to blank
        model.getCurrentMoment().previewTimerTitle.set("");
    }

    public void decrementHour() {
        int hour = model.getCurrentMoment().getPreviewTimeHour();
        if (hour > 0) {
            this.model.getCurrentMoment().previewTimeHourProperty().setValue(hour - 1);
            setPreview();
            //Reset timer tile text to blank
            model.getCurrentMoment().previewTimerTitle.set("");
        }
    }

    public void incrementMinute() {
        int min = model.getCurrentMoment().getPreviewTimeMin();
        if (min < 60) {
            this.model.getCurrentMoment().previewTimeMinProperty().setValue(min + 1);
            setPreview();
            //Reset timer tile text to blank
            model.getCurrentMoment().previewTimerTitle.set("");
        }
    }

    public void decrementMinute() {
        int min = model.getCurrentMoment().getPreviewTimeMin();
        if (min > 0) {
            this.model.getCurrentMoment().previewTimeMinProperty().setValue(min - 1);
            setPreview();
            //Reset timer tile text to blank
            model.getCurrentMoment().previewTimerTitle.set("");
        }
    }
}
