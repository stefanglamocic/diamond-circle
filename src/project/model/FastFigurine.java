package project.model;

public class FastFigurine extends Figurine{
    public FastFigurine(Color color, String name){
        super(color, name);
        switch (color){
            case RED: setImage(Images.redCat); break;
            case BLUE: setImage(Images.blueCat); break;
            case GREEN: setImage(Images.greenCat); break;
            case YELLOW: setImage(Images.yellowCat); break;
        }
    }
}
