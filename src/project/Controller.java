package project;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import project.model.*;

import java.util.*;

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

    private Matrix matrix;
    private Timer timer;
    private static int gameCount;
    private static int playersNumber = 2;
    private static int matrixDimension = 7;
    private Player[] players = new Player[playersNumber];
    private ObservableList<Figurine> figurines;


    public static final String gameDurationText = "Vrijeme trajanja igre: ";
    public static final String gameCountText = "Trenutni broj odigranih igara: ";

    public void initialize(){
        figurineListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        generatePlayers();
        matrix = new Matrix(gridPane, matrixDimension);

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
            timer = new Timer(this);
        }
        else{
            startStopItem.setText("Start");
            gameCount++;
            timer.stopTimer();
            gameTimeLabel.setText(gameDurationText + "0:00");
            gameCountLabel.setText(gameCountText + gameCount);
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

        matrix = new Matrix(gridPane, matrixDimension);
    }

    private Object[] colorRandomizer(int playersNumber){
        Random rng = new Random();
        Set<Color> colors = new HashSet<>();

        while(colors.size() < playersNumber){
            int value = rng.nextInt(4000);
            Color color = null;
            if(value < 1000)
                color = Color.RED;
            else if(value > 1000 && value < 2000)
                color = Color.BLUE;
            else if(value > 2000 && value < 3000)
                color = Color.YELLOW;
            else
                color = Color.GREEN;
            colors.add(color);
        }
        return colors.toArray();
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

        generatePlayers();
        matrix = new Matrix(gridPane, matrixDimension);
    }

    private void generatePlayers(){
        Figurine.figurineCounter = 0;
        figurines = FXCollections.observableArrayList();
        playersHBox.getChildren().clear();
        players = new Player[playersNumber];
        Object[] colors = colorRandomizer(playersNumber);
        for(int i = 0; i < playersNumber; i++){
            players[i] = new Player("Igrač" + (i + 1), (Color) colors[i]);
            figurines.addAll(players[i].getFigurines());
            Label label = new Label(players[i].getName());
            label.setFont(new Font("Arial Bold", 17));
            label.setTextFill(players[i].getPlayerColor());
            playersHBox.getChildren().add(label);
        }
        figurineListView.setItems(figurines);
    }

    @FXML
    public void exitApplication(){
        Platform.exit();
    }
}
