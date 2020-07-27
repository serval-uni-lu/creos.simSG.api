package duc.sg.java.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MatrixDouble {
    private double[] data;

    private int numRows, numCols;

    public MatrixDouble() {
        data = new double[0];
    }

    public MatrixDouble(int numRows, int numCols) {
        this.numCols = numCols;
        this.numRows = numRows;
        data = new double[numRows*numCols];
    }

    public void set(int row, int col, double value) {
        if(row >= getNumRows()) {
            addLines(row - getNumRows() + 1);
        }

        if(col >= getNumCols()) {
            addColumns(col - getNumCols() + 1);
        }

        data[ row * numCols + col ] = value;
    }

    public double get(int row, int col) {
        return data[row * numCols + col];
    }

    public void addLine() {
       addLines(1);
    }

    public void addLines(int nbNewLine) {
        numRows+=nbNewLine;
        double[] newData = new double[numRows*numCols];
        System.arraycopy(data, 0, newData,0, data.length);
        data = newData;
    }

    public void addColumn() {
        addColumns(1);
    }

    public void addColumns(int nbColToAdd) {
        var oldNumCols = numCols;
        numCols+=nbColToAdd;
        double[] newData = new double[numCols*numRows];

        for (int row = 0; row < numRows; row++) {
            System.arraycopy(data,row*oldNumCols, newData, row*numCols, oldNumCols);
        }

        data = newData;
    }

    public double[] getData() {
        return data;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    @Override
    public String toString() {
        var stream = new ByteArrayOutputStream();
        var printStream = new PrintStream(stream);

        for (var row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                printStream.printf( "%6.3f", get(row, col));
            }
            printStream.println();
        }

        return stream.toString();
    }
}
