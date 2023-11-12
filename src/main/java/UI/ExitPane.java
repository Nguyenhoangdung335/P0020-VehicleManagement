package UI;

import javax.swing.JOptionPane;

public final class ExitPane {
    public static void exitOption () {
        int answer = JOptionPane.showConfirmDialog(null, 
                "Do you want to exit the application?", 
                "Exist?", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE
        );
        if (answer == 0) System.exit(0);
    }
}
