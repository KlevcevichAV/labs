package sample.view.keyboard;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.view.keyboard.button.Button;

public class DigitalKeyboard {
    private Button zero;
    private Button one;
    private Button two;
    private Button three;
    private Button four;
    private Button five;
    private Button six;
    private Button seven;
    private Button eight;
    private Button nine;
    private Button decimalPoint;
    private VBox digitalKeyboard;
    private void createButton(){
        zero = new Button("0", 3);
        one = new Button("1", 2);
        two = new Button("2", 2);
        three = new Button("3", 2);
        four = new Button("4", 2);
        five = new Button("5", 2);
        six = new Button("6", 2);
        seven = new Button("7", 2);
        eight = new Button("8", 2);
        nine = new Button("9", 2);
        decimalPoint = new Button(".", 2);
    }

    private VBox createKeyboard(){
        HBox sevenOne = new HBox(seven, four, one);
        sevenOne.spacingProperty().set(10);
        HBox eightTwo = new HBox(eight, five, two);
        eightTwo.spacingProperty().set(10);
        HBox nineThree = new HBox(nine, six, three);
        nineThree.spacingProperty().set(10);
        HBox zeroAndPoint = new HBox(zero, decimalPoint);
        zeroAndPoint.spacingProperty().set(10);
        VBox digitalKeyboard = new VBox(sevenOne, eightTwo, nineThree, zeroAndPoint);
        digitalKeyboard.spacingProperty().set(10);

        return digitalKeyboard;
    }

    public DigitalKeyboard(){
        createButton();
        digitalKeyboard = createKeyboard();
    }

    public VBox getDigitalKeyboard() {
        return digitalKeyboard;
    }

    public Button getDecimalPoint() {
        return decimalPoint;
    }

    public Button getZero() {
        return zero;
    }

    public Button getOne() {
        return one;
    }

    public Button getTwo() {
        return two;
    }

    public Button getThree() {
        return three;
    }

    public Button getFour() {
        return four;
    }

    public Button getFive() {
        return five;
    }

    public Button getSix() {
        return six;
    }

    public Button getSeven() {
        return seven;
    }

    public Button getEight() {
        return eight;
    }

    public Button getNine() {
        return nine;
    }
}
