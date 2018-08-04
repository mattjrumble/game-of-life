package gameoflife;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class MyButtonListener implements ActionListener {

    private JButton button;
    private UserInterface ui;

    public MyButtonListener(UserInterface ui, JButton button) {
        this.ui = ui;
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String buttonText = button.getText();
        if (buttonText.equals("Start")) {
            ui.startNewPlayTurnTimer();
            button.setText("Pause");
            ui.getButton("Reset").setEnabled(true);
            ui.getButton("Randomise").setEnabled(false);
        } else if (buttonText.equals("Pause")) {
            ui.stopPlayTurnTimer();
            button.setText("Resume");
        } else if (buttonText.equals("Resume")) {
            ui.startNewPlayTurnTimer();
            button.setText("Pause");
        } else if (buttonText.equals("Reset")) {
            ui.resetGame();
            ui.getButton("Start").setText("Start");
            ui.getButton("Reset").setEnabled(true);
            ui.getButton("Randomise").setEnabled(true);
        } else if (buttonText.equals("Randomise")) {
            ui.randomiseCells();
        }
    }

}
