package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ModalWindowSearch {
    static Stage window;
    static int choice = 0;
    static MenuButton kindOfSportButton, categoryButton;
    static RadioButton fullNameOrKindOfSport , title, fullNameOrCategory;
    static TextField condition1Field, condition2Field;
    static Label condition1Label, condition2Label;
    static Button search, cancel;
    static TableView table;
    static String choiceSearch = new String();

    static void createTable(){
        table = new TableView();
        TableColumn<Sportsman, String> fullNameCol = new TableColumn<>("ФИО");
        TableColumn<Sportsman, String> structure = new TableColumn<>("Состав");
        TableColumn<Sportsman, String> position = new TableColumn<>("Позиция");
        TableColumn<Sportsman, Integer> title = new TableColumn<>("Титулы");
        TableColumn<Sportsman, String> kindOfSport = new TableColumn<>("Вид спорта");
        TableColumn<Sportsman, String> category = new TableColumn<>("Разряд");

        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        structure.setCellValueFactory(new PropertyValueFactory<>("structure"));
        position.setCellValueFactory(new PropertyValueFactory<>("position"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        kindOfSport.setCellValueFactory(new PropertyValueFactory<>("kindOfSport"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));

        table.getColumns().addAll(fullNameCol, structure, position, title, kindOfSport, category);
    }

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

    static void createButton(ObservableList<Sportsman> list){
        search = new Button("Search");
        cancel = new Button("Cancel");
        search.setOnAction(e->{
            table.setItems(search(choice, condition1Field.getText(), condition2Field.getText(), list));
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
        createButton(list);
        createTable();
        createCategoryButton(list);
        createKindOfSportButton(list);
    }

    static VBox createVBox(){
        HBox choice = new HBox(fullNameOrKindOfSport, title, fullNameOrCategory);
        choice.setSpacing(30);
        HBox condition1 = new HBox(condition1Label, condition1Field);
        condition1.setSpacing(70);
        Group edit = new Group(kindOfSportButton, condition2Field, categoryButton);
        HBox condition2 = new HBox(condition2Label, edit);
        condition2.setSpacing(30);
        VBox vBox = new VBox(choice, condition1, condition2, table, search, cancel);
        vBox.setSpacing(10);
        return vBox;
    }

    static boolean find(ArrayList<String> list, String element){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).equals(element)) return true;
        }
        return false;
    }

    static void createKindOfSportButton(ObservableList<Sportsman> list){
        ArrayList<String> nameButton = new ArrayList<>();
        kindOfSportButton = new MenuButton("Вид спорта");
        MenuItem temp1 = new MenuItem("-");
        kindOfSportButton.getItems().add(temp1);
        temp1.setOnAction(e->{
            kindOfSportButton.setText("-");
        });
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

    static void createCategoryButton(ObservableList<Sportsman> list){
        ArrayList<String> nameButton = new ArrayList<>();
        categoryButton = new MenuButton("Разряд");
        categoryButton.setVisible(false);
        MenuItem temp1 = new MenuItem("-");
        categoryButton.getItems().add(temp1);
        temp1.setOnAction(e->{
            categoryButton.setText("-");
        });
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
        table.setItems(list);
        VBox vBox = createVBox();
        root.getChildren().add(vBox);
        Scene scene = new Scene(root, 550, 600);
        window.setScene(scene);
        window.setTitle("Search");
        window.showAndWait();
    }

    static ObservableList<Sportsman> searchFIOOrKindOfSport(String condition1, String condition2, ObservableList<Sportsman> list){
        if(!(condition1.isEmpty() && condition2.isEmpty())){
            ObservableList<Sportsman> listSearch = FXCollections.observableArrayList();
            for(int i = 0; i < list.size(); i++){
                if(condition1.equals(list.get(i).getFullName()) || condition2.equals(list.get(i).getKindOfSport())){
                    listSearch.add(list.get(i));
                }
            }
            return listSearch;
        }else return list;
    }

    static ObservableList<Sportsman> searchFIOOrCategory(String condition1, String condition2, ObservableList<Sportsman> list){
        if(!condition1.isEmpty() || !condition2.isEmpty()){
            ObservableList<Sportsman> listSearch = FXCollections.observableArrayList();
            for(int i = 0; i < list.size(); i++){
                if(condition1.equals(list.get(i).getFullName()) || condition2.equals(list.get(i).getCategory())){
                    listSearch.add(list.get(i));
                }
            }
            return listSearch;
        } else return list;
    }

    static ObservableList<Sportsman> searchTitle(String condition1, String condition2, ObservableList<Sportsman> list){
        ObservableList<Sportsman> listSearch = FXCollections.observableArrayList();
        int border1 = Integer.parseInt(condition1);
        int border2 = Integer.parseInt(condition2);
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getTitle() <= border2 && list.get(i).getTitle() >= border1){
                listSearch.add(list.get(i));
            }
        }
        return listSearch;
    }

    static ObservableList<Sportsman> search(int choice, String condition1, String condition2, ObservableList<Sportsman> list){
        switch (choice){
            case 0:{
                return searchFIOOrKindOfSport(condition1, choiceSearch, list);
            }
            case 1:{
                return searchTitle(condition1, condition2, list);
            }
            default:{
                return searchFIOOrCategory(condition1, choiceSearch, list);
            }
        }
    }
}
