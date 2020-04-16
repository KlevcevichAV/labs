package sample;

import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tool {
    public ToggleButton tool;

    public void createButton(String pathPicture){
        Image url= new Image("file:./Picture/" + pathPicture);
        ImageView imageView = new ImageView(url);
        tool = new ToggleButton("", imageView);
    }

    public void setCursor(Canvas canvas, String pathPicture){
        Image image = new Image("file:./Picture/" + pathPicture);
        canvas.setCursor(new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2));
    }

}
