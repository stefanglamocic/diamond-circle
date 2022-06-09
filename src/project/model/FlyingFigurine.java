package project.model;

import project.Game;

public class FlyingFigurine extends Figurine{
    public FlyingFigurine(Game game, Color color, String name){
        super(game, color, name);
        switch (color){
            case RED: setImage(Images.redOwl); break;
            case BLUE: setImage(Images.blueOwl); break;
            case GREEN: setImage(Images.greenOwl); break;
            case YELLOW: setImage(Images.yellowOwl); break;
        }
    }
}
