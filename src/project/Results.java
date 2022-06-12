package project;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import project.model.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Results {
    @FXML
    private VBox vbox;

    public void initialize(){
        File[] files = Main.resultsFolder.listFiles();
        if(files != null){
            for(File f : files){
                try(BufferedReader reader = Files.newBufferedReader(f.toPath())){
                    String text = reader.lines()
                                        .collect(Collectors.joining(System.lineSeparator()));

                    TitledPane pane = new TitledPane();
                    pane.setExpanded(false);
                    pane.setText(f.getName());
                    TextArea textArea = new TextArea(text);
                    textArea.setEditable(false);
                    textArea.setWrapText(true);
                    textArea.setPrefWidth(800);
                    pane.setContent(textArea);
                    pane.setPrefWidth(800);
                    pane.setMinHeight(400);

                    vbox.getChildren().add(pane);
                }catch (IOException e){
                    Main.logger.log(Level.SEVERE, "Can't display results!", e);
                }
            }
        }
    }
}
