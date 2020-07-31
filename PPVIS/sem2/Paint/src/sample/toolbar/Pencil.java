package sample.toolbar;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Pencil extends ToolBrush {
    public void createButton(){
        createButton("pencil.png");
    }

    public void setCursor(Canvas canvas){
        setCursor(canvas, "pencil.png");
    }

    public void designPressed(GraphicsContext g, double x, double y, Color color){
        designPressed(g, x, y, 1, color);
    }

    Pencil(){
        createButton();
    }

}