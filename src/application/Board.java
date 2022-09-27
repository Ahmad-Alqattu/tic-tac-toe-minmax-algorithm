package application;

import java.util.ArrayList;

public class Board implements Cloneable {

    private Cell[] cells;
    private int cellsTaken = 0;

    public Cell[] getCells() {
        return cells;
    }

    public Cell getCell(int pos) {
        return cells[pos];
    }

    public Board() {
        cells = new Cell[]{ new Cell(0), new Cell(1), new Cell(2), new Cell(3),
            new Cell(4), new Cell(5), new Cell(6), new Cell(7), new Cell(8) };
    }

    //cloning
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Board copy = (Board)super.clone();
        Cell[] cellsCopy = new Cell[9];
        for (int i = 0; i < 9; i++) {
            cellsCopy[i] = new Cell(cells[i]);
        }
        copy.cells = cellsCopy;
        return copy;
    }

    public boolean isFull() {
        return cellsTaken == 9;
    }

    public void takeCell() {
        cellsTaken++;
    }
}
