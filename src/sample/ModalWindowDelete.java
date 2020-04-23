package sample;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ModalWindowDelete {
    static Stage window;
    static int choice = 0;
    static int counterDelete = 0;
    static MenuButton kindOfSportButton, categoryButton;
    static RadioButton fullNameOrKindOfSport , title, fullNameOrCategory;
    static TextField condition1Field, condition2Field;
    static Label condition1Label, condition2Label;
    static Button delete, cancel;
    static String choiceSearch = new String();
    static ObservableList<Sportsman> list;
    static void createGroup(){
        fullNameOrKindOfSport = new RadioButton("По ФИО или виду спорта");
        fullNameOrKindOfSport.setSelected(true);
        fullNameOrKindOfSport.setOnAction(e->{
            condition2Field.setVisible(false);
            categoryButton.setVisible(false);
            kindOfSportButton.setVisible(true);
            choice = 0;
            condition1Label.setText("ФИО");
            condition2Label.setText("Вид спорта");
        });
        title = new RadioButton("По количеству титулов");
        title.setOnAction(e->{
            condition2Field.setVisible(true);
            categoryButton.setVisible(false);
            kindOfSportButton.setVisible(false);
            choice = 1;
            condition1Label.setText("Минимум");
            condition2Label.setText("Максимум");
        });
        fullNameOrCategory = new RadioButton("По ФИО или разряду");
        fullNameOrCategory.setOnAction(e->{
            condition2Field.setVisible(false);
            categoryButton.setVisible(true);
            kindOfSportButton.setVisible(false);
            choice = 2;
            condition1Label.setText("ФИО");
            condition2Label.setText("Разряд");
        });
        ToggleGroup groupStructure = new ToggleGroup();
        fullNameOrKindOfSport.setToggleGroup(groupStructure);
        title.setToggleGroup(groupStructure);
        fullNameOrCategory.setToggleGroup(groupStructure);
    }

    static void createButton(){
        delete = new Button("Delete");
        cancel = new Button("Cancel");
        delete.setOnAction(e->{
            delete(choice, condition1Field.getText(), condition2Field.getText());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            if(counterDelete == 0){
                alert.setContentText("Ничего не найдено!");
            }else alert.setContentText("Удалено спортсменов: " + counterDelete);
            counterDelete = 0;
            alert.showAndWait();
            window.close();
        });
        cancel.setOnAction(e->{
            window.close();
        });
    }

    static void initialize(ObservableList<Sportsman> list){
        condition1Field = new TextField();
        condition1Field.setPrefWidth(400);
        condition2Field = new TextField();
        condition2Field.setVisible(false);
        condition2Field.setPrefWidth(450);
        condition1Label = new Label("ФИО");
        condition2Label = new Label("Вид спорта");
        createGroup();
        ModalWindowDelete.list = list;
        createButton();
        createCategoryButton();
        createKindOfSportButton();
    }

    static VBox createVBox(){
        HBox choice = new HBox(fullNameOrKindOfSport, title, fullNameOrCategory);
        choice.setSpacing(30);
        HBox condition1 = new HBox(condition1Label, condition1Field);
        condition1.setSpacing(70);
        Group edit = new Group(kindOfSportButton, condition2Field, categoryButton);
        HBox condition2 = new HBox(condition2Label, edit);
        condition2.setSpacing(30);
        VBox vBox = new VBox(choice, condition1, condition2, delete, cancel);
        vBox.setSpacing(10);
        return vBox;
    }

    static boolean find(ArrayList<String> list, String element){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).equals(element)) return true;
        }
        return false;
    }

    static void createKindOfSportButton(){
        ArrayList<String> nameButton = new ArrayList<>();
        kindOfSportButton = new MenuButton("Вид спорта");
        for(int i = 0; i < list.size(); i++){
            if(!find(nameButton, list.get(i).getKindOfSport())){
                nameButton.add(list.get(i).getKindOfSport());
                MenuItem temp = new MenuItem(list.get(i).getKindOfSport());
                int finalI = i;
                temp.setOnAction(e->{
                    choiceSearch = list.get(finalI).getKindOfSport();
                    kindOfSportButton.setText(choiceSearch);
                });
                kindOfSportButton.getItems().addAll(temp);
            }
        }
    }

    static void createCategoryButton(){
        ArrayList<String> nameButton = new ArrayList<>();
        categoryButton = new MenuButton("Разряд");
        categoryButton.setVisible(false);
        choiceSearch = new String();
        for(int i = 0; i < list.size(); i++){
            if(!find(nameButton, list.get(i).getCategory())){
                nameButton.add(list.get(i).getCategory());
                MenuItem temp = new MenuItem(list.get(i).getCategory());
                int finalI = i;
                temp.setOnAction(e->{
                    choiceSearch = list.get(finalI).getCategory();
                    categoryButton.setText(choiceSearch);
                });
                categoryButton.getItems().addAll(temp);
            }
        }
    }

    public static void newWindow(ObservableList<Sportsman> list){
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        Pane root = new Pane();
        initialize(list);
        VBox vBox = createVBox();
        root.getChildren().add(vBox);
        Scene scene = new Scene(root, 610, 200);
        window.setScene(scene);
        window.setTitle("Delete");
        window.showAndWait();
    }

    static void deleteFIOOrKindOfSport(String condition1, String condition2, ObservableList<Sportsman> list){
        if(!(condition1.isEmpty() && condition2.isEmpty())){
            for(int i = 0; i < list.size(); i++){
                if(condition1.equals(list.get(i).getFullName()) || condition2.equals(list.get(i).getKindOfSport())){
                    list.remove(i--);
                    counterDelete++;
                }
            }
        }
    }

    static void deleteFIOOrCategory(String condition1, String condition2, ObservableList<Sportsman> list){
        if(!condition1.isEmpty() || !condition2.isEmpty()){
            for(int i = 0; i < list.size(); i++){
                if(condition1.equals(list.get(i).getFullName()) || condition2.equals(list.get(i).getCategory())){
                    list.remove(i--);
                    counterDelete++;
                }
            }
        }
    }

    static void deleteTitle(String condition1, String condition2, ObservableList<Sportsman> list){
        int border1 = Integer.parseInt(condition1);
        int border2 = Integer.parseInt(condition2);
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getTitle() <= border2 && list.get(i).getTitle() >= border1){
                list.remove(i--);
                counterDelete++;
            }
        }
    }

    static void delete(int choice, String condition1, String condition2){
        switch (choice){
            case 0:{
                deleteFIOOrKindOfSport(condition1, choiceSearch, list);
                break;
            }
            case 1:{
                deleteTitle(condition1, condition2, list);
                break;
            }
            default:{
                deleteFIOOrCategory(condition1, choiceSearch, list);
            }
        }
    }
}
