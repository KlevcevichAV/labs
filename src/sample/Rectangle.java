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


public class Rectangle {
    ToggleButton rectangle;
    javafx.scene.shape.Rectangle rectangleFigure;
    double coordinateX , coordinateY;

    public void createButton(){
        Image url= new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/rectangle.png");
        ImageView imageView = new ImageView(url);
        rectangle = new ToggleButton("" , imageView);
    }

    public void setCursor(Canvas canvas){
        Image image = new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/rectangle.png");
        canvas.setCursor(new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2));
    }


    void setCoordinateRectangle(double x1 , double y1 , double x2 , double y2){
        rectangleFigure.setX(x1);
        rectangleFigure.setY(y1);
        rectangleFigure.setWidth(x2 - x1);
        rectangleFigure.setHeight(y2 - y1);
    }

    public void design(GraphicsContext g , double x , double y){
        rectangleFigure.setVisible(true);
        setCoordinateRectangle(x , y , x , y);
        g.beginPath();
        g.moveTo(x , y);
        coordinateX = x;
        coordinateY = y;
    }

    void createRectangle(GraphicsContext g , RadioButton fill , double x , double y , double x1 , double y1){
        g.setLineWidth(1);
        if(fill.isSelected()){
            g.setFill(rectangleFigure.getFill());
            g.fillRect(x,y,x1-x , y1-y);
        }
        g.setStroke(rectangleFigure.getStroke());
        g.strokeRect(x,y,x1-x,y1-y);
        g.stroke();
        g.closePath();
    }

    public void endDesign(GraphicsContext g , RadioButton fill , MouseEvent e){
        rectangleFigure.setVisible(false);
        createRectangle(g , fill , Math.min(coordinateX , e.getX()) , Math.min(coordinateY , e.getY()) ,
                Math.max(coordinateX , e.getX()) , Math.max(coordinateY , e.getY()));
    }

    void moveRectangle(Timeline timeline , MouseEvent e){
        KeyFrame kfX = new KeyFrame(Duration.millis(0.5) , new KeyValue(rectangleFigure.xProperty() , Math.min(e.getX() , coordinateX)));
        KeyFrame kfY = new KeyFrame(Duration.millis(0.5) , new KeyValue(rectangleFigure.yProperty() , Math.min(e.getY() , coordinateY)));
        KeyFrame kfWidth = new KeyFrame(Duration.millis(0.5) , new KeyValue(rectangleFigure.widthProperty() , Math.abs(e.getX() - coordinateX)));
        KeyFrame kfHeight = new KeyFrame(Duration.millis(0.5) , new KeyValue(rectangleFigure.heightProperty() , Math.abs(e.getY() - coordinateY)));
        timeline.getKeyFrames().addAll(kfX , kfY);
        timeline.getKeyFrames().addAll(kfWidth , kfHeight);
    }

    Rectangle(){
        createButton();
        rectangleFigure = new javafx.scene.shape.Rectangle();
        rectangleFigure.setFill(null);
        rectangleFigure.setStroke(Color.BLACK);
        rectangleFigure.setVisible(false);
    }
}