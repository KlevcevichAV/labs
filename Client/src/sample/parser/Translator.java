package sample.parser;

import sample.data.Constant;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Translator {
    public static String fileToString(File file) throws IOException {
        FileReader reader = new FileReader(file);
        int pointer;
        String result = "";
        while ((pointer = reader.read()) != -1) {
            result = result + (char) pointer;
        }
        return result;
    }

    public static int searchIndex(String string, char c, int quantity) {
        int counter = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == c) {
                if (++counter == quantity) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static String host() throws IOException {
        File file = new File(Constant.SETTINGS_FILE);
        String fileString = fileToString(file);
        String result = "";
        for (int i = 1 + searchIndex(fileString, ':', 1); i < searchIndex(fileString, '\n', 1); i++) {
            result = result + fileString.charAt(i);
        }
        return result;
    }

    public static int port() throws IOException {
        File file = new File(Constant.SETTINGS_FILE);
        String fileString = fileToString(file);
        String result = "";
        for (int i = 1 + searchIndex(fileString, ':', 2); i < fileString.length(); i++) {
            if (fileString.charAt(i) >= '0' && fileString.charAt(i) <= '9') {
                result = result + fileString.charAt(i);
            } else {
                if (fileString.charAt(i) == '\n') continue;
                else return 8000;
            }
        }
        int resultInt = Integer.parseInt(result);
        return resultInt;
    }

    public static File stringToFile(String string, String path) throws IOException {
        File file = new File(path);
        FileWriter writer = new FileWriter(file);
        writer.write(string);
        writer.flush();
        return file;
    }
}
