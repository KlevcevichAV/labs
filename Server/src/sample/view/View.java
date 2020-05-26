package sample.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class View {
    private Button run;
    private Button stop;
    private TextArea console;

    public TextArea getConsole() {
        return console;
    }

    public Button getRun() {
        return run;
    }

    public Button getStop() {
        return stop;
    }

    private Scene createScene() {
        BorderPane root = new BorderPane();
        run = new Button("Запустить сервер");
        stop = new Button("Остановить сервер");
        HBox buttonBox = new HBox(run, stop);
        console = new TextArea();
        console.setEditable(false);
        root.setTop(buttonBox);
        root.setCenter(console);
        Scene scene = new Scene(root, 400, 350);
        return scene;
    }

    public View(Stage primaryStage) {
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(createScene());
        primaryStage.show();
    }
}
