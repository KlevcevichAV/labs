package sample;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MenuPaint {
    MenuBar menuBar;

    public void onOpen(Canvas canvas) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open picture");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File openFile = fileChooser.showOpenDialog(null);
        if(openFile != null){
            double canvasWidth = canvas.getWidth();
            double canvasHeight = canvas.getHeight();
            Image bgImage = new Image(new FileInputStream(openFile));
            canvas.getGraphicsContext2D().drawImage(bgImage, 0, 0, canvasWidth, canvasHeight);

        }else System.out.println(0);

    }

    public void onSave(Canvas canvas) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save picture");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        Image snapshot = canvas.snapshot(null,null);
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            String name = file.getName();
            String extension = name.substring(1+name.lastIndexOf(".")).toLowerCase();
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), extension, file);
        }
    }

    public void onClear(Canvas canvas){
        canvas.getGraphicsContext2D().setFill(Color.WHITE);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void onExit(){
        Platform.exit();
    }

    public void onPaste(ToolBarPaint.Pointer pointerButton, RectSelection rectSelection){
        rectSelection.rectangleFigure.setVisible(false);
        pointerButton.pointerButton = 8;
    }

    Menu createFileMenu(Canvas canvas){
        Menu fileMenu = new Menu("File");
        MenuItem open = new MenuItem("Open");
        open.setOnAction(e->{
            try {
                onOpen(canvas);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        open.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        MenuItem save = new MenuItem("Save");
        save.setOnAction(e->{
            try {
                onSave(canvas);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        MenuItem clear = new MenuItem("Clear");
        clear.setOnAction(e->onClear(canvas));
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e->onExit());
        fileMenu.getItems().addAll(open, save, clear, exit);
        return fileMenu;
    }

    Menu createEditMenu(Canvas canvas, RectSelection rectSelection, ToolBarPaint.Pointer pointerButton){
        Menu editMenu = new Menu("Edit");
        MenuItem copy = new MenuItem("Copy");
        copy.setOnAction(e->rectSelection.copy(canvas));
        copy.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));
        MenuItem paste = new MenuItem("Paste");
        paste.setOnAction(e->onPaste(pointerButton, rectSelection));
        paste.setAccelerator(KeyCombination.keyCombination("Ctrl+V"));
        editMenu.getItems().addAll(copy, paste);
        return editMenu;
    }

    MenuPaint(Canvas canvas, RectSelection rectSelection, ToolBarPaint.Pointer pointer){
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(createFileMenu(canvas), createEditMenu(canvas, rectSelection, pointer));
    }

}