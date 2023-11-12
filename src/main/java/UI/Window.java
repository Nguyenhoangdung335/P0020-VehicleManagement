package UI;

import UI.util.DocumentFilterFactory;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import util.Event;

public final class Window extends JFrame{
    private Screen screen = new Screen();
    private Event onCreateFrame = new Event();
    
    public Window () {
        this.add(screen, BorderLayout.CENTER);
        
        this.setTitle("Showroom's Vehicle Management");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(onClose);
        this.setFocusable(true);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        onCreateFrame.addListener(new DocumentFilterFactory());
        onCreateFrame.invoke(this);
    }
    
    WindowListener onClose = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            ExitPane exit = new ExitPane();
            exit.exitOption();
        }
    };
}
