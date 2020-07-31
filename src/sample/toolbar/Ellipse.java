package sample.toolbar;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Ellipse extends Tool {
    public javafx.scene.shape.Ellipse ellipseFigure;
    double coordinateX, coordinateY;
    public boolean click;
    public void createButton(){
        createButton("ellipse.png");
    }

    void setSettingsCircle(double x, double y, double radiusX, double radiusY){
        ellipseFigure.setStroke(Color.BLACK);
        ellipseFigure.setVisible(true);
        ellipseFigure.setCenterX(x);
        ellipseFigure.setCenterY(y);
        ellipseFigure.setRadiusX(radiusX);
        ellipseFigure.setRadiusY(radiusY);
    }

    double getBeginEllipseX(double x){
        return Math.min(coordinateX, x);
    }

    double getBeginEllipseY(double y){
        return Math.min(coordinateY, y);
    }

    double getRadiusEllipseX(double x){
        return Math.max(coordinateX, x) - getBeginEllipseX(x);
    }

    double getRadiusEllipseY(double y){
        return Math.max(coordinateY, y) - getBeginEllipseY(y);
    }

    public void design(GraphicsContext g, ColorPicker colorPicker, RadioButton fill, double x, double y){
        if(!click){
            setSettingsCircle(x, y, 0, 0);
            coordinateX = x;
            coordinateY = y;
        }else{
            ellipseFigure.setVisible(false);
            g.setStroke(colorPicker.getValue());
            if(fill.isSelected()){
                g.setFill(colorPicker.getValue());
                g.fillOval(getBeginEllipseX(x), getBeginEllipseY(y), getRadiusEllipseX(x), getRadiusEllipseY(y));
            }
            g.setStroke(colorPicker.getValue());
            g.strokeOval(getBeginEllipseX(x), getBeginEllipseY(y), getRadiusEllipseX(x), getRadiusEllipseY(y));
            g.closePath();
        }
        click = !click;
    }

    public void moveCircle(Timeline timeline, MouseEvent e, ColorPicker colorPicker){
        ellipseFigure.setStroke(colorPicker.getValue());
        KeyFrame kfCenterX = new KeyFrame(Duration.millis(0.5), new KeyValue(ellipseFigure.centerXProperty(), (e.getX() + coordinateX)/2));
        KeyFrame kfCenterY = new KeyFrame(Duration.millis(0.5), new KeyValue(ellipseFigure.centerYProperty(), (e.getY() + coordinateY)/2));
        KeyFrame kfRadiusX = new KeyFrame(Duration.millis(0.5), new KeyValue(ellipseFigure.radiusXProperty(), getRadiusEllipseX(e.getX())/2));
        KeyFrame kfRadiusY = new KeyFrame(Duration.millis(0.5), new KeyValue(ellipseFigure.radiusYProperty(), getRadiusEllipseY(e.getY())/2));
        timeline.getKeyFrames().addAll(kfCenterX, kfCenterY, kfRadiusX, kfRadiusY);
    }

    Ellipse(){
        createButton();
        click = false;
        ellipseFigure = new javafx.scene.shape.Ellipse();
        ellipseFigure.setFill(null);
        ellipseFigure.setVisible(false);

    }
}