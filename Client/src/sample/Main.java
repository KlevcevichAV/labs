package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.controller.Controller;
import sample.parser.Translator;

import java.io.*;
import java.net.Socket;

public class Main extends Application {
    void createClient(Stage primaryStage) throws IOException {
        String host = Translator.host();
        int port = Translator.port();
        Socket clientSocket = new Socket(host, port); // этой строкой мы запрашиваем
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        Controller controller = new Controller(primaryStage, clientSocket, reader, in, out);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        createClient(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
