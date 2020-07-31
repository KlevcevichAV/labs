package sample.view;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.data.Constant;
import sample.data.Sportsman;

import java.util.List;

public class View {
    private BorderPane root;
    private Toolbar toolbar;
    private TableView table;
    public MenuBar menu;
    public MenuItem add, search, delete, load, save, exit;

    private void arrangement() {
        VBox top = new VBox(menu, toolbar.getToolbar());
        root.setTop(top);
        root.setCenter(table);
        root.setBottom(toolbar.getPages());
        table.setMaxHeight(270);
    }

    private void createTable() {
        table = new TableView();
        TableColumn<Sportsman, String> fullNameCol = new TableColumn<>(Constant.FULL_NAME);
        TableColumn<Sportsman, String> structure = new TableColumn<>(Constant.STRUCTURE);
        TableColumn<Sportsman, String> position = new TableColumn<>(Constant.POSITION);
        TableColumn<Sportsman, Integer> title = new TableColumn<>(Constant.TITLE);
        TableColumn<Sportsman, String> kindOfSport = new TableColumn<>(Constant.KIND_OF_SPORT);
        TableColumn<Sportsman, String> category = new TableColumn<>(Constant.CATEGORY);

        fullNameCol.setCellValueFactory(new PropertyValueFactory<>(Constant.FULL_NAME_EN));
        structure.setCellValueFactory(new PropertyValueFactory<>(Constant.STRUCTURE_EN));
        position.setCellValueFactory(new PropertyValueFactory<>(Constant.POSITION_EN));
        title.setCellValueFactory(new PropertyValueFactory<>(Constant.TITLE_EN));
        kindOfSport.setCellValueFactory(new PropertyValueFactory<>(Constant.KIND_OF_SPORT_EN));
        category.setCellValueFactory(new PropertyValueFactory<>(Constant.CATEGORY_EN));

        table.getColumns().addAll(fullNameCol, structure, position, title, kindOfSport, category);
    }

    private void initialize() {
        toolbar = new Toolbar();
        Menu file = new Menu("File");
        Menu function = new Menu("Function");
        add = new Menu("Add");
        search = new Menu("Search");
        delete = new Menu("Delete");
        load = new Menu("Load");
        save = new Menu("Save");
        exit = new Menu("Exit");
        file.getItems().addAll(load, save);
        function.getItems().addAll(add, search, delete, exit);
        menu = new MenuBar();
        menu.getMenus().addAll(file, function);
        createTable();
        arrangement();
    }

    public void fillingTable(List<Sportsman> list) {
        table.setItems((ObservableList) list);
    }

    public Button getAddButton() {
        return toolbar.getAdd();
    }

    public Button getOpenButton() {
        return toolbar.getLoad();
    }

    public Button getSaveButton() {
        return toolbar.getSave();
    }

    public Button getPageOneButton() {
        return toolbar.getPageOne();
    }

    public Button getPagePrevButton() {
        return toolbar.getPagePrev();
    }

    public Button getPageNextButton() {
        return toolbar.getPageNext();
    }

    public Button getEditNumberRow() {
        return toolbar.getNumberRow();
    }

    public Button getPageLastButton() {
        return toolbar.getPageLast();
    }

    public TextField getNumberRowField() {
        return toolbar.getNumberRowField();
    }

    public void disableLeft() {
        toolbar.getPageOne().setDisable(true);
        toolbar.getPagePrev().setDisable(true);
    }

    public void disableRight() {
        toolbar.getPageNext().setDisable(true);
        toolbar.getPageLast().setDisable(true);
    }

    public void enableLeft() {
        toolbar.getPageOne().setDisable(false);
        toolbar.getPagePrev().setDisable(false);
    }

    public void enableRight() {
        toolbar.getPageNext().setDisable(false);
        toolbar.getPageLast().setDisable(false);
    }

    public Button getSearch() {
        return toolbar.getSearch();
    }

    public Button getDelete() {
        return toolbar.getDelete();
    }

    public void setQuantityPages(int sizeElements) {
        toolbar.quantityPages.setText("Всего элементов в таблице -- " + sizeElements);
    }

    public void setLabel(int numberPage, int quantityPages) {
        toolbar.numberPage.setText(numberPage + " из " + quantityPages);
    }

    public void setCounterElements(int counterElements, int quantityElements) {
        toolbar.counterElements.setText(counterElements + " из " + quantityElements);
    }

    public void setSettingsTable(int numberRow) {
        table.setMaxHeight(28 + numberRow * 24);
        root.setMaxHeight(115 + 27 + table.getMaxHeight());
    }

    public View(Stage primaryStage, List<Sportsman> list) {
        root = new BorderPane();
        initialize();
        disableLeft();
        disableRight();
        primaryStage.setTitle("Table");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }
}