package sample;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.events.Events;
import sample.menuPaint.MenuPaint;
import sample.toolbar.ToolBarPaint;

public class Main extends Application{
    boolean click;
    ToolBarPaint toolBarPaint;
    private BorderPane root;
    private Label nameTextField;
    private TextField brushSize;
    private ColorPicker colorPicker;
    private ScrollPane scrollPane;
    private Canvas canvas;
    private MenuPaint menuPaint;
    private Events events;

    public void createTop(){
        HBox top1 = new HBox(menuPaint.menuBar);
        HBox top2 = new HBox(nameTextField, brushSize, colorPicker, toolBarPaint.fill);
        top2.setSpacing(10);
        VBox top = new VBox(top1, top2);
        root.setTop(top);
    }

    void arrangement(){
        toolBarPaint.createGroup();
        VBox left = toolBarPaint.createVBox();
        left.setSpacing(5);
        root.setLeft(left);
        Group group = new Group(toolBarPaint.createGroupFigure(canvas));
        scrollPane.setContent(group);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        root.setCenter(scrollPane);
        createTop();
    }

    void initializeVariable(){
        scrollPane = new ScrollPane();
        colorPicker.setValue(Color.BLACK);
        brushSize.setEditable(false);
        click = false;
        nameTextField = new Label("Size :");
    }

    void initialize(){
        root = new BorderPane();
        brushSize = new TextField("1");
        colorPicker = new ColorPicker();
        canvas = new Canvas(600, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        toolBarPaint = new ToolBarPaint();
        initializeVariable();
        menuPaint = new MenuPaint(canvas, toolBarPaint.rectSelection, toolBarPaint.pointer);
        menuPaint.onClear(canvas);
        arrangement();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        initialize();
        events = new Events(canvas, colorPicker, brushSize, toolBarPaint);
        events.event();
        primaryStage.setScene(new Scene(root, 700, 650));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}