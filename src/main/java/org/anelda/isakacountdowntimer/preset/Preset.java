package org.anelda.isakacountdowntimer.preset;

public class Preset {
    public final int id;
    public final String title;
    public final int durationInMinute;
    public final boolean autoStart;

    public Preset(int id, String title, int durationInMinute, boolean autoStart) {
        this.id = id;
        this.title = title;
        this.durationInMinute = durationInMinute;
        this.autoStart = autoStart;
    }
}
