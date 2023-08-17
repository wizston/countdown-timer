package org.anelda.wizston.countdowntimer.preset;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.anelda.wizston.countdowntimer.model.dao.PresetDao;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class NewPreviewController implements Initializable {

    @FXML
    public Button closeNewPresetModalBtn, saveNewPresetModalBtn;

    @FXML
    public TextField newPresetTitleTxt, newPresetHourTxt, newPresetMinTxt, newPresetSecTxt;

    @FXML
    public Label requiredFieldLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        requiredFieldLabel.setVisible(false);

        closeNewPresetModalBtn.setOnAction(event -> {
            Stage stage = (Stage) closeNewPresetModalBtn.getScene().getWindow();
            stage.close();
        });

        saveNewPresetModalBtn.setOnAction(event -> {
            if (Objects.equals(newPresetTitleTxt.getText(), "") || newPresetHourTxt.getText().length() < 1 ||
                    newPresetMinTxt.getText().length() < 1 || newPresetSecTxt.getText().length() < 1) {

                requiredFieldLabel.setText("All fields are required");
                requiredFieldLabel.setVisible(true);
                return;
            } else {
                requiredFieldLabel.setVisible(false);
            }

            if (!isNumeric(newPresetHourTxt.getText()) || !isNumeric(newPresetMinTxt.getText()) || !isNumeric(newPresetSecTxt.getText())) {
                requiredFieldLabel.setText("Please enter only numbers for time values");
                requiredFieldLabel.setVisible(true);
                return;
            }

            try {
                PresetDao.createPreset(newPresetTitleTxt.getText(), newPresetHourTxt.getText(), newPresetMinTxt.getText(), newPresetSecTxt.getText());
                PresetDao.updatePresetList();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            newPresetTitleTxt.setText("");
            newPresetHourTxt.setText("");
            newPresetMinTxt.setText("");
            newPresetSecTxt.setText("");

            //Close modal
            Stage stage = (Stage) closeNewPresetModalBtn.getScene().getWindow();
            stage.close();
        });
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
