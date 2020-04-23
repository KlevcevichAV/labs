package sample.parser;

import javafx.collections.ObservableList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import sample.data.Sportsman;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;

public class DOMxmlWriter {
    public static void createXml(ObservableList<Sportsman> list, String path) throws ParserConfigurationException, IOException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element root = document.createElement("root");
        document.appendChild(root);
        for (int i = 0; i < list.size(); i++){
            Element temp = document.createElement("sportsman");
            createSportsman(temp, list.get(i));
            root.appendChild(temp);
        }
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(document), new StreamResult(new FileOutputStream(path)));
    }

    static void createSportsman(Element element, Sportsman sportsman){
        element.setAttribute("fullName", sportsman.getFullName());
        element.setAttribute("structure", sportsman.getStructure());
        element.setAttribute("position", sportsman.getPosition());
        element.setAttribute("title", Integer.toString(sportsman.getTitle()));
        element.setAttribute("kindOfSport", sportsman.getKindOfSport());
        element.setAttribute("category", sportsman.getCategory());
    }
}
