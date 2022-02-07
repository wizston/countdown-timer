package org.anelda.isakacountdowntimer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    public Label welcomeText;

    public final SimpleStringProperty timeValue = new SimpleStringProperty("00:00:00");

    public void initialize() {
        this.timeValue.addListener((observableValue, oldText, newText) -> {
            if (oldText != null) {
                welcomeText.textProperty().unbindBidirectional(oldText);
            }
            if (newText != null) {
                welcomeText.textProperty().bind(timeValue);
            }
        });
    }

    @FXML
    protected void onHelloButtonClick() {
        timeValue.set("Welcome to JavaFX Application!");
    }
}