package project;

import javafx.application.Platform;
import static project.Controller.*;

public class Timer implements Runnable{
    private Thread thread;
    private Game game;
    private int i;

    public Timer(Game game){
        this.game = game;
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    public void run(){
        i = 0;
        while(true){
            game.checkPause();
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                return;
            }
            i++;
            game.checkPause();
            Platform.runLater(() -> game.getController().gameTimeLabel.setText(gameDurationText + getTime()));

            if(Thread.interrupted())
                return;
        }
    }

    public String getTime(){
        int mod = i % 60;
        return i / 60 + ":" + (mod < 10 ? "0" + mod : mod);
    }

    public void stopTimer(){
        thread.interrupt();
    }
}
