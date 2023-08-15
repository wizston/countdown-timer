package org.anelda.wizston.countdowntimer.model;

import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.scene.paint.Color;


public class Moment {

    public final SimpleStringProperty previewTimerTitle = new SimpleStringProperty("");
    public final SimpleStringProperty previewTimeValue = new SimpleStringProperty("00:00:00");
    public final SimpleStringProperty previewTimeHourValue = new SimpleStringProperty("00");
    public final SimpleStringProperty previewTimeMinValue = new SimpleStringProperty("00");
    public final SimpleStringProperty previewTimeSecValue = new SimpleStringProperty("00");
    private final IntegerProperty previewTimeHour = new SimpleIntegerProperty();
    private final IntegerProperty previewTimeMin = new SimpleIntegerProperty();
    private final IntegerProperty previewTimeSec = new SimpleIntegerProperty();


    public final SimpleStringProperty outputTimeValue = new SimpleStringProperty("00:00:00");

    public final ObjectProperty<Color> colorProp = new SimpleObjectProperty<>(Color.valueOf("#e5e5e5"));
    public final SimpleStringProperty currentTimeValue = new SimpleStringProperty("00:00:00");
    public int[] initTimeValue = new int[]{0, 0, 0};

    private final IntegerProperty hour = new SimpleIntegerProperty();
    private final IntegerProperty minute = new SimpleIntegerProperty();
    private final IntegerProperty second = new SimpleIntegerProperty();

    public final SimpleStringProperty alertMessage = new SimpleStringProperty();

    public final SimpleStringProperty timerTitle = new SimpleStringProperty("");

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


    public final String getTimerTitleProperty() {
        return timerTitleProperty().get();
    }

    public final void setTimerTitle(String title) {
        this.timerTitleProperty().set(title);
    }

    public final int getHour() {
        return hourProperty().get();
    }

    public final void setHour(int hour) {
        this.hourProperty().set(hour);
    }

    public final StringProperty timerTitleProperty() {
        return this.timerTitle;
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

    public final BooleanProperty timerRunningProperty() {
        return this.timerRunning;
    }

    public final boolean getTimerRunning() {
        return this.timerRunningProperty().get();
    }

    public final void setTimerRunning(final boolean second) {
        this.timerRunningProperty().set(second);
    }


    // Preview
    public SimpleStringProperty previewTimerTitleProperty() {
        return previewTimerTitle;
    }
    public SimpleStringProperty previewTimeValueProperty() {
        return previewTimeValue;
    }
    public SimpleStringProperty previewTimeHourValueProperty() {
        return previewTimeHourValue;
    }
    public SimpleStringProperty previewTimeMinValueProperty() {
        return previewTimeMinValue;
    }
    public SimpleStringProperty previewTimeSecValueProperty() {
        return previewTimeSecValue;
    }

    public String getPreviewTimeValue() {
        return previewTimeValueProperty().get();
    }

    public final void setPreviewTimeValue(String value) {
        previewTimeValueProperty().set(value);
    }

    public final void setPreviewTimeHourValue(String value) {
        previewTimeHourValueProperty().set(value);
    }

    public final void setPreviewTimeMinValue(String value) {
        previewTimeMinValueProperty().set(value);
    }

    public final void setPreviewTimeSecValue(String value) {
        previewTimeSecValueProperty().set(value);
    }

    //OUTPUT
    public SimpleStringProperty outputTimeValueProperty() {
        return outputTimeValue;
    }
    public ObjectProperty<Color> colorPropProperty() {
        return colorProp;
    }

    public String getOutputTimeValue() {
        return outputTimeValueProperty().get();
    }

    public final void setOutputTimeValue(String value) {
        outputTimeValueProperty().set(value);
    }

    public final void setInitTimeValue(int hour, int minute, int seconds) {
        initTimeValue = new int[]{hour, minute, seconds};
    }

    public final void setInitTimeValue() {
        initTimeValue = new int[]{getHour(), getMinute(), getSecond()};
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

    //ALERT MESSAGE
    public SimpleStringProperty alertMessageProperty() {
        return alertMessage;
    }
}
