package org.anelda.wizston.countdowntimer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import org.anelda.wizston.countdowntimer.model.DataModel;
import org.anelda.wizston.countdowntimer.model.Moment;
import org.anelda.wizston.countdowntimer.option.OptionController;
import org.anelda.wizston.countdowntimer.output.OutputController;
import org.anelda.wizston.countdowntimer.output.OutputWrapperController;
import org.anelda.wizston.countdowntimer.preset.PresetController;
import org.anelda.wizston.countdowntimer.preview.PreviewController;


public class MainTimerView extends Application  {


    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();

        GridPane rootGrid = new GridPane();

        FXMLLoader outputLoader = new FXMLLoader(getClass().getResource("output/output.fxml"));

        OutputWrapperController outputWrapperController = new OutputWrapperController();
        OutputController outputController = new OutputController();

        Callback<Class<?>, Object> controllerFactory = type -> {
            if (type == OutputController.class) {
                return outputController ;
            } else if (type == OutputWrapperController.class) {
                return outputWrapperController ;
            } else {
                // default behavior for controllerFactory:
                try {
                    return type.newInstance();
                } catch (Exception exc) {
                    exc.printStackTrace();
                    throw new RuntimeException(exc); // fatal, just bail...
                }
            }
        };

        outputLoader.setControllerFactory(controllerFactory);

        Pane outputNode = outputLoader.load();

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

        //OutputController outputController = outputLoader.getController();
        PreviewController previewController = previewLoader.getController();
        PresetController presetController = presetLoader.getController();
        OptionController optionController = optionLoader.getController();

        DataModel model = new DataModel();
        Moment moment = new Moment(1,4,59);
        model.setCurrentMoment(moment);

        Scene scene = new Scene(rootGrid);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/logo.png"));
        primaryStage.setTitle("Wizston:Countdown Timer 0.1");
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(false);
        primaryStage.show();

        outputController.initModel(model);
        previewController.initModel(model);
        presetController.initModel(model);
        optionController.initModel(model);

        //Close every other windows when main window closes
        primaryStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
    }

    private void closeWindowEvent(WindowEvent event) {
        System.out.println("Window close request ...");
        Platform.exit();
    }

    @Override
    public void stop(){
        System.out.println("Stage is closing");
        Platform.exit();
    }
    public static void main(String[] args) {
        launch();
    }
}
