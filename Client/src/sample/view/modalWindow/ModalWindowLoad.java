package sample.view.modalWindow;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ModalWindowLoad {
    private static Stage window;
    private static Button load;
    public static String table;
    private static ComboBox<String> listFile;

    private static ComboBox<String> createList(String listString){
        ComboBox<String> list = new ComboBox<>();
        String temp = "";
        for(int i = 0; i < listString.length(); i++){
            if(listString.charAt(i) == '\n' || i + 1 == listString.length()){
                list.getItems().add(temp.toString());
                temp = "";
            }else temp = temp + listString.charAt(i);
        }
        return list;
    }

    private static void initialize(BufferedReader in, BufferedWriter out) throws IOException {
        load = new Button("Load");
        String serverWord = "";
        while (true) {
            String temp = in.readLine();
            if(!checkEnd(temp))serverWord = serverWord + temp + "\n"; else break;
        }
        listFile = createList(serverWord);
        load.setOnAction(e->{
            for(int i = 0; i < listFile.getItems().size(); i++){
                if (listFile.getItems().get(i).equals(listFile.getSelectionModel().getSelectedItem())) {
                    try {
                        out.write(listFile.getItems().get(i) + "\n");
                        out.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        table = "";
                        while (true){
                            String temp = in.readLine();
                            if(temp.equals("</root>")) {
                                table = table + temp;
                                break;
                            }else table = table + temp + "\n";
                        }
                        window.close();
                        break;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    static boolean checkEnd(String string){
        return string.equals("end");
    }

    public static void newWindow(BufferedReader in, BufferedWriter out) throws IOException {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Pane root = new Pane();
        initialize(in, out);
        VBox vBox = new VBox(listFile, load);
        root.getChildren().add(vBox);
        Scene scene = new Scene(root, 200, 100);
        window.setScene(scene);
        window.setTitle("Add");
        window.showAndWait();
    }
}
