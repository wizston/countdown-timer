module org.anelda.isakacountdowntimer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens org.anelda.isakacountdowntimer to javafx.fxml;
    exports org.anelda.isakacountdowntimer;
    exports org.anelda.isakacountdowntimer.model;
    exports org.anelda.isakacountdowntimer.output;
    exports org.anelda.isakacountdowntimer.preset;
    exports org.anelda.isakacountdowntimer.preview;
    exports org.anelda.isakacountdowntimer.option;
}