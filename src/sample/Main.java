package sample;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Application {
    int pointerButtonForClick;
    double widthCanvas , heightCanvas , counterZoom;
    boolean click;
    boolean checkForText;
    private int pointerButton;
    private Image imageCopy;
    private BorderPane root;
    private Label nameTextField;
    private TextField brushSize;
    private TextArea brushText;
    private ColorPicker colorPicker;
    private ScrollPane scrollPane;
    private Button pencil;
    private Button brush;
    private Button eraser;
    private Button line;
    private Button rectangle;
    private Button rectSelection;
    private Button text;
    private Button magnifier;
    private Button ellipseButton;
    private Canvas canvas;
    private Line side1;
    private Rectangle rectangleFigure;
    private Ellipse ellipseFigure;
    private RadioButton stroke;
    private RadioButton fill;

    void arrangementRadioButton(boolean check){
        stroke.setVisible(check);
        fill.setVisible(check);
    };

    void initializeButton(){
        Image url= new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/pencil.png");
        ImageView imageView = new ImageView(url);
        pencil = new Button("" , imageView);
        url= new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/brush.png");
        imageView = new ImageView(url);
        brush = new Button("" , imageView);
        url= new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/eraser.png");
        imageView = new ImageView(url);
        eraser = new Button("" , imageView);
        url= new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/line.png");
        imageView = new ImageView(url);
        line = new Button("" , imageView);
        url= new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/rectangle.png");
        imageView = new ImageView(url);
        rectangle = new Button("",imageView);
        url= new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/rect_selection.png");
        imageView = new ImageView(url);
        rectSelection = new Button("",imageView);
        url= new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/text.png");
        imageView = new ImageView(url);
        text = new Button("",imageView);
        url= new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/magnifier.png" , 16 , 16 , false , true);
        imageView = new ImageView(url);
        magnifier = new Button("",imageView);
        ellipseButton = new Button("Circle");
        stroke = new RadioButton("Stroke");
        fill = new RadioButton("Fill");
        arrangementRadioButton(false);
    }

    public void onPencil(){
        pencil.setOnAction(e->{
            createText(canvas.getGraphicsContext2D());
            nameTextField.setText("Size :");
            hideRectangle();
            pointerButton = 0;
            brushSize.setText("1");
            brushSize.setEditable(false);
            arrangementRadioButton(false);
        });
    }

    public void onBrush(){
        brush.setOnAction(e->{
            createText(canvas.getGraphicsContext2D());
            hideRectangle();
            nameTextField.setText("Size :");
            pointerButton = 1;
            brushSize.setEditable(true);
            arrangementRadioButton(false);
        });
    }

    public void onEraser(){
        eraser.setOnAction(e->{
            createText(canvas.getGraphicsContext2D());
            nameTextField.setText("Size :");
            hideRectangle();
            pointerButton = 2;
            brushSize.setEditable(true);
            arrangementRadioButton(false);
        });
    }

    public void onLine() {
        line.setOnAction(e->{
            createText(canvas.getGraphicsContext2D());
            nameTextField.setText("Size :");
            getStrokeDashArrayClearRectangle();
            hideRectangle();
            pointerButton = 3;
            brushSize.setText("1");
            brushSize.setEditable(false);
            arrangementRadioButton(false);
        });
    }

    public void onRectangle(){
        rectangle.setOnAction(e->{
            createText(canvas.getGraphicsContext2D());
            getStrokeDashArrayClearRectangle();
            nameTextField.setText("Size :");
            hideRectangle();
            pointerButton = 4;
            brushSize.setText("1");
            brushSize.setEditable(false);
            arrangementRadioButton(true);
        });
    }

    public void onRectSelection(){
        rectSelection.setOnAction(e->{
            createText(canvas.getGraphicsContext2D());
            nameTextField.setText("Size :");
            hideRectangle();
            pointerButton = 5;
            brushSize.setText("1");
            brushSize.setEditable(false);
            arrangementRadioButton(false);
        });
    }

    public void onText(){
        text.setOnAction(e->{
            createText(canvas.getGraphicsContext2D());
            hideRectangle();
            pointerButtonForClick = 1;
            pointerButton = 6;
//            brushSize.setText("Your text");
            brushSize.setEditable(false);
            arrangementRadioButton(false);

        });
    }

    public void onMagnifier(){
        magnifier.setOnAction(e->{
            createText(canvas.getGraphicsContext2D());
            hideRectangle();
            nameTextField.setText("Size :");
            pointerButton = 7;
            brushSize.setText("1");
            brushSize.setEditable(true);
            arrangementRadioButton(false);
        });
    }

    public void onEllipse(){
        ellipseButton.setOnAction(e->{
            createText(canvas.getGraphicsContext2D());
            hideRectangle();
            pointerButton = 9;
            brushSize.setText("1");
            brushSize.setEditable(false);
            arrangementRadioButton(true);
        });
    }

    public void onButton(){
        onPencil();
        onBrush();
        onEraser();
        onLine();
        onRectangle();
        onRectSelection();
        onText();
        onMagnifier();
        onEllipse();
    }

    public void onOpen() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open picture");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG" , "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File openFile = fileChooser.showOpenDialog(null);
        if(openFile != null){
            double canvasWidth = canvas.getWidth();
            double canvasHeight = canvas.getHeight();
            Image bgImage = new Image(new FileInputStream(openFile));
            canvas.getGraphicsContext2D().drawImage(bgImage, 0, 0, canvasWidth, canvasHeight);

        }else System.out.println(0);

    }

    public void onSave() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save picture");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG" , "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        Image snapshot = canvas.snapshot(null ,null);
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            String name = file.getName();
            String extension = name.substring(1+name.lastIndexOf(".")).toLowerCase();
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot , null), extension, file);
        }
    }

    public void onClear(){
        canvas.getGraphicsContext2D().clearRect(0, 0 , canvas.getWidth() , canvas.getHeight());
    }

    public void onExit(){
        Platform.exit();
    }

    public void onCopy(){
        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setViewport(new Rectangle2D(rectangleFigure.getX() , rectangleFigure.getY() , rectangleFigure.getWidth() , rectangleFigure.getHeight()));
        imageCopy = canvas.snapshot(snapshotParameters , null);
    }

    public void onPaste(){
        hideRectangle();
        nameTextField.setText("Size :");
        pointerButton = 8;
        brushSize.setText("1");
        brushSize.setEditable(false);
    }

    Menu createFileMenu(){
        Menu fileMenu = new Menu("File");
        MenuItem open = new MenuItem("Open");
        open.setOnAction(e->{
            try {
                onOpen();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        open.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        MenuItem save = new MenuItem("Save");
        save.setOnAction(e->{
            try {
                onSave();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        MenuItem clear = new MenuItem("Clear");
        clear.setOnAction(e->onClear());
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e->onExit());
        fileMenu.getItems().addAll(open , save , clear , exit);
        return fileMenu;
    }

    Menu createEditMenu(){
        Menu editMenu = new Menu("Edit");
        MenuItem copy = new MenuItem("Copy");
        copy.setOnAction(e->onCopy());
        copy.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));
        MenuItem paste = new MenuItem("Paste");
        paste.setOnAction(e->onPaste());
        paste.setAccelerator(KeyCombination.keyCombination("Ctrl+V"));
        editMenu.getItems().addAll(copy , paste);
        return editMenu;
    }

    MenuBar createMenuBar(){
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(createFileMenu() , createEditMenu());
        return menuBar;
    }

    public void createTop(){
        HBox top1 = new HBox(createMenuBar());
        HBox groupRadioButton = new HBox(fill , stroke);
        groupRadioButton.setSpacing(5);
        ToggleGroup group = new ToggleGroup();
        fill.setToggleGroup(group);
        stroke.setToggleGroup(group);
        HBox top2 = new HBox(nameTextField , brushSize , colorPicker , groupRadioButton);
        VBox top = new VBox(top1 , top2);
        root.setTop(top);
    }

    void arrangement(){
        VBox left = new VBox(pencil , brush , eraser , line , rectangle , rectSelection , text , magnifier , ellipseButton);
        left.setSpacing(5);
        root.setLeft(left);
        Group group = new Group(canvas , side1 , brushText , rectangleFigure , ellipseFigure);
        scrollPane.setContent(group);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        root.setCenter(scrollPane);
        createTop();
    }

    void setCursor(){
        switch (pointerButton){
            case 0:{
                Image image = new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/pencil.png");
                canvas.setCursor(new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2));
                break;
            }
            case 1:{
                Image image = new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/brush.png");
                canvas.setCursor(new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2));
                break;
            }
            case 2:{
                Image image = new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/eraser.png");
                canvas.setCursor(new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2));
                break;
            }
            case 3:{
                Image image = new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/line.png");
                canvas.setCursor(new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2));
                break;
            }
            case 4:{
                Image image = new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/rectangle.png");
                canvas.setCursor(new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2));
                break;
            }
            case 5:{
                Image image = new Image("file:/home/sanchir/IdeaProjects/Paint/Picture/rect_selection.png");
                canvas.setCursor(new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2));
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

    void design(GraphicsContext g , double x , double y , double size){
        switch (pointerButton){
            case 0:
            case 1: {
                g.setStroke(colorPicker.getValue());
                g.lineTo(x , y);
                g.stroke();
                g.closePath();
                g.beginPath();
                g.moveTo(x , y);
                break;
            }
            case 2:{
                g.clearRect(x - size / 2 , y - size / 2 , size ,  size);
                break;
            }
        }
    };

    void designPressed(GraphicsContext g , double x , double y){
        switch (pointerButton){
            case 0:{
                g.setLineWidth(1);
                g.setStroke(colorPicker.getValue());
                g.beginPath();
                g.moveTo(x , y);
                g.stroke();
                break;
            }
            case 1:{
                g.setLineWidth(Double.parseDouble(brushSize.getText()));
                g.setStroke(colorPicker.getValue());
                g.beginPath();
                g.moveTo(x , y);
                g.stroke();
                break;
            }
            case 2:{
                double size = Double.parseDouble(brushSize.getText());
                g.clearRect(x - size / 2 , y - size / 2 , size ,  size);
                break;
            }
        }
    }

    void designReleased(GraphicsContext g , double x , double y){
        switch (pointerButton){
            case 0:{
                g.setStroke(colorPicker.getValue());
                g.lineTo(x ,y);
                g.stroke();
                g.closePath();
                break;
            }
            case 1:{
                g.setLineWidth(Double.parseDouble(brushSize.getText()));
                g.setStroke(colorPicker.getValue());
                g.lineTo(x ,y);
                g.stroke();
                g.closePath();
                break;
            }
        }
    }

    void createRectangle(GraphicsContext g , double x , double y , double x1 , double y1){
        g.setStroke(colorPicker.getValue());
        g.setLineWidth(1);
        g.lineTo(x1 , y);
        g.lineTo(x1 , y1);
        g.lineTo(x , y1);
        g.lineTo(x , y);
        g.stroke();
        g.closePath();
    }

    double coordinateX , coordinateY;

    void setCoordinateLine(double x1 , double y1 , double x2 , double y2){
        side1.setStartX(x1);
        side1.setStartY(y1);
        side1.setEndX(x2);
        side1.setEndY(y2);
    }

    void setCoordinateRectangle(double x1 , double y1 , double x2 , double y2){
        rectangleFigure.setX(x1);
        rectangleFigure.setY(y1);
        rectangleFigure.setHeight(x2 - x1);
        rectangleFigure.setWidth(y2 - y1);
    }

    void getStrokeDashArrayRectangle(double  width, double height){
        rectangleFigure.getStrokeDashArray().addAll(width , height);
    }

    void getStrokeDashArrayClearRectangle(){
        rectangleFigure.getStrokeDashArray().clear();
    }

    void disclosureRectangle(){
        rectangleFigure.setVisible(true);
    }

    void hideRectangle(){
        rectangleFigure.setVisible(false);
    }

    void createText(GraphicsContext g){
        if(checkForText){
            g.fillText( brushText.getText(), rectangleFigure.getX(), rectangleFigure.getY());
            checkForText = false;
            brushText.clear();
            brushText.setVisible(false);
        }
    }

    void setSettingsCircle(double x , double y , double radiusX , double radiusY){
        ellipseFigure.setStroke(Color.BLACK);
        ellipseFigure.setVisible(true);
        ellipseFigure.setCenterX(x);
        ellipseFigure.setCenterY(y);
        ellipseFigure.setRadiusX(radiusX);
        ellipseFigure.setRadiusY(radiusY);
    }

    double getBeginEllipseX(double x){
        return Math.min(coordinateX , x);
    }

    double getBeginEllipseY(double y){
        return Math.min(coordinateY , y);
    }

    double getRadiusEllipseX(double x){
        return Math.max(coordinateX , x) - getBeginEllipseX(x);
    }

    double getRadiusEllipseY(double y){
        return Math.max(coordinateY , y) - getBeginEllipseY(y);
    }

    void designClick(GraphicsContext g , double x , double y){
        if(click && pointerButtonForClick != pointerButton) {
            g.closePath();
            click = false;
        }
        switch (pointerButton){
            case 3:{
                if(!click){
                    side1.setVisible(true);
                    g.beginPath();
                    g.moveTo(x , y);
                    setCoordinateLine(x + 2,y,x + 2 ,y);
                }else {
                    side1.setVisible(false);
                    g.setStroke(colorPicker.getValue());
                    g.setLineWidth(1);
                    g.lineTo(x,y);
                    g.stroke();
                    g.closePath();
                }
                click = !click;
                pointerButtonForClick = pointerButton;
                break;
            }
            case 4:{
                if(!click){
                    disclosureRectangle();
                    getStrokeDashArrayClearRectangle();
                    setCoordinateRectangle(x + 2,y + 2,x + 2 ,y + 2);
                    g.beginPath();
                    g.moveTo(x , y);
                    coordinateX = x;
                    coordinateY = y;
                }else{
                    hideRectangle();
                    createRectangle(g , coordinateX , coordinateY , x , y);
                }
                click = !click;
                pointerButtonForClick = pointerButton;
                break;
            }
            case 5:{
                if(!click){
                    disclosureRectangle();
                    getStrokeDashArrayRectangle(5.0 , 5.0);
                    setCoordinateRectangle(x + 2,y + 2,x + 2 ,y + 2);
                    coordinateX = x + 2;
                    coordinateY = y + 2;
                }else{
                    setCoordinateRectangle(coordinateX , coordinateY , x + 2 , y + 2);
                }
                click = !click;
                pointerButtonForClick = pointerButton;
                break;
            }
            case 6:{
                g.setLineWidth(1);
                g.setStroke(colorPicker.getValue());
                if(!click){
                    brushText.setVisible(false);
                    disclosureRectangle();
                    getStrokeDashArrayRectangle(5.0 , 5.0);
                    setCoordinateRectangle(x + 2,y + 2,x + 2 ,y + 2);
                    coordinateX = x + 2;
                    coordinateY = y + 2;
                    checkForText = false;
                }else{
                    setCoordinateRectangle(coordinateX , coordinateY , x + 2 , y + 2);
                    brushText.setLayoutX(rectangleFigure.getX());
                    brushText.setLayoutY(rectangleFigure.getY());
                    brushText.setMaxSize(rectangleFigure.getWidth() , rectangleFigure.getHeight());
                    brushText.setVisible(true);
                    checkForText = true;
                }
                click = !click;
                pointerButtonForClick = pointerButton;
                break;
            }
            case 7:{
                if(counterZoom == Double.parseDouble(brushSize.getText())) break;
                widthCanvas *= Double.parseDouble(brushSize.getText())/counterZoom;
                heightCanvas *= Double.parseDouble(brushSize.getText())/counterZoom;
                counterZoom = Double.parseDouble(brushSize.getText());
                Image snapshot = canvas.snapshot(null ,null);
                canvas.setWidth(widthCanvas);
                canvas.setHeight(heightCanvas);
                g.drawImage(snapshot, 0, 0, widthCanvas, heightCanvas);
                break;
            }
            case 8:{
                canvas.getGraphicsContext2D().drawImage(imageCopy, x, y, imageCopy.getWidth(), imageCopy.getHeight());
                break;
            }
            case 9:{
                if(!click){
                    setSettingsCircle(x , y , 0 , 0);
                    coordinateX = x;
                    coordinateY = y;
                }else{
                    ellipseFigure.setVisible(false);
                    g.setStroke(colorPicker.getValue());
                    double startX = Math.min(coordinateX , x);
                    double startY = Math.min(coordinateY , y);
                    g.strokeOval(getBeginEllipseX(x) , getBeginEllipseY(y) , getRadiusEllipseX(x) , getRadiusEllipseY(y));
                    g.closePath();
                }
                click = !click;
                pointerButtonForClick = pointerButton;
                break;
            }
        }
    }

    void moveLine(Timeline timeline , MouseEvent e){
        KeyFrame kfx = new KeyFrame(Duration.millis(0.5) , new KeyValue(side1.endXProperty() , e.getX() + 2));
        KeyFrame kfy = new KeyFrame(Duration.millis(0.5) , new KeyValue(side1.endYProperty() , e.getY()));
        timeline.getKeyFrames().addAll(kfx , kfy);
    }

    double getWidthRectangle(double x){
        return Math.max(rectangleFigure.getX() , x) - Math.min(rectangleFigure.getX() , x);
    }

    double getHeightRectangle(double y){

        return Math.max(rectangleFigure.getY() , y) - Math.min(rectangleFigure.getY() , y);
    }

    void moveRectangle(Timeline timeline , MouseEvent e){
        KeyFrame kfX = new KeyFrame(Duration.millis(0.5) , new KeyValue(rectangleFigure.xProperty() , Math.min(e.getX() , coordinateX)));
        KeyFrame kfY = new KeyFrame(Duration.millis(0.5) , new KeyValue(rectangleFigure.yProperty() , Math.min(e.getY() , coordinateY)));
        KeyFrame kfWidth = new KeyFrame(Duration.millis(0.5) , new KeyValue(rectangleFigure.widthProperty() , Math.abs(e.getX() - coordinateX)));
        KeyFrame kfHeight = new KeyFrame(Duration.millis(0.5) , new KeyValue(rectangleFigure.heightProperty() , Math.abs(e.getY() - coordinateY)));
        timeline.getKeyFrames().addAll(kfX , kfY);
        timeline.getKeyFrames().addAll(kfWidth , kfHeight);
    }

    void moveCircle(Timeline timeline , MouseEvent e){
        ellipseFigure.setStroke(colorPicker.getValue());
        KeyFrame kfCenterX = new KeyFrame(Duration.millis(0.5) , new KeyValue(ellipseFigure.centerXProperty() , (e.getX() + coordinateX)/2));
        KeyFrame kfCenterY = new KeyFrame(Duration.millis(0.5) , new KeyValue(ellipseFigure.centerYProperty() , (e.getY() + coordinateY)/2));
        KeyFrame kfRadiusX = new KeyFrame(Duration.millis(0.5) , new KeyValue(ellipseFigure.radiusXProperty() , getRadiusEllipseX(e.getX())/2));
        KeyFrame kfRadiusY = new KeyFrame(Duration.millis(0.5) , new KeyValue(ellipseFigure.radiusYProperty() , getRadiusEllipseY(e.getY())/2));
        timeline.getKeyFrames().addAll(kfCenterX , kfCenterY , kfRadiusX , kfRadiusY);
    }

    void event(GraphicsContext g){
        onButton();
        canvas.setOnMouseMoved(e->{
            setCursor();
            Timeline timeline = new Timeline();
            switch (pointerButton){
                case 3:{
                    moveLine(timeline , e);
                    break;
                }
                case 4:
                case 6:
                case 5:{
                    moveRectangle(timeline , e);
                    break;
                }
                case 9:{
                    moveCircle(timeline , e);
                    break;
                }

            }

            if(click)timeline.play(); else timeline.stop();
        });
        canvas.setOnMouseDragged(e->{
            double size = Double.parseDouble(brushSize.getText());
            design(g , e.getX() , e.getY() , size);
        });
        canvas.setOnMousePressed(e->{
            designPressed(g , e.getX() , e.getY());
        });
        canvas.setOnMouseReleased(e->{
            designReleased(g , e.getX() , e.getY());
        });
        canvas.setOnMouseClicked(e->{
            createText(g);
            designClick(g , e.getX() , e.getY());
        });
    }

    void initializeVariable(){
        side1 = new Line();
        rectangleFigure = new Rectangle();
//        rectangleFigure.getStrokeDashArray().addAll(25d, 10d);
        rectangleFigure.setFill(null);
//        rectangleFigure.setStroke(Color.BLACK);
        rectangleFigure.setVisible(false);
        ellipseFigure = new Ellipse();
        ellipseFigure.setFill(null);
        ellipseFigure.setVisible(false);
        scrollPane = new ScrollPane();
        pointerButton = 0;
        colorPicker.setValue(Color.BLACK);
        brushSize.setEditable(false);
        click = false;
        pointerButtonForClick = pointerButton;
        counterZoom = 1;
        widthCanvas = 600;
        heightCanvas = 600;
        nameTextField = new Label("Size :");
        checkForText = false;
    }

    void initialize(){
        root = new BorderPane();
        brushSize = new TextField("1");
        brushText = new TextArea();
        brushText.setVisible(false);
        colorPicker = new ColorPicker();
        initializeButton();
        initializeVariable();
        canvas = new Canvas(600 , 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        onClear();
        arrangement();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        initialize();
        event(canvas.getGraphicsContext2D());
        primaryStage.setScene(new Scene(root, 700, 650));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}