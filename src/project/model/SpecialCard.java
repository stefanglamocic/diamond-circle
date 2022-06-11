package project.model;

import javafx.application.Platform;
import project.Controller;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class SpecialCard extends Card{
    public SpecialCard(){
        setImage(Images.specialCard);
    }

    public synchronized int effect(Matrix matrix) throws InterruptedException{
        List<Field> traversalRoute = matrix.getTraversalRoute();
        List<Integer> indexes =
        ThreadLocalRandom.current()
                .ints(0, traversalRoute.size())
                .distinct()
                .limit(Controller.numOfHoles)
                .boxed()
                .collect(Collectors.toList());

        for(Integer i : indexes)
            matrix.setHole(traversalRoute.get(i), new Hole());

        Thread.sleep(1000);

        for(Integer i : indexes){
            Field field = matrix.getTraversalRoute().get(i);
            if(field.isOccupied()){
                if(!field.getFigurine().isFlying())
                    matrix.removeFigurine(field);
            }
        }
        return 0;
    }
}
