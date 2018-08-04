package gameoflife;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        
        UserInterface ui = new UserInterface(new GameOfLifeBoard(60));
        SwingUtilities.invokeLater(ui);
        
    }
    
}
