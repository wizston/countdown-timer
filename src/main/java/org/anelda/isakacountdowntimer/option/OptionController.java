package org.anelda.isakacountdowntimer.option;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import org.anelda.isakacountdowntimer.model.DataModel;

public class OptionController {

    private DataModel model;

    ListView<String> list = new ListView<String>();
    ObservableList<String> data = FXCollections.observableArrayList(
            "chocolate", "salmon", "gold", "coral", "darkorchid",
            "darkgoldenrod", "lightsalmon", "black", "rosybrown", "blue",
            "blueviolet", "brown");

    public void initModel(DataModel model) {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once");
        }
        this.model = model;
    }
}
