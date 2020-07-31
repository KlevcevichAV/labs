package sample.toolbar;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class Rectangle extends ToolRectangle {

    public void createButton(){
        createButton("rectangle.png");
    }

    public void setCursor(Canvas canvas){
        setCursor(canvas, "rectangle.png");
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

    @Override
    void createFigureRectangle() {
        rectangleFigure = new javafx.scene.shape.Rectangle();
        rectangleFigure.setFill(null);
        rectangleFigure.setStroke(Color.BLACK);
        rectangleFigure.setVisible(false);
    }

    Rectangle(){
        createButton();
        createFigureRectangle();
    }
}