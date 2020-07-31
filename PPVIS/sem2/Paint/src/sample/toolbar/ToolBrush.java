package sample.toolbar;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ToolBrush extends Tool{
    public void designPressed(GraphicsContext g, double x, double y, double size, Color color){
        g.setLineWidth(size);
        g.setStroke(color);
        g.beginPath();
        g.moveTo(x, y);
        g.stroke();
    }

    public void design(GraphicsContext g, double x, double y){
        g.lineTo(x, y);
        g.stroke();
        g.closePath();
        g.beginPath();
        g.moveTo(x, y);
    }

    public void designReleased(GraphicsContext g, double x, double y){
        g.lineTo(x,y);
        g.stroke();
        g.closePath();
    }
}
