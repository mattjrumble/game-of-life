package gameoflife;

import java.util.Timer;
import java.util.TimerTask;

public class DrawTimerTask extends TimerTask {

    private UserInterface ui;
    private Timer timer;
    private int DRAW_STEP;

    public DrawTimerTask(UserInterface ui, Timer timer, int DRAW_STEP) {
        this.ui = ui;
        this.timer = timer;
        this.DRAW_STEP = DRAW_STEP;
    }

    @Override
    public void run() {
        ui.actOnCurrentCell();
        ui.repaint();
        timer.schedule(new DrawTimerTask(ui, timer, DRAW_STEP), DRAW_STEP);
    }

}
