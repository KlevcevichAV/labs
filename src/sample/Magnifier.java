package sample;

import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Magnifier {
    ToggleButton magnifier;
    double widthCanvas , heightCanvas , counterZoom;
    public void createButton(){
        Image  url= new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/magnifier.png" , 16 , 16 , false , true);
        ImageView imageView = new ImageView(url);
        magnifier = new ToggleButton("" , imageView);
    }

    public void setCursor(Canvas canvas){
        Image image = new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/magnifier.png");
        canvas.setCursor(new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2));
    }

    public void approximation(Canvas canvas, TextField brushSize){
        widthCanvas *= Double.parseDouble(brushSize.getText())/counterZoom;
        heightCanvas *= Double.parseDouble(brushSize.getText())/counterZoom;
        counterZoom = Double.parseDouble(brushSize.getText());
        Image snapshot = canvas.snapshot(null ,null);
        canvas.setWidth(widthCanvas);
        canvas.setHeight(heightCanvas);
        canvas.getGraphicsContext2D().drawImage(snapshot, 0, 0, widthCanvas, heightCanvas);
    }

    Magnifier(){
        createButton();
        counterZoom = 1;
        widthCanvas = 600;
        heightCanvas = 600;
    }
}