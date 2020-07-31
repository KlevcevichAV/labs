package sample.toolbar;

import javafx.scene.canvas.Canvas;

public class Brush extends ToolBrush {
    void createButton(){
        createButton("brush.png");
    }

    public void setCursor(Canvas canvas){
        setCursor(canvas, "brush.png");
    }

    Brush(){
        createButton();
    }
}