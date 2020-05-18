package sample.parser;

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
        result = result + "\n";
        return result;
    }

    public static File stringToFile(String string, String path) throws IOException {
        File file = new File(path);
        FileWriter writer = new FileWriter(file);
        writer.write(string);
        writer.flush();
        return file;
    }
}
