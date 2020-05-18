package sample.view.modalWindow;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.data.Constant;
import sample.data.Sportsman;

import java.io.BufferedWriter;
import java.io.IOException;

public class ModalWindowAdd {
    private static Stage window;
    private static TextField fullNameEdit, positionEdit, titleEdit, kindOfSportEdit;
    private static RadioButton main, spare, na;
    private static RadioButton masterOfSport, kms, category3, category2, category1;
    private static Button add, cancel;
    private static Sportsman result;

    private static void createGroupStructure() {
        main = new RadioButton(Constant.MAIN);
        main.setSelected(true);
        spare = new RadioButton(Constant.SPARE);
        na = new RadioButton(Constant.NA);
        ToggleGroup groupStructure = new ToggleGroup();
        main.setToggleGroup(groupStructure);
        spare.setToggleGroup(groupStructure);
        na.setToggleGroup(groupStructure);
    }

    private static void createGroupCategory() {
        masterOfSport = new RadioButton(Constant.MASTER_OF_SPORT);
        masterOfSport.setSelected(true);
        kms = new RadioButton(Constant.СMS);
        category3 = new RadioButton(Constant.DISCHARGE3);
        category2 = new RadioButton(Constant.DISCHARGE2);
        category1 = new RadioButton(Constant.DISCHARGE1);
        ToggleGroup group = new ToggleGroup();
        masterOfSport.setToggleGroup(group);
        kms.setToggleGroup(group);
        category3.setToggleGroup(group);
        category2.setToggleGroup(group);
        category1.setToggleGroup(group);
    }

    private static int getStructure() {
        if (main.isSelected()) {
            return 0;
        } else if (spare.isSelected()) return 1;
        else return 2;
    }

    private static int getCategory() {
        if (masterOfSport.isSelected()) {
            return 0;
        } else {
            if (kms.isSelected()) {
                return 1;
            } else if (category3.isSelected()) {
                return 2;
            } else if (category2.isSelected()) {
                return 3;
            } else return 4;
        }
    }

    private static void createButton(BufferedWriter out) {
        add = new Button("Add");
        cancel = new Button("Cancel");
        add.setOnAction(e -> {
            int title = Integer.parseInt(titleEdit.getText());
            result = new Sportsman(fullNameEdit.getText(), getStructure(),
                    positionEdit.getText(), title,
                    kindOfSportEdit.getText(), getCategory());
            try {
                out.write(Constant.ADD + "\n");
                out.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            window.close();
        });
        cancel.setOnAction(e -> {
            result = null;
            window.close();
        });
    }

    private static void initialize(BufferedWriter out) {
        fullNameEdit = new TextField();
        fullNameEdit.setPrefWidth(400);
        positionEdit = new TextField();
        positionEdit.setPrefWidth(450);
        titleEdit = new TextField();
        titleEdit.setPrefWidth(448);
        kindOfSportEdit = new TextField();
        kindOfSportEdit.setPrefWidth(447);
        createGroupStructure();
        createGroupCategory();
        createButton(out);
    }

    private static VBox createVBox() {
        HBox fullName = new HBox(new Label(Constant.FULL_NAME), fullNameEdit);
        fullName.setSpacing(30);
        HBox structure = new HBox(new Label(Constant.STRUCTURE), main, spare, na);
        structure.setSpacing(70);
        HBox position = new HBox(new Label(Constant.POSITION), positionEdit);
        position.setSpacing(30);
        HBox title = new HBox(new Label(Constant.TITLE), titleEdit);
        title.setSpacing(40);
        HBox kindOfSport = new HBox(new Label(Constant.KIND_OF_SPORT), kindOfSportEdit);
        kindOfSport.setSpacing(12);
        HBox category = new HBox(new Label(Constant.CATEGORY), masterOfSport, kms, category3, category2, category1);
        category.setSpacing(5);
        HBox button = new HBox(add, cancel);
        button.setSpacing(100);
        VBox vBox = new VBox(fullName, structure, position, title, kindOfSport, category, button);
        vBox.setSpacing(10);
        return vBox;
    }

    public static void newWindow(BufferedWriter out) {
        result = null;
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Pane root = new Pane();
        initialize(out);
        VBox vBox = createVBox();
        root.getChildren().add(vBox);
        Scene scene = new Scene(root, 550, 300);
        window.setScene(scene);
        window.setTitle("Add");
        window.showAndWait();
    }

    public static Sportsman getResult() {
        return result;
    }
}
