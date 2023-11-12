package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import util.ConstVars;

public class Screen extends JPanel{
    private ConstVars consts = ConstVars.getInstance();
    private MainFunctionPane functionPane = new MainFunctionPane();
    private MenuPanel menuPane = new MenuPanel(functionPane);
    
    public Screen () {
        this.setPreferredSize(new Dimension(consts.S_WIDTH, consts.S_HEIGHT));
        this.setLayout(new BorderLayout());
        
        this.add(menuPane, BorderLayout.WEST);
        this.add(functionPane, BorderLayout.EAST);
    }
}
