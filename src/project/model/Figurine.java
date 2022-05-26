package project.model;

import javafx.scene.image.ImageView;

public abstract class Figurine extends ImageView implements Runnable{
    private Color color;
    private int diamonds;
    private Thread thread;

    public Figurine(Color color){
        this.color = color;
        thread = new Thread(this);
        thread.setDaemon(true);
    }

    public Figurine(){
        color = Color.INVISIBLE;
    }

    public void run(){}

    public void start(){
        thread.start();
    }
}
