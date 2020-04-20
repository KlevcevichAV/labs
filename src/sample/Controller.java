package sample;

import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

public class Controller {
    Model model;
    View view;
    void add(Sportsman sportsman){
        model.addElement(sportsman);
    }

    //    250
    void search(){
    }

    void delete(){

    }

    void event(){
        view.getAddButton().setOnAction(e->{
            ModalWindowAdd.newWindow();
            Sportsman newSportsman = ModalWindowAdd.getResult();
            if(newSportsman != null){
                add(newSportsman);
            }
        });
        view.getOpenButton().setOnAction(e->{
            try {
                model.openFile(new File("temp.xml"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (SAXException saxException) {
                saxException.printStackTrace();
            } catch (ParserConfigurationException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            }
            view.fillingTable(model.getTable());
        });
        view.getSaveButton().setOnAction(e->{
            try {
                model.saveFile("temp.xml");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (TransformerException transformerException) {
                transformerException.printStackTrace();
            } catch (ParserConfigurationException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            }
        });
    }

    Controller(Stage primaryStage){
        model = new Model();
        view = new View(primaryStage, model.getTable());
        event();
    }
}
