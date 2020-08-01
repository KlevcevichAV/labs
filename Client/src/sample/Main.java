package sample;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import sample.controller.Controller;
import sample.parser.Translator;

import java.io.*;
import java.net.Socket;

public class Main extends Application {
    void createClient(Stage primaryStage) throws IOException {
        String host = Translator.host();
        int port = Translator.port();
        try {
            Socket clientSocket = new Socket(host, port); // этой строкой мы запрашиваем
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            Controller controller = new Controller(primaryStage, clientSocket, reader, in, out);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setTitle("Error");
            alert.setHeaderText("Сервер не запущен.");
            alert.setContentText("Запустите приложение после запуска сервера.");

            alert.showAndWait();
            System.exit(0);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        createClient(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
