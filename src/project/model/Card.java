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

    public abstract int effect(Matrix matrix) throws InterruptedException;
}
