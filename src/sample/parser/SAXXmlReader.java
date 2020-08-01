package sample.parser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.transform.Transform;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import sample.data.Constant;
import sample.data.Sportsman;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class SAXXmlReader {
    public static ObservableList<Sportsman> createList(File file) throws ParserConfigurationException, IOException, SAXException {
        ObservableList<Sportsman> list = FXCollections.observableArrayList();
        DefaultHandler handler = new DefaultHandler() {
            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                if (qName.equals(Constant.SPORTSMAN)) {
                    String fullName = attributes.getValue(Constant.FULL_NAME_EN);
                    String structure = attributes.getValue(Constant.STRUCTURE_EN);
                    String position = attributes.getValue(Constant.POSITION_EN);
                    String title1 = attributes.getValue(Constant.TITLE_EN);
                    int title = Integer.parseInt(title1);
                    String kindOfSport = attributes.getValue(Constant.KIND_OF_SPORT_EN);
                    String category = attributes.getValue(Constant.CATEGORY_EN);
                    list.addAll(new Sportsman(fullName, decisionStructure(structure), position,
                            title, kindOfSport, decisionCategory(category)));
                }
            }
        };
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        parser.parse(file, handler);
        return list;
    }

    public static Sportsman receive(String string) throws ParserConfigurationException, IOException, SAXException {
        File file = Translator.stringToFile(string, Constant.ADD_FILE);
        Sportsman result;
        ObservableList<Sportsman> list = FXCollections.observableArrayList();
        DefaultHandler handler = new DefaultHandler() {
            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                if (qName.equals(Constant.SPORTSMAN)) {
                    String fullName = attributes.getValue(Constant.FULL_NAME_EN);
                    String structure = attributes.getValue(Constant.STRUCTURE_EN);
                    String position = attributes.getValue(Constant.POSITION_EN);
                    String title1 = attributes.getValue(Constant.TITLE_EN);
                    int title = Integer.parseInt(title1);
                    String kindOfSport = attributes.getValue(Constant.KIND_OF_SPORT_EN);
                    String category = attributes.getValue(Constant.CATEGORY_EN);
                    list.addAll(new Sportsman(fullName, decisionStructure(structure), position,
                            title, kindOfSport, decisionCategory(category)));
                }
            }
        };
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        parser.parse(file, handler);
        result = list.get(0);
        return result;
    }

    private static int decisionStructure(String structure) {
        switch (structure) {
            case Constant.SPARE: {
                return 1;
            }
            case Constant.MAIN: {
                return 0;
            }
            default: {
                return 2;
            }
        }
    }
    
    private static int decisionCategory(String structure) {
        switch (structure) {
            case Constant.MASTER_OF_SPORT: {
                return 0;
            }
            case Constant.СMS: {
                return 1;
            }
            case Constant.DISCHARGE3: {
                return 2;
            }
            case Constant.DISCHARGE2: {
                return 3;
            }
            default: {
                return 4;
            }
        }
    }
}
