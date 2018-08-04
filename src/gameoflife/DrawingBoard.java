package gameoflife;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class DrawingBoard extends JPanel {

    private GameOfLifeBoard personalBoard;
    private int CELL_SIZE;
    private int NO_CELLS_ACROSS;

    public DrawingBoard(GameOfLifeBoard personalBoard, int CELL_SIZE) {
        super.setBackground(Color.WHITE);
        this.personalBoard = personalBoard;
        this.CELL_SIZE = CELL_SIZE;
        this.NO_CELLS_ACROSS = personalBoard.getNO_CELLS_ACROSS();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Test drawing of the personalBoard.
        for (int i = 0; i < NO_CELLS_ACROSS; i++) {
            for (int j = 0; j < NO_CELLS_ACROSS; j++) {
                if (personalBoard.getBoard()[i][j] == true) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

}
