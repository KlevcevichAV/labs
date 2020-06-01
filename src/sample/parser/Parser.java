package sample.parser;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public int pointerExpression;
    public List<String> arrayExpression;

    public boolean comparingArraySizeAndPointer(){
        return pointerExpression < arrayExpression.size();
    }

    public String getElement(){
        return arrayExpression.get(pointerExpression);
    }

    public void incPointerExpression(){
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
        for (int i = begin; i < end; i++) {
            result = result + expression.charAt(i);
        }
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

    public int searchMainPointer(String expression) {
        int result = -1;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '%') {
                return i;
            }
            if (expression.charAt(i) == '(') i = checkParentheses(i, expression) - 1;
        }
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '+') {
                return i;
            }
            if (expression.charAt(i) == '(') i = checkParentheses(i, expression) - 1;
        }
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
        if (result == 0) return result;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '*') {
                return i;
            }
            if (expression.charAt(i) == '(') i = checkParentheses(i, expression) - 1;
        }
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '/') {
                return i;
            }
            if (expression.charAt(i) == '(') i = checkParentheses(i, expression) - 1;
        }
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '^') {
                return i;
            }
            if (expression.charAt(i) == '(') i = checkParentheses(i, expression) - 1;
        }
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '√') {
                return i;
            }
            if (expression.charAt(i) == '(') i = checkParentheses(i, expression) - 1;
        }
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

    public Double parsing(String expression) {
        while (expression.charAt(0) == '(') {
            int pointer = checkParentheses(0, expression);
            if (pointer == expression.length()) {
                expression = copy(1, expression.length() - 1, expression);
            } else break;
        }
        int pointer = searchMainPointer(expression);
        if (expression.charAt(expression.length() - 1) == '!') {
            if (pointer == -1) {
                String newExpression = copy(0, expression.length() - 1, expression);
                return factorial(parsing(newExpression));
            }
        }
        if (pointer == 0 && expression.charAt(0) == '-') {
            String newExpression = copy(1, expression.length(), expression);
            double result = -parsing(newExpression);
            arrayExpression.set(arrayExpression.size() - 1, "-" + arrayExpression.get(arrayExpression.size() - 1));
            return result;
        }
        String trigFunc = searchTrigFunc(expression);
        if (trigFunc != null) {
            arrayExpression.add(trigFunc);
            switch (trigFunc) {
                case "cos": {
                    String newExpression = copy(3, expression.length(), expression);
                    return Math.cos(parsing(newExpression));
                }
                case "sin": {
                    String newExpression = copy(3, expression.length(), expression);
                    return Math.sin(parsing(newExpression));
                }
                case "tg": {
                    String newExpression = copy(2, expression.length(), expression);
                    return Math.tan(parsing(newExpression));
                }
                case "ctg": {
                    String newExpression = copy(3, expression.length(), expression);
                    return 1.0 / Math.tan(parsing(newExpression));
                }
                case "lg": {
                    String newExpression = copy(2, expression.length(), expression);
                    return Math.log10(parsing(newExpression));
                }
            }
        }
        if (pointer == -1) {
            double number = Double.parseDouble(expression);
            String addedElement = "" + number;
            arrayExpression.add(addedElement);
            return number;
        } else {
            String numberLeft = copy(0, pointer, expression);
            String numberRight = copy(pointer + 1, expression.length(), expression);
            String addedElement = "" + expression.charAt(pointer);
            arrayExpression.add(addedElement);
            switch (expression.charAt(pointer)) {
                case '+': {
                    Double left = parsing(numberLeft);
                    Double right = parsing(numberRight);
                    if(left == null || right == null) return null;
                    return left + right;
                }
                case '-': {
                    arrayExpression.set(arrayExpression.size() - 1, "+");
                    numberRight = copy(pointer, expression.length(), expression);
                    Double left = parsing(numberLeft);
                    Double right = parsing(numberRight);
                    if(left == null || right == null) return null;
                    return left + right;
                }
                case '*': {
                    Double left = parsing(numberLeft);
                    Double right = parsing(numberRight);
                    if(left == null || right == null) return null;
                    return left * right;
                }
                case '^': {
                    return Math.pow(parsing(numberLeft), parsing(numberRight));
                }
                case '/': {
                    Double left = parsing(numberLeft);
                    Double right = parsing(numberRight);
                    if(left == null || right == null) return null;
                    if (right == 0.0) {
                        return null;
                    } else return left / right;
                }
                case '%': {
                    Double left = parsing(numberLeft);
                    Double right = parsing(numberRight);
                    if(left == null || right == null) return null;
                    return left % right;
                }
                case '√': {
                    Double right = parsing(numberRight);
                    if(right == null) return null;
                    return Math.sqrt(right);
                }
                default: {
                    return null;
                }
            }
        }
    }

    public Double start(String expression) {
        pointerExpression = 0;
        arrayExpression = new ArrayList<>();
        Double result = parsing(expression);
        System.out.println("hah");
        return result;
    }
}
