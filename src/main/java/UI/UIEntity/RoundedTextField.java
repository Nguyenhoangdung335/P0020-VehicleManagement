package UI.UIEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import javax.swing.JTextField;

public class RoundedTextField extends JTextField{
    private int arcSize = 15;
    private int borderSize = 1;

    //<editor-fold desc="CONSTRUCTORS" defaultstate="collapsed">
    public RoundedTextField() {
        super();
    }
    
    public RoundedTextField(int arcSize) {
        super();
        this.arcSize = arcSize;
    }
    
    public RoundedTextField(int arcSize, int borderSize) {
        super();
        this.arcSize = arcSize;
        this.borderSize = borderSize;
    }
    //</editor-fold>

    @Override
    protected void paintComponent(Graphics g) {
        /*
        if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
            int borderOffset = ((RoundedCornerBorder)getBorder()).getBorderOffset();
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(getBackground());
            g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(
                0, 0, getWidth() - borderOffset, getHeight() - borderOffset));
            g2.dispose();
        }
        super.paintComponent(g);
        */
        
        if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(getBackground());
            g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(
                0, 0, getWidth() - 1, getHeight() - 1));
            g2.dispose();
        }
        super.paintComponent(g);
        
    }
    
    @Override
    public void updateUI() {
        super.updateUI();
        setOpaque(false);
        setBorder(new RoundedCornerBorder(arcSize, borderSize));
    }
}
