package org.anelda.isakacountdowntimer.model;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataModel {
    private final ObservableList<Moment> momentList = FXCollections.observableArrayList(moment ->
            new Observable[] {moment.hourProperty(), moment.minuteProperty(), moment.secondProperty(),
                    moment.previewTimeValueProperty(), moment.previewTimeHourProperty(), moment.previewTimeMinProperty(), moment.previewTimeSecProperty(),
                    moment.outputTimeValueProperty(), moment.currentTimeValueProperty(), moment.alertMessageProperty(), moment.timerRunningProperty()});

    private final ObjectProperty<Moment> currentMoment = new SimpleObjectProperty<>(null);

    public ObjectProperty<Moment> currentMomentProperty() {
        return currentMoment ;
    }

    public final Moment getCurrentMoment() {
        return currentMomentProperty().get();
    }

    public final void setCurrentMoment(Moment moment) {
        currentMomentProperty().set(moment);
    }

    public ObservableList<Moment> getMomentList() {
        return momentList;
    }
}
