package org.anelda.wizston.countdowntimer.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class UserPreferences {
    public final BooleanProperty countUpOverTime = new SimpleBooleanProperty(true);
    public final BooleanProperty showOvertime = new SimpleBooleanProperty(false);

    public final BooleanProperty countUpOverTimeProperty() {
        return this.countUpOverTime;
    }

    public final BooleanProperty showOvertimeProperty() {
        return this.showOvertime;
    }



    //TIMER RUNNING
    public boolean isCountUpOverTimeEnabled() {
        return this.countUpOverTimeProperty().get();
    }
    
    public final boolean getCountUpOverTime() {
        return this.countUpOverTimeProperty().get();
    }
    
    public final void setCountUpOverTime(final boolean value) {
        this.countUpOverTimeProperty().set(value);
    }

    public final void setShowOvertime(final boolean value) {
        this.showOvertimeProperty().set(value);
    }
}
