package org.anelda.isakacountdowntimer.model;

import javafx.animation.Timeline;
import javafx.beans.property.*;


public class Moment {

    public final SimpleStringProperty previewTimeValue = new SimpleStringProperty("00:00:00");
    private final IntegerProperty previewTimeHour = new SimpleIntegerProperty();
    private final IntegerProperty previewTimeMin = new SimpleIntegerProperty();
    private final IntegerProperty previewTimeSec = new SimpleIntegerProperty();


    public final SimpleStringProperty outputTimeValue = new SimpleStringProperty("00:00:00");
    public final SimpleStringProperty currentTimeValue = new SimpleStringProperty("00:00:00");

    private final IntegerProperty hour = new SimpleIntegerProperty();
    private final IntegerProperty minute = new SimpleIntegerProperty();
    private final IntegerProperty second = new SimpleIntegerProperty();

    public final SimpleStringProperty alertMessage = new SimpleStringProperty();

    private final BooleanProperty timerRunning = new SimpleBooleanProperty(false);


    public final Timeline timeline = new Timeline();

    public Moment (int hour, int minute, int second) {
        setHour(hour);
        setMinute(minute);
        setSecond(second);

        setPreviewTimeHour(hour);
        setPreviewTimeMin(minute);
        setPreviewTimeSec(second);
    }

    //Start Hour Preview
    public final int getPreviewTimeHour() {
        return previewTimeHourProperty().get();
    }

    public final void setPreviewTimeHour(int hour) {
        this.previewTimeHourProperty().set(hour);
    }

    public final IntegerProperty previewTimeHourProperty() {
        return this.previewTimeHour;
    }


    //Start Minute Preview
    public final int getPreviewTimeMin() {
        return previewTimeMinProperty().get();
    }

    public final void setPreviewTimeMin(int min) {
        this.previewTimeMinProperty().set(min);
    }

    public final IntegerProperty previewTimeMinProperty() {
        return this.previewTimeMin;
    }

    //Start Minute Preview
    public final int getPreviewTimeSec() {
        return previewTimeSecProperty().get();
    }

    public final void setPreviewTimeSec(int min) {
        this.previewTimeSecProperty().set(min);
    }

    public final IntegerProperty previewTimeSecProperty() {
        return this.previewTimeSec;
    }



    public final int getHour() {
        return hourProperty().get();
    }

    public final void setHour(int hour) {
        this.hourProperty().set(hour);
    }

    public final IntegerProperty hourProperty() {
        return this.hour;
    }


    public final IntegerProperty minuteProperty() {
        return this.minute;
    }

    public final int getMinute() {
        return this.minuteProperty().get();
    }

    public final void setMinute(final int minute) {
        this.minuteProperty().set(minute);
    }



    public final IntegerProperty secondProperty() {
        return this.second;
    }

    public final int getSecond() {
        return this.secondProperty().get();
    }

    public final void setSecond(final int second) {
        this.secondProperty().set(second);
    }


    // Preview
    public SimpleStringProperty previewTimeValueProperty() {
        return previewTimeValue;
    }

    public String getPreviewTimeValue() {
        return previewTimeValueProperty().get();
    }

    public final void setPreviewTimeValue(String value) {

        previewTimeValueProperty().set(value);
    }

    //OUTPUT
    public SimpleStringProperty outputTimeValueProperty() {
        return outputTimeValue;
    }

    public String getOutputTimeValue() {
        return outputTimeValueProperty().get();
    }

    public final void setOutputTimeValue(String value) {
        outputTimeValueProperty().set(value);
    }

    //CURRENT TIME VALUE
    public SimpleStringProperty currentTimeValueProperty() {
        return currentTimeValue;
    }

    public String getCurrentTimeValue() {
        return currentTimeValueProperty().get();
    }

    public final void setCurrentTimeValue(String value) {
        currentTimeValueProperty().set(value);
    }


    //TIMER RUNNING
    public boolean isTimerRunning() {
        return timerRunningProperty().get();
    }

    public BooleanProperty timerRunningProperty() {
        return timerRunning;
    }

    //ALERT MESSAGE
    public SimpleStringProperty alertMessageProperty() {
        return alertMessage;
    }
}
