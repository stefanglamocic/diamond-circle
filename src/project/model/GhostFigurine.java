package project.model;

import javafx.application.Platform;
import project.Game;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GhostFigurine extends Figurine{
    public GhostFigurine(Game game){
        super(game);
    }

    public void run(){
        Matrix matrix = game.getController().getMatrix();
        List<Field> traversalRoute = matrix.getTraversalRoute();
        int matrixDimension = matrix.getDimension();
        Random rng = new Random();
        while(true){
            game.checkPause();
            int numOfDiamonds = rng.nextInt(matrixDimension - 1) + 2;
            for(int i = 0; i < numOfDiamonds; i++){
                int index = rng.nextInt(traversalRoute.size());
                Platform.runLater(() -> {
                    if(traversalRoute.get(index).getDiamonds() < matrixDimension)
                        traversalRoute.get(index).incrementDiamonds();
                });
            }
            try{
                Thread.sleep(5000);
            }catch (InterruptedException e){
                return;
            }

            if(Thread.interrupted())
                return;
        }
    }

    public String getType(){
        return "duh";
    }
}
