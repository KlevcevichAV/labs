package sample;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModalWindowAdd {
    static Stage window;
    static TextField fullNameEdit, positionEdit, titleEdit, kindOfSportEdit;
    static RadioButton main , spare, na;
    static RadioButton masterOfSport , kms, category3, category2, category1;
    static Button add, cancel;
    static Sportsman result;
    static void createGroupStructure(){
        main = new RadioButton("Основной");
        main.setSelected(true);
        spare = new RadioButton("Запасной");
        na = new RadioButton("n/a");
        ToggleGroup groupStructure = new ToggleGroup();
        main.setToggleGroup(groupStructure);
        spare.setToggleGroup(groupStructure);
        na.setToggleGroup(groupStructure);
    }

    static void createGroupCategory(){
        masterOfSport = new RadioButton("Мастер спорта");
        masterOfSport.setSelected(true);
        kms = new RadioButton("кмс");
        category3 = new RadioButton("3-й разряд");
        category2 = new RadioButton("2-й разряд");
        category1 = new RadioButton("1-й разряд");
        ToggleGroup group = new ToggleGroup();
        masterOfSport.setToggleGroup(group);
        kms.setToggleGroup(group);
        category3.setToggleGroup(group);
        category2.setToggleGroup(group);
        category1.setToggleGroup(group);
    }

    static int getStructure(){
        if(main.isSelected()){
            return 0;
        }else if(spare.isSelected())return 1; else return 2;
    }

    static int getCategory(){
        if(masterOfSport.isSelected()){
            return 0;
        }else{
            if(kms.isSelected()){
                return 1;
            } else if (category3.isSelected()) {
                return 2;
            }else if(category2.isSelected()){
                return 3;
            }else return 4;
        }
    }

    static void createButton(){
        add = new Button("Add");
        cancel = new Button("Cancel");
        add.setOnAction(e->{
            int title = Integer.parseInt(titleEdit.getText());
            result = new Sportsman(fullNameEdit.getText(),getStructure(),
                    positionEdit.getText(), title,
                    kindOfSportEdit.getText(), getCategory());
            window.close();
        });
        cancel.setOnAction(e->{
            result = null;
            window.close();
        });
    }

    static void initialize(){
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
        createButton();
    }

    static VBox createVBox(){
        HBox fullName = new HBox(new Label("ФИО спортсмена"), fullNameEdit);
        fullName.setSpacing(30);
        HBox structure = new HBox(new Label("Состав"), main, spare, na);
        structure.setSpacing(70);
        HBox position = new HBox(new Label("Позиция"), positionEdit);
        position.setSpacing(30);
        HBox title = new HBox(new Label("Титулы"), titleEdit);
        title.setSpacing(40);
        HBox kindOfSport = new HBox(new Label("Вид спорта"), kindOfSportEdit);
        kindOfSport.setSpacing(12);
        HBox category = new HBox(new Label("Разряд"), masterOfSport, kms, category3, category2, category1);
        category.setSpacing(5);
        HBox button = new HBox(add, cancel);
        button.setSpacing(100);
        VBox vBox = new VBox(fullName, structure, position, title, kindOfSport, category, button);
        vBox.setSpacing(10);
        return vBox;
    }

    public static void newWindow(){
        result = null;
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Pane root = new Pane();
        initialize();
        VBox vBox = createVBox();
        root.getChildren().add(vBox);
        Scene scene = new Scene(root, 550, 300);
        window.setScene(scene);
        window.setTitle("Add");
        window.showAndWait();
    }

    static public Sportsman getResult(){
        return result;
    }
}
