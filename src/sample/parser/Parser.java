package sample.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Parser {
    public int pointerExpression;
    public List<TreeNote> arrayExpression;
    private final String operation = "*/%";
    private final String operationPM = "+-";

    private boolean checkOperationPM(String operation) {
        if (operation.equals("")) return false;
        for (int i = 1; i < operation.length(); i++) {
            if (this.operation.indexOf(operation.charAt(i)) != -1) return true;
            if (this.operationPM.indexOf(operation.charAt(i)) != -1) return false;
        }
        return false;
    }

    public boolean comparingArraySizeAndPointer() {
        return pointerExpression < arrayExpression.size();
    }

    public String getElement() {
        return arrayExpression.get(pointerExpression).sign;
    }

    public String getPrevElementExpression() {
        return arrayExpression.get(pointerExpression - 1).expression;
    }

    public String getPrevElementValue() {
        return arrayExpression.get(pointerExpression - 1).value;
    }

    public void incPointerExpression() {
        pointerExpression++;
    }

    public int checkParentheses(int begin, String expression) {
        int pointer = -1;
        int check = 0;
        if (expression.charAt(begin) == '(') check++;
        int i = begin + 1;
        while (check != 0 && i != expression.length()) {
            if (expression.charAt(i) == '(') check++;
            if (expression.charAt(i) == ')') check--;
            i++;
        }
        if (check == 0) {
            return i;
        } else return pointer;
    }

    public String copy(int begin, int end, String expression) {
        String result = "";
        StringBuilder stringBuilder = new StringBuilder(result);
        for (int i = begin; i < end; i++) {
            stringBuilder.append(expression.charAt(i));
        }
        result = stringBuilder.toString();
        return result;
    }

    public boolean checkForKeyElements(String expression) {
        if (expression.indexOf('+') != -1) return true;
        if (expression.indexOf('-') != -1) return true;
        if (expression.indexOf('*') != -1) return true;
        if (expression.indexOf('/') != -1) return true;
        if (expression.indexOf('%') != -1) return true;
        return expression.indexOf('√') != -1;
    }

    public int searchSign(String expression, char sign) {
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == sign) {
                return i;
            }
            if (expression.charAt(i) == '(') i = checkParentheses(i, expression) - 1;
        }
        return -1;
    }

    public int searchMainPointer(String expression) {
        int result = searchSign(expression, '%');
        if (result != -1) return result;
        result = searchSign(expression, '+');
        if (result != -1) return result;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '-') {
                if (i == 0) {
                    result = 0;
                    continue;
                }
                return i;
            }
            if (expression.charAt(i) == '(') i = checkParentheses(i, expression) - 1;
        }
        result = searchSign(expression, '*');
        if (result != -1) return result;
        result = searchSign(expression, '/');
        if (result != -1) return result;
        result = searchSign(expression, '^');
        if (result != -1) return result;
        if (result == 0) return result;
        result = searchSign(expression, '√');
        return result;
    }

    private double factorial(double number) {
        double result = 1;
        int size = (int) number;
        for (int i = 2; i <= size; i++) {
            result *= i;
        }
        return result;
    }

    public String searchTrigFunc(String expression) {
        int pointer = searchMainPointer(expression);
        if (pointer == -1) {
            if (expression.charAt(0) == 't' && expression.charAt(1) == 'g') {
                return "tg";
            }
            if (expression.charAt(0) == 'c' && expression.charAt(1) == 't' && expression.charAt(2) == 'g') {
                return "ctg";
            }
            if (expression.charAt(0) == 's' && expression.charAt(1) == 'i' && expression.charAt(2) == 'n') {
                return "sin";
            }
            if (expression.charAt(0) == 'c' && expression.charAt(1) == 'o' && expression.charAt(2) == 's') {
                return "cos";
            }
            if (expression.charAt(0) == 'l' && expression.charAt(1) == 'g') {
                return "lg";
            }
        }
        return null;
    }

    private String removeBracket(String expression) {
        while (expression.charAt(0) == '(') {
            int pointer = checkParentheses(0, expression);
            if (pointer == expression.length()) {
                expression = copy(1, expression.length() - 1, expression);
            } else break;
        }
        return expression;
    }

    private Double negativeNumber(String expression) {
        String newExpression = copy(1, expression.length(), expression);
        double result = -parsing(newExpression);
        arrayExpression.get(arrayExpression.size() - 2).edit(2, "(" + expression + ")");
        arrayExpression.get(arrayExpression.size() - 2).edit(0, Double.toString(result));
        arrayExpression.remove(arrayExpression.size() - 1);
        return result;
    }

    private Double trigonometricFunctionAndLg(String expression, String function, int thisPointer) {
        arrayExpression.get(thisPointer).edit(1, "" + function);
        switch (function) {
            case "cos": {
                String newExpression = copy(3, expression.length(), expression);
                Double result = Math.cos(parsing(newExpression));
                arrayExpression.get(thisPointer).edit(0, "" + result);
                return result;
            }
            case "sin": {
                String newExpression = copy(3, expression.length(), expression);
                Double result = Math.sin(parsing(newExpression));
                arrayExpression.get(thisPointer).edit(0, "" + result);
                return result;
            }
            case "tg": {
                String newExpression = copy(2, expression.length(), expression);
                Double result = Math.cos(parsing(newExpression));
                arrayExpression.get(thisPointer).edit(0, "" + result);
                return result;
            }
            case "ctg": {
                String newExpression = copy(3, expression.length(), expression);
                Double result = 1.0 / Math.tan(parsing(newExpression));
                arrayExpression.get(thisPointer).edit(0, "" + result);
                return result;
            }
            case "lg": {
                String newExpression = copy(2, expression.length(), expression);
                return Math.log10(parsing(newExpression));
            }
            default: {
                return null;
            }
        }
    }

    private Double sum(String numberLeft, String numberRight, int thisPointer) {
        Double left = parsing(numberLeft);
        Double right = parsing(numberRight);
        if (left == null || right == null) return null;
        Double result = left + right;
        arrayExpression.get(thisPointer).edit(0, "" + result);
        return left + right;
    }

    private Double difference(String expression, String numberLeft, String numberRight, int thisPointer, int pointer) {
        boolean checkSign = checkOperationPM(numberRight);
        if (checkSign) numberRight = copy(pointer + 1, expression.length(), expression);
        else numberRight = copy(pointer, expression.length(), expression);
        Double left = parsing(numberLeft);
        int pointerElement = arrayExpression.size();
        Double right = parsing(numberRight);
        if (checkSign) {
            Double result = left - right;
            arrayExpression.get(thisPointer).edit(0, "" + result);
            return result;
        }
        Double temp = right * -1;
        arrayExpression.get(pointerElement).edit(0, temp.toString());
        if (left == null || right == null) return null;
        Double result = left + right;
        arrayExpression.get(thisPointer).edit(0, "" + result);
        return result;
    }

    private Double multiplication(String numberLeft, String numberRight, int thisPointer) {
        Double left = parsing(numberLeft);
        Double right = parsing(numberRight);
        if (left == null || right == null) return null;
        Double result = left * right;
        arrayExpression.get(thisPointer).edit(0, "" + result);
        return result;
    }

    private Double pow(String numberLeft, String numberRight, int thisPointer) {
        Double left = parsing(numberLeft);
        Double right = parsing(numberRight);
        if (left == null || right == null) return null;
        Double result = Math.pow(left, right);
        arrayExpression.get(thisPointer).edit(0, "" + result);
        return result;
    }

    private Double division(String numberLeft, String numberRight, int thisPointer) {
        Double left = parsing(numberLeft);
        Double right = parsing(numberRight);
        if (left == null || right == null) return null;
        if (right == 0.0) {
            return null;
        } else {
            Double result = left / right;
            arrayExpression.get(thisPointer).edit(0, "" + result);
            return result;
        }
    }

    private Double mod(String numberLeft, String numberRight, int thisPointer) {
        Double left = parsing(numberLeft);
        Double right = parsing(numberRight);
        if (left == null || right == null) return null;
        Double result = left % right;
        arrayExpression.get(thisPointer).edit(0, "" + result);
        return result;
    }

    private Double square(String numberRight, int thisPointer) {
        Double right = parsing(numberRight);
        if (right == null) return null;
        Double result = Math.sqrt(right);
        arrayExpression.get(thisPointer).edit(0, "" + result);
        return Math.sqrt(right);
    }

    private Double operation(String expression, int pointer, int thisPointer) {
        String numberLeft = copy(0, pointer, expression);
        String numberRight = copy(pointer + 1, expression.length(), expression);
        String addedElement = "" + expression.charAt(pointer);
        arrayExpression.get(arrayExpression.size() - 1).edit(1, addedElement);
        switch (expression.charAt(pointer)) {
            case '+': {
                return sum(numberLeft, numberRight, thisPointer);
            }
            case '-': {
                return difference(expression, numberLeft, numberRight, thisPointer, pointer);
            }
            case '*': {
                return multiplication(numberLeft, numberRight, thisPointer);
            }
            case '^': {
                return pow(numberLeft, numberRight, thisPointer);
            }
            case '/': {
                return division(numberLeft, numberRight, thisPointer);
            }
            case '%': {
                return mod(numberLeft, numberRight, thisPointer);
            }
            case '√': {
                return square(numberRight, thisPointer);
            }
            default: {
                return null;
            }
        }
    }

    public Double parsing(String expression) {
        int thisPointer = arrayExpression.size();
        arrayExpression.add(new TreeNote(2, expression));
        expression = removeBracket(expression);
        int pointer = searchMainPointer(expression);
        if (expression.charAt(expression.length() - 1) == '!') {
            if (pointer == -1) {
                String newExpression = copy(0, expression.length() - 1, expression);
                return factorial(parsing(newExpression));
            }
        }
        if (pointer == 0 && expression.charAt(0) == '-') {
            return negativeNumber(expression);
        }
        String trigFunc = searchTrigFunc(expression);
        if (trigFunc != null) {
            return trigonometricFunctionAndLg(expression, trigFunc, thisPointer);
        }
        if (pointer == -1) {
            double number = Double.parseDouble(expression);
            String addedElement = "" + number;
            arrayExpression.get(arrayExpression.size() - 1).edit(0, addedElement);
            return number;
        } else {
            return operation(expression, pointer, thisPointer);
        }
    }

    public Double start(String expression) {
        pointerExpression = 0;
        arrayExpression = new ArrayList<>();
        Double result = parsing(expression);
        return result;
    }
}
