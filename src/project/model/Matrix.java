package project.model;

public class Matrix {
    private int dimension;
    private Field[][] fields;

    public Matrix(int dimension){
        this.dimension = dimension;
        fields = new Field[dimension][dimension];
    }
}
