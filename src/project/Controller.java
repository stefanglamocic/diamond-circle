package project;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.util.Callback;
import project.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Controller {
    @FXML
    private MenuItem startStopItem;
    @FXML
    private GridPane gridPane;
    @FXML
    private Label gameCountLabel;
    @FXML
    public Label gameTimeLabel;
    @FXML
    private ListView<Figurine> figurineListView;
    @FXML
    private ToggleGroup toggleGroupDimensions;
    @FXML
    private ToggleGroup toggleGroupPlayers;
    @FXML
    private HBox playersHBox;
    @FXML
    private StackPane cardStack;
    @FXML
    private Label turnDescription;

    public static boolean gameStarted;
    private Matrix matrix;
    private static int gameCount;
    private static int playersNumber = 2;
    private static int matrixDimension = 7;
    private Player[] players = new Player[playersNumber];
    private ObservableList<Figurine> figurines;
    private Game game;

    public static final String gameDurationText = "Vrijeme trajanja igre: ";
    public static final String gameCountText = "Trenutni broj odigranih igara: ";
    public static final int numOfHoles;

    static{
        int tempHoles = 5;
        Properties properties = new Properties();
        try(InputStream is = new FileInputStream("src" + File.separator + "data.properties")){
            properties.load(is);
            tempHoles = Integer.parseInt(properties.getProperty("numOfHoles"));
        }catch (IOException e){
            //logger
        }finally {
            numOfHoles = tempHoles;
        }
    }

    public void initialize(){
        figurineListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //setCardDeck();
        resetGame();

        figurineListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Figurine> call(ListView<Figurine> figurineListView) {
                return new ListCell<>(){
                    @Override
                    protected void updateItem(Figurine item, boolean empty){
                        super.updateItem(item, empty);
                        if(empty){
                            setText(null);
                            setGraphic(null);
                        }
                        else{
                            setText(item.getName());
                            setTextFill(item.getFigurineColor());
                            ImageView imageView = new ImageView(item.getImage());
                            imageView.setFitWidth(50);
                            imageView.setFitHeight(42);
                            setGraphic(imageView);
                        }
                    }
                };
            }
        });
    }

    @FXML
    public void startStopAction(){
        if(startStopItem.getText().equals("Start")){
            startStopItem.setText("Stop");
            if(!gameStarted) {
                gameStarted = true;
                game.start();
            }
            game.setPause(false);
        }
        else{
            startStopItem.setText("Start");
            game.setPause(true);
        }
    }

    @FXML
    public void dimensionSelected(){
        RadioMenuItem item = (RadioMenuItem) toggleGroupDimensions.getSelectedToggle();
        String value = item.getText();

        switch (value){
            case "7x7": matrixDimension = 7; break;
            case "8x8": matrixDimension = 8; break;
            case "9x9": matrixDimension = 9; break;
            case "10x10": matrixDimension = 10; break;
        }

        resetGame();
    }

    private Color[] colorRandomizer(int playersNumber){
        Color[] colors = new Color[playersNumber];
        List<Integer> randomNums =
        ThreadLocalRandom.current()
                .ints(0, playersNumber)
                .distinct()
                .limit(playersNumber)
                .boxed()
                .collect(Collectors.toList());

        for(int i = 0; i < playersNumber; i++){
            switch (randomNums.get(i)){
                case 0: colors[i] = Color.RED; break;
                case 1: colors[i] = Color.BLUE; break;
                case 2: colors[i] = Color.YELLOW; break;
                case 3: colors[i] = Color.GREEN; break;
            }
        }

        return colors;
    }

    @FXML
    public void playersSelected(){
        RadioMenuItem item = (RadioMenuItem) toggleGroupPlayers.getSelectedToggle();
        String value = item.getText();

        switch (value){
            case "2 players": playersNumber = 2; break;
            case "3 players": playersNumber = 3; break;
            case "4 players": playersNumber = 4; break;
        }

        resetGame();
    }

    private void generatePlayers(){
        Figurine.figurineCounter = 0;
        figurines = FXCollections.observableArrayList();
        Platform.runLater(() -> playersHBox.getChildren().clear());

        players = new Player[playersNumber];
        Color[] colors = colorRandomizer(playersNumber);
        for(int i = 0; i < playersNumber; i++){
            players[i] = new Player(game, "Igrač" + (i + 1), colors[i]);
            figurines.addAll(players[i].getFigurines());
            Label label = new Label(players[i].getPlayerName());
            label.setFont(new Font("Arial Bold", 17));
            label.setTextFill(players[i].getPlayerColor());

            Platform.runLater(() -> playersHBox.getChildren().add(label));
        }
        Platform.runLater(() -> figurineListView.setItems(figurines));
    }

    @FXML
    public void exitApplication(){
        Platform.exit();
    }

    private void setCardDeck(){
        cardStack.getChildren().clear();
        ImageView deck = new ImageView(Images.backCard);
        deck.setPreserveRatio(true);
        deck.setSmooth(true);
        deck.setCache(true);
        deck.setFitWidth(290);
        cardStack.getChildren().add(deck);
    }

    public StackPane getCardStack(){ return cardStack; }

    public synchronized void resetGame(){
        gameStarted = false;
        startStopItem.setText("Start");
        Platform.runLater(() -> {
            gameTimeLabel.setText(gameDurationText + "0:00");
            gameCountLabel.setText(gameCountText + gameCount);
            turnDescription.setText("");
            setCardDeck();
        });

        matrix = new Matrix(gridPane, matrixDimension);
        if(game != null)
            game.stopGame();
        game = new Game(this);
        generatePlayers();
    }

    @FXML
    public void reset(){
        resetGame();
    }

    public Player[] getPlayers(){ return players; }

    public HBox getPlayersHBox(){ return playersHBox; }

    public synchronized void incrementGameCount(){
        gameCount++;
    }

    public Matrix getMatrix(){
        return matrix;
    }

    public Label getTurnDescription(){ return turnDescription; }
}
