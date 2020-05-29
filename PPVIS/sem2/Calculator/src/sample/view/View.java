package sample.view;

import javafx.scene.layout.VBox;
import sample.view.display.Display;
import sample.view.keyboard.Keyboard;

public class View {
    Display display;
    Keyboard keyboard;
    VBox calculator;

    public View(){
        display = new Display();
        keyboard = new Keyboard();
        calculator = new VBox(display, keyboard.getKeyboard());
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public Display getDisplay() {
        return display;
    }

    public VBox getCalculator() {
        return calculator;
    }
}
