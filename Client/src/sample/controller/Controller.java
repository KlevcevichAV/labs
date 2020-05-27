package sample.controller;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import sample.data.Constant;
import sample.parser.Translator;
import sample.view.modalWindow.ModalWindowAdd;
import sample.view.modalWindow.ModalWindowLoad;
import sample.view.modalWindow.ModalWindowSearch;
import sample.data.Sportsman;
import sample.model.Model;
import sample.parser.DOMxmlWriter;
import sample.parser.SAXXmlReader;
import sample.view.View;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Controller {
    Model model;
    View view;
    Socket client;
    BufferedReader reader;
    BufferedReader in;
    public boolean check;
    BufferedWriter out;
    boolean checkLoad;

    private void add() throws IOException, TransformerException, ParserConfigurationException, SAXException {
        ModalWindowAdd.newWindow(out);
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

    private ComboBox<String> createList(String listString) {
        ComboBox<String> list = new ComboBox<>();
        String temp = "";
        for (int i = 0; i < listString.length(); i++) {
            if (listString.charAt(i) == '\n' || i + 1 == listString.length()) {
                list.getItems().add(temp.toString());
                temp = "";
            } else temp = temp + listString.charAt(i);
        }
        return list;
    }

    private Alert createEmptyDialog(javafx.scene.Node content, String title) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(title);

        alert.getDialogPane().setContent(content);

        return alert;
    }

    private String table;

    private File onOpen() throws IOException {
        out.write(Constant.LOAD + "\n");
        out.flush();
        ModalWindowLoad.newWindow(in, out);
        File file = null;
//        System.out.println(table);
//        System.out.println(ModalWindowLoad.table);
        if (ModalWindowLoad.table != "") file = Translator.stringToFile(ModalWindowLoad.table, Constant.LOAD_FILE);
        checkLoad = true;
        return file;
    }

    static boolean checkEnd(String string) {
        return string.equals("end");
    }

    private File onSave() throws IOException, TransformerException, ParserConfigurationException {
        out.write(Constant.SAVE + "\n");
        out.flush();
        GridPane gridPane = new GridPane();
        TextField field = new TextField("name");
        gridPane.add(field, 0, 1);
        Alert distanceDialog = createEmptyDialog(gridPane, "Input name table");

        ButtonType GET = new ButtonType("Get");
        distanceDialog.getButtonTypes().add(GET);

        ((Button) distanceDialog.getDialogPane().lookupButton(GET)).setOnAction(actionEvent -> {
            String nameFile = "";
            nameFile = field.getText();
            if (!nameFile.contains(".xml")) nameFile = nameFile + ".xml";
            try {
                out.write(nameFile + "\n");
                out.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            distanceDialog.close();
        });

        if (!checkLoad) distanceDialog.show();

        File file = new File(Constant.SAVE_FILE);
        if (file != null) {
            DOMxmlWriter.createXml(model.getWholeTable(), Constant.SAVE_FILE);
            String result = Translator.fileToString(file);
            out.write(result);
            out.flush();
        }
        return file;
    }

    private void setLabel(int counterElements) {
        view.setLabel(model.pointerPage, model.quantityPages);
        view.setCounterElements(counterElements, model.numberRow);
        view.setQuantityPages(model.getTableSize());
    }

    public void dialogSearch(){
        try {
            out.write(Constant.SEARCH + "\n");
            out.flush();
            out.write(ModalWindowSearch.pointerChoice + "\n");
            out.flush();
            out.write(ModalWindowSearch.table.getItems().size() + "\n");
            out.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void event() {
        pageSwitchingControl();
        view.getAddButton().setOnAction(e -> {
            ModalWindowAdd.newWindow(out);
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
            ModalWindowSearch.newWindow(model.getWholeTable(), this);
        });
        view.search.setOnAction(e -> {
            ModalWindowSearch.newWindow(model.getWholeTable(), this);
        });
        view.getDelete().setOnAction(e -> {
            model.deleteElements(out);
            List<Sportsman> temp = model.getTable();
            setLabel(temp.size());
            view.fillingTable(temp);
            pageSwitchingControl();
        });
        view.delete.setOnAction(e -> {
            model.deleteElements(out);
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
        view.exit.setOnAction(e -> {
            try {
                out.write("/exit\n");
                out.flush();
                in.close();
                out.close();
                reader.close();
                client.close();
                System.exit(0);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
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
            try {
                out.write(Constant.NEXT_PAGE + "\n");
                out.flush();
                out.write(model.pointerPage + "\n");
                out.flush();


            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        view.getPagePrevButton().setOnAction(e -> {
            model.prevPage();
            List<Sportsman> temp = model.getTable();
            setLabel(temp.size());
            view.fillingTable(temp);
            pageSwitchingControl();
            try {
                out.write(Constant.PREV_PAGE + "\n");
                out.flush();
                out.write(model.pointerPage + "\n");
                out.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        view.getPageOneButton().setOnAction(e -> {
            model.pageOne();
            List<Sportsman> temp = model.getTable();
            setLabel(temp.size());
            view.fillingTable(temp);
            pageSwitchingControl();
            try {
                out.write(Constant.FIRST_PAGE + "\n");
                out.flush();
                out.write(model.pointerPage + "\n");
                out.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        view.getPageLastButton().setOnAction(e -> {
            model.lastPage();
            List<Sportsman> temp = model.getTable();
            setLabel(temp.size());
            view.fillingTable(temp);
            pageSwitchingControl();
            try {
                out.write(Constant.LAST_PAGE + "\n");
                out.flush();
                out.write(model.pointerPage + "\n");
                out.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
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
            try {
                out.write(Constant.CHANGE_ROW_TABLE + "\n");
                out.flush();
                out.write(view.getNumberRowField().getText() + "\n");
                out.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    public Controller(Stage primaryStage, Socket client, BufferedReader reader, BufferedReader in, BufferedWriter out) throws IOException {
        check = true;
        checkLoad = false;
        model = new Model(10);
        view = new View(primaryStage, model.getTable());
        this.client = client;
        this.reader = reader;
        this.in = in;
        this.out = out;
        primaryStage.setOnCloseRequest(e -> {
            try {
                out.write("/exit\n");
                out.flush();
                in.close();
                out.close();
                reader.close();
                client.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        event();
    }
}
