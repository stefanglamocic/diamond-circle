package project.model;

import project.Game;

public class FastFigurine extends Figurine{
    public FastFigurine(Game game, Player player, Color color, String name){
        super(game, player, color, name);
        switch (color){
            case RED: setImage(Images.redCat); break;
            case BLUE: setImage(Images.blueCat); break;
            case GREEN: setImage(Images.greenCat); break;
            case YELLOW: setImage(Images.yellowCat); break;
        }
    }
}
