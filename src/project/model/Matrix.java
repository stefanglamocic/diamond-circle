package project.model;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Matrix {
    private int dimension;
    private Field[][] fields;
    private GridPane gridPane;
    private List<Field> traversalRoute;

    public Matrix(GridPane gridPane, int dimension){
        this.gridPane = gridPane;
        this.dimension = dimension;
        fields = new Field[dimension][dimension];
        traversalRoute = new ArrayList<>();

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
        createTraversalRoute();
    }

    private int getFieldOrdinalNumber(int i, int j){
        return dimension * i + j + 1;
    }

    public synchronized void setFigurine(int i, int j, Figurine figurine){
        fields[i][j].setFigurine(figurine);
        Platform.runLater(() -> {
            figurine.setFitHeight(fields[i][j].getHeight() - 30);
            figurine.setFitWidth(fields[i][j].getWidth() - 20);
            fields[i][j].getChildren().add(figurine);
        });
    }

    public synchronized void setHole(int i, int j, Hole hole){
        fields[i][j].setHole(hole);
        Platform.runLater(() -> {
            hole.setFitHeight(fields[i][j].getHeight() - 20);
            hole.setFitWidth(fields[i][j].getWidth() - 20);
            fields[i][j].getChildren().add(1, hole);
        });
    }

    public synchronized void removeHole(int i, int j){
        if(fields[i][j].isHole()){
            fields[i][j].removeHole();
            Platform.runLater(() -> {
                fields[i][j].getChildren().remove(1);
            });
        }
    }

    private void spiralDiamondView(Field[][] matrix, int x, int y, int m, int n, int k){
        int midCol = y + ((n - y) / 2);
        int midRow = midCol;

        for (int i = midCol, j = 0; i < n && k > 0; ++i, k--, j++){
            //System.out.print(matrix[x + j][i].getOrdinalNumber() + " ");
            traversalRoute.add(matrix[x + j][i]);
        }

        for (int i = n, j = 0; i >= midCol && k > 0; --i, k--, j++) {
            //System.out.print(matrix[(midRow) + j][i].getOrdinalNumber() + " ");
            traversalRoute.add(matrix[(midRow) + j][i]);
        }

        for (int i = midCol - 1, j = 1; i >= y && k > 0; --i, k--, j++) {
            //System.out.print(matrix[(n) - j][i].getOrdinalNumber() + " ");
            traversalRoute.add(matrix[(n) - j][i]);
        }

        for (int i = y + 1, j = 1; i < midCol && k > 0; ++i, k--, j++) {
            //System.out.print(matrix[(midRow) - j][i].getOrdinalNumber() + " ");
            traversalRoute.add(matrix[(midRow) - j][i]);
        }

        if (x + 1 <= m - 1 && k > 0)
            spiralDiamondView(matrix, x + 1, y + 1, m - 1, n - 1, k);
    }

    private void createTraversalRoute(){
        if(dimension % 2 != 0)
            spiralDiamondView(fields, 0, 0, dimension - 1, dimension - 1, (dimension * dimension) - ((dimension + 1) / 2) * 4);
        else
            spiralDiamondView(fields, 0, 0, dimension - 2, dimension - 2, (dimension * dimension) - ((dimension + 1) / 2) * 4);
    }

    public List<Field> getTraversalRoute(){ return traversalRoute; }
}
