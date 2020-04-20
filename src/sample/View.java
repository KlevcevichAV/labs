package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
//        table.rowFactoryProperty().set(10);
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

    View(Stage primaryStage, ObservableList<Sportsman> list){
        root = new BorderPane();
        initialize();
        fillingTable(list);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.show();
    }
}
