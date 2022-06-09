package project;

import javafx.application.Platform;
import static project.Controller.*;

public class Timer implements Runnable{
    private Thread thread;
    private Game game;

    public Timer(Game game){
        this.game = game;
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    public void run(){
        int i = 0;
        while(true){
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                return;
            }
            i++;
            game.checkPause();
            int finalI = i;
            Platform.runLater(() -> game.getController().gameTimeLabel.setText(gameDurationText + getTime(finalI)));

            if(Thread.interrupted())
                return;
        }
    }

    private String getTime(int i){
        int mod = i % 60;
        return i / 60 + ":" + (mod < 10 ? "0" + mod : mod);
    }

    public void stopTimer(){
        thread.interrupt();
    }
}
