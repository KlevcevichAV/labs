package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Toolbar{
    HBox root;
    Button add;
    Button search;
    Button delete;
    Button load;
    Button save;
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
        root = new HBox(add, search, delete, load, save);
        root.setSpacing(23);
        pageOne = new Button("<<");
        pagePrev = new Button("<");
        numberPage = new Label("1 из 1");
        pageNext = new Button(">");
        pageLast = new Button(">>");
        pages = new HBox(pageOne, pagePrev, numberPage, pageNext, pageLast);
        pages.setSpacing(80);
    }


    HBox getToolbar(){
        return root;
    }

    HBox getPages(){ return pages;}
}
