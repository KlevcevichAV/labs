package sample.view.display;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

public class Display extends TextField {
    private ScrollPane scrollPane;

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public Display() {
        super();
        settingDisplayConfiguration();
        scrollPane = new ScrollPane();
        settingDisScrollPaneConfiguration();
    }

    private void settingDisplayConfiguration() {
        super.setEditable(false);
        super.setPrefHeight(50);
        super.setPrefWidth(1000);
        super.getStylesheets().add(getClass().getResource("display.css").toExternalForm());
    }

    private void settingDisScrollPaneConfiguration() {
        scrollPane.setContent(this);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.getStylesheets().add(getClass().getResource("scrollBar.css").toExternalForm());
    }
}
