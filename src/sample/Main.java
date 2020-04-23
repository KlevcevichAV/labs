package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.MVC.Controller;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Controller controller = new Controller(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
