package sample;

import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pencil {
    public ToggleButton pencil;

    public void createButton(){
        Image url= new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/pencil.png");
        ImageView imageView = new ImageView(url);
        pencil = new ToggleButton("", imageView);
    }

    public void setCursor(Canvas canvas){
        Image image = new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/pencil.png");
        canvas.setCursor(new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2));
    }

    public void designPressed(GraphicsContext g, double x, double y, ColorPicker colorPicker){
        g.setLineWidth(1);
        g.setStroke(colorPicker.getValue());
        g.beginPath();
        g.moveTo(x, y);
        g.stroke();
    }

    public void design(GraphicsContext g, double x, double y, ColorPicker colorPicker){
        g.setStroke(colorPicker.getValue());
        g.lineTo(x, y);
        g.stroke();
        g.closePath();
        g.beginPath();
        g.moveTo(x, y);
    }

    public void designReleased(GraphicsContext g, double x, double y, ColorPicker colorPicker){
        g.setStroke(colorPicker.getValue());
        g.lineTo(x,y);
        g.stroke();
        g.closePath();
    }

    Pencil(){
        createButton();
    }

}