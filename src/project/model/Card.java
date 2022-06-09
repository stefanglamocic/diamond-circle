package project.model;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public abstract class Card extends ImageView {
    public Card(){
        setPreserveRatio(true);
        setCache(true);
        setSmooth(true);
        setFitWidth(290);
    }

    public int effect(Matrix matrix){
        matrix.removeHoles();
        return 0;
    }
}
