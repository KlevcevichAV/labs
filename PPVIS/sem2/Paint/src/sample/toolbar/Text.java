package sample.toolbar;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class Text extends ToolRectangle{
    boolean checkForText;
    public TextArea brushText;

    public void createButton(){
        createButton("text.png");
    }

    public void design(double x, double y){
        rectangleFigure.setVisible(true);
        setCoordinateRectangle(x,y);
        coordinateX = x;
        coordinateY = y;
        brushText.setVisible(false);
        checkForText = false;
    }

    @Override
    public void endDesign(MouseEvent e){
        setCoordinateRectangle(e.getX(), e.getY());
        brushText.setLayoutX(rectangleFigure.getX());
        brushText.setLayoutY(rectangleFigure.getY());
        brushText.setMaxSize(rectangleFigure.getWidth(), rectangleFigure.getHeight());
        brushText.setVisible(true);
        checkForText = true;
    }

    public void createText(GraphicsContext g, ColorPicker colorPicker){
        if(checkForText){
            g.setStroke(colorPicker.getValue());
            g.strokeText( brushText.getText(), rectangleFigure.getX(),rectangleFigure.getY() + 10);
            checkForText = false;
            brushText.clear();
            brushText.setVisible(false);
            rectangleFigure.setVisible(false);
        }
    }

    Text(){
        createButton();
        brushText = new TextArea();
        brushText.setVisible(false);
        createFigureRectangle();
        checkForText = false;
    }
}