package project.model;
import static javafx.scene.paint.Color.web;

public enum Color {
    RED(web("#EC1C24")),
    BLUE(web("#2478FF")),
    GREEN(web("#0ED145")),
    YELLOW(web("#FFF200")),
    INVISIBLE(web("#000000"));

    private final javafx.scene.paint.Color color;

    Color(javafx.scene.paint.Color color){
        this.color = color;
    }

    javafx.scene.paint.Color getColor(){ return color; }
}
