package sample;

import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Brush {
    public ToggleButton brush;

    public void createButton(){
        Image url= new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/brush.png");
        ImageView imageView = new ImageView(url);
        brush = new ToggleButton("", imageView);
    }

    public void setCursor(Canvas canvas){
        Image image = new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/brush.png");
        canvas.setCursor(new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2));
    }

    public void designPressed(GraphicsContext g, double x, double y, double size, Color color){
        g.setLineWidth(size);
        g.setStroke(color);
        g.beginPath();
        g.moveTo(x, y);
        g.stroke();
    }

    public void design(GraphicsContext g, double x, double y, Color color){
        g.setStroke(color);
        g.lineTo(x, y);
        g.stroke();
        g.closePath();
        g.beginPath();
        g.moveTo(x, y);
    }

    public void designReleased(GraphicsContext g, double x, double y, Color color){
        g.setStroke(color);
        g.lineTo(x,y);
        g.stroke();
        g.closePath();
    }

    Brush(){
        createButton();
    }
}