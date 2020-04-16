package sample.events;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.toolbar.ToolBarPaint;

public class Events {
    boolean click;
    Canvas canvas;
    ColorPicker colorPicker;
    TextField brushSize;
    ToolBarPaint toolBarPaint;
    void setCursor(){
        switch (toolBarPaint.pointer.pointerButton){
            case 0:{
                toolBarPaint.pencil.setCursor(canvas);
                break;
            }
            case 1:{
                toolBarPaint.brush.setCursor(canvas);
                break;
            }
            case 2:{
                toolBarPaint.eraser.setCursor(canvas);
                break;
            }
            case 3:{
                toolBarPaint.line.setCursor(canvas);
                break;
            }
            case 4:{
                toolBarPaint.rectangle.setCursor(canvas);
                break;
            }
            case 5:{
                toolBarPaint.rectSelection.setCursor(canvas);
                break;
            }
            case 6:{
                canvas.setCursor(Cursor.TEXT);
                break;
            }
            default:{
                canvas.setCursor(Cursor.DEFAULT);
            }
        }
    }

    void design(double x, double y){
        GraphicsContext g = canvas.getGraphicsContext2D();
        switch (toolBarPaint.pointer.pointerButton){
            case 0:{
                toolBarPaint.pencil.design(g, x, y);
                break;
            }
            case 1: {
                toolBarPaint.brush.design(g, x, y);
                break;
            }
            case 2:{
                toolBarPaint.eraser.design(g, x, y);
                break;
            }
        }
    };

    void designPressed(double x, double y){
        GraphicsContext g = canvas.getGraphicsContext2D();
        switch (toolBarPaint.pointer.pointerButton){
            case 0:{
                toolBarPaint.pencil.designPressed(g, x, y, colorPicker.getValue());
                break;
            }
            case 1:{
                double size = Double.parseDouble(brushSize.getText());
                toolBarPaint.brush.designPressed(g, x, y, size, colorPicker.getValue());
                break;
            }
            case 2:{
                double size = Double.parseDouble(brushSize.getText());
                toolBarPaint.eraser.designPressed(g, x, y, size);
                break;
            }
        }
    }

    void designReleased(double x, double y){
        GraphicsContext g = canvas.getGraphicsContext2D();
        switch (toolBarPaint.pointer.pointerButton){
            case 0:{
                toolBarPaint.pencil.designReleased(g, x, y);
                break;
            }
            case 1:{
                toolBarPaint.brush.designReleased(g, x, y);
                break;
            }
            case 2:{
                toolBarPaint.eraser.designReleased(g, x, y);
                break;
            }
        }
    }

    void designClick(double x, double y){
        GraphicsContext g = canvas.getGraphicsContext2D();
        switch (toolBarPaint.pointer.pointerButton){
            case 3:{
                toolBarPaint.line.design(canvas.getGraphicsContext2D(), x, y);
                click = true;
                break;
            }
            case 4:{
                toolBarPaint.rectangle.design(x, y);
                click = true;
                break;
            }
            case 5:{
                toolBarPaint.rectSelection.design(x, y);
                click = true;
                break;
            }
            case 6:{
                toolBarPaint.text.design(x, y);
                click = true;
                break;
            }
            case 7:{
                toolBarPaint.magnifier.approximation(canvas, brushSize);
                break;
            }
            case 8:{
                toolBarPaint.rectSelection.paste(canvas, x, y);
                break;
            }
            case 9:{
                toolBarPaint.ellipse.design(canvas.getGraphicsContext2D(), colorPicker, toolBarPaint.fill, x, y);
                click = toolBarPaint.ellipse.click;
                break;
            }
        }
    }

    void eventFill(){
        if(toolBarPaint.fill.isSelected()){
            toolBarPaint.rectangle.rectangleFigure.setFill(colorPicker.getValue());
            toolBarPaint.ellipse.ellipseFigure.setFill(colorPicker.getValue());
        }else{
            toolBarPaint.rectangle.rectangleFigure.setFill(null);
            toolBarPaint.ellipse.ellipseFigure.setFill(null);
        }
    }

    void eventMove(MouseEvent e){
        Timeline timeline = new Timeline();
        switch (toolBarPaint.pointer.pointerButton){
            case 3:{
                toolBarPaint.line.moveLine(timeline, e, colorPicker);
                break;
            }
            case 4:{
                toolBarPaint.rectangle.moveRectangle(timeline, e);
                break;
            }
            case 5:{
                toolBarPaint.rectSelection.moveRectangle(timeline, e);
                break;
            }
            case 6:{
                toolBarPaint.text.moveRectangle(timeline, e);
                break;
            }
            case 9:{
                toolBarPaint.ellipse.moveCircle(timeline, e, colorPicker);
                break;
            }

        }
        if(click)timeline.play(); else timeline.stop();
    }

    public void event(){
        toolBarPaint.onButton(canvas, colorPicker, brushSize, toolBarPaint.rectSelection.rectangleFigure, toolBarPaint.text.rectangleFigure);
        canvas.setOnMouseMoved(e->{
            setCursor();
            eventMove(e);
        });
        canvas.setOnMouseDragged(e->{
            double size = Double.parseDouble(brushSize.getText());
            design(e.getX(), e.getY());
        });
        canvas.setOnMousePressed(e->{
            designPressed(e.getX(), e.getY());
        });
        canvas.setOnMouseReleased(e->{
            designReleased(e.getX(), e.getY());
        });
        canvas.setOnMouseClicked(e->{
            toolBarPaint.text.createText(canvas.getGraphicsContext2D(), colorPicker);
            designClick(e.getX(), e.getY());
        });
        toolBarPaint.line.side.setOnMouseClicked(e->{
            toolBarPaint.line.endDesign(canvas.getGraphicsContext2D(), e.getX(), e.getY(), colorPicker);
        });
        toolBarPaint.rectangle.rectangleFigure.setOnMouseClicked(e->{
            toolBarPaint.rectangle.endDesign(canvas.getGraphicsContext2D(), toolBarPaint.fill, e);
            click = false;
        });
        toolBarPaint.rectSelection.rectangleFigure.setOnMouseClicked(e->{
            toolBarPaint.rectSelection.endDesign(e);
            click = false;
        });
        toolBarPaint.text.rectangleFigure.setOnMouseClicked(e->{
            toolBarPaint.text.endDesign(e);
            click = false;
        });
        toolBarPaint.fill.setOnMouseClicked(e->{
            eventFill();
        });
        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                toolBarPaint.rectangle.rectangleFigure.setStroke(colorPicker.getValue());
                toolBarPaint.ellipse.ellipseFigure.setStroke(colorPicker.getValue());
                if(toolBarPaint.fill.isSelected()) {
                    toolBarPaint.rectangle.rectangleFigure.setFill(colorPicker.getValue());
                    toolBarPaint.ellipse.ellipseFigure.setFill(colorPicker.getValue());
                }
            }
        });
    }

    public Events(Canvas canvas, ColorPicker colorPicker, TextField brushSize, ToolBarPaint toolBarPaint){
        click = false;
        this.canvas = canvas;
        this.colorPicker = colorPicker;
        this.brushSize = brushSize;
        this.toolBarPaint = toolBarPaint;
    }
}