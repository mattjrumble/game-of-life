package gameoflife;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.util.Timer;
import javax.swing.SwingUtilities;
import java.awt.MouseInfo;
import java.awt.Point;

public class UserInterface implements Runnable {

    private GameOfLifeBoard personalBoard;
    private DrawingBoard drawingBoard;
    private JFrame frame;
    private Timer playTurnTimer;
    private Timer drawTimer;
    private JButton buttonStart;
    private JButton buttonReset;
    private JButton buttonRandomise;
    private MyMouseListener myMouseListener;
    private boolean makingCellsAlive;
    private boolean makingCellsDead;
    private final int PLAY_STEP = 100;
    private final int DRAW_STEP = 10;
    private final int CELL_SIZE = 8;
    private final int NO_CELLS_ACROSS;
    private final double RANDOM_PROBABILITY = 0.15;
    // Try to make BOARD_WIDTH a multiple of 3 so that the buttons look nice.
    private final int BOARD_WIDTH;
    private final int BUTTON_HEIGHT = 50;
    private final int INSET_TOP = 26;
    private final int INSET_LEFT = 3;
    private final int INSET_RIGHT = 3;
    private final int INSET_BOTTOM = 3;

    public UserInterface(GameOfLifeBoard personalBoard) {
        this.personalBoard = personalBoard;
        this.NO_CELLS_ACROSS = personalBoard.getNO_CELLS_ACROSS();
        this.BOARD_WIDTH = CELL_SIZE * NO_CELLS_ACROSS;
        this.makingCellsAlive = false;
        this.makingCellsDead = false;
        this.myMouseListener = new MyMouseListener(this);
    }

    @Override
    public void run() {
        frame = new JFrame("Game of Life");
        frame.setPreferredSize(new Dimension(INSET_LEFT + INSET_RIGHT + BOARD_WIDTH, INSET_TOP + INSET_BOTTOM + BOARD_WIDTH + BUTTON_HEIGHT));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        createComponents(frame.getContentPane());

        startNewDrawTimer();

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void startNewPlayTurnTimer() {
        playTurnTimer = new Timer();
        playTurnTimer.schedule(new PlayTurnTimerTask(personalBoard, playTurnTimer, PLAY_STEP), PLAY_STEP);
    }

    public void stopPlayTurnTimer() {
        playTurnTimer.cancel();
    }

    public void startNewDrawTimer() {
        drawTimer = new Timer();
        drawTimer.schedule(new DrawTimerTask(this, drawTimer, DRAW_STEP), DRAW_STEP);
    }

    public void turnToLiving(int x, int y) {
        if (x >= 0 && x < NO_CELLS_ACROSS && y >= 0 && y < NO_CELLS_ACROSS) {
            personalBoard.turnToLiving(x, y);
        }
    }

    public void turnToDead(int x, int y) {
        if (x >= 0 && x < NO_CELLS_ACROSS && y >= 0 && y < NO_CELLS_ACROSS) {
            personalBoard.turnToDead(x, y);
        }
    }

    public void actOnCurrentCell() {
        if (isMakingCellsAlive()) {
            turnCurrentCellToLiving();
        } else if (isMakingCellsDead()) {
            turnCurrentCellToDead();
        }
    }

    public void turnCurrentCellToLiving() {
        // Turns the cell that the mouse is positioned over to living.
        Point point = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(point, drawingBoard);
        point = convertFromPixelsToCells(point);
        turnToLiving(point.x, point.y);
    }

    public void turnCurrentCellToDead() {
        // Turns the cell that the mouse is positioned over to dead.
        Point point = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(point, drawingBoard);
        point = convertFromPixelsToCells(point);
        turnToDead(point.x, point.y);
    }

    public void resetGame() {
        stopPlayTurnTimer();
        personalBoard.resetCells();
    }

    public void randomiseCells() {
        personalBoard.randomiseCells(RANDOM_PROBABILITY);
    }

    public JButton getButton(String buttonType) {
        if (buttonType.equals("Start")) {
            return buttonStart;
        } else if (buttonType.equals("Reset")) {
            return buttonReset;
        } else if (buttonType.equals("Randomise")) {
            return buttonRandomise;
        } else {
            return null;
        }
    }

    public void repaint() {
        drawingBoard.repaint();
    }

    private void createComponents(Container container) {

        container.setLayout(null);

        drawingBoard = new DrawingBoard(personalBoard, CELL_SIZE);
        drawingBoard.setBounds(0, 0, BOARD_WIDTH, BOARD_WIDTH);
        drawingBoard.addMouseListener(myMouseListener);
        container.add(drawingBoard);

        buttonStart = new JButton("Start");
        buttonStart.setBounds(0, BOARD_WIDTH, BOARD_WIDTH / 3, BUTTON_HEIGHT);
        buttonStart.addActionListener(new MyButtonListener(this, buttonStart));
        buttonStart.setFocusPainted(false);
        container.add(buttonStart);

        buttonReset = new JButton("Reset");
        buttonReset.setBounds(BOARD_WIDTH / 3, BOARD_WIDTH, BOARD_WIDTH / 3, BUTTON_HEIGHT);
        buttonReset.addActionListener(new MyButtonListener(this, buttonReset));
        buttonReset.setEnabled(false);
        buttonReset.setFocusPainted(false);
        container.add(buttonReset);

        buttonRandomise = new JButton("Randomise");
        buttonRandomise.setBounds(2 * BOARD_WIDTH / 3, BOARD_WIDTH, BOARD_WIDTH / 3, BUTTON_HEIGHT);
        buttonRandomise.addActionListener(new MyButtonListener(this, buttonRandomise));
        buttonRandomise.setFocusPainted(false);
        container.add(buttonRandomise);
    }

    public JFrame getFrame() {
        return frame;
    }

    public int getCELL_SIZE() {
        return CELL_SIZE;
    }

    public boolean isMakingCellsAlive() {
        return makingCellsAlive;
    }

    public void setMakingCellsAlive(boolean makingCellsAlive) {
        this.makingCellsAlive = makingCellsAlive;
    }

    public boolean isMakingCellsDead() {
        return makingCellsDead;
    }

    public void setMakingCellsDead(boolean makingCellsDead) {
        this.makingCellsDead = makingCellsDead;
    }

    private int convertFromPixelsToCells(int x) {
        // Converts number of pixels across in to number of cells across.
        // e.g. If the cell size is 4, then 2 pixels across is index 0, 5 pixels across is index 1, 8 pixels across is index 2.
        return ((x / this.CELL_SIZE));
    }

    private Point convertFromPixelsToCells(Point p) {
        // Converts number of pixels across in to number of cells across.
        // e.g. If the cell size is 4, then 2 pixels across is index 0, 5 pixels across is index 1, 8 pixels across is index 2.
        return new Point(p.x / this.CELL_SIZE, p.y / this.CELL_SIZE);
    }

}
