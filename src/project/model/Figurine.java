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
    private boolean started, end;

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
        Matrix matrix = game.getController().getMatrix();
        List<Field> traversalRoute = matrix.getTraversalRoute();
        int currentIndex = 0;
        while(!end){
            //wait for turn...
            game.checkPause();
            if(currentIndex == 0) {
                while(traversalRoute.get(currentIndex).getFigurine() != null)
                    currentIndex++;
                matrix.setFigurine(traversalRoute.get(currentIndex), this);
            }
            Card drawnCard = game.drawACard();
            int hops;
            try {
                hops = drawnCard.effect(matrix);
            }catch (InterruptedException e){
                end = true;
                return;
            }

            //pomjeranje za n polja
            if(!end && hops > 0){
                int previousIndex = currentIndex;
                traversalRoute.get(currentIndex).removeFigurine();
                currentIndex += hops;
                while (traversalRoute.get(currentIndex).getFigurine() != null)
                    currentIndex++;
                if(currentIndex > traversalRoute.size() - 1)
                    currentIndex = traversalRoute.size() - 1;

                int finalCurrentIndex = currentIndex;
                Platform.runLater(() -> game.getController().getTurnDescription().setText(name + " " +
                        "prelazi " + (finalCurrentIndex - previousIndex) + " polja - pomjera se sa pozicije" +
                        " " + traversalRoute.get(previousIndex).getOrdinalNumber() + " na " +
                        traversalRoute.get(finalCurrentIndex).getOrdinalNumber()));

                game.checkPause();

                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    end = true;
                    return;
                }

                matrix.setFigurine(traversalRoute.get(currentIndex), this);
                if(currentIndex == traversalRoute.size() - 1){
                    //kraj
                    try{
                        Thread.sleep(300);
                        matrix.removeFigurine(traversalRoute.get(currentIndex));
                    }catch (InterruptedException e){
                        end = true;
                        return;
                    }
                }
            }

            //notify...
        }
    }

    public void start(){
        thread.start();
    }

    public void join() throws InterruptedException{
        thread.join();
    }

    public void interrupt(){
        thread.interrupt();
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }
}
