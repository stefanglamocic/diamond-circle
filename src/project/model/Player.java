package project.model;

import java.util.Random;

public class Player {
    private final String name;
    private Figurine[] figurines;
    private final Color color;

    public Player(String name, Color color){
        this.name = name;
        this.color = color;
        figurines = generateFigurines(color);
    }

    private Figurine[] generateFigurines(Color color){
        Random rng = new Random();
        Figurine[] temp = new Figurine[4];

        for(int i = 0; i < 4; i++){
            switch(rng.nextInt(3)){
                case 0:
                    temp[i] = new RegularFigurine(color, "Obicna figura " + i + " (" + name + ")");
                    break;
                case 1:
                    temp[i] = new FlyingFigurine(color, "Leteca figura " + i + " (" + name + ")");
                    break;
                case 2:
                    temp[i] = new FastFigurine(color, "Brza figura " + i + " (" + name + ")");
                    break;
            }
        }

        return temp;
    }

    public String getName(){ return name; }

    public javafx.scene.paint.Color getPlayerColor(){ return color.getColor(); }
}
