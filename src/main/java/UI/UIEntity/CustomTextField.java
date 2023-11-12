package UI.UIEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

public class CustomTextField extends JTextField implements FocusListener{        
    private InputFieldPanel parent;
    
    private String backgroundText;
    private Color defaultForeground;
    private boolean isBlank = true;
    private boolean hasBackgroundText = false;
    
    public CustomTextField (DocumentFilter filter) {
        if (filter != null)
            setDocumentFilter(filter);
    }
    
    public CustomTextField (InputFieldPanel parent) {
        this.parent = parent;
    }
    
    public CustomTextField (InputFieldPanel parent, DocumentFilter filter) {
        this.parent = parent;
        if (filter != null)
            setDocumentFilter(filter);
    }
    
    public void setDocumentFilter (DocumentFilter filter) {
        ((AbstractDocument)this.getDocument()).setDocumentFilter(filter);
    }
    
    public void setBackgroundText (String backgroundText) {
        if (backgroundText == null)
            hasBackgroundText = false;
        else 
            hasBackgroundText = true;
        this.backgroundText = backgroundText;
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        if (text == null) {
            isBlank = false;
            return;
        } else if (!isBlank && text.length() == 0) {
            isBlank = true;
        }
    }
    
    @Override 
    protected void paintComponent(Graphics g) {
        if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
            int borderOffset = ((RoundedCornerBorder)getBorder()).getBorderOffset();
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(getBackground());
            g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(
                0, 0, getWidth() - borderOffset, getHeight() - borderOffset));
            g2.dispose();
        }
        super.paintComponent(g);
    }
    @Override public void updateUI() {
        super.updateUI();
        setOpaque(false);
        setBorder(new RoundedCornerBorder(15, 1));
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (isBlank && hasBackgroundText) {
            this.setForeground(defaultForeground);
            super.setText(null);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (isBlank && hasBackgroundText) {
            this.defaultForeground = getForeground();
            this.setForeground(Color.GRAY);
            super.setText(backgroundText);
        }
    }
}
