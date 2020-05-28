package sample.view.keyboard;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Keyboard {
    private DigitalKeyboard digitalKeyboard;
    private KeyboardOperations keyboardOperations;
    private HBox keyboard;



    private void setStyleOne(){
        VBox group = new VBox(keyboardOperations.getTopGroup(), digitalKeyboard.getDigitalKeyboard());
        group.spacingProperty().set(10);
        keyboard = new HBox(group, keyboardOperations.getLeftGroup());
        keyboard.spacingProperty().set(10);
    }

    public Keyboard(){
        digitalKeyboard = new DigitalKeyboard();
        keyboardOperations = new KeyboardOperations();
        setStyleOne();
    }

    public HBox getKeyboard() { ;
        return keyboard;
    }
}
