package project.model;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class Field extends StackPane{
    private final int ordinalNumber;
    private int diamonds;
    private Hole hole;
    private Figurine figurine;
    private GhostFigurine ghost;


    public Field(int ordinalNumber){
        this.ordinalNumber = ordinalNumber;
        Label label = new Label(String.valueOf(ordinalNumber));
        StackPane.setAlignment(label, Pos.CENTER);
        this.getChildren().add(label);
        setMinSize(50, 50);
        setStyle("-fx-border-color:black; -fx-border-width: 0.3; -fx-border-style: solid;");
    }

    public int getOrdinalNumber(){ return ordinalNumber; }

    public synchronized void setFigurine(Figurine figurine){
        this.figurine = figurine;
        getChildren().add(figurine);
    }

    public Figurine getFigurine(){ return figurine; }

    public void removeFigurine(){
        figurine = null;
    }

    public void setHole(Hole hole){ this.hole = hole; }

    public void removeHole(){ hole = null; }

    public boolean isHole(){ return hole != null; }

    public boolean isOccupied(){ return figurine != null; }
}
