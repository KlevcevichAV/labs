package sample.MVC;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import org.xml.sax.SAXException;
import sample.ModalWindow.ModalWindowDelete;
import sample.data.Sportsman;
import sample.parser.DOMxmlWriter;
import sample.parser.SAXXmlReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

public class Model {
    ObservableList<Sportsman> list;
    int quantityPages;
    int pointerPage;
    int numberRow;

    void addElement(Sportsman sportsman){
        list.addAll(sportsman);
        quantityPages = list.size()/numberRow + 1;
        if(list.size() % numberRow == 0) quantityPages--;
    }

    void deleteElements(){
        ModalWindowDelete.newWindow(list);
        list = ModalWindowDelete.list;
    }

    void nextPage(){
        pointerPage++;
    }

    void lastPage(){
        pointerPage = quantityPages;
    }

    void prevPage(){
        pointerPage--;
    }

    void pageOne(){
        pointerPage = 1;
    }

    void openFile(File file) throws IOException, SAXException, ParserConfigurationException {
        if(file != null)list = SAXXmlReader.createList(file);
    }

    void saveFile(File file) throws IOException, TransformerException, ParserConfigurationException {
        if(file != null) DOMxmlWriter.createXml(list, file.getPath());
    }

    void setTable(ObservableList<Sportsman> list){
        this.list = list;
    }

    void setNumberRow(int numberRow){
        this.numberRow = numberRow;
    }

    ObservableList<Sportsman> getTable(){
        return list;
    }

    ObservableList<Sportsman> getTable(Label label1, Label label2, Label quantityElements){
        quantityPages = list.size()/numberRow + 1;
        if(list.size() % numberRow == 0) quantityPages--;
        label1.setText(pointerPage + " из " + quantityPages);
        quantityElements.setText("Всего элементов в таблице -- " + list.size());
        if(list.size() > numberRow){
            ObservableList<Sportsman> temp = FXCollections.observableArrayList();
            int counter = 0;
            for(int i = (pointerPage - 1) * numberRow; i < list.size(); i++){
                temp.add(list.get(i));
                counter++;
                if(counter == numberRow) break;
            }
            label2.setText(temp.size() + "из" + numberRow);
            return temp;
        }
        label2.setText(list.size() + "из" + numberRow);
        return list;
    }

    Model(int numberRow){
        pointerPage = 1;
        quantityPages = 1;
        this.numberRow = numberRow;
        list = FXCollections.observableArrayList();
    }
}
