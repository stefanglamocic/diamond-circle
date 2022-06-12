package project;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import project.model.Figurine;
import project.model.Matrix;

public class TraversalRoute {
    @FXML
    private GridPane gridPane;

    private final int matrixDimension;
    private final Figurine figurine;

    public TraversalRoute(Figurine figurine, int matrixDimension){
        this.figurine = figurine;
        this.matrixDimension = matrixDimension;
    }

    public void initialize(){
        Matrix matrix = new Matrix(gridPane, matrixDimension);
        matrix.paintCurrentTraversal(figurine.getCurrentIndex());
    }
}
