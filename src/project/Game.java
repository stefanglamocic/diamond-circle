package project;

import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import project.model.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Game extends Thread{
    private final Controller controller;
    private boolean pause;
    private final List<Card> cardDeck;
    private final StackPane cardStack;
    private Timer timer;
    private GhostFigurine ghost;
    private final List<Integer> indexes;
    private int turnIndex = 0;

    public Game(Controller controller){
        this.controller = controller;
        cardStack = controller.getCardStack();
        cardDeck = generateCardDeck();
        indexes = scrambleIndexes();
        setDaemon(true);
    }

    public void run(){

        timer = new Timer(this);
        controller.getPlayers()[indexes.get(0)].setTurn(true);
        ghost = new GhostFigurine(this);
        ghost.start();
        for(Player p : controller.getPlayers()) {
            p.start();
            p.setStarted(true);
        }

        for(Player p : controller.getPlayers()){
            try{
                p.join();
            }catch (InterruptedException e){
                return;
            }
        }

        if(Thread.interrupted()) {
            controller.resetGame();
            return;
        }

        writeResults();
        controller.incrementGameCount();
        timer.stopTimer();
    }

    public void stopGame(){
        for(Player p : controller.getPlayers()){
            if(p.isStarted())
                p.interrupt();
        }
        if(timer != null)
            timer.stopTimer();
        if(ghost != null)
            ghost.interrupt();
        this.interrupt();
    }

    public synchronized void checkPause(){
        while(pause){
            try{
                wait();
            }catch (InterruptedException e){
                Main.logger.log(Level.SEVERE, "wait() interrupted", e);
            }
        }
    }

    public synchronized void setPause(boolean pause) {
        this.pause = pause;
        notifyAll();
    }

    public synchronized Controller getController(){ return controller; }

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

    public synchronized Card drawACard() throws InterruptedException{
        Card drawnCard = cardDeck.remove(0);
        cardDeck.add(drawnCard);
        if(cardStack.getChildren().size() > 1)
            Platform.runLater(() -> cardStack.getChildren().remove(1));
        Thread.sleep(600);
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

    public synchronized void startTurn(Player player){
        while (!player.isTurn()){
            try{
                wait();
            }catch (InterruptedException e){
                Main.logger.log(Level.SEVERE, "wait() interrupted", e);
            }
        }
    }

    public synchronized void endTurn(){
        controller.getPlayers()[indexes.get(turnIndex)].setTurn(false);
        do {
            turnIndex = (turnIndex + 1) % indexes.size();
        }while (controller.getPlayers()[indexes.get(turnIndex)].isEnd());
        controller.getPlayers()[indexes.get(turnIndex)].setTurn(true);
        notifyAll();
    }

    public void writeResults(){
        LocalDateTime time = LocalDateTime.now();
        File result = new File(Main.resultsFolder, time.format(DateTimeFormatter.ofPattern("dd_MM_yyyy_kk_mm_ss")) + ".txt");
        try(FileWriter writer = new FileWriter(result, true)){
            for(Player p : controller.getPlayers()){
                writer.write(p.getPlayerName() + System.lineSeparator());
                for(Figurine f : p.getFigurines()){
                    writer.write("\t" + f.getName() + " (" + f.getType() + ", " + f.getColorName() + ")" +
                            " - pređeni put: " + f.getTraversalSummary() + " stigla do cilja " +
                            (f.isGoalReached() ? "da" : "ne") + System.lineSeparator());
                }
            }
            writer.write("Ukupno vrijeme trajanja igre: " + timer.getTime());
        }catch (IOException e){
            Main.logger.log(Level.SEVERE, "Can't write the details!", e);
        }
    }
}
