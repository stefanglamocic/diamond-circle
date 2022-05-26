package project;

import javafx.application.Platform;
import static project.Controller.*;

public class Timer implements Runnable{
    private Thread thread;
    private Controller controller;

    public Timer(Controller controller){
        this.controller = controller;
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
            int finalI = i;
            Platform.runLater(() -> controller.gameTimeLabel.setText(gameDurationText + finalI + "s"));

            if(Thread.interrupted())
                return;
        }
    }

    public void stopTimer(){
        thread.interrupt();
    }
}
