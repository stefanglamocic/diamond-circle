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

        Platform.runLater(() -> {
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
        });

        createTraversalRoute();
    }

    private int getFieldOrdinalNumber(int i, int j){
        return dimension * i + j + 1;
    }

    public void setFigurine(Field field, Figurine figurine){
        Platform.runLater(() -> {
            figurine.setFitHeight(field.getHeight() - 34);
            figurine.setFitWidth(field.getWidth() - 19);
            field.setFigurine(figurine);
        });
    }

    public void removeFigurine(Field field){
        Platform.runLater(() -> {
            field.getFigurine().setEnd(true);
            field.getChildren().remove(field.getFigurine());
            field.removeFigurine();
        });
    }

    public void setHole(Field field, Hole hole){
        field.setHole(hole);
        Platform.runLater(() -> {
            hole.setFitHeight(field.getHeight() - 20);
            hole.setFitWidth(field.getWidth() - 20);
            field.getChildren().add(1, hole);
        });
    }

    private void removeHole(Field field){
        if(field.isHole()){
            field.removeHole();
            Platform.runLater(() -> {
                field.getChildren().remove(1);
            });
        }
    }

    public void removeHoles(){
        for(Field f : traversalRoute)
            removeHole(f);
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
        Platform.runLater(() -> {
            if(dimension % 2 != 0)
                spiralDiamondView(fields, 0, 0, dimension - 1, dimension - 1, (dimension * dimension) - ((dimension + 1) / 2) * 4);
            else
                spiralDiamondView(fields, 0, 0, dimension - 2, dimension - 2, (dimension * dimension) - ((dimension + 1) / 2) * 4);
        });

    }

    public synchronized List<Field> getTraversalRoute(){ return traversalRoute; }

    public synchronized int getDimension(){
        return dimension;
    }

    public void paintCurrentTraversal(int currentIndex){
        Platform.runLater(() -> {
            if(currentIndex > -1){
                for(int i = 0; i < currentIndex; i++)
                    traversalRoute.get(i).setStyle("-fx-background-color: #fff3cd");
            }
        });
    }
}
