package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class View {
    private BorderPane root;
    private Toolbar toolbar;
    private TableView table;

    void arrangement(){
        root.setTop(toolbar.getToolbar());
        root.setCenter(table);
        root.setBottom(toolbar.getPages());
        table.setMaxHeight(270);
    }

    void createTable(){
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

    void initialize(){
        toolbar = new Toolbar();
        createTable();
        arrangement();
    }

    ObservableList<Sportsman> getUserList() {
        Sportsman sportsman1 = new Sportsman("Месси", 0, "Нападающий",
                39, "Футбол", 0);
        Sportsman sportsman2 = new Sportsman("Роналду", 0, "Нападающий",
                39, "Футбол", 0);
        Sportsman sportsman3 = new Sportsman("Суарез", 0, "Нападающий",
                29, "Футбол", 0);
        Sportsman sportsman4 = new Sportsman("Обамеянг", 0, "Нападающий",
                10, "Футбол", 0);
        ObservableList<Sportsman> list = FXCollections.observableArrayList(sportsman1, sportsman2, sportsman3, sportsman4);
        return list;
    }

    void fillingTable(ObservableList<Sportsman> list){
        table.setItems(list);
    }

    public Button getAddButton(){
        return toolbar.add;
    }

    public Button getOpenButton(){
        return toolbar.load;
    }

    public Button getSaveButton(){
        return toolbar.save;
    }

    public Button getPageOneButton(){
        return toolbar.pageOne;
    }

    public Button getPagePrevButton(){
        return toolbar.pagePrev;
    }

    public Button getPageNextButton(){
        return toolbar.pageNext;
    }

    public Button getEditNumberRow(){
        return toolbar.numberRow;
    }

    public Button getPageLastButton(){
        return toolbar.pageLast;
    }

    public TextField getNumberRowField(){
        return toolbar.numberRowField;
    }

    void disableLeft(){
        toolbar.pageOne.setDisable(true);
        toolbar.pagePrev.setDisable(true);
    }

    void disableRight(){
        toolbar.pageNext.setDisable(true);
        toolbar.pageLast.setDisable(true);
    }

    void enableLeft(){
        toolbar.pageOne.setDisable(false);
        toolbar.pagePrev.setDisable(false);
    }

    void enableRight(){
        toolbar.pageNext.setDisable(false);
        toolbar.pageLast.setDisable(false);
    }

    public  Button getSearch(){
        return toolbar.search;
    }

    public Button getDelete(){
        return toolbar.delete;
    }

    public Label getQuantityPages(){
        return toolbar.quantityPages;
    }

    public Label getLabel(){
        return toolbar.numberPage;
    }

    public Label getCounterElements(){
        return toolbar.counterElements;
    }

    View(Stage primaryStage, ObservableList<Sportsman> list){
        root = new BorderPane();
        initialize();
        fillingTable(list);
        disableLeft();
        disableRight();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.show();
    }
}
