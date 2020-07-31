package sample.toolbar;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Eraser extends ToolBrush {
    public void createButton(){
        createButton("eraser.png");
    }

    public void setCursor(Canvas canvas){
        setCursor(canvas, "eraser.png");
    }

    public void designPressed(GraphicsContext g, double x, double y, double size){
        designPressed(g, x, y, size, Color.WHITE);
    }

    Eraser(){
        createButton();
    }

}