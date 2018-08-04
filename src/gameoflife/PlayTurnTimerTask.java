package gameoflife;

import java.util.Timer;
import java.util.TimerTask;

public class PlayTurnTimerTask extends TimerTask {

    private GameOfLifeBoard personalBoard;
    private Timer timer;
    private int PLAY_STEP;

    public PlayTurnTimerTask(GameOfLifeBoard personalBoard, Timer timer, int PLAY_STEP) {
        this.personalBoard = personalBoard;
        this.timer = timer;
        this.PLAY_STEP = PLAY_STEP;
    }

    @Override
    public void run() {
        personalBoard.playTurn();
        timer.schedule(new PlayTurnTimerTask(personalBoard, timer, PLAY_STEP), PLAY_STEP);
    }

}
