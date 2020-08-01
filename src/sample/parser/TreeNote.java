package sample.parser;

public class TreeNote {
    public String sign;
    public String expression;
    public String value;

    public TreeNote(int pointer, String expression) {
        sign = "";
        this.expression = "";
        value = "";
        switch (pointer) {
            case 0: {
                value = expression;
                break;
            }
            case 1: {
                sign = expression;
                break;
            }
            default: {
                this.expression = expression;
            }
        }
    }

    public void edit(int pointer, String editField) {
        switch (pointer) {
            case 0: {
                value = editField;
                break;
            }
            case 1: {
                sign = editField;
                break;
            }
            default: {
                this.expression = editField;
            }
        }
    }
}
