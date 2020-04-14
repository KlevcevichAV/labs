package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Text {
    ToggleButton text;
    javafx.scene.shape.Rectangle rectangleFigure;
    double coordinateX , coordinateY;
    boolean checkForText;
    public TextArea brushText;

    public void createButton(){
        Image url= new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/text.png");
        ImageView imageView = new ImageView(url);
        text = new ToggleButton("" , imageView);
    }

    void setCoordinateRectangle(double x2 , double y2){
        rectangleFigure.setX(coordinateX);
        rectangleFigure.setY(coordinateY);
        rectangleFigure.setWidth(x2 - coordinateX);
        rectangleFigure.setHeight(y2 - coordinateY);
    }

    public void design(GraphicsContext g , double x , double y){
        g.setLineWidth(1);
        g.setStroke(rectangleFigure.getStroke());
        rectangleFigure.setFill(null);
        brushText.setVisible(false);
        rectangleFigure.setVisible(true);
        setCoordinateRectangle(x,y);
        coordinateX = x;
        coordinateY = y;
        checkForText = false;
    }

    public void endDesign(MouseEvent e){
        setCoordinateRectangle(e.getX() , e.getY());
        brushText.setLayoutX(rectangleFigure.getX());
        brushText.setLayoutY(rectangleFigure.getY());
        brushText.setMaxSize(rectangleFigure.getWidth() , rectangleFigure.getHeight());
        brushText.setVisible(true);
        checkForText = true;
    }

    void moveRectangle(Timeline timeline , MouseEvent e){
        KeyFrame kfX = new KeyFrame(Duration.millis(0.5) , new KeyValue(rectangleFigure.xProperty() , Math.min(e.getX() , coordinateX)));
        KeyFrame kfY = new KeyFrame(Duration.millis(0.5) , new KeyValue(rectangleFigure.yProperty() , Math.min(e.getY() , coordinateY)));
        KeyFrame kfWidth = new KeyFrame(Duration.millis(0.5) , new KeyValue(rectangleFigure.widthProperty() , Math.abs(e.getX() - coordinateX)));
        KeyFrame kfHeight = new KeyFrame(Duration.millis(0.5) , new KeyValue(rectangleFigure.heightProperty() , Math.abs(e.getY() - coordinateY)));
        timeline.getKeyFrames().addAll(kfX , kfY);
        timeline.getKeyFrames().addAll(kfWidth , kfHeight);
    }

    void createText(GraphicsContext g, ColorPicker colorPicker){
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
        rectangleFigure = new javafx.scene.shape.Rectangle();
        brushText = new TextArea();
        brushText.setVisible(false);
        rectangleFigure.setFill(null);
        rectangleFigure.setStroke(Color.BLACK);
        rectangleFigure.setVisible(false);
        rectangleFigure.getStrokeDashArray().addAll(5.0 , 5.0);
        checkForText = false;
    }
}