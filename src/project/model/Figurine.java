package project.model;

import javafx.scene.image.ImageView;

public abstract class Figurine extends ImageView implements Runnable{
    private final String name;
    private final Color color;
    private int diamonds;
    private Thread thread;

    public Figurine(Color color, String name){
        this.color = color;
        this.name = name;
        setPreserveRatio(true);
        setSmooth(true);
        setCache(true);
        thread = new Thread(this);
        thread.setDaemon(true);
    }

    public String getName(){ return name; }

    public javafx.scene.paint.Color getFigurineColor(){ return color.getColor(); }

    public Figurine(){
        color = Color.INVISIBLE;
        name = "Duh figura";
    }

    public void run(){}

    public void start(){
        thread.start();
    }
}
