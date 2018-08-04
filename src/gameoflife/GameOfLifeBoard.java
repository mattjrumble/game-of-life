package gameoflife;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Point;

public class GameOfLifeBoard {

    private final int NO_CELLS_ACROSS;
    private boolean[][] cells;

    public GameOfLifeBoard(int NO_CELLS_ACROSS) {
        this.NO_CELLS_ACROSS = NO_CELLS_ACROSS;
        cells = new boolean[NO_CELLS_ACROSS][NO_CELLS_ACROSS];
        resetCells();
    }

    public boolean[][] getBoard() {
        return cells;
    }

    public void playTurn() {
        //randomiseCells(0.3);
        boolean[][] newCells = new boolean[NO_CELLS_ACROSS][NO_CELLS_ACROSS];
        // Go through each cell and calculate whether it lives or dies.
        for (int i = 0; i < NO_CELLS_ACROSS; i++) {
            for (int j = 0; j < NO_CELLS_ACROSS; j++) {
                int neighbours = getNumberOfLivingNeighbours(i, j);
                if (isAlive(i, j)) {
                    if ((neighbours < 2) || (neighbours > 3)) {
                        newCells[i][j] = false;
                    } else {
                        newCells[i][j] = true;
                    }
                } else {
                    if (neighbours == 3) {
                        newCells[i][j] = true;
                    } else {
                        newCells[i][j] = false;
                    }
                }
            }
        }
        cells = newCells;
    }

    public void turnToLiving(int x, int y) {
        cells[x][y] = true;
    }

    public void turnToDead(int x, int y) {
        cells[x][y] = false;
    }

    public boolean isAlive(int x, int y) {
        return cells[x][y];
    }

    public int getNumberOfLivingNeighbours(int x, int y) {
        int count = 0;
        ArrayList<Point> neighbours = new ArrayList<>();

        neighbours.add(new Point(x - 1, y - 1));
        neighbours.add(new Point(x - 1, y));
        neighbours.add(new Point(x - 1, y + 1));
        neighbours.add(new Point(x, y - 1));
        neighbours.add(new Point(x, y + 1));
        neighbours.add(new Point(x + 1, y - 1));
        neighbours.add(new Point(x + 1, y));
        neighbours.add(new Point(x + 1, y + 1));

        for (Point neighbour : neighbours) {
            int correctedX = neighbour.x;
            int correctedY = neighbour.y;
            if (correctedX < 0) {
                correctedX = NO_CELLS_ACROSS - 1;
            }
            if (correctedX > NO_CELLS_ACROSS - 1) {
                correctedX = 0;
            }
            if (correctedY < 0) {
                correctedY = NO_CELLS_ACROSS - 1;
            }
            if (correctedY > NO_CELLS_ACROSS - 1) {
                correctedY = 0;
            }
            if (isAlive(correctedX, correctedY)) {
                count++;
            }
        }

        return count;
    }

    public void resetCells() {
        for (int i = 0; i < NO_CELLS_ACROSS; i++) {
            for (int j = 0; j < NO_CELLS_ACROSS; j++) {
                cells[i][j] = false;
            }
        }
    }

    public void randomiseCells(double probabilityAlive) {
        for (int i = 0; i < NO_CELLS_ACROSS; i++) {
            for (int j = 0; j < NO_CELLS_ACROSS; j++) {
                if (new Random().nextDouble() < probabilityAlive) {
                    cells[i][j] = true;
                } else {
                    cells[i][j] = false;
                }
            }
        }
    }

    public int getNO_CELLS_ACROSS() {
        return NO_CELLS_ACROSS;
    }

}
