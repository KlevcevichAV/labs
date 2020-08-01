package sample.controller;

import javafx.stage.Stage;
import org.xml.sax.SAXException;
import sample.data.Sportsman;
import sample.model.Model;
import sample.parser.DOMxmlWriter;
import sample.parser.SAXXmlReader;
import sample.parser.Translator;
import sample.server.Server;
import sample.view.View;
import sample.data.Constant;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {
    private View view;
    private Model model;
    private Server server;
    private List<String> listFiles;
    public boolean loadCheck;
    private String nameFile;

    private void events() {
        view.getRun().setOnAction(e -> {
            if (!server.getCheck()) {
                view.getConsole().appendText("/run\n");
                server.incCheck();
                try {
                    server.createServer();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } else {
                view.getConsole().appendText("/error server is running\n");
            }
        });
        view.getStop().setOnAction(e -> {
            if (server.getCheck()) {
                view.getConsole().appendText("/stop\n");
                server.incCheck();
                server.thread.interrupt();
            } else {
                view.getConsole().appendText("/error server is not running\n");
            }
        });
    }

    public List<String> createList() {
        List<String> result = new ArrayList<>();
        File dir = new File("./Tables");
        final String extension = ".xml";
        File[] arrFiles = dir.listFiles((File pathname) -> pathname.getName().endsWith(extension));
        ;
        List<File> lst = Arrays.asList(arrFiles);
        for (File file : lst) {
            result.add(file.getName());
        }
        return result;
    }

    public String stringFiles() {
        String result = "";
        for (String fileName : listFiles) {
            result = result + fileName + '\n';
        }
        result = result + "end\n";
        return result;
    }

    private String definitionOfTheCondition(String choice) {
        String result = "";
        switch (choice) {
            case "1": {
                result = "-full_name_or_kind_of_sport\n";
                break;
            }
            case "2": {
                result = "-titles\n";
                break;
            }
            case "3": {
                result = "-full_name_or_category\n";
                break;
            }
            default: {
                result = "error";
            }
        }
        return result;
    }

    private void definitionResultRemove(int result) throws IOException {
        switch (result) {
            case 0: {
                addText("Nothing found\n");
                break;
            }
            case 1: {
                addText("1 sportsman removed\n");
                break;
            }
            default: {
                addText(result + " sportsmen removed\n");
            }
        }
    }

    private void definitionResultSearch() throws IOException {
        String result = server.in.readLine();
        switch (result) {
            case "0": {
                addText("Nothing found\n");
                break;
            }
            case "1": {
                addText("1 sportsman found\n");
                break;
            }
            default: {
                addText(result + " sportsmen found\n");
            }
        }
    }

    public void function(String operation) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        switch (operation) {
            case Constant.LOAD: {
                server.out.write(stringFiles());
                server.out.flush();
                String choice = server.in.readLine();
                File file = new File("./Tables/" + choice);
                model.openFile(file);
                DOMxmlWriter.createXml(model.getTable(), Constant.LOAD_FILE);
                String fileString = Translator.fileToString(new File(Constant.LOAD_FILE));
                server.out.write(fileString);
                server.out.flush();
                loadCheck = true;
                nameFile = file.getName();
                view.getConsole().appendText(Constant.LOAD + " " + nameFile + "\n");
                String letter = model.pointerPage + "\n";
                server.out.write(letter);
                server.out.flush();
                server.out.write(model.quantityPages + "\n");
                server.out.flush();
                server.out.write(model.getTableSize() + "\n");
                server.out.flush();
                server.out.write(model.numberRow + "\n");
                server.out.flush();
                break;
            }
            case Constant.SAVE: {
                if (!loadCheck) {
                    nameFile = server.in.readLine();
                }
                DOMxmlWriter.createXml(model.getWholeTable(), "./Tables/" + nameFile);
                view.getConsole().appendText(Constant.SAVE + " " + nameFile + "\n");
                break;
            }
            case Constant.ADD: {
                String table = "";
                while (true) {
                    String temp = server.in.readLine();
                    if (temp.equals("</root>")) {
                        table = table + temp;
                        break;
                    } else table = table + temp + "\n";
                }
                File file = Translator.stringToFile(table, Constant.ADD_FILE);
                Sportsman sportsmanAdded = SAXXmlReader.receive(table);
                model.addElement(sportsmanAdded);
                view.getConsole().appendText(Constant.ADD + " sportsman" + "\n");
                DOMxmlWriter.createXml(model.getTable(), Constant.LOAD_FILE);
                String letter = Translator.fileToString(new File(Constant.LOAD_FILE));
                server.out.write(letter);
                server.out.flush();
                server.out.write(model.pointerPage + "\n");
                server.out.flush();
                server.out.write(model.quantityPages + "\n");
                server.out.flush();
                server.out.write(model.getTableSize() + "\n");
                server.out.flush();
                server.out.write(model.numberRow + "\n");
                server.out.flush();
                break;
            }
            case Constant.DELETE: {
                DOMxmlWriter.createXml(model.getWholeTable(), Constant.LOAD_FILE);
                String letter = Translator.fileToString(new File(Constant.LOAD_FILE));
                server.out.write(letter);
                server.out.flush();
                String table = "";
                while (true) {
                    String temp = server.in.readLine();
                    if (temp.equals("</root>")) {
                        table = table + temp;
                        break;
                    } else table = table + temp + "\n";
                }
                String condition = server.in.readLine();
                condition = definitionOfTheCondition(condition);
                view.getConsole().appendText(Constant.DELETE + " " + condition);

                File file = Translator.stringToFile(table, Constant.LOAD_FILE);
                List<Sportsman> listDeleted = SAXXmlReader.createList(file);
                int deleted = model.getTableSize() - listDeleted.size();
                definitionResultRemove(deleted);
                model.deleteElements(listDeleted);
                break;
            }
            case Constant.SEARCH: {
                DOMxmlWriter.createXml(model.getWholeTable(), Constant.LOAD_FILE);
                String letter = Translator.fileToString(new File(Constant.LOAD_FILE));
                server.out.write(letter);
                server.out.flush();
                while (true) {
                    String condition = server.in.readLine();
                    if (condition.equals("end")) break;
                    condition = definitionOfTheCondition(condition);
                    view.getConsole().appendText(Constant.SEARCH + " " + condition);
                    definitionResultSearch();
                }
                break;
            }
            case Constant.NEXT_PAGE: {
                addText(Constant.NEXT_PAGE + "\n");
                model.nextPage();
                addText(Constant.CURRENT_PAGE + model.pointerPage + "\n");
                DOMxmlWriter.createXml(model.getTable(), Constant.LOAD_FILE);
                String file = Translator.fileToString(new File(Constant.LOAD_FILE));
                server.out.write(file);
                server.out.flush();
                server.out.write(model.pointerPage + "\n");
                server.out.flush();
                server.out.write(model.quantityPages + "\n");
                server.out.flush();
                break;
            }
            case Constant.PREV_PAGE: {
                addText(Constant.PREV_PAGE + "\n");
                model.prevPage();
                addText(Constant.CURRENT_PAGE + model.pointerPage + "\n");
                DOMxmlWriter.createXml(model.getTable(), Constant.LOAD_FILE);
                String file = Translator.fileToString(new File(Constant.LOAD_FILE));
                server.out.write(file);
                server.out.flush();
                server.out.write(model.pointerPage + "\n");
                server.out.flush();
                server.out.write(model.quantityPages + "\n");
                server.out.flush();
                break;
            }
            case Constant.FIRST_PAGE: {
                addText(Constant.FIRST_PAGE + "\n");
                model.pageOne();
                addText(Constant.CURRENT_PAGE + model.pointerPage + "\n");
                DOMxmlWriter.createXml(model.getTable(), Constant.LOAD_FILE);
                String file = Translator.fileToString(new File(Constant.LOAD_FILE));
                server.out.write(file);
                server.out.flush();
                server.out.write(model.pointerPage + "\n");
                server.out.flush();
                server.out.write(model.quantityPages + "\n");
                server.out.flush();
                break;
            }
            case Constant.LAST_PAGE: {
                addText(Constant.LAST_PAGE + "\n");
                model.lastPage();
                addText(Constant.CURRENT_PAGE + model.pointerPage + "\n");
                DOMxmlWriter.createXml(model.getTable(), Constant.LOAD_FILE);
                String file = Translator.fileToString(new File(Constant.LOAD_FILE));
                server.out.write(file);
                server.out.flush();
                server.out.write(model.pointerPage + "\n");
                server.out.flush();
                server.out.write(model.quantityPages + "\n");
                server.out.flush();
                break;
            }
            case Constant.CHANGE_ROW_TABLE: {
                String currentRow = server.in.readLine();
                model.numberRow = Integer.parseInt(currentRow);
                addText(Constant.CHANGE_ROW_TABLE + " " + currentRow + "\n");
                model.pointerPage = 1;
                DOMxmlWriter.createXml(model.getTable(), Constant.LOAD_FILE);
                String file = Translator.fileToString(new File(Constant.LOAD_FILE));
                server.out.write(file);
                server.out.flush();
                server.out.write(model.pointerPage + "\n");
                server.out.flush();
                server.out.write(model.quantityPages + "\n");
                server.out.flush();
                server.out.write(model.getTableSize() + "\n");
                server.out.flush();
                break;
            }
            default: {
                System.out.println("WHAT????");
            }
        }
    }

    public void addText(String text) {
        view.getConsole().appendText(text);
    }

    public Controller(Stage primaryStage) {
        server = new Server(this);
        model = new Model(10);
        view = new View(primaryStage);
        listFiles = createList();
        loadCheck = false;
        primaryStage.setOnCloseRequest(e -> {
            try {
                server.closeServer();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        events();
    }
}
