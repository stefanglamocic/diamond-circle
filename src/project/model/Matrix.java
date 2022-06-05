package project.model;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class Matrix {
    private int dimension;
    private Field[][] fields;
    private GridPane gridPane;

    public Matrix(GridPane gridPane, int dimension){
        this.gridPane = gridPane;
        this.dimension = dimension;
        fields = new Field[dimension][dimension];

        gridPane.getChildren().clear();

        for(int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++){
                fields[i][j] = new Field(getFieldOrdinalNumber(i, j));
                GridPane.setVgrow(fields[i][j], Priority.ALWAYS);
                GridPane.setHgrow(fields[i][j], Priority.ALWAYS);
                gridPane.add(fields[i][j], j, i);
            }
        }

        gridPane.setMaxSize(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    private int getFieldOrdinalNumber(int i, int j){
        return dimension * i + j + 1;
    }

    public synchronized void setFigurine(int i, int j, Figurine figurine){
        Platform.runLater(() -> {
            figurine.setFitHeight(fields[i][j].getHeight() - 30);
            fields[i][j].getChildren().add(figurine);
        });
    }
}
