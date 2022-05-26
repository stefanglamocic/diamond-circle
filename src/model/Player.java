package model;

import java.util.Random;

public class Player {
    private String name;
    private Figurine[] figurines;

    public Player(String name, Color color){
        this.name = name;
        figurines = generateFigurines(color);
    }

    private Figurine[] generateFigurines(Color color){
        Random rng = new Random();
        Figurine[] temp = new Figurine[4];

        for(int i = 0; i < 4; i++){
            switch(rng.nextInt(3)){
                case 0:
                    temp[i] = new RegularFigurine(color);
                    break;
                case 1:
                    temp[i] = new FlyingFigurine(color);
                    break;
                case 2:
                    temp[i] = new FastFigurine(color);
                    break;
            }
        }

        return temp;
    }
}
