package project.model;

import javafx.scene.image.Image;
import project.Game;

public class RegularFigurine extends Figurine{
    public RegularFigurine(Game game, Player player, Color color, String name){
        super(game, player, color, name);
        switch (color){
            case RED: setImage(Images.redTurtle); break;
            case BLUE: setImage(Images.blueTurtle); break;
            case GREEN: setImage(Images.greenTurtle); break;
            case YELLOW: setImage(Images.yellowTurtle); break;
        }
    }

    public String getType(){
        return "obična";
    }
}
