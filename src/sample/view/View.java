package sample.view;

import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Constant;
import sample.view.display.Display;
import sample.view.keyboard.Keyboard;

public class View {
    private Stage stage;
    private BorderPane root;
    private Display displayExpression;
    private Display displayResult;
    private Keyboard keyboard;
    private VBox calculator;
    private TreeView<String> tree;

    public void createTree(TreeItem<String> root) {
        tree = new TreeView<>(root);
        tree.getStylesheets().add(getClass().getResource("tree.css").toExternalForm());
        this.root.setRight(tree);
    }

    public View(Stage stage) {
        this.stage = stage;
        root = new BorderPane();
        displayExpression = new Display();
        displayResult = new Display();
        createTree(new TreeItem<>());
        keyboard = new Keyboard();
        accommodation();
        root.setStyle(Constant.BLACK_FILL);
        Scene scene = new Scene(root, 535, 510);
        stage.setScene(scene);
        stage.show();

    }

    private void accommodation() {
        HBox displays = new HBox(displayExpression.getScrollPane(), displayResult.getScrollPane());
        displays.spacingProperty().set(10);
        root.setTop(displays);
        root.setCenter(keyboard.getKeyboard());
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public Display getDisplayResult() {
        return displayResult;
    }

    public Display getDisplayExpression() {
        return displayExpression;
    }

    public TreeView<String> getTree() {
        return tree;
    }

    public Stage getStage() {
        return stage;
    }

    public VBox getCalculator() {
        return calculator;
    }
}
