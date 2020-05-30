package sample.view.keyboard.button;

public class Button extends javafx.scene.control.Button {
    public Button() {
        super();
        setStyle(-1);
    }

    private void setStyle(int color){
        switch (color){
            case 0:{
                super.getStylesheets().add(getClass().getResource("buttonGrey.css").toExternalForm());
                break;
            }
            case 1:{
                super.getStylesheets().add(getClass().getResource("buttonOrange.css").toExternalForm());
                break;
            }
            case 2:{
                super.getStylesheets().add(getClass().getResource("buttonDarkGrey.css").toExternalForm());
                break;
            }
            case 3:{
                super.getStylesheets().add(getClass().getResource("buttonNull.css").toExternalForm());
            }
        }
    }

    public Button(String title, int color ){
        super();
        setStyle(color);
        super.setText(title);
    }
}
