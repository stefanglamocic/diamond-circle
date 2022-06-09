package project.model;

import java.util.Random;

public class Player extends Thread{
    private final String name;
    private final Figurine[] figurines;
    private final Color color;
    private Matrix matrix;
    private boolean over;

    public Player(Matrix matrix, String name, Color color){
        setDaemon(true);
        this.matrix = matrix;
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
                    temp[i] = new RegularFigurine(matrix, color, "Figura " + (Figurine.figurineCounter + 1) + " - obična");
                    break;
                case 1:
                    temp[i] = new FlyingFigurine(matrix, color, "Figura " + (Figurine.figurineCounter + 1) + " - leteća");
                    break;
                case 2:
                    temp[i] = new FastFigurine(matrix, color, "Figura " + (Figurine.figurineCounter + 1) + " - super brza");
                    break;
            }
        }

        return temp;
    }

    public String getPlayerName(){ return name; }

    public javafx.scene.paint.Color getPlayerColor(){ return color.getColor(); }

    public Figurine[] getFigurines(){ return figurines; }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }
}
