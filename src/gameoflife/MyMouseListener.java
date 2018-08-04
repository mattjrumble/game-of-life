package gameoflife;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyMouseListener implements MouseListener {

    private UserInterface ui;

    public MyMouseListener(UserInterface ui) {
        this.ui = ui;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setRelevantOperation(e, false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setRelevantOperation(e, false);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        setRelevantOperation(e, true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        setRelevantOperation(e, false);
    }

    private void setRelevantOperation(MouseEvent e, boolean state) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            ui.setMakingCellsAlive(state);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            ui.setMakingCellsDead(state);
        }
    }

}
