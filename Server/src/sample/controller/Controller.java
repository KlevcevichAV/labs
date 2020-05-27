package sample.controller;

import javafx.stage.Stage;
import sample.parser.Translator;
import sample.server.Server;
import sample.view.View;
import sample.data.Constant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {
    View view;
    Server server;
    List<String> listFiles;
    public boolean loadCheck;
    String nameFile;

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

    private void definitionResultRemove() throws IOException {
        String result = server.in.readLine();
        switch (result) {
            case "0": {
                addText("Nothing found\n");
                break;
            }
            case "1": {
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

    public void function(String operation) throws IOException {
        switch (operation) {
            case Constant.LOAD: {
                server.out.write(stringFiles());
                server.out.flush();
                String choice = server.in.readLine();
                File file = new File("./Tables/" + choice);
                String fileString = Translator.fileToString(file);
                server.out.write(fileString);
                server.out.flush();
                loadCheck = true;
                nameFile = file.getName();
                view.getConsole().appendText(Constant.LOAD + " " + nameFile + "\n");
                break;
            }
            case Constant.SAVE: {
                String table = "";
                while (true) {
                    String temp = server.in.readLine();
                    if (temp.equals("</root>")) {
                        table = table + temp;
                        break;
                    } else table = table + temp + "\n";
                }
                if (!loadCheck) {
                    nameFile = server.in.readLine();
                }
                File file = Translator.stringToFile(table, "./Tables/" + nameFile);
                view.getConsole().appendText(Constant.SAVE + " " + nameFile + "\n");
                break;
            }
            case Constant.ADD: {
                view.getConsole().appendText(Constant.ADD + " sportsman" + "\n");
                break;
            }
            case Constant.DELETE: {
                String condition = server.in.readLine();
                condition = definitionOfTheCondition(condition);
                view.getConsole().appendText(Constant.DELETE + " " + condition);
                definitionResultRemove();
                break;
            }
            case Constant.SEARCH: {
                String condition = server.in.readLine();
                condition = definitionOfTheCondition(condition);
                view.getConsole().appendText(Constant.SEARCH + " " + condition);
                definitionResultSearch();
                break;
            }
            case Constant.NEXT_PAGE: {
                addText(Constant.NEXT_PAGE + "\n");
                String currentPage = server.in.readLine();
                addText(Constant.CURRENT_PAGE + currentPage + "\n");
                break;
            }
            case Constant.PREV_PAGE: {
                addText(Constant.PREV_PAGE + "\n");
                String currentPage = server.in.readLine();
                addText(Constant.CURRENT_PAGE + currentPage + "\n");
                break;
            }
            case Constant.FIRST_PAGE: {
                addText(Constant.FIRST_PAGE + "\n");
                String currentPage = server.in.readLine();
                addText(Constant.CURRENT_PAGE + currentPage + "\n");
                break;
            }
            case Constant.LAST_PAGE: {
                addText(Constant.LAST_PAGE + "\n");
                String currentPage = server.in.readLine();
                addText(Constant.CURRENT_PAGE + currentPage + "\n");
                break;
            }
            case Constant.CHANGE_ROW_TABLE: {
                String currentRow = server.in.readLine();
                addText(Constant.CHANGE_ROW_TABLE + " " + currentRow + "\n");
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
