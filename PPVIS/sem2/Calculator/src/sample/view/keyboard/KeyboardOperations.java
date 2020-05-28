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

    private VBox leftGroup;
    private VBox topGroup;

    private void createButton(){
        plusSign = new Button("+", 1);
        minusSign = new Button("-", 1);
        multiplicationSign = new Button("*", 1);
        divisionSign = new Button("/", 1);
        equalSign = new Button("=", 1);
        rootSign = new Button("√", 1);
        fractionSign = new Button("⅟", 1);
        modSign = new Button("%", 0);
        cleaningSign = new Button("C", 0);
        singleCharacterDelete = new Button("⊠", 0);
        leftBracket = new Button("(", 1);
        rightBracket = new Button(")", 1);
    }

    private void createLeftGroup(){
        leftGroup = new VBox(rootSign, divisionSign, multiplicationSign, minusSign, plusSign, equalSign);
        leftGroup.spacingProperty().set(10);
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

    public VBox getLeftGroup() {
        return leftGroup;
    }

    public VBox getTopGroup() {
        return topGroup;
    }
}
