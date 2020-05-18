package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.controller.Controller;

import java.io.*;
import java.net.Socket;

public class Main extends Application {
    void createClient(Stage primaryStage) throws IOException {
        Socket clientSocket = new Socket("localhost", 4004); // этой строкой мы запрашиваем
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        Controller controller = new Controller(primaryStage, clientSocket, reader, in, out);
        if(!controller.check){
            out.write("/exit");
            out.flush();
            in.close();
            out.close();
            System.out.println("Клиент был закрыт...");
            clientSocket.close();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        createClient(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
