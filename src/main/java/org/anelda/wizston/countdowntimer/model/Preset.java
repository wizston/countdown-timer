package org.anelda.wizston.countdowntimer.model;

public class Preset {
    public final int id;
    public final String title;
    public final int hour;
    public final int minute;
    public final int sec;
    public final boolean autoStart;

    public Preset(int id, String title, int hour, int minute, int sec, boolean autoStart) {
        this.id = id;
        this.title = title;
        this.hour = hour;
        this.minute = minute;
        this.sec = sec;
        this.autoStart = autoStart;
    }
}
