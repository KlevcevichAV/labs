package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class RectSelection extends ToolRectangle{
    public Image imageCopy;

    public void createButton(){
        createButton("rect_selection.png");
    }

    public void setCursor(Canvas canvas){
        setCursor(canvas, "rect_selection.png");
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

    RectSelection(){
        createButton();
        createFigureRectangle();
    }
}