package project.model;

import project.Game;

import java.util.Random;

public class Player extends Thread{
    private final String name;
    private final Figurine[] figurines;
    private final Color color;
    private Game game;
    private boolean turn, started;

    public Player(Game game, String name, Color color){
        setDaemon(true);
        this.game = game;
        this.name = name;
        this.color = color;
        figurines = generateFigurines(color);
    }

    public void run(){
        for(Figurine f : figurines){
            f.start();
            try{
                f.join();
            }catch (InterruptedException e){
                return;
            }
        }
    }

    private Figurine[] generateFigurines(Color color){
        Random rng = new Random();
        Figurine[] temp = new Figurine[4];

        for(int i = 0; i < 4; i++){
            switch(rng.nextInt(3)){
                case 0:
                    temp[i] = new RegularFigurine(game, color, "Figura " + (Figurine.figurineCounter + 1) + " - obična");
                    break;
                case 1:
                    temp[i] = new FlyingFigurine(game, color, "Figura " + (Figurine.figurineCounter + 1) + " - leteća");
                    break;
                case 2:
                    temp[i] = new FastFigurine(game, color, "Figura " + (Figurine.figurineCounter + 1) + " - super brza");
                    break;
            }
        }

        return temp;
    }

    public String getPlayerName(){ return name; }

    public javafx.scene.paint.Color getPlayerColor(){ return color.getColor(); }

    public Figurine[] getFigurines(){ return figurines; }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    @Override
    public void interrupt() {
        super.interrupt();
        for(Figurine f : figurines){
            if(f.isStarted())
                f.interrupt();
        }
    }
}
