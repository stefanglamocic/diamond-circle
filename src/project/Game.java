package project;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import project.model.Card;
import project.model.Player;
import project.model.RegularCard;
import project.model.SpecialCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Game extends Thread{
    private final Controller controller;
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
        List<Integer> indexes = scrambleIndexes();
        Player[] players = controller.getPlayers();
        HBox playersHBox = controller.getPlayersHBox();
        timer = new Timer(this);

        for(int i = 0; i < players.length; i++){
            Label playerLabel = (Label)playersHBox.getChildren().get(indexes.get(i));
            Platform.runLater(() -> playerLabel.setUnderline(true));
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){}

            Platform.runLater(() -> playerLabel.setUnderline(false));
        }

        //pokretanje threadova igraca
            //pokretanje figura

        //kraj
        controller.incrementGameCount();
        controller.resetGame();
    }

    public void stopGame(){
        //stopirati sve aktivne thread-ove
        if(timer != null)
            timer.stopTimer();
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

    private List<Integer> scrambleIndexes(){
        Player[] players = new Player[controller.getPlayers().length];

        return ThreadLocalRandom.current()
                .ints(0, players.length)
                .distinct()
                .limit(players.length)
                .boxed()
                .collect(Collectors.toList());
    }
}
