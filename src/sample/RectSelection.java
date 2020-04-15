package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.ImageCursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class RectSelection {
    ToggleButton rectSelection;
    javafx.scene.shape.Rectangle rectangleFigure;
    double coordinateX, coordinateY;
    public Image imageCopy;

    public void createButton(){
        Image url= new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/rect_selection.png");
        ImageView imageView = new ImageView(url);
        rectSelection = new ToggleButton("", imageView);
    }

    public void setCursor(Canvas canvas){
        Image image = new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/rect_selection.png");
        canvas.setCursor(new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2));
    }

    void setCoordinateRectangle(double x2, double y2){
        rectangleFigure.setX(coordinateX);
        rectangleFigure.setY(coordinateY);
        rectangleFigure.setWidth(x2 - coordinateX);
        rectangleFigure.setHeight(y2 - coordinateY);
    }

    public void design(GraphicsContext g, double x, double y){
        rectangleFigure.setVisible(true);
        rectangleFigure.setFill(null);
        setCoordinateRectangle(x,y);
        coordinateX = x;
        coordinateY = y;
    }

    public void endDesign(MouseEvent e){
        setCoordinateRectangle(e.getX(), e.getY());
    }

    public void copy(Canvas canvas){
        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setViewport(new Rectangle2D(rectangleFigure.getX(), rectangleFigure.getY(),
                rectangleFigure.getWidth(), rectangleFigure.getHeight()));
        imageCopy = canvas.snapshot(snapshotParameters, null);
    }
    public void paste(Canvas canvas, double x, double y){
        canvas.getGraphicsContext2D().drawImage(imageCopy, x, y, imageCopy.getWidth(), imageCopy.getHeight());
    }

    void moveRectangle(Timeline timeline, MouseEvent e){
        KeyFrame kfX = new KeyFrame(Duration.millis(0.5), new KeyValue(rectangleFigure.xProperty(), Math.min(e.getX(), coordinateX)));
        KeyFrame kfY = new KeyFrame(Duration.millis(0.5), new KeyValue(rectangleFigure.yProperty(), Math.min(e.getY(), coordinateY)));
        KeyFrame kfWidth = new KeyFrame(Duration.millis(0.5), new KeyValue(rectangleFigure.widthProperty(), Math.abs(e.getX() - coordinateX)));
        KeyFrame kfHeight = new KeyFrame(Duration.millis(0.5), new KeyValue(rectangleFigure.heightProperty(), Math.abs(e.getY() - coordinateY)));
        timeline.getKeyFrames().addAll(kfX, kfY);
        timeline.getKeyFrames().addAll(kfWidth, kfHeight);
    }


    RectSelection(){
        createButton();
        rectangleFigure = new javafx.scene.shape.Rectangle();
        rectangleFigure.setFill(null);
        rectangleFigure.setStroke(Color.BLACK);
        rectangleFigure.setVisible(false);
        rectangleFigure.getStrokeDashArray().addAll(5.0, 5.0);
    }
}