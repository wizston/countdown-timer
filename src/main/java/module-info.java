module org.anelda.wizston.countdowntimer {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;

//    requires com.dlsc.formsfx;
//    requires validatorfx;
//    requires org.kordamp.ikonli.javafx;
//    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires org.controlsfx.controls;

//    opens org.anelda.wizston.countdowntimer to javafx.fxml;
    exports org.anelda.wizston.countdowntimer;
    exports org.anelda.wizston.countdowntimer.model;
    exports org.anelda.wizston.countdowntimer.output;
    exports org.anelda.wizston.countdowntimer.preset;
    exports org.anelda.wizston.countdowntimer.preview;
    exports org.anelda.wizston.countdowntimer.option;
}