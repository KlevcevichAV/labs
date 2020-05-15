package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main extends Application {
    Button button1;
    void createClient(Stage primaryStage) throws IOException {
        Socket clientSocket = new Socket("localhost", 4004); // этой строкой мы запрашиваем
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        AtomicBoolean check = new AtomicBoolean(true);
        while (check.get()){
            System.out.println("Вы что-то хотели сказать? Введите это здесь:");
            String word = reader.readLine(); // ждём пока клиент что-нибудь
            out.write(word + "\n"); // отправляем сообщение на сервер
            out.flush();
            String serverWord = in.readLine(); // ждём, что скажет сервер
            System.out.println(serverWord); // получив - выводим на экран
            if(serverWord.equals("Привет, это Сервер! Подтверждаю, вы написали : exit"))break;
        }
        in.close();
        out.close();
        System.out.println("Клиент был закрыт...");
        clientSocket.close();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);
        root.setVgap(10);

        // Button 1
        button1 = new Button("Button with Text");

        root.getChildren().addAll(button1);

        primaryStage.setTitle("Java Button (o7planning.org)");
        button1.setOnAction(e->{
            try {
                createClient(primaryStage);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        Scene scene = new Scene(root, 350, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
