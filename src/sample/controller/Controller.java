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
        if (symbol == '+') return true;
        if (symbol == '-') return true;
        if (symbol == '*') return true;
        if (symbol == '/') return true;
        if (symbol == '%') return true;
        if (symbol == '√') return true;
        return false;
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

    private boolean checkNumber(char symbol) {
        return (symbol >= '0' && symbol <= '9');
    }

    private int checkTrigonometricFunction(String expression) {
        if (expression.length() == 0 || expression.length() < 3) return -1;
        if (expression.charAt(expression.length() - 1) != '(') return -1;
        String check = copy(expression.length() - 4, expression.length(), expression);
        if (check == "tg(") return 3;
        if (expression.length() != 4) return -1;
        check = copy(expression.length() - 5, expression.length(), expression);
        if (check == "sin(") return 1;
        if (check == "cos(") return 2;
        if (check == "ctg(") return 4;
        return -1;
    }

    private void deleteOneCharacter() {
        String expression = view.getDisplay().getText();
        if (expression.length() == 0) return;
        int check = checkTrigonometricFunction(expression);
        switch (check) {
            case -1: {
                String newExpression = copy(0, expression.length() - 1, expression);
                view.getDisplay().setText(newExpression);
                break;
            }
            case 3: {
                String newExpression = copy(0, expression.length() - 3, expression);
                view.getDisplay().setText(newExpression);
                break;
            }
            default: {
                String newExpression = copy(0, expression.length() - 4, expression);
                view.getDisplay().setText(newExpression);
            }
        }
    }

    private void openingBracket() {
        String expression = view.getDisplay().getText();
        if (expression.length() == 0) {
            view.getDisplay().setText("(");
            return;
        }
        char symbol = expression.charAt(expression.length() - 1);
        if (checkNumber(symbol) || symbol == ')') {
            view.getDisplay().setText(expression + "*(");
            return;
        }
        view.getDisplay().setText(expression + "(");
    }

    private void closingBracket() {
        String expression = view.getDisplay().getText();
        if (expression.length() == 0) return;
        char symbol = expression.charAt(expression.length() - 1);
        if (symbol == '(') return;
        if (checkSign(symbol)) return;
        if (checkBracket(expression)) return;
        view.getDisplay().setText(expression + ")");
    }

    private void signMinus(String sign) {
        String expression = view.getDisplay().getText();
        if (expression.length() == 0) {
            view.getDisplay().setText(sign);
            return;
        }
        char symbol = expression.charAt(expression.length() - 1);
        if (symbol == '(') {
            view.getDisplay().setText(expression + sign);
            return;
        }
        if (symbol == '√') return;
        if (checkSign(symbol)) {
            String newExpression = copy(0, expression.length() - 1, expression);
            view.getDisplay().setText(newExpression + sign);
            return;
        }
        view.getDisplay().setText(expression + sign);
    }

    private void sign(String sign) {
        String expression = view.getDisplay().getText();
        if (expression.length() == 0) return;
        char symbol = expression.charAt(expression.length() - 1);
        if (symbol == '(') return;
        if (symbol == '√') return;
        if (checkSign(symbol)) {
            if(expression.length() == 1) return;
            if(expression == "(-") return;
            String newExpression = copy(0, expression.length() - 1, expression);
            view.getDisplay().setText(newExpression + sign);
            return;
        }
        view.getDisplay().setText(expression + sign);
    }

    private void root() {
        String expression = view.getDisplay().getText();
        if (expression.length() == 0) {
            view.getDisplay().setText("√");
            return;
        }
        char symbol = expression.charAt(expression.length() - 1);
        if (symbol == ')' || checkNumber(symbol)) {
            view.getDisplay().setText(expression + "*√");
            return;
        }
//        if (checkSign(symbol)) {
//            view.getDisplay().setText(expression + "√");
//        }
        view.getDisplay().setText(expression + "√");
    }

    private void equal() {
        String expression = view.getDisplay().getText();
        if(expression.length() == 0) return;
        int pointer = checkClosingBracket(expression);
        for(int i = 0; i < pointer; i++){
            expression = expression + ')';
        }
        view.getDisplay().setText(expression);
        Double resultD = parser.start(expression);
        if(resultD == null){
            System.out.println("Infinity");
            return;
        }
        String result = "" + resultD;
        if (result.charAt(result.length() - 1) == '0' && result.charAt(result.length() - 2) == '.') {
            result = copy(0, result.length() - 2, result);
        }
        System.out.println(resultD);
    }

    private void trigonometric(String sign) {
        sign = sign + '(';
        String expression = view.getDisplay().getText();
        if (expression.length() == 0) {
            view.getDisplay().setText(sign);
            return;
        }
        char symbol = expression.charAt(expression.length() - 1);
        if (checkNumber(symbol) || symbol == ')') {
            view.getDisplay().setText(expression + "*" + sign);
            return;
        }
        if (checkSign(symbol) || symbol == '(') {
            view.getDisplay().setText(expression + sign);
            return;
        }
    }

    private void fraction(String sign) {
        String expression = view.getDisplay().getText();
        if (expression.length() == 0) {
            view.getDisplay().setText(sign);
            return;
        }
        char symbol = expression.charAt(expression.length() - 1);
        if (checkNumber(symbol)) {
            view.getDisplay().setText(expression + "*" + sign);
            return;
        }
        if (symbol == ')') {
            view.getDisplay().setText(expression + "*" + sign);
            return;
        }
        if (checkSign(symbol) || symbol == '(') {
            view.getDisplay().setText(expression + sign);
        }
    }

    private void setEventButton(Button button) {
        button.setOnAction(e -> {
            String expression = view.getDisplay().getText();
            if (expression.length() == 0) {
                view.getDisplay().setText(view.getDisplay().getText() + button.getText());
                return;
            }
            if (expression.charAt(expression.length() - 1) == ')') {
                return;
            } else view.getDisplay().setText(view.getDisplay().getText() + button.getText());
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
            if (checkSign(symbol)) {
                view.getDisplay().setText(expression + 0 + button.getText());
                return;
            }
            if (checkAnotherPoint(expression)) {
//                createInformationWindow("В числе не может быть 2 точки.");
                return;
            }
            if (symbol >= '0' && symbol <= '9') {
                view.getDisplay().setText(expression + button.getText());
                return;
            }
            if (symbol == '(') {
                view.getDisplay().setText(expression + 0 + button.getText());
                return;
            } else {
//                createInformationWindow("Нельзя поставить точку после закрывающейся скобки.");
                return;
            }
        });
    }

    private void setEventClear(Button button) {
        button.setOnAction(e -> {
            view.getDisplay().clear();
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

    public Controller() {
        view = new View();
        parser = new Parser();
        setEvent();
    }
}
