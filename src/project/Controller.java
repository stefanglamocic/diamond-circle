package project;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import project.model.Figurine;

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

    private Timer timer;
    private static int gameCount;
    public static final String gameDurationText = "Vrijeme trajanja igre: ";
    public static final String gameCountText = "Trenutni broj odigranih igara: ";

    public void initialize(){
        figurineListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
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
            gameTimeLabel.setText(gameDurationText + "0s");
            gameCountLabel.setText(gameCountText + gameCount);
        }
    }
}
