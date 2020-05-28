package sample.controller;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import sample.data.Constant;
import sample.parser.Translator;
import sample.view.modalWindow.ModalWindowAdd;
import sample.view.modalWindow.ModalWindowDelete;
import sample.view.modalWindow.ModalWindowLoad;
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
    View view;
    Socket client;
    BufferedReader reader;
    BufferedReader in;
    public boolean check;
    BufferedWriter out;
    boolean checkLoad;

    private void pageSwitchingControl(int pointerPage, int quantityPages ) {
        if (pointerPage == 1) {
            view.disableLeft();
        } else view.enableLeft();
        if (pointerPage == quantityPages) {
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

    private boolean checkEnd(String string) {
        return string.equals("end");
    }

    private void onSave() throws IOException, TransformerException, ParserConfigurationException {
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
    }

    private void setLabel(int pointerPage, int quantityPages){
        view.setLabel(pointerPage, quantityPages);
    }

    private void setLabel(int pointerPage, int quantityPages,  int counterElements, int numberRow, int size) {
        setLabel(pointerPage, quantityPages);
        view.setCounterElements(counterElements, numberRow);
        view.setQuantityPages(size);
    }


    private String inputTable() throws IOException {
        String table = "";
        while (true) {
            String temp = in.readLine();
            if (temp.equals("</root>")) {
                table = table + temp;
                break;
            } else table = table + temp + "\n";
        }
        return table;
    }

    public void dialogAdd(Sportsman sportsman){
        try {
            out.write(Constant.ADD + "\n");
            out.flush();
            String fileString = DOMxmlWriter.createSportsman(sportsman);
            out.write(fileString);
            out.flush();
            String table = inputTable();
            File file = Translator.stringToFile(table, Constant.LOAD_FILE);
            List<Sportsman> tableSportsman = SAXXmlReader.createList(file);
            view.fillingTable(tableSportsman);
            int pointerPage = Integer.parseInt(in.readLine());
            int quantityPages = Integer.parseInt(in.readLine());
            int counterElements = tableSportsman.size();
            int numberRow = Integer.parseInt(in.readLine());
            int size = Integer.parseInt(in.readLine());
            setLabel(pointerPage, quantityPages, counterElements, numberRow, size);
            pageSwitchingControl(pointerPage, quantityPages);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public void dialogSearch1(){
        try {
            out.write(Constant.SEARCH + "\n");
            out.flush();
            String table = inputTable();
            File file = Translator.stringToFile(table, Constant.LOAD_FILE);
            List<Sportsman> tableSportsman = SAXXmlReader.createList(file);
            ModalWindowSearch.newWindow(tableSportsman, this);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }  catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public void dialogSearch2() throws IOException {
        out.write(ModalWindowSearch.pointerChoice + "\n");
        out.flush();
        out.write(ModalWindowSearch.table.getItems().size() + "\n");
        out.flush();
    }

    public void cancelSearch() throws IOException {
        out.write("end\n");
        out.flush();
    }

    public void dialogDelete(){
        try {
            out.write(Constant.DELETE + "\n");
            out.flush();
            String table = inputTable();
            File file = Translator.stringToFile(table, Constant.LOAD_FILE);
            List<Sportsman> tableSportsman = SAXXmlReader.createList(file);
            ModalWindowDelete.newWindow(tableSportsman);
            List<Sportsman> temp = ModalWindowDelete.getList();
            view.fillingTable(temp);
            DOMxmlWriter.createXml(temp, Constant.LOAD_FILE);
            String letter = Translator.fileToString(new File(Constant.LOAD_FILE));
            out.write(letter);
            out.flush();

            out.write(ModalWindowDelete.pointerChoice + "\n");
            out.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }  catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public void dialogLoad() throws IOException, ParserConfigurationException, SAXException {
        out.write(Constant.LOAD + "\n");
        out.flush();
        String serverWord = "";
        while (true) {
            String temp = in.readLine();
            if (!checkEnd(temp)) serverWord = serverWord + temp + "\n";
            else break;
        }
        ModalWindowLoad.newWindow(serverWord);
        out.write(ModalWindowLoad.nameFile + "\n");
        out.flush();
        String table = inputTable();
        File file = Translator.stringToFile(table, Constant.LOAD_FILE);
        List<Sportsman> tableSportsman = SAXXmlReader.createList(file);
        String intValue = in.readLine();
        int pointerPage = Integer.parseInt(intValue);
        int quantityPages = Integer.parseInt(in.readLine());
        int counterElements = tableSportsman.size();
        int numberRow = Integer.parseInt(in.readLine());
        int size = Integer.parseInt(in.readLine());
        setLabel(pointerPage, quantityPages, counterElements, numberRow, size);
        pageSwitchingControl(pointerPage, quantityPages);
        view.fillingTable(tableSportsman);
        checkLoad = true;
    }

    private void event() {
        pageSwitchingControl(1, 1);
        view.getAddButton().setOnAction(e -> {
            ModalWindowAdd.newWindow();
            Sportsman newSportsman = ModalWindowAdd.getResult();
            if (newSportsman != null) {
                dialogAdd(newSportsman);
            }
        });
        view.add.setOnAction(e -> {
            ModalWindowAdd.newWindow();
            Sportsman newSportsman = ModalWindowAdd.getResult();
            if (newSportsman != null) {
                dialogAdd(newSportsman);
            }
        });
        view.getSearch().setOnAction(e -> {
            dialogSearch1();
        });
        view.search.setOnAction(e -> {
            dialogSearch1();
        });
        view.getDelete().setOnAction(e -> {
            dialogDelete();
        });
        view.delete.setOnAction(e -> {
            dialogDelete();
        });
        view.getOpenButton().setOnAction(e -> {
            try {
                dialogLoad();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ParserConfigurationException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            } catch (SAXException saxException) {
                saxException.printStackTrace();
            }
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
                dialogLoad();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ParserConfigurationException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            } catch (SAXException saxException) {
                saxException.printStackTrace();
            }
        });
        view.getSaveButton().setOnAction(e -> {
            try {
                onSave();
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
                onSave();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (TransformerException transformerException) {
                transformerException.printStackTrace();
            } catch (ParserConfigurationException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            }
        });
        view.getPageNextButton().setOnAction(e -> {
            try {
                out.write(Constant.NEXT_PAGE + "\n");
                out.flush();
                String fileString = inputTable();
                File file = Translator.stringToFile(fileString, Constant.LOAD_FILE);
                List<Sportsman> tableSportsman = SAXXmlReader.createList(file);
                view.fillingTable(tableSportsman);
                int pointerPage = Integer.parseInt(in.readLine());
                int quantityPages = Integer.parseInt(in.readLine());
                pageSwitchingControl(pointerPage, quantityPages);
                setLabel(pointerPage, quantityPages);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (SAXException saxException) {
                saxException.printStackTrace();
            } catch (ParserConfigurationException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            }
        });
        view.getPagePrevButton().setOnAction(e -> {
            try {
                out.write(Constant.PREV_PAGE + "\n");
                out.flush();
                String fileString = inputTable();
                File file = Translator.stringToFile(fileString, Constant.LOAD_FILE);
                List<Sportsman> tableSportsman = SAXXmlReader.createList(file);
                view.fillingTable(tableSportsman);
                int pointerPage = Integer.parseInt(in.readLine());
                int quantityPages = Integer.parseInt(in.readLine());
                pageSwitchingControl(pointerPage, quantityPages);
                setLabel(pointerPage, quantityPages);
            } catch (IOException | ParserConfigurationException | SAXException ioException) {
                ioException.printStackTrace();
            }
        });
        view.getPageOneButton().setOnAction(e -> {
            try {
                out.write(Constant.FIRST_PAGE + "\n");
                out.flush();
                String fileString = inputTable();
                File file = Translator.stringToFile(fileString, Constant.LOAD_FILE);
                List<Sportsman> tableSportsman = SAXXmlReader.createList(file);
                view.fillingTable(tableSportsman);
                int pointerPage = Integer.parseInt(in.readLine());
                int quantityPages = Integer.parseInt(in.readLine());
                pageSwitchingControl(pointerPage, quantityPages);
                setLabel(pointerPage, quantityPages);
            } catch (IOException | ParserConfigurationException | SAXException ioException) {
                ioException.printStackTrace();
            }
        });
        view.getPageLastButton().setOnAction(e -> {
            try {
                out.write(Constant.LAST_PAGE + "\n");
                out.flush();
                String fileString = inputTable();
                File file = Translator.stringToFile(fileString, Constant.LOAD_FILE);
                List<Sportsman> tableSportsman = SAXXmlReader.createList(file);
                view.fillingTable(tableSportsman);
                int pointerPage = Integer.parseInt(in.readLine());
                int quantityPages = Integer.parseInt(in.readLine());
                pageSwitchingControl(pointerPage, quantityPages);
                setLabel(pointerPage, quantityPages);
            } catch (IOException | ParserConfigurationException | SAXException ioException) {
                ioException.printStackTrace();
            }
        });
        view.getEditNumberRow().setOnAction(e -> {
            String stringNumberRow = view.getNumberRowField().getText();
            int numberRow = Integer.parseInt(stringNumberRow);
            view.setSettingsTable(numberRow);
            String stringTable = "";
            List<Sportsman> list = null;
            try {
                out.write(Constant.CHANGE_ROW_TABLE + "\n");
                out.flush();
                out.write(numberRow + "\n");
                out.flush();
                stringTable = inputTable();
                list = SAXXmlReader.createList(Translator.stringToFile(stringTable, Constant.LOAD_FILE));
                String intValue = in.readLine();
                int pointerPage = Integer.parseInt(intValue);
                int quantityPages = Integer.parseInt(in.readLine());
                int counterElements = list.size();
                int size = Integer.parseInt(in.readLine());
                setLabel(pointerPage, quantityPages, counterElements, numberRow, size);
                pageSwitchingControl(pointerPage, quantityPages);
                view.fillingTable(list);
            } catch (ParserConfigurationException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (SAXException saxException) {
                saxException.printStackTrace();
            }
        });
    }

    public Controller(Stage primaryStage, Socket client, BufferedReader reader, BufferedReader in, BufferedWriter out) throws IOException {
        check = true;
        checkLoad = false;
        List<Sportsman> temp = new ArrayList<>();
        view = new View(primaryStage, temp);
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
