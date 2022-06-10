package project;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import project.model.*;

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
    private List<Integer> indexes;

    public Game(Controller controller){
        this.controller = controller;
        cardStack = controller.getCardStack();
        cardDeck = generateCardDeck();
        indexes = scrambleIndexes();
        setDaemon(true);
    }

    public void run(){
        timer = new Timer(this);

        //HBox playersHBox = controller.getPlayersHBox();
        /*
            Player player = players[indexes.get(i)];
            Label playerLabel = (Label)playersHBox.getChildren().get(indexes.get(i));
            Platform.runLater(() -> playerLabel.setUnderline(true));

            player.start();
            player.setStarted(true);

            Platform.runLater(() -> playerLabel.setUnderline(false));*/

        for(Player p : controller.getPlayers())
            p.start();

        for(Player p : controller.getPlayers()){
            try{
                p.join();
            }catch (InterruptedException e){
                break;
            }
        }

        //kraj
        controller.incrementGameCount();
        controller.resetGame();
    }

    public void stopGame(){
        //stopirati sve aktivne thread-ove
        for(Player p : controller.getPlayers()){
            if(p.isStarted())
                p.interrupt();
        }
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