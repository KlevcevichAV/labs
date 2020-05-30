package sample.view.keyboard;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.view.keyboard.button.Button;

public class KeyboardOperations {
    private Button plusSign;
    private Button minusSign;
    private Button multiplicationSign;
    private Button divisionSign;
    private Button equalSign;
    private Button rootSign;
    private Button fractionSign;
    private Button modSign;
    private Button cleaningSign;
    private Button singleCharacterDelete;
    private Button leftBracket;
    private Button rightBracket;
    private Button sinSign;
    private Button cosSign;
    private Button tgSign;
    private Button ctgSign;

    private VBox leftGroup;
    private VBox topGroup;
    private HBox trigonometricGroup;

    private void createButton(){
        plusSign = new Button("+", 1);
        minusSign = new Button("-", 1);
        multiplicationSign = new Button("*", 1);
        divisionSign = new Button("/", 1);
        equalSign = new Button("=", 1);
        rootSign = new Button("√", 1);
        fractionSign = new Button("⅟", 0);
        modSign = new Button("%", 0);
        cleaningSign = new Button("C", 0);
        singleCharacterDelete = new Button("⊠", 0);
        leftBracket = new Button("(", 0);
        rightBracket = new Button(")", 0);
        sinSign = new Button("sin", 4);
        cosSign = new Button("cos", 4);
        tgSign = new Button("tg", 4);
        ctgSign = new Button("ctg", 4);
    }

    private void createLeftGroup(){
        leftGroup = new VBox(rootSign, divisionSign, multiplicationSign, minusSign, plusSign, equalSign);
        leftGroup.spacingProperty().set(10);
    }

    private void createTrigonometricGroup(){
        trigonometricGroup = new HBox(sinSign, cosSign, tgSign, ctgSign);
        trigonometricGroup.spacingProperty().set(10);
    }

    private void createTopGroup(){
        HBox group1 = new HBox(cleaningSign, singleCharacterDelete, modSign);
        group1.spacingProperty().set(10);
        HBox group2 = new HBox(leftBracket, rightBracket, fractionSign );
        group2.spacingProperty().set(10);
        topGroup = new VBox(group2, group1);
        topGroup.spacingProperty().set(10);
    }

    public KeyboardOperations(){
        createButton();
        createLeftGroup();
        createTopGroup();
        createTrigonometricGroup();
    }

    public Button getCleaningSign() {
        return cleaningSign;
    }

    public Button getDivisionSign() {
        return divisionSign;
    }

    public Button getEqualSign() {
        return equalSign;
    }

    public Button getFractionSign() {
        return fractionSign;
    }

    public Button getLeftBracket() {
        return leftBracket;
    }

    public Button getMinusSign() {
        return minusSign;
    }

    public Button getModSign() {
        return modSign;
    }

    public Button getMultiplicationSign() {
        return multiplicationSign;
    }

    public Button getPlusSign() {
        return plusSign;
    }

    public Button getRightBracket() {
        return rightBracket;
    }

    public Button getRootSign() {
        return rootSign;
    }

    public Button getSingleCharacterDelete() {
        return singleCharacterDelete;
    }

    public Button getSinSign() {
        return sinSign;
    }

    public Button getCosSign() {
        return cosSign;
    }

    public Button getTgSign() {
        return tgSign;
    }

    public Button getCtgSign() {
        return ctgSign;
    }

    public VBox getLeftGroup() {
        return leftGroup;
    }

    public HBox getTrigonometricGroup(){
        return trigonometricGroup;
    }

    public VBox getTopGroup() {
        return topGroup;
    }
}
