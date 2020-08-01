package sample.view.keyboard;

import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Keyboard {
    private DigitalKeyboard digitalKeyboard;
    private KeyboardOperations keyboardOperations;
    private VBox keyboard;
    private RadioButton trigonometry;
    private boolean check;

    private void setStyle() {
        VBox group = new VBox(keyboardOperations.getTopGroup(), digitalKeyboard.getDigitalKeyboard());
        group.spacingProperty().set(10);
        HBox group1 = new HBox(group, keyboardOperations.getLeftGroup());
        group1.spacingProperty().set(10);
        keyboard = new VBox(trigonometry, keyboardOperations.getTrigonometricGroup(), group1);
        keyboard.spacingProperty().set(10);
    }

    void setAssesTrigonometry() {
        trigonometry.setOnAction(e -> {
            check = !check;
            keyboardOperations.getSinSign().setDisable(check);
            keyboardOperations.getCosSign().setDisable(check);
            keyboardOperations.getTgSign().setDisable(check);
            keyboardOperations.getCtgSign().setDisable(check);
        });
    }

    public void disable() {
        keyboardOperations.getSinSign().setDisable(true);
        keyboardOperations.getCosSign().setDisable(true);
        keyboardOperations.getTgSign().setDisable(true);
        keyboardOperations.getCtgSign().setDisable(true);
    }

    public Keyboard() {
        digitalKeyboard = new DigitalKeyboard();
        keyboardOperations = new KeyboardOperations();
        disable();
        trigonometry = new RadioButton("Trigonometry");
        trigonometry.setStyle("-fx-text-fill: white;");
        setAssesTrigonometry();
        setStyle();
        check = true;
    }

    public DigitalKeyboard getDigitalKeyboard() {
        return digitalKeyboard;
    }

    public KeyboardOperations getKeyboardOperations() {
        return keyboardOperations;
    }

    public VBox getKeyboard() {
        ;
        return keyboard;
    }
}
