package org.anelda.wizston.countdowntimer.option;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Screen;
import org.anelda.wizston.countdowntimer.model.DataModel;

public class OptionController {

    @FXML
    public ComboBox displaysComboBox;

    private DataModel model;

    ListView<String> list = new ListView<String>();
    ObservableList<String> data = FXCollections.observableArrayList();

    public void initModel(DataModel model) {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;

        int i = 1;
        for (Screen screen: Screen.getScreens()) {
            data.add(String.format("Display %s (Width: %s, Height: %s", i, screen.getBounds().getWidth(), screen.getBounds().getHeight()));
            i++;
        }

        ObservableList<Screen> screens = Screen.getScreens();

        // Create a combo box
        displaysComboBox.setItems(data);
        displaysComboBox.getSelectionModel().selectFirst();
    }
}
