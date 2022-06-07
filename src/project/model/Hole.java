package project.model;

import javafx.scene.image.ImageView;

public class Hole extends ImageView {
    public Hole(){
        setImage(Images.hole);
        setPreserveRatio(true);
        setSmooth(true);
        setCache(true);
    }
}
