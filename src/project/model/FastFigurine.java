package project.model;

public class FastFigurine extends Figurine{
    public FastFigurine(Matrix matrix, Color color, String name){
        super(matrix, color, name);
        switch (color){
            case RED: setImage(Images.redCat); break;
            case BLUE: setImage(Images.blueCat); break;
            case GREEN: setImage(Images.greenCat); break;
            case YELLOW: setImage(Images.yellowCat); break;
        }
    }
}
