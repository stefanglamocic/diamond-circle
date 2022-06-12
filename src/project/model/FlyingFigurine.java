package project.model;

import project.Game;

public class FlyingFigurine extends Figurine{
    public FlyingFigurine(Game game, Player player, Color color, String name){
        super(game, player, color, name);
        switch (color){
            case RED: setImage(Images.redOwl); break;
            case BLUE: setImage(Images.blueOwl); break;
            case GREEN: setImage(Images.greenOwl); break;
            case YELLOW: setImage(Images.yellowOwl); break;
        }
    }

    public boolean isFlying(){
        return true;
    }

    public String getType(){
        return "leteća";
    }
}
