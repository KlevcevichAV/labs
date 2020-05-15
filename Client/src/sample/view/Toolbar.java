package sample.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Toolbar {
    private VBox root;
    private HBox pages;
    private int pointerPage;
    private Button add, search, delete, load, save, numberRow;
    public Label numbRow, counterElements, quantityPages, numberPage;
    private TextField numberRowField;
    private Button pageOne, pageLast, pagePrev, pageNext;

    public Toolbar() {
        add = new Button("Добавить");
        search = new Button("Поиск");
        delete = new Button("Удаление");
        load = new Button("Загрузить");
        save = new Button("Сохранить");
        numberRow = new Button("Ok");
        numberRowField = new TextField("10");
        numbRow = new Label("Введите количество строк в таблице");
        quantityPages = new Label("Всего элементов в таблице -- 0");
        counterElements = new Label("0 из 0");
        HBox tool = new HBox(add, search, delete, load, save);
        HBox numberPageBox = new HBox(numbRow, numberRowField, numberRow);
        numberPageBox.setSpacing(10);
        HBox groupLabel = new HBox(counterElements, quantityPages);
        groupLabel.setSpacing(20);
        root = new VBox(tool, numberPageBox, groupLabel);
        root.setSpacing(23);
        pageOne = new Button("<<");
        pagePrev = new Button("<");
        numberPage = new Label("1 из 1");
        pageNext = new Button(">");
        pageLast = new Button(">>");
        pages = new HBox(pageOne, pagePrev, numberPage, pageNext, pageLast);
        pages.setSpacing(80);
        pointerPage = 1;
    }

    public VBox getToolbar() {
        return root;
    }

    public HBox getPages() {
        return pages;
    }

    public Button getAdd() {
        return add;
    }

    public Button getSearch() {
        return search;
    }

    public Button getDelete() {
        return delete;
    }

    public Button getLoad() {
        return load;
    }

    public Button getSave() {
        return save;
    }

    public Button getPageOne() {
        return pageOne;
    }

    public Button getPageLast() {
        return pageLast;
    }

    public Button getPagePrev() {
        return pagePrev;
    }

    public Button getPageNext() {
        return pageNext;
    }

    public Button getNumberRow() {
        return numberRow;
    }

    public TextField getNumberRowField() {
        return numberRowField;
    }
}