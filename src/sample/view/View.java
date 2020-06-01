package sample.view;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import sample.view.display.Display;
import sample.view.keyboard.Keyboard;

public class View {
    private BorderPane root;
    private Display displayExpression;
    private Keyboard keyboard;
    private VBox calculator;
    private TreeView<String> tree;

    public void createTree(TreeItem<String> root){
        tree = new TreeView<>(root);
        this.root.setRight(tree);
    }

    public View(BorderPane root){
        this.root = root;
        displayExpression = new Display();
        createTree(new TreeItem<>());
        tree.getStylesheets().add(getClass().getResource("tree.css").toExternalForm());;
        keyboard = new Keyboard();
        calculator = new VBox(displayExpression, keyboard.getKeyboard());

    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public Display getDisplayExpression() {
        return displayExpression;
    }

    public TreeView<String> getTree() {
        return tree;
    }

    public VBox getCalculator() {
        return calculator;
    }
}
