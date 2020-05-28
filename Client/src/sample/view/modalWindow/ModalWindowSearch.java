package sample.view.modalWindow;

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
import sample.controller.Controller;
import sample.data.Constant;
import sample.data.Sportsman;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModalWindowSearch {
    public static Stage window;
    private static int choice = 0;
    private static MenuButton kindOfSportButton, categoryButton;
    private static RadioButton fullNameOrKindOfSport, title, fullNameOrCategory;
    private static TextField condition1Field, condition2Field;
    private static Label condition1Label, condition2Label;
    private static Button search, cancel;
    public static TableView table;
    public static int pointerChoice;
    private static String choiceSearch = "";
    private static Controller controller;

    private static void createTable() {
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

    private static void createGroup() {
        pointerChoice = 1;
        fullNameOrKindOfSport = new RadioButton("По ФИО или виду спорта");
        fullNameOrKindOfSport.setSelected(true);
        fullNameOrKindOfSport.setOnAction(e -> {
            pointerChoice = 1;
            condition2Field.setVisible(false);
            categoryButton.setVisible(false);
            kindOfSportButton.setVisible(true);
            choice = 0;
            condition1Label.setText(Constant.FULL_NAME);
            condition2Label.setText(Constant.KIND_OF_SPORT);
        });
        title = new RadioButton("По количеству титулов");
        title.setOnAction(e -> {
            pointerChoice = 2;
            condition2Field.setVisible(true);
            categoryButton.setVisible(false);
            kindOfSportButton.setVisible(false);
            choice = 1;
            condition1Label.setText("Минимум");
            condition2Label.setText("Максимум");
        });
        fullNameOrCategory = new RadioButton("По ФИО или разряду");
        fullNameOrCategory.setOnAction(e -> {
            pointerChoice = 3;
            condition2Field.setVisible(false);
            categoryButton.setVisible(true);
            kindOfSportButton.setVisible(false);
            choice = 2;
            condition1Label.setText(Constant.FULL_NAME);
            condition2Label.setText(Constant.CATEGORY);
        });
        ToggleGroup groupStructure = new ToggleGroup();
        fullNameOrKindOfSport.setToggleGroup(groupStructure);
        title.setToggleGroup(groupStructure);
        fullNameOrCategory.setToggleGroup(groupStructure);
    }

    private static void createButton(List<Sportsman> list) {
        search = new Button("Search");
        cancel = new Button("Cancel");
        search.setOnAction(e -> {
            table.setItems(search(choice, condition1Field.getText(), condition2Field.getText(), (ObservableList<Sportsman>) list));
            try {
                controller.dialogSearch2();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        cancel.setOnAction(e -> {
            try {
                controller.cancelSearch();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            window.close();
        });
    }

    private static void initialize(List<Sportsman> list) {
        condition1Field = new TextField();
        condition1Field.setPrefWidth(400);
        condition2Field = new TextField();
        condition2Field.setVisible(false);
        condition2Field.setPrefWidth(450);
        condition1Label = new Label(Constant.FULL_NAME);
        condition2Label = new Label(Constant.KIND_OF_SPORT);
        createGroup();
        createButton(list);
        createTable();
        createCategoryButton(list);
        createKindOfSportButton(list);
    }

    private static VBox createVBox() {
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

    private static boolean find(List<String> list, String element) {
        for (String condition : list) {
            if (condition.equals(element)) return true;
        }
        return false;
    }

    private static void createKindOfSportButton(List<Sportsman> list) {
        List<String> nameButton = new ArrayList<>();
        kindOfSportButton = new MenuButton(Constant.KIND_OF_SPORT);
        MenuItem temp1 = new MenuItem("-");
        kindOfSportButton.getItems().add(temp1);
        temp1.setOnAction(e -> {
            choiceSearch = "";
            kindOfSportButton.setText("-");
        });
        for (Sportsman sportsman : list) {
            if (!find(nameButton, sportsman.getKindOfSport())) {
                nameButton.add(sportsman.getKindOfSport());
                MenuItem temp = new MenuItem(sportsman.getKindOfSport());
                temp.setOnAction(e -> {
                    choiceSearch = sportsman.getKindOfSport();
                    kindOfSportButton.setText(choiceSearch);
                });
                kindOfSportButton.getItems().addAll(temp);
            }
        }
    }

    private static void createCategoryButton(List<Sportsman> list) {
        List<String> nameButton = new ArrayList<>();
        categoryButton = new MenuButton(Constant.CATEGORY);
        categoryButton.setVisible(false);
        MenuItem temp1 = new MenuItem("-");
        categoryButton.getItems().add(temp1);
        temp1.setOnAction(e -> {
            choiceSearch = "";
            categoryButton.setText("-");
        });
        for (Sportsman sportsman : list) {
            if (!find(nameButton, sportsman.getCategory())) {
                nameButton.add(sportsman.getCategory());
                MenuItem temp = new MenuItem(sportsman.getCategory());
                temp.setOnAction(e -> {
                    choiceSearch = sportsman.getCategory();
                    categoryButton.setText(choiceSearch);
                });
                categoryButton.getItems().addAll(temp);
            }
        }
    }

    public static void newWindow(List<Sportsman> list, Controller controller1) {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        controller = controller1;
        Pane root = new Pane();
        initialize(list);
        table.setItems((ObservableList) list);
        VBox vBox = createVBox();
        root.getChildren().add(vBox);
        Scene scene = new Scene(root, 610, 600);
        window.setScene(scene);
        window.setTitle("Search");
        window.showAndWait();
    }

    private static ObservableList<Sportsman> searchFIOOrKindOfSport(String condition1, String condition2, ObservableList<Sportsman> list) {
        if (!(condition1.isEmpty() && condition2.isEmpty())) {
            ObservableList<Sportsman> listSearch = FXCollections.observableArrayList();
            for (Sportsman sportsman : list) {
                if (condition1.equals(sportsman.getFullName()) || condition2.equals(sportsman.getKindOfSport())) {
                    listSearch.add(sportsman);
                }
            }
            return listSearch;
        } else return list;
    }

    private static ObservableList<Sportsman> searchFIOOrCategory(String condition1, String condition2, ObservableList<Sportsman> list) {
        if (!condition1.isEmpty() || !condition2.isEmpty()) {
            ObservableList<Sportsman> listSearch = FXCollections.observableArrayList();
            for (Sportsman sportsman : list) {
                if (condition1.equals(sportsman.getFullName()) || condition2.equals(sportsman.getCategory())) {
                    listSearch.add(sportsman);
                }
            }
            return listSearch;
        } else return list;
    }

    private static ObservableList<Sportsman> searchTitle(String condition1, String condition2, ObservableList<Sportsman> list) {
        ObservableList<Sportsman> listSearch = FXCollections.observableArrayList();
        int border1 = Integer.parseInt(condition1);
        int border2 = Integer.parseInt(condition2);
        for (Sportsman sportsman : list) {
            if (sportsman.getTitle() <= border2 && sportsman.getTitle() >= border1) {
                listSearch.add(sportsman);
            }
        }
        return listSearch;
    }

    private static ObservableList<Sportsman> search(int choice, String condition1, String condition2, ObservableList<Sportsman> list) {
        switch (choice) {
            case 0: {
                return searchFIOOrKindOfSport(condition1, choiceSearch, list);
            }
            case 1: {
                return searchTitle(condition1, condition2, list);
            }
            default: {
                return searchFIOOrCategory(condition1, choiceSearch, list);
            }
        }
    }
}
