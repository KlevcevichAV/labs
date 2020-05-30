package sample.controller;


import javafx.scene.control.Alert;
import sample.parser.Parser;
import sample.view.View;
import sample.view.keyboard.button.Button;

public class Controller {
    private View view;
    private Parser parser;

    private String copy(int begin, int end, String expression) {
        String result = "";
        for (int i = begin; i < end; i++) {
            result = result + expression.charAt(i);
        }
        return result;
    }

    private void createInformationWindow(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Оповещение");
        alert.setHeaderText("Ошибка ввода.");
        alert.setContentText(content);

        alert.showAndWait();
    }

    private boolean checkPoint(char symbol){
        return symbol == '.';
    }

    private boolean checkSign(char symbol){
        if(symbol == '+') return true;
        if(symbol == '-') return true;
        if(symbol == '*') return true;
        if(symbol == '/') return true;
        if(symbol == '%') return true;
        if(symbol == '√') return true;
        return false;
    }

    private boolean checkAnotherPoint(String expression){
        if(expression.length() == 0) return true;
        for(int i = expression.length() - 1; i >= 0; i--){
            if(expression.charAt(i) < '0' || expression.charAt(i) > '9'){
                if(expression.charAt(i) =='.') return true; else return false;
            }
        }
        return false;
    }

    private int checkTrigonometricFunction(String expression){
        if(expression.length() == 0 || expression.length() < 3) return -1;
        if(expression.charAt(expression.length() - 1) != '(') return -1;
        String check = copy(expression.length() - 4, expression.length(), expression);
        if(check == "tg(") return 3;
        if(expression.length() != 4) return -1;
        check = copy(expression.length() - 5, expression.length(), expression);
        if(check == "sin(") return 1;
        if(check == "cos(") return 2;
        if(check == "ctg(") return 4;
        return -1;
    }

    private void setEventButton(Button button) {
        button.setOnAction(e -> {
            String expression = view.getDisplay().getText();
            if(expression.length() == 0){
                view.getDisplay().setText(view.getDisplay().getText() + button.getText());
                return;
            }
            if(expression.charAt(expression.length() - 1) == ')'){
                createInformationWindow("Поставьте какой-нибудь знак перед вводом числа или уберите скобку.");
            }
            else view.getDisplay().setText(view.getDisplay().getText() + button.getText());
        });
    }

    private void setEventPoint(Button button) {
        button.setOnAction(e -> {
            String expression = view.getDisplay().getText();
            if (expression.length() == 0) {
                view.getDisplay().setText(0 + button.getText());
                return;
            }
            char symbol = expression.charAt(expression.length() - 1);
            if(checkSign(symbol)){
                view.getDisplay().setText(expression + 0 + button.getText());
                return;
            }
            if(symbol >= '0' && symbol <= '9'){
                view.getDisplay().setText(expression + button.getText());
                return;
            }
            if(checkAnotherPoint(expression)){
                createInformationWindow("В числе не может быть 2 точки.");
                return;
            }
            if(symbol == '('){
                view.getDisplay().setText(expression + 0 + button.getText());
                return;
            }else {
                createInformationWindow("Нельзя поставить точку после закрывающейся скобки.");
                return;
            }
        });
    }

    private void setEventClear(Button button){
        button.setOnAction(e->{
            view.getDisplay().clear();
        });
    }

    private void deleteOneCharacter(){
        String expression = view.getDisplay().getText();
        if(expression.length() == 0) return;
        int check = checkTrigonometricFunction(expression);
        switch (check){
            case -1:{
                String newExpression = copy(0, expression.length() - 1, expression);
                view.getDisplay().setText(newExpression);
                break;
            }
            case 3:{
                String newExpression = copy(0, expression.length() - 3, expression);
                view.getDisplay().setText(newExpression);
                break;
            }
            default:{
                String newExpression = copy(0, expression.length() - 4, expression);
                view.getDisplay().setText(newExpression);
            }
        }
    }

    private void setEventDeleteOneCharacter(Button button){
        button.setOnAction(e->{
            deleteOneCharacter();
        });
    }

    private void setEventDigitalKeyboard() {
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

    private void setEventOperationKeyboard(){
        setEventClear(view.getKeyboard().getKeyboardOperations().getCleaningSign());
        setEventDeleteOneCharacter(view.getKeyboard().getKeyboardOperations().getSingleCharacterDelete());
    }

    private void setEvent() {
        setEventDigitalKeyboard();
        setEventOperationKeyboard();
    }

    public View getView() {
        return view;
    }

    public Controller() {
        view = new View();
        parser = new Parser();
        setEvent();
    }
}
