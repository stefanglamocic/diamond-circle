package project.model;

import javafx.scene.image.Image;

public class RegularFigurine extends Figurine{
    public RegularFigurine(Matrix matrix, Color color, String name){
        super(matrix, color, name);
        switch (color){
            case RED: setImage(Images.redTurtle); break;
            case BLUE: setImage(Images.blueTurtle); break;
            case GREEN: setImage(Images.greenTurtle); break;
            case YELLOW: setImage(Images.yellowTurtle); break;
        }
    }
}
