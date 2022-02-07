package org.anelda.isakacountdowntimer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.anelda.isakacountdowntimer.model.DataModel;
import org.anelda.isakacountdowntimer.model.Moment;
import org.anelda.isakacountdowntimer.option.OptionController;
import org.anelda.isakacountdowntimer.output.OutputController;
import org.anelda.isakacountdowntimer.preset.PresetController;
import org.anelda.isakacountdowntimer.preview.PreviewController;

import java.io.IOException;


public class MainTimerView extends Application  {


    @Override
    public void start(Stage primaryStage) throws Exception {
//        FXMLLoader fxmlLoader = new FXMLLoader(MainTimerView.class.getResource("main-timer-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        stage.setTitle("Isaka Countdown Timer!");
//        stage.setScene(scene);
//        stage.show();



        BorderPane root = new BorderPane();

        GridPane rootGrid = new GridPane();

        FXMLLoader outputLoader = new FXMLLoader(getClass().getResource("output/output.fxml"));
        outputLoader.setController(new OutputController());
        Node outputNode = outputLoader.load();

        FXMLLoader previewLoader = new FXMLLoader(getClass().getResource("preview/preview.fxml"));
        previewLoader.setController(new PreviewController());
        Node previewNode = previewLoader.load();

        FXMLLoader presetLoader = new FXMLLoader(getClass().getResource("preset/preset.fxml"));
        presetLoader.setController(new PresetController());
        Node presetNode = presetLoader.load();

        FXMLLoader optionLoader = new FXMLLoader(getClass().getResource("option/option.fxml"));
        optionLoader.setController(new OptionController());
        Node optionNode = optionLoader.load();

        TextField previewTextField = new TextField();
        previewTextField.setId("txtPreviewMessage");
        previewTextField.prefHeight(25.0);

        Button showTextButton = new Button();
        showTextButton.setText("Show Text");

        Button setOutputButton = new Button();
        setOutputButton.setId("setOutputButton");
        setOutputButton.setText("SET &gt;&gt;");

        Pane previewControlPane = new Pane();
        previewControlPane.getChildren().add(previewTextField);
        previewControlPane.getChildren().add(showTextButton);


        rootGrid.addRow(0, previewNode, outputNode);
        rootGrid.addRow(1, presetNode, optionNode);

//        rootGrid.setHgap(10);

        OutputController outputController = outputLoader.getController();
        PreviewController previewController = previewLoader.getController();
        PresetController presetController = presetLoader.getController();
        OptionController optionController = optionLoader.getController();

        DataModel model = new DataModel();
        Moment moment = new Moment(1,4,59);
        model.setCurrentMoment(moment);

        outputController.initModel(model);
        previewController.initModel(model);
        presetController.initModel(model);
        optionController.initModel(model);

        Scene scene = new Scene(rootGrid);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
