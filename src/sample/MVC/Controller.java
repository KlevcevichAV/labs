package sample.MVC;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import sample.ModalWindow.ModalWindowAdd;
import sample.ModalWindow.ModalWindowSearch;
import sample.data.Sportsman;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Controller {
    Model model;
    View view;

    void pageSwitchingControl(){
        if(model.pointerPage == 1){
            view.disableLeft();
        }else view.enableLeft();
        if(model.pointerPage == model.quantityPages){
            view.disableRight();
        }else view.enableRight();

    }

    public File onOpen() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open table");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("xml", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File openFile = fileChooser.showOpenDialog(null);
        return openFile;
    }

    public File onSave() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save table");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("xml", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(null);
        return file;
    }

    void event(){
        pageSwitchingControl();
        view.getAddButton().setOnAction(e->{
            ModalWindowAdd.newWindow();
            Sportsman newSportsman = ModalWindowAdd.getResult();
            if(newSportsman != null){
                model.addElement(newSportsman);
                view.fillingTable(model.getTable(view.getLabel(), view.getCounterElements(), view.getQuantityPages()));
                pageSwitchingControl();
            }
        });
        view.add.setOnAction(e->{
            ModalWindowAdd.newWindow();
            Sportsman newSportsman = ModalWindowAdd.getResult();
            if(newSportsman != null){
                model.addElement(newSportsman);
                view.fillingTable(model.getTable(view.getLabel(), view.getCounterElements(), view.getQuantityPages()));
                pageSwitchingControl();
            }
        });
        view.getSearch().setOnAction(e->{
            ModalWindowSearch.newWindow(model.getTable());
        });
        view.search.setOnAction(e->{
            ModalWindowSearch.newWindow(model.getTable());
        });
        view.getDelete().setOnAction(e->{
            model.deleteElements();
            view.fillingTable(model.getTable(view.getLabel(), view.getCounterElements(), view.getQuantityPages()));
            pageSwitchingControl();
        });
        view.delete.setOnAction(e->{
            model.deleteElements();
            view.fillingTable(model.getTable(view.getLabel(), view.getCounterElements(), view.getQuantityPages()));
            pageSwitchingControl();
        });
        view.getOpenButton().setOnAction(e->{
            try {
                model.openFile(onOpen());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (SAXException saxException) {
                saxException.printStackTrace();
            } catch (ParserConfigurationException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            }
            view.fillingTable(model.getTable(view.getLabel(), view.getCounterElements(), view.getQuantityPages()));
            pageSwitchingControl();
        });
        view.load.setOnAction(e->{
            try {
                model.openFile(onOpen());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (SAXException saxException) {
                saxException.printStackTrace();
            } catch (ParserConfigurationException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            }
            view.fillingTable(model.getTable(view.getLabel(), view.getCounterElements(), view.getQuantityPages()));
            pageSwitchingControl();
        });
        view.getSaveButton().setOnAction(e->{
            try {
                model.saveFile(onSave());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (TransformerException transformerException) {
                transformerException.printStackTrace();
            } catch (ParserConfigurationException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            }
        });
        view.save.setOnAction(e->{
            try {
                model.saveFile(onSave());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (TransformerException transformerException) {
                transformerException.printStackTrace();
            } catch (ParserConfigurationException parserConfigurationException) {
                parserConfigurationException.printStackTrace();
            }
        });
        view.getPageNextButton().setOnAction(e->{
            model.nextPage();
            view.fillingTable(model.getTable(view.getLabel(), view.getCounterElements(), view.getQuantityPages()));
            pageSwitchingControl();
        });
        view.getPagePrevButton().setOnAction(e->{
            model.prevPage();
            view.fillingTable(model.getTable(view.getLabel(), view.getCounterElements(), view.getQuantityPages()));
            pageSwitchingControl();
        });
        view.getPageOneButton().setOnAction(e->{
            model.pageOne();
            view.fillingTable(model.getTable(view.getLabel(), view.getCounterElements(), view.getQuantityPages()));
            pageSwitchingControl();
        });
        view.getPageLastButton().setOnAction(e->{
            model.lastPage();
            view.fillingTable(model.getTable(view.getLabel(), view.getCounterElements(), view.getQuantityPages()));
            pageSwitchingControl();
        });
        view.getEditNumberRow().setOnAction(e->{
            String stringNumberRow = view.getNumberRowField().getText();
            int numberRow = Integer.parseInt(stringNumberRow);
            model.setNumberRow(numberRow);
            model.pointerPage = 1;
            view.setSettingsTable(model.numberRow);
            view.fillingTable(model.getTable(view.getLabel(), view.getCounterElements(), view.getQuantityPages()));
            pageSwitchingControl();
        });
    }

    public Controller(Stage primaryStage){
        model = new Model(10);
        view = new View(primaryStage, model.getTable());
        event();
    }
}
