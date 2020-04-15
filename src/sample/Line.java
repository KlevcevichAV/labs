package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class Line {
    ToggleButton line;
    public javafx.scene.shape.Line side;

    public void createButton(){
        Image url= new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/line.png");
        ImageView imageView = new ImageView(url);
        line = new ToggleButton("", imageView);
    }

    public void setCursor(Canvas canvas){
        Image image = new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/line.png");
        canvas.setCursor(new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2));
    }

    void setCoordinateLine(double x1, double y1, double x2, double y2){
        side.setStartX(x1);
        side.setStartY(y1);
        side.setEndX(x2);
        side.setEndY(y2);
    }

    public void design(GraphicsContext g, double x, double y){
        side.setVisible(true);
        g.beginPath();
        g.moveTo(x, y);
        setCoordinateLine(x, y, x, y);
    }

    public void endDesign(GraphicsContext g, double x, double y, ColorPicker colorPicker){
        side.setVisible(false);
        g.setStroke(colorPicker.getValue());
        g.setLineWidth(1);
        g.lineTo(x,y);
        g.stroke();
        g.closePath();
    }

    void moveLine(Timeline timeline, MouseEvent e, ColorPicker colorPicker){
        side.setStroke(colorPicker.getValue());
        KeyFrame kfx = new KeyFrame(Duration.millis(0.5), new KeyValue(side.endXProperty(), e.getX()));
        KeyFrame kfy = new KeyFrame(Duration.millis(0.5), new KeyValue(side.endYProperty(), e.getY()));
        timeline.getKeyFrames().addAll(kfx, kfy);
    }

    Line(){
        createButton();
        side = new javafx.scene.shape.Line();
    }
}