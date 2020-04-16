package sample.toolbar;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class ToolBarPaint {
    public class Pointer{
        public int pointerButton;
        Pointer(){
            pointerButton = 0;
        }
    };
    public Pointer pointer;
    public Pencil pencil;
    public Brush brush;
    public Eraser eraser;
    public Line line;
    public Rectangle rectangle;
    public RectSelection rectSelection;
    public Text text;
    public Magnifier magnifier;
    public Ellipse ellipse;
    public RadioButton fill;
    public  ToggleGroup group;

    void initializeButton(){
        pencil = new Pencil();
        brush = new Brush();
        eraser = new Eraser();
        line = new Line();
        rectangle = new Rectangle();
        rectSelection = new RectSelection();
        text = new Text();
        magnifier = new Magnifier();
        ellipse = new Ellipse();
        fill = new RadioButton("Fill");
        fill.setVisible(false);
    }

    void hideRectangle(javafx.scene.shape.Rectangle rectangle1, javafx.scene.shape.Rectangle rectangle2){
        rectangle1.setVisible(false);
        rectangle2.setVisible(false);
    }

    public void onPencil(Canvas canvas, ColorPicker colorPicker, TextField brushSize, javafx.scene.shape.Rectangle rectangle1, javafx.scene.shape.Rectangle rectangle2){
        pencil.tool.setOnAction(e->{
            text.createText(canvas.getGraphicsContext2D(), colorPicker);
            hideRectangle(rectangle1, rectangle2);
            pointer.pointerButton = 0;
            brushSize.setText("1");
            brushSize.setEditable(false);
            fill.setVisible(false);
        });
    }

    public void onBrush(Canvas canvas, ColorPicker colorPicker, TextField brushSize, javafx.scene.shape.Rectangle rectangle1, javafx.scene.shape.Rectangle rectangle2){
        brush.tool.setOnAction(e->{
            text.createText(canvas.getGraphicsContext2D(), colorPicker);
            hideRectangle(rectangle1, rectangle2);
            pointer.pointerButton = 1;
            brushSize.setEditable(true);
            fill.setVisible(false);
        });
    }

    public void onEraser(Canvas canvas, ColorPicker colorPicker, TextField brushSize, javafx.scene.shape.Rectangle rectangle1, javafx.scene.shape.Rectangle rectangle2){
        eraser.tool.setOnAction(e->{
            text.createText(canvas.getGraphicsContext2D(), colorPicker);
            hideRectangle(rectangle1, rectangle2);
            pointer.pointerButton = 2;
            brushSize.setEditable(true);
            fill.setVisible(false);
        });
    }

    public void onLine(Canvas canvas, ColorPicker colorPicker, TextField brushSize, javafx.scene.shape.Rectangle rectangle1, javafx.scene.shape.Rectangle rectangle2) {
        line.tool.setOnAction(e->{
            text.createText(canvas.getGraphicsContext2D(), colorPicker);
            hideRectangle(rectangle1, rectangle2);
            pointer.pointerButton = 3;
            brushSize.setText("1");
            brushSize.setEditable(false);
            fill.setVisible(false);
        });
    }

    public void onRectangle(Canvas canvas, ColorPicker colorPicker, TextField brushSize, javafx.scene.shape.Rectangle rectangle1, javafx.scene.shape.Rectangle rectangle2){
        rectangle.tool.setOnAction(e->{
            text.createText(canvas.getGraphicsContext2D(), colorPicker);
            hideRectangle(rectangle1, rectangle2);
            pointer.pointerButton = 4;
            brushSize.setText("1");
            brushSize.setEditable(false);
            fill.setVisible(true);
        });
    }

    public void onRectSelection(Canvas canvas, ColorPicker colorPicker, TextField brushSize, javafx.scene.shape.Rectangle rectangle1, javafx.scene.shape.Rectangle rectangle2){
        rectSelection.tool.setOnAction(e->{
            text.createText(canvas.getGraphicsContext2D(), colorPicker);
            hideRectangle(rectangle1, rectangle2);
            pointer.pointerButton = 5;
            brushSize.setText("1");
            brushSize.setEditable(false);
            fill.setVisible(false);
        });
    }

    public void onText(Canvas canvas, ColorPicker colorPicker, TextField brushSize, javafx.scene.shape.Rectangle rectangle1, javafx.scene.shape.Rectangle rectangle2){
        text.tool.setOnAction(e->{
            text.createText(canvas.getGraphicsContext2D(), colorPicker);
            hideRectangle(rectangle1, rectangle2);
//            pointerButtonForClick = 1;
            pointer.pointerButton = 6;
            brushSize.setEditable(false);
            fill.setVisible(false);

        });
    }

    public void onMagnifier(Canvas canvas, ColorPicker colorPicker, TextField brushSize, javafx.scene.shape.Rectangle rectangle1, javafx.scene.shape.Rectangle rectangle2){
        magnifier.tool.setOnAction(e->{
            text.createText(canvas.getGraphicsContext2D(), colorPicker);
            hideRectangle(rectangle1, rectangle2);
            pointer.pointerButton = 7;
            brushSize.setText("1");
            brushSize.setEditable(true);
            fill.setVisible(false);
        });
    }

    public void onEllipse(Canvas canvas, ColorPicker colorPicker, TextField brushSize, javafx.scene.shape.Rectangle rectangle1, javafx.scene.shape.Rectangle rectangle2){
        ellipse.tool.setOnAction(e->{
            text.createText(canvas.getGraphicsContext2D(), colorPicker);
            hideRectangle(rectangle1, rectangle2);
            pointer.pointerButton = 9;
            brushSize.setEditable(false);
            fill.setVisible(true);
        });
    }

    public void onButton(Canvas canvas, ColorPicker colorPicker, TextField brushSize, javafx.scene.shape.Rectangle rectangle1, javafx.scene.shape.Rectangle rectangle2){
        onPencil(canvas, colorPicker, brushSize, rectangle1, rectangle2);
        onBrush(canvas, colorPicker, brushSize, rectangle1, rectangle2);
        onEraser(canvas, colorPicker, brushSize, rectangle1, rectangle2);
        onLine(canvas, colorPicker, brushSize, rectangle1, rectangle2);
        onRectangle(canvas, colorPicker, brushSize, rectangle1, rectangle2);
        onRectSelection(canvas, colorPicker, brushSize, rectangle1, rectangle2);
        onText(canvas, colorPicker, brushSize, rectangle1, rectangle2);
        onMagnifier(canvas, colorPicker, brushSize, rectangle1, rectangle2);
        onEllipse(canvas, colorPicker, brushSize, rectangle1, rectangle2);
    }

    public void createGroup(){
        group = new ToggleGroup();
        pencil.tool.setToggleGroup(group);
        brush.tool.setToggleGroup(group);
        eraser.tool.setToggleGroup(group);
        line.tool.setToggleGroup(group);
        rectangle.tool.setToggleGroup(group);
        ellipse.tool.setToggleGroup(group);
        rectSelection.tool.setToggleGroup(group);
        text.tool.setToggleGroup(group);
        magnifier.tool.setToggleGroup(group);
    }

    public ToolBarPaint(){
        pointer = new Pointer();
        initializeButton();
    }

}