package sample;

import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Brush extends ToolBrush {
    void createButton(){
        createButton("brush.png");
    }

    void setCursor(Canvas canvas){
        setCursor(canvas, "brush.png");
    }

    Brush(){
        createButton();
    }
}