package project;

import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import project.Controller;
import project.model.Card;
import project.model.RegularCard;
import project.model.SpecialCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game extends Thread{
    private Controller controller;
    private boolean pause;
    private final List<Card> cardDeck;
    private final StackPane cardStack;
    private Timer timer;

    public Game(Controller controller){
        this.controller = controller;
        cardStack = controller.getCardStack();
        cardDeck = generateCardDeck();
        setDaemon(true);
    }

    public void run(){
        timer = new Timer(this);
    }

    public void stopGame(){
        timer.stopTimer();
    }

    public synchronized boolean isPause() {
        return pause;
    }

    public synchronized void checkPause(){
        while(pause){
            try{
                wait();
            }catch (InterruptedException e){
                //logger
            }
        }
    }

    public synchronized void setPause(boolean pause) {
        this.pause = pause;
        notifyAll();
    }

    public Controller getController(){ return controller; }

    private List<Card> generateCardDeck(){
        List<Card> cardDeck = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            cardDeck.add(new RegularCard(1));
            cardDeck.add(new RegularCard(2));
            cardDeck.add(new RegularCard(3));
            cardDeck.add(new RegularCard(4));
        }

        for(int i = 0; i < 12; i++)
            cardDeck.add(new SpecialCard());

        Collections.shuffle(cardDeck);
        return cardDeck;
    }

    public Card drawACard(){
        Card drawnCard = cardDeck.remove(0);
        cardDeck.add(drawnCard);
        if(cardStack.getChildren().size() > 1)
            Platform.runLater(() -> cardStack.getChildren().remove(1));
        try{
            Thread.sleep(350);
        }catch (InterruptedException e){
            //logger
        }
        Platform.runLater(() -> cardStack.getChildren().add(drawnCard));

        return drawnCard;
    }
}
