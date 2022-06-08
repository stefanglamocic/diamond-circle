package project.model;

public class FlyingFigurine extends Figurine{
    public FlyingFigurine(Matrix matrix, Color color, String name){
        super(matrix, color, name);
        switch (color){
            case RED: setImage(Images.redOwl); break;
            case BLUE: setImage(Images.blueOwl); break;
            case GREEN: setImage(Images.greenOwl); break;
            case YELLOW: setImage(Images.yellowOwl); break;
        }
    }
}
