package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.controller.Controller;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        primaryStage.setTitle("Calculator");
        Controller controller = new Controller();
        root.setCenter(controller.getView().getKeyboard().getKeyboard());
        root.setTop(controller.getView().getDisplay().getScrollPane());
        root.setStyle(Constant.BLACK_FILL);
        primaryStage.setScene(new Scene(root, 235, 425, Color.BLACK));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
