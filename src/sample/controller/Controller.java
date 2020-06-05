package sample.controller;


import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import sample.parser.Parser;
import sample.view.View;
import sample.view.keyboard.button.Button;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private View view;
    private Parser parser;
    private TreeItem<String> root;
    private final String sign = "+-*/%√";
    private final String operation = "+-*/%";
    private List<String> unaryOperation;

    public String copy(int begin, int end, String expression) {
        String result = "";
        StringBuilder stringBuilder = new StringBuilder(result);
        for (int i = begin; i < end; i++) {
            stringBuilder.append(expression.charAt(i));
        }
        result = stringBuilder.toString();
        return result;
    }

    private void createInformationWindow(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Оповещение");
        alert.setHeaderText("Ошибка ввода.");
        alert.setContentText(content);

        alert.showAndWait();
    }

    private void createUnaryOperation(){
        unaryOperation = List.of("--", "√", "cos", "sin", "tg", "ctg");
//        unaryOperation.add("--");
//        unaryOperation.add("√");
//        unaryOperation.add("cos");
//        unaryOperation.add("sin");
//        unaryOperation.add("tg");
//        unaryOperation.add("ctg");
    }

    private boolean checkPoint(char symbol) {
        return symbol == '.';
    }

    private int checkClosingBracket(String expression) {
        int counter = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') counter++;
            if (expression.charAt(i) == ')') counter--;
        }
        return counter;
    }

    private boolean checkBracket(String expression) {
        int counter = checkClosingBracket(expression);
        if (counter == 0) return true;
        return false;
    }

    private boolean checkSign(char symbol) {
        int check = sign.indexOf(symbol);
        return check != -1;
    }

    private boolean checkUnaryOperation(String operation) {
        boolean check = unaryOperation.contains(operation);
        return check;
    }

    private boolean checkOperation(String operation) {
        if(operation.equals("")) return false;
        int check = this.operation.indexOf(operation);
        return check != -1;
    }

    private boolean checkAnotherPoint(String expression) {
        if (expression.length() == 0) return true;
        for (int i = expression.length() - 1; i >= 0; i--) {
            if (expression.charAt(i) < '0' || expression.charAt(i) > '9') {
                if (expression.charAt(i) == '.') return true;
                else return false;
            }
        }
        return false;
    }

    private boolean checkNumeric(String numeric){
        if(numeric.equals("-")) return false;
        for(int i = 0; i < numeric.length(); i++){
            if(i == 0 && numeric.charAt(i) == '-') continue;
            if(numeric.charAt(i) < '0' || numeric.charAt(i) > '9'){
                if(numeric.charAt(i) != '.') return false;
            }
        }
        return true;
    }

    private boolean checkNumber(char symbol) {
        return (symbol >= '0' && symbol <= '9');
    }

    private int checkTrigonometricFunction(String expression) {
        if (expression.length() == 0 || expression.length() < 3) return -1;
        if (expression.charAt(expression.length() - 1) != '(') return -1;
        String check = copy(expression.length() - 3, expression.length(), expression);
        int result = -1;
        if (check.equals("tg(")) result = 3;
        if (expression.length() < 4) return result;
        check = copy(expression.length() - 4, expression.length(), expression);
        if (check.equals("sin(")) return 1;
        if (check.equals("cos(")) return 2;
        if (check.equals("ctg(")) return 4;
        return result;
    }

    private void deleteOneCharacter() {
        String expression = view.getDisplayExpression().getText();
        if (expression.length() == 0) return;
        int check = checkTrigonometricFunction(expression);
        switch (check) {
            case -1: {
                String newExpression = copy(0, expression.length() - 1, expression);
                view.getDisplayExpression().setText(newExpression);
                break;
            }
            case 3: {
                String newExpression = copy(0, expression.length() - 3, expression);
                view.getDisplayExpression().setText(newExpression);
                break;
            }
            default: {
                String newExpression = copy(0, expression.length() - 4, expression);
                view.getDisplayExpression().setText(newExpression);
            }
        }
    }

    private void openingBracket() {
        String expression = view.getDisplayExpression().getText();
        if (expression.length() == 0) {
            view.getDisplayExpression().setText("(");
            return;
        }
        char symbol = expression.charAt(expression.length() - 1);
        if (checkNumber(symbol) || symbol == ')') {
            view.getDisplayExpression().setText(expression + "*(");
            return;
        }
        view.getDisplayExpression().setText(expression + "(");
    }

    private void closingBracket() {
        String expression = view.getDisplayExpression().getText();
        if (expression.length() == 0) return;
        char symbol = expression.charAt(expression.length() - 1);
        if (symbol == '(') return;
        if (checkSign(symbol)) return;
        if (checkBracket(expression)) return;
        view.getDisplayExpression().setText(expression + ")");
    }

    private void signMinus(String sign) {
        String expression = view.getDisplayExpression().getText();
        if (expression.length() == 0) {
            view.getDisplayExpression().setText(sign);
            return;
        }
        char symbol = expression.charAt(expression.length() - 1);
        if (symbol == '.') return;
        if (symbol == '(') {
            view.getDisplayExpression().setText(expression + sign);
            return;
        }
        if (symbol == '√') return;
        if (checkSign(symbol)) {
            String newExpression = copy(0, expression.length() - 1, expression);
            view.getDisplayExpression().setText(newExpression + sign);
            return;
        }
        view.getDisplayExpression().setText(expression + sign);
    }

    private void sign(String sign) {
        String expression = view.getDisplayExpression().getText();
        if (expression.length() == 0) return;
        char symbol = expression.charAt(expression.length() - 1);
        if (symbol == '(') return;
        if (symbol == '.') return;
        if (symbol == '√') return;
        if (checkSign(symbol)) {
            if (expression.length() == 1) return;
            if (expression == "(-") return;
            String newExpression = copy(0, expression.length() - 1, expression);
            view.getDisplayExpression().setText(newExpression + sign);
            return;
        }
        view.getDisplayExpression().setText(expression + sign);
    }

    private void root() {
        String expression = view.getDisplayExpression().getText();
        if (expression.length() == 0) {
            view.getDisplayExpression().setText("√");
            return;
        }
        char symbol = expression.charAt(expression.length() - 1);
        if (symbol == ')' || checkNumber(symbol)) {
            view.getDisplayExpression().setText(expression + "*√");
            return;
        }
        view.getDisplayExpression().setText(expression + "√");
    }

    private void equal() {
        String expression = view.getDisplayExpression().getText();
        if (expression.length() == 0) return;
        int pointer = checkClosingBracket(expression);
        for (int i = 0; i < pointer; i++) {
            expression = expression + ')';
        }
        view.getDisplayExpression().setText(expression);
        Double resultD = parser.start(expression);
        if (resultD == null) {
            view.getDisplayExpression().setText("Undefined");
            return;
        }
        String result = "" + resultD;
        createTree();
        if (result.charAt(result.length() - 1) == '0' && result.charAt(result.length() - 2) == '.') {
            result = copy(0, result.length() - 2, result);
        }
        view.getDisplayResult().setText(result);
//        System.out.println(resultD);
    }

    private void trigonometric(String sign) {
        sign = sign + '(';
        String expression = view.getDisplayExpression().getText();
        if (expression.length() == 0) {
            view.getDisplayExpression().setText(sign);
            return;
        }
        char symbol = expression.charAt(expression.length() - 1);
        if (checkNumber(symbol) || symbol == ')') {
            view.getDisplayExpression().setText(expression + "*" + sign);
            return;
        }
        if (checkSign(symbol) || symbol == '(') {
            view.getDisplayExpression().setText(expression + sign);
            return;
        }
    }

    private void fraction(String sign) {
        String expression = view.getDisplayExpression().getText();
        if (expression.length() == 0) {
            view.getDisplayExpression().setText(sign);
            return;
        }
        char symbol = expression.charAt(expression.length() - 1);
        if (checkNumber(symbol)) {
            view.getDisplayExpression().setText(expression + "*" + sign);
            return;
        }
        if (symbol == ')') {
            view.getDisplayExpression().setText(expression + "*" + sign);
            return;
        }
        if (checkSign(symbol) || symbol == '(') {
            view.getDisplayExpression().setText(expression + sign);
        }
    }

    private void setEventButton(Button button) {
        button.setOnAction(e -> {
            String expression = view.getDisplayExpression().getText();
            if (expression.length() == 0) {
                view.getDisplayExpression().setText(view.getDisplayExpression().getText() + button.getText());
                return;
            }
            if (expression.charAt(expression.length() - 1) == ')') {
                return;
            } else view.getDisplayExpression().setText(view.getDisplayExpression().getText() + button.getText());
        });
    }

    private void setEventPoint(Button button) {
        button.setOnAction(e -> {
            String expression = view.getDisplayExpression().getText();
            if (expression.length() == 0) {
                view.getDisplayExpression().setText(0 + button.getText());
                return;
            }
            char symbol = expression.charAt(expression.length() - 1);
            if (checkSign(symbol)) {
                view.getDisplayExpression().setText(expression + 0 + button.getText());
                return;
            }
            if (checkAnotherPoint(expression)) {
//                createInformationWindow("В числе не может быть 2 точки.");
                return;
            }
            if (symbol >= '0' && symbol <= '9') {
                view.getDisplayExpression().setText(expression + button.getText());
                return;
            }
            if (symbol == '(') {
                view.getDisplayExpression().setText(expression + 0 + button.getText());
                return;
            } else {
//                createInformationWindow("Нельзя поставить точку после закрывающейся скобки.");
                return;
            }
        });
    }

    private void setEventClear(Button button) {
        button.setOnAction(e -> {
            view.getDisplayExpression().clear();
            view.getDisplayResult().clear();
            view.createTree(null);
        });
    }

    private void setEventSignMinus(Button button) {
        button.setOnAction(e -> {
            signMinus(button.getText());
        });
    }

    private void setEventSign(Button button) {
        button.setOnAction(e -> {
            sign(button.getText());
        });
    }

    private void setEventRoot(Button button) {
        button.setOnAction(e -> {
            root();
        });
    }

    private void setEventDeleteOneCharacter(Button button) {
        button.setOnAction(e -> {
            deleteOneCharacter();
        });
    }

    private void setEventOpeningBracket(Button button) {
        button.setOnAction(e -> {
            openingBracket();
        });
    }

    private void setEventClosingBracket(Button button) {
        button.setOnAction(e -> {
            closingBracket();
        });
    }

    private void setEventFraction(Button button) {
        button.setOnAction(e -> {
            fraction("1/");
        });
    }

    private void setEventEqual(Button button) {
        button.setOnAction(e -> {
            equal();
        });
    }

    private void setEventTrigonometric(Button button) {
        button.setOnAction(e -> {
            trigonometric(button.getText());
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

    private void setEventOperationKeyboard() {
        setEventClear(view.getKeyboard().getKeyboardOperations().getCleaningSign());
        setEventDeleteOneCharacter(view.getKeyboard().getKeyboardOperations().getSingleCharacterDelete());
        setEventOpeningBracket(view.getKeyboard().getKeyboardOperations().getLeftBracket());
        setEventClosingBracket(view.getKeyboard().getKeyboardOperations().getRightBracket());
        setEventSign(view.getKeyboard().getKeyboardOperations().getPlusSign());
        setEventSignMinus(view.getKeyboard().getKeyboardOperations().getMinusSign());
        setEventSign(view.getKeyboard().getKeyboardOperations().getMultiplicationSign());
        setEventSign(view.getKeyboard().getKeyboardOperations().getDivisionSign());
        setEventSign(view.getKeyboard().getKeyboardOperations().getModSign());
        setEventFraction(view.getKeyboard().getKeyboardOperations().getFractionSign());
        setEventRoot(view.getKeyboard().getKeyboardOperations().getRootSign());
        setEventTrigonometric(view.getKeyboard().getKeyboardOperations().getSinSign());
        setEventTrigonometric(view.getKeyboard().getKeyboardOperations().getCosSign());
        setEventTrigonometric(view.getKeyboard().getKeyboardOperations().getTgSign());
        setEventTrigonometric(view.getKeyboard().getKeyboardOperations().getCtgSign());
        setEventEqual(view.getKeyboard().getKeyboardOperations().getEqualSign());
    }

    private void setEvent() {
        setEventDigitalKeyboard();
        setEventOperationKeyboard();
    }

    public View getView() {
        return view;
    }

    private void createTree() {
        root = fillTree(new TreeItem(), 0, 0);
        root = root.getChildren().get(0);
        view.createTree(root);
    }

    private String createExpression(){
        String result = "";
        if(checkNumeric(root.getValue())){
            result = root.getValue();
            if (result.charAt(result.length() - 1) == '0' && result.charAt(result.length() - 2) == '.') {
                result = copy(0, result.length() - 2, result);
            }
            return result;
        }
        result = createElementExpression(root);
        return result;
    }

    public String createElementExpression(TreeItem<String> item){
        String result = "";
        if(checkNumeric(item.getValue())) {
            result = item.getValue();
            if (result.charAt(result.length() - 1) == '0' && result.charAt(result.length() - 2) == '.') {
                result = copy(0, result.length() - 2, result);
            }
            return result;
        }
        if(checkOperation(item.getValue())){
            String left = createElementExpression(item.getChildren().get(0));
            String right = createElementExpression(item.getChildren().get(1));
            result = '(' + left + item.getValue() + right + ')';
        }
        if(checkUnaryOperation(item.getValue())){
            result = item.getValue() + '(' + createElementExpression(item.getChildren().get(0)) + ')';
        }
        return result;
    }

    private TreeItem fillTree(TreeItem root, int pointer, int pointerRoot) {
        if(!parser.comparingArraySizeAndPointer()) return null;
        int pointerArray = parser.pointerExpression;
        String element = parser.getElement();
        String expression = parser.arrayExpression.get(parser.pointerExpression).value;
        parser.incPointerExpression();
        TreeItem<String> item = new TreeItem<>(expression);
        if(checkUnaryOperation(element)){
            item = fillTree(item, 0, pointerArray);
        }
        if(checkOperation(element)){
            item = fillTree(item, 1, pointerArray);
            item = fillTree(item, 2, pointerArray);
        }
        if(pointer == 2){
            if(parser.arrayExpression.get(pointerRoot).sign.equals("-")){
                if(item.getValue().charAt(0) == '-'){
                    String newNumber = copy(1, item.getValue().length(), item.getValue());
                    item.setValue(newNumber);
                }
            }
        }
        TreeItem<String> finalItem = item;
        item.expandedProperty().addListener(e->{
            finalItem.setValue(finalItem.isExpanded() ? parser.arrayExpression.get(pointerArray).sign : parser.arrayExpression.get(pointerArray).value);
            String fullExpression = view.getDisplayExpression().getText();
            view.getDisplayExpression().setText(createExpression());
        });
//        finalItem.setValue(parser.arrayExpression.get(pointerArray).value);
        root.getChildren().add(item);
        return root;
    }

    public Controller(Stage stage) {
        createUnaryOperation();
        view = new View(stage);
        parser = new Parser();
        setEvent();
    }
}