package sample.toolbar;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ToolRectangle extends Tool{
    public javafx.scene.shape.Rectangle rectangleFigure;
    double coordinateX, coordinateY;

    void setCoordinateRectangle(double x2, double y2){
        rectangleFigure.setX(Math.min(coordinateX, x2));
        rectangleFigure.setY(Math.min(coordinateY, y2));
        rectangleFigure.setWidth(Math.max(coordinateX, x2) - rectangleFigure.getX());
        rectangleFigure.setHeight(Math.max(coordinateY, y2) - rectangleFigure.getY());
    }

    public void design(double x, double y){
        rectangleFigure.setVisible(true);
        setCoordinateRectangle(x,y);
        coordinateX = x;
        coordinateY = y;
    }

    public void endDesign(MouseEvent e){
        setCoordinateRectangle(e.getX(), e.getY());
    }

    public void moveRectangle(Timeline timeline, MouseEvent e){
        KeyFrame kfX = new KeyFrame(Duration.millis(0.5), new KeyValue(rectangleFigure.xProperty(), Math.min(e.getX(), coordinateX)));
        KeyFrame kfY = new KeyFrame(Duration.millis(0.5), new KeyValue(rectangleFigure.yProperty(), Math.min(e.getY(), coordinateY)));
        KeyFrame kfWidth = new KeyFrame(Duration.millis(0.5), new KeyValue(rectangleFigure.widthProperty(), Math.abs(e.getX() - coordinateX)));
        KeyFrame kfHeight = new KeyFrame(Duration.millis(0.5), new KeyValue(rectangleFigure.heightProperty(), Math.abs(e.getY() - coordinateY)));
        timeline.getKeyFrames().addAll(kfX, kfY);
        timeline.getKeyFrames().addAll(kfWidth, kfHeight);
    }

    void createFigureRectangle(){
        rectangleFigure = new javafx.scene.shape.Rectangle();
        rectangleFigure.setFill(null);
        rectangleFigure.setStroke(Color.BLACK);
        rectangleFigure.setVisible(false);
        rectangleFigure.getStrokeDashArray().addAll(5.0, 5.0);
    }
}
