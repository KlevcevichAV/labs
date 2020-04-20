package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Model {
    ObservableList<Sportsman> list;

    void addElement(Sportsman sportsman){
        list.addAll(sportsman);
        System.out.println(123);
    }

    void openFile(File file) throws IOException, SAXException, ParserConfigurationException {
        list = SAXXmlReader.createList(new File("temp.xml"));
    }

    void saveFile(String path) throws IOException, TransformerException, ParserConfigurationException {
        DOMxmlWriter.createXml(list, path);
    }

    void setTable(ObservableList<Sportsman> list){
        this.list = list;
    }

    ObservableList<Sportsman> getTable(){
        return list;
    }

    Model(){
        list = FXCollections.observableArrayList();
    }
}
