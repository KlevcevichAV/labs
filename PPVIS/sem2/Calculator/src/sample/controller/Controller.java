package sample.controller;


import javafx.scene.control.Alert;
import sample.parser.Parser;
import sample.view.View;
import sample.view.keyboard.button.Button;

public class Controller {
    private View view;
    private Parser parser;

    private void setEventButton(Button button) {
        button.setOnAction(e -> {
            view.getDisplay().setText(view.getDisplay().getText() + button.getText());
        });
    }

    private void createInformationWindow(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Оповещение");
        alert.setHeaderText("Ошибка ввода.");
        alert.setContentText(content);

        alert.showAndWait();
    }


    private void setEventPoint(Button button) {
        button.setOnAction(e -> {
            String expression = view.getDisplay().getText();
            if (expression.length() == 0) {
                createInformationWindow("Ставьте точку  после цифры.");
            }
            char symbol = expression.charAt(expression.length() - 1);
            if ((symbol >= '0' && symbol <= '9') && parser.checkAnotherPoint(expression)) {
                view.getDisplay().setText(view.getDisplay().getText() + button.getText());
            } else {
                if (parser.checkAnotherPoint(expression)) {
                    createInformationWindow("Ставьте точку  после цифры.");
                } else {
                    createInformationWindow("В одном числе не может быть более 1 точки.");
                }
            }
        });
    }

    private void eventDigitalKeyboard() {
        setEventButton(view.getKeyboard().getDigitalKeyboard().getZero());
        setEventButton(view.getKeyboard().getDigitalKeyboard().getOne());
        setEventButton(view.getKeyboard().getDigitalKeyboard().getTwo());
        setEventButton(view.getKeyboard().getDigitalKeyboard().getThree());
        setEventButton(view.getKeyboard().getDigitalKeyboard().getFour());
        setEventButton(view.getKeyboard().getDigitalKeyboard().getFive());
        setEventButton(view.getKeyboard().getDigitalKeyboard().getSix());
        setEventButton(view.getKeyboard().getDigitalKeyboard().getSeven());
        setEventButton(view.getKeyboard().getDigitalKeyboard().getEight());
        setEventButton(view.getKeyboard().getDigitalKeyboard().getNine());
        setEventPoint(view.getKeyboard().getDigitalKeyboard().getDecimalPoint());
    }

    private void event() {
        eventDigitalKeyboard();
    }

    public View getView() {
        return view;
    }

    public Controller() {
        view = new View();
        parser = new Parser();
        event();
    }
}
