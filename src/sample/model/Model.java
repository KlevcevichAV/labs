package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.xml.sax.SAXException;
import sample.modalWindow.ModalWindowDelete;
import sample.data.Sportsman;
import sample.parser.DOMxmlWriter;
import sample.parser.SAXXmlReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Model {
    private List<Sportsman> list;
    public int quantityPages;
    public int pointerPage;
    public int numberRow;

    public void addElement(Sportsman sportsman) {
        list.add(sportsman);
        quantityPages = list.size() / numberRow + 1;
        if (list.size() % numberRow == 0) quantityPages--;
    }

    public void deleteElements() {
        ModalWindowDelete.newWindow(list);
        list = ModalWindowDelete.getList();
    }

    public void nextPage() {
        pointerPage++;
    }

    public void lastPage() {
        pointerPage = quantityPages;
    }

    public void prevPage() {
        pointerPage--;
    }

    public void pageOne() {
        pointerPage = 1;
    }

    public void openFile(File file) throws IOException, SAXException, ParserConfigurationException {
        if (file != null) list = SAXXmlReader.createList(file);
    }

    public void saveFile(File file) throws IOException, TransformerException, ParserConfigurationException {
        if (file != null) DOMxmlWriter.createXml(list, file.getPath());
    }

    public void setNumberRow(int numberRow) {
        this.numberRow = numberRow;
    }

    public void setQuantityPages() {
        quantityPages = list.size() / numberRow + 1;
        if (list.size() % numberRow == 0) quantityPages--;
        if (quantityPages == 0) quantityPages = 1;
    }

    public int getTableSize() {
        return list.size();
    }

    public List<Sportsman> getWholeTable() {
        return list;
    }

    public List<Sportsman> getTable() {
        setQuantityPages();
        if (list.size() > numberRow) {
            ObservableList<Sportsman> temp = FXCollections.observableArrayList();
            int counter = 0;
            for (int i = (pointerPage - 1) * numberRow; i < list.size(); i++) {
                temp.add(list.get(i));
                counter++;
                if (counter == numberRow) break;
            }
            return temp;
        }
        return list;
    }

    public Model(int numberRow) {
        pointerPage = 1;
        quantityPages = 1;
        this.numberRow = numberRow;
        list = FXCollections.observableArrayList();
    }
}