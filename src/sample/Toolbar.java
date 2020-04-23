package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Toolbar{
    VBox root;
    int pointerPage;
    Button add;
    Button search;
    Button delete;
    Button load;
    Button save;
    Label numbRow;
    Label counterElements;
    Label quantityPages;
    Button numberRow;
    TextField numberRowField;
    Button pageOne;
    Button pageLast;
    Label numberPage;
    Button pagePrev;
    Button pageNext;
    HBox pages;

    Toolbar(){
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

    public VBox getToolbar(){
        return root;
    }



    HBox getPages(){ return pages;}

}
