package sample.view.keyboard;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Keyboard {
    private DigitalKeyboard digitalKeyboard;
    private KeyboardOperations keyboardOperations;
    private VBox keyboard;

    private void setStyle(){
        VBox group = new VBox(keyboardOperations.getTopGroup(), digitalKeyboard.getDigitalKeyboard());
        group.spacingProperty().set(10);
        HBox group1 = new HBox(group, keyboardOperations.getLeftGroup());
        group1.spacingProperty().set(10);
        keyboard = new VBox(keyboardOperations.getTrigonometricGroup(), group1);
        keyboard.spacingProperty().set(10);
    }

    public Keyboard(){
        digitalKeyboard = new DigitalKeyboard();
        keyboardOperations = new KeyboardOperations();
        setStyle();
    }

    public DigitalKeyboard getDigitalKeyboard() {
        return digitalKeyboard;
    }

    public KeyboardOperations getKeyboardOperations() {
        return keyboardOperations;
    }

    public VBox getKeyboard() { ;
        return keyboard;
    }
}
