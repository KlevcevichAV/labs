package sample.view.modalWindow;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalWindowLoad {
    private static Stage window;
    private static Button load;
    public static String table;
    public static String nameFile;
    private static ComboBox<String> listFile;

    private static ComboBox<String> createList(String listString) {
        ComboBox<String> list = new ComboBox<>();
        String temp = "";
        for (int i = 0; i < listString.length(); i++) {
            if (listString.charAt(i) == '\n' || i + 1 == listString.length()) {
                list.getItems().add(temp.toString());
                temp = "";
            } else temp = temp + listString.charAt(i);
        }
        return list;
    }

    private static void initialize(String serverWord) {
        load = new Button("Load");
        listFile = createList(serverWord);
        load.setOnAction(e -> {
            for (int i = 0; i < listFile.getItems().size(); i++) {
                if (listFile.getItems().get(i).equals(listFile.getSelectionModel().getSelectedItem())) {
                    nameFile = listFile.getItems().get(i);
                    window.close();
                }
            }
        });
    }

    static boolean checkEnd(String string) {
        return string.equals("end");
    }

    public static void newWindow(String serverWord) {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Pane root = new Pane();
        initialize(serverWord);
        VBox vBox = new VBox(listFile, load);
        root.getChildren().add(vBox);
        Scene scene = new Scene(root, 200, 100);
        window.setScene(scene);
        window.setTitle("Add");
        window.showAndWait();
    }
}
