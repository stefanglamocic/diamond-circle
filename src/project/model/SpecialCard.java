package project.model;

import project.Controller;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SpecialCard extends Card{
    public SpecialCard(){
        setImage(Images.specialCard);
    }

    public int effect(Matrix matrix){
        super.effect(matrix);
        List<Field> traversalRoute = matrix.getTraversalRoute();
        Random rng = new Random();

        try{
            Thread.sleep(350);
        }catch (InterruptedException e){
            //logger
        }

        ThreadLocalRandom.current()
                .ints(0, traversalRoute.size())
                .distinct()
                .limit(Controller.numOfHoles)
                .forEach(i -> matrix.setHole(
                        traversalRoute.get(i),
                        new Hole()
                ));

        return 0;
    }
}
