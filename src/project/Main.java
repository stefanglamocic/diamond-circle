package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {
    public static final File resultsFolder = new File("results");

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("DiamondCircle");
        primaryStage.setScene(new Scene(root, 1350, 1000));
        primaryStage.show();
    }


    public static void main(String[] args) {
        resultsFolder.mkdir();
        launch(args);
    }
}
