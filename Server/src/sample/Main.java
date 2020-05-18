package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.controller.Controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new Controller(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
