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
    private Player player;
    private int currentIndex;

    public Figurine(Game game, Player player, Color color, String name){
        this.game = game;
        this.player = player;
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
        started = true;
        Matrix matrix = game.getController().getMatrix();
        List<Field> traversalRoute = matrix.getTraversalRoute();
        currentIndex = 0;
        while(!end){
            game.startTurn(player);
            game.checkPause();
            if(currentIndex == 0) {
                while(traversalRoute.get(currentIndex).isOccupied())
                    currentIndex++;
                matrix.setFigurine(traversalRoute.get(currentIndex), this);
            }

            int hops;
            try {
                Card card = game.drawACard();
                hops = card.effect(matrix);
            }catch (InterruptedException e){
                end = true;
                return;
            }

            if(hops == 0){
                Platform.runLater(() -> {
                    game.getController().getTurnDescription().setText(player.getPlayerName() + " izvlači specijalnu kartu.");
                });
                try {
                    Thread.sleep(600);
                }catch (InterruptedException e){
                    end = true;
                    return;
                }
                matrix.removeHoles();
            }

            //pomjeranje za n polja
            if(!end && hops > 0){
                int previousIndex = currentIndex;
                traversalRoute.get(currentIndex).removeFigurine();
                if(isSuperFast())
                    hops *= 2;
                currentIndex += hops;
                if(currentIndex > traversalRoute.size() - 1)
                    currentIndex = traversalRoute.size() - 1;
                while (currentIndex < traversalRoute.size() - 1 && traversalRoute.get(currentIndex).getFigurine() != null)
                    currentIndex++;

                Platform.runLater(() -> game.getController().getTurnDescription().setText("Na potezu je "
                        + player.getPlayerName() + ", " + name + " " +
                        "prelazi " + (currentIndex - previousIndex) + ". polja - pomjera se sa pozicije" +
                        " " + traversalRoute.get(previousIndex).getOrdinalNumber() + ". na " +
                        traversalRoute.get(currentIndex).getOrdinalNumber() + "."));

                game.checkPause();

                for(int i = previousIndex + 1; i <= currentIndex; i++){
                    int finalI = i;
                    Platform.runLater(() -> traversalRoute.get(finalI - 1).removeFigurine());
                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        end = true;
                        return;
                    }

                    game.checkPause();
                    matrix.setFigurine(traversalRoute.get(i), this);
                }

                if(currentIndex == traversalRoute.size() - 1){
                    //kraj
                    try{
                        Thread.sleep(300);
                        matrix.removeFigurine(traversalRoute.get(currentIndex));
                        end = true;
                    }catch (InterruptedException e){
                        end = true;
                        return;
                    }
                }
            }

            game.endTurn();
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

    public boolean isFlying(){
        return false;
    }

    public boolean isSuperFast(){ return false; }
}
