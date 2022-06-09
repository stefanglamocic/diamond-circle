package project.model;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import project.Game;

import java.util.List;

public abstract class Figurine extends ImageView implements Runnable{
    public static int figurineCounter;

    private final String name;
    private final Color color;
    private Game game;
    private int diamonds;
    private Thread thread;

    public Figurine(Game game, Color color, String name){
        this.game = game;
        this.color = color;
        this.name = name;
        figurineCounter++;
        setPreserveRatio(true);
        setSmooth(true);
        setCache(true);
        thread = new Thread(this);
        thread.setDaemon(true);
    }

    public String getName(){ return name; }

    public javafx.scene.paint.Color getFigurineColor(){ return color.getColor(); }

    public Figurine(Game game){
        this.game = game;
        color = Color.INVISIBLE;
        name = "Duh figura";
    }

    public void run(){
        /*List<Field> traversalRoute = matrix.getTraversalRoute();
        for(int i = 0; i < traversalRoute.size(); i++){
            if(i > 0)
                traversalRoute.get(i - 1).removeFigurine();
            matrix.setFigurine(traversalRoute.get(i), this);
            try{
                Thread.sleep(500);
            }catch (InterruptedException e) {

            }
        }
        traversalRoute.get(traversalRoute.size() - 1).removeFigurine();
        Platform.runLater(() -> traversalRoute.get(traversalRoute.size() - 1).getChildren().remove(1));*/
    }

    public void start(){
        thread.start();
    }
}
