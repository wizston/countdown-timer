package org.anelda.wizston.countdowntimer.services;

import org.anelda.wizston.countdowntimer.model.DataModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimerService {

    DataModel model;

    public TimerService(DataModel model) {
        this.model = model;
    }
    public void setEndTimeLabel() {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("hh:mm"); // AM/PM format
        now.add(Calendar.HOUR, model.getCurrentMoment().getHour());
        now.add(Calendar.MINUTE, model.getCurrentMoment().getMinute());
        now.add(Calendar.SECOND, model.getCurrentMoment().getSecond());
        model.getCurrentMoment().setEndTimeValue(df.format(now.getTime()));
    }
}
