package sample.parser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import sample.data.Sportsman;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class SAXXmlReader {
    public static ObservableList<Sportsman> createList(File file) throws ParserConfigurationException, IOException, SAXException {
        ObservableList<Sportsman> list = FXCollections.observableArrayList();
        DefaultHandler handler = new DefaultHandler(){
            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                if (qName.equals("sportsman")) {
                    String fullName = attributes.getValue("fullName");
                    String structure = attributes.getValue("structure");
                    String position = attributes.getValue("position");
                    String title1 = attributes.getValue("title");
                    int title = Integer.parseInt(title1);
                    String kindOfSport = attributes.getValue("kindOfSport");
                    String category = attributes.getValue("category");
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
    static int decisionStructure(String structure){
        switch (structure){
            case "запасной":{
                return 1;
            }
            case "основной":{
                return 0;
            }
            default:{
                return 2;
            }
        }
    }

    static int decisionCategory(String structure){
        switch (structure){
            case "мастер спорта":{
                return 0;
            }
            case "кмс":{
                return 1;
            }
            case "3-йр азряд":{
                return 2;
            }
            case "2-й разряд":{
                return 3;
            }
            default:{
                return 4;
            }
        }
    }
}
