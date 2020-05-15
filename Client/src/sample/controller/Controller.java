package sample.controller;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import sample.view.modalWindow.ModalWindowAdd;
import sample.view.modalWindow.ModalWindowSearch;
import sample.data.Sportsman;
import sample.parser.DOMxmlWriter;
import sample.parser.SAXXmlReader;
import sample.view.View;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Socket client;
    private View view;
    private BufferedReader reader;
    private BufferedReader in;
    private BufferedWriter out;

    private void add() throws IOException, TransformerException, ParserConfigurationException, SAXException {
        ModalWindowAdd.newWindow();
        Sportsman newSportsman = ModalWindowAdd.getResult();
        String sportsmanString = "";
        if (newSportsman != null) {
            sportsmanString = DOMxmlWriter.createSportsman(newSportsman);
            System.out.println(sportsmanString);
            Sportsman sportsman = SAXXmlReader.receive(sportsmanString);
            System.out.println("YesZ");
        }
    }

    private void pageSwitchingControl() {
        if (model.pointerPage == 1) {
            view.disableLeft();
        } else view.enableLeft();
        if (model.pointerPage == model.quantityPages) {
            view.disableRight();
        } else view.enableRight();

    }

    private File onOpen() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open table");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("xml", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File openFile = fileChooser.showOpenDialog(null);
        return openFile;
    }

    private File onSave() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save table");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("xml", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(null);
        return file;
    }

    private void setLabel(int counterElements) {
        view.setLabel(model.pointerPage, model.quantityPages);
        view.setCounterElements(counterElements, model.numberRow);
        view.setQuantityPages(model.getTableSize());
    }

    private void event() {
        pageSwitchingControl();
        view.getAddButton().setOnAction(e -> {
            ModalWindowAdd.newWindow();
            Sportsman newSportsman = ModalWindowAdd.getResult();
            if (newSportsman != null) {
                model.addElement(newSportsman);
                List<Sportsman> temp = model.getTable();
                setLabel(temp.size());
                view.fillingTable(temp);
                pageSwitchingControl();
            }
        });
        view.add.setOnAction(e -> {
            try {
                add();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (TransformerException transformerException) {
                transformerException.printStackTrace();
            } catch (ParserConfigurationException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            } catch (SAXException saxException) {
                saxException.printStackTrace();
            }
        });
        view.getSearch().setOnAction(e -> {
            ModalWindowSearch.newWindow(model.getWholeTable());
        });
        view.search.setOnAction(e -> {
            ModalWindowSearch.newWindow(model.getWholeTable());
        });
        view.getDelete().setOnAction(e -> {
            model.deleteElements();
            List<Sportsman> temp = model.getTable();
            setLabel(temp.size());
            view.fillingTable(temp);
            pageSwitchingControl();
        });
        view.delete.setOnAction(e -> {
            model.deleteElements();
            List<Sportsman> temp = model.getTable();
            setLabel(temp.size());
            view.fillingTable(temp);
            pageSwitchingControl();
        });
        view.getOpenButton().setOnAction(e -> {
            try {
                model.openFile(onOpen());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (SAXException saxException) {
                saxException.printStackTrace();
            } catch (ParserConfigurationException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            }
            List<Sportsman> temp = model.getTable();
            setLabel(temp.size());
            view.fillingTable(temp);
            pageSwitchingControl();
        });
        view.load.setOnAction(e -> {
            try {
                model.openFile(onOpen());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (SAXException saxException) {
                saxException.printStackTrace();
            } catch (ParserConfigurationException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            }
            List<Sportsman> temp = model.getTable();
            setLabel(temp.size());
            view.fillingTable(temp);
            pageSwitchingControl();
        });
        view.getSaveButton().setOnAction(e -> {
            try {
                model.saveFile(onSave());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (TransformerException transformerException) {
                transformerException.printStackTrace();
            } catch (ParserConfigurationException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            }
        });
        view.save.setOnAction(e -> {
            try {
                model.saveFile(onSave());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (TransformerException transformerException) {
                transformerException.printStackTrace();
            } catch (ParserConfigurationException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            }
        });
        view.getPageNextButton().setOnAction(e -> {
            model.nextPage();
            List<Sportsman> temp = model.getTable();
            setLabel(temp.size());
            view.fillingTable(temp);
            pageSwitchingControl();
        });
        view.getPagePrevButton().setOnAction(e -> {
            model.prevPage();
            List<Sportsman> temp = model.getTable();
            setLabel(temp.size());
            view.fillingTable(temp);
            pageSwitchingControl();
        });
        view.getPageOneButton().setOnAction(e -> {
            model.pageOne();
            List<Sportsman> temp = model.getTable();
            setLabel(temp.size());
            view.fillingTable(temp);
            pageSwitchingControl();
        });
        view.getPageLastButton().setOnAction(e -> {
            model.lastPage();
            List<Sportsman> temp = model.getTable();
            setLabel(temp.size());
            view.fillingTable(temp);
            pageSwitchingControl();
        });
        view.getEditNumberRow().setOnAction(e -> {
            String stringNumberRow = view.getNumberRowField().getText();
            int numberRow = Integer.parseInt(stringNumberRow);
            model.setNumberRow(numberRow);
            model.pointerPage = 1;
            view.setSettingsTable(model.numberRow);
            List<Sportsman> temp = model.getTable();
            setLabel(temp.size());
            view.fillingTable(temp);
            pageSwitchingControl();
        });
    }

    public Controller(Stage primaryStage, Socket client) throws IOException {
        view = new View(primaryStage, new ArrayList<>());
        this.client = client;
        reader = new BufferedReader(new InputStreamReader(System.in));
        in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(this.client.getOutputStream()));
        event();
    }
}