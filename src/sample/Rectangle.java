package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class Rectangle extends ToolRectangle{

    public void createButton(){
        createButton("rectangle.png");
    }

    public void setCursor(Canvas canvas){
        setCursor(canvas, "rectangle.png");
    }


    public void design(GraphicsContext g, double x, double y){
        design(x, y);
        g.beginPath();
        g.moveTo(x, y);
    }

    void createRectangle(GraphicsContext g, RadioButton fill, double x, double y, double x1, double y1){
        g.setLineWidth(1);
        if(fill.isSelected()){
            g.setFill(rectangleFigure.getFill());
            g.fillRect(x,y,x1-x, y1-y);
        }
        g.setStroke(rectangleFigure.getStroke());
        g.strokeRect(x,y,x1-x,y1-y);
        g.stroke();
        g.closePath();
    }

    public void endDesign(GraphicsContext g, RadioButton fill, MouseEvent e){
        rectangleFigure.setVisible(false);
        createRectangle(g, fill, Math.min(coordinateX, e.getX()), Math.min(coordinateY, e.getY()),
                Math.max(coordinateX, e.getX()), Math.max(coordinateY, e.getY()));
    }

    Rectangle(){
        createButton();
        createFigureRectangle();
        rectangleFigure.getStrokeDashArray().clear();
    }
}