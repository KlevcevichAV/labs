package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.view.keyboard.DigitalKeyboard;
import sample.view.keyboard.Keyboard;
import sample.view.keyboard.KeyboardOperations;
import sample.view.display.Display;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        primaryStage.setTitle("Hello World");
        Display display = new Display();
        display.setText("1231");
        Keyboard keyboard = new Keyboard();
        HBox root1 = new HBox(display, keyboard.getKeyboard());
        root1.spacingProperty().set(10);
        root.setCenter(root1);
        root.setTop(display.getScrollPane());
        root.setStyle(Constant.BLACK_FILL);
        primaryStage.setScene(new Scene(root, 235, 425, Color.BLACK));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
