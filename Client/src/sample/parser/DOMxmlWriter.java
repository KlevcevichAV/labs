package sample.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import sample.data.Constant;
import sample.data.Sportsman;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;

public class DOMxmlWriter {
    public static void createXml(List<Sportsman> list, String path) throws ParserConfigurationException, IOException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element root = document.createElement("root");
        document.appendChild(root);
        for (Sportsman sportsman : list) {
            Element temp = document.createElement(Constant.SPORTSMAN);
            createSportsman(temp, sportsman);
            root.appendChild(temp);
        }
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(document), new StreamResult(new FileOutputStream(path)));
    }

    public static String createSportsman(Sportsman sportsman) throws ParserConfigurationException, IOException, TransformerException {
        String result = "";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element root = document.createElement("root");
        document.appendChild(root);
        Element temp = document.createElement(Constant.SPORTSMAN);
        createSportsman(temp, sportsman);
        root.appendChild(temp);
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(document), new StreamResult(new FileOutputStream(Constant.ADD_FILE)));
        result = Translator.fileToString(new File(Constant.ADD_FILE));
        return result;
    }

    private static void createSportsman(Element element, Sportsman sportsman) {
        element.setAttribute(Constant.FULL_NAME_EN, sportsman.getFullName());
        element.setAttribute(Constant.STRUCTURE_EN, sportsman.getStructure());
        element.setAttribute(Constant.POSITION_EN, sportsman.getPosition());
        element.setAttribute(Constant.TITLE_EN, Integer.toString(sportsman.getTitle()));
        element.setAttribute(Constant.KIND_OF_SPORT_EN, sportsman.getKindOfSport());
        element.setAttribute(Constant.CATEGORY_EN, sportsman.getCategory());
    }
}
