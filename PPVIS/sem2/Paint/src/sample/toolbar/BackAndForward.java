package sample.toolbar;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class BackAndForward {

    private List<Image> listSnapshot;
    private int counterSnapshot;

    public void addSnapshot(Canvas canvas){
        Image snapshot = canvas.snapshot(null,null);
        listSnapshot.add(snapshot);
        counterSnapshot++;
    }

    public void removingUnnecessary(){
        while (counterSnapshot + 1 < listSnapshot.size()){
            listSnapshot.remove(listSnapshot.size() - 1);
        }
    }

    public void dec(){
        if(counterSnapshot > 0) counterSnapshot--;
    }

    public void inc(){
        if(counterSnapshot + 1 < listSnapshot.size())counterSnapshot++;
    }

    public void setCanvas(Canvas canvas, ToolBarPaint toolBarPaint){
        if(counterSnapshot == listSnapshot.size()) return;
        double widthCanvas = toolBarPaint.magnifier.widthCanvas;
        double heightCanvas = toolBarPaint.magnifier.heightCanvas;
        canvas.getGraphicsContext2D().drawImage(listSnapshot.get(counterSnapshot), 0, 0, widthCanvas, heightCanvas);
    }

    public BackAndForward(Canvas canvas){
        counterSnapshot = -1;
        listSnapshot = new ArrayList<>();
    }
}
