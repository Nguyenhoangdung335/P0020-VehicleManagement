package UI.UIEntity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.text.Format;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import util.ParseAllFormat;

public final class CustomFormattedTextField extends JFormattedTextField{
    private final Color ERROR_BORDER_COLOR = new Color(225, 0, 0);
    private Color NORMAL_BORDER_COLOR = Color.GRAY;
    
    public CustomFormattedTextField () {
        setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
        updateBackgroundOnEdit();
        this.addFocusListener(new MousePositionCorrectorListener());
    }
    
    public CustomFormattedTextField (String initialValue) {
        this();
        this.setValue(initialValue);
    }
    
    public CustomFormattedTextField (Format format) {
        super(format);
        //super(new ParseAllFormat(format));
        setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
        updateBackgroundOnEdit();
        this.addFocusListener(new MousePositionCorrectorListener());
    }
    
    public CustomFormattedTextField (Format format, Object initialValue) {
        this(format);
        this.setValue(initialValue);
    }
    
    private void updateBackgroundOnEdit () {
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { updateBackground(); }

            @Override
            public void removeUpdate(DocumentEvent e) { updateBackground(); }

            @Override
            public void changedUpdate(DocumentEvent e) { updateBackground(); }
        });
    }
    
    private void updateBackground () {
        ((RoundedCornerBorder)getBorder()).setBorderColor( 
                checkValidContent()? NORMAL_BORDER_COLOR: ERROR_BORDER_COLOR
        );
    }
    
    private boolean checkValidContent () {
        AbstractFormatter formatter = getFormatter();
        if (formatter != null) {
            try {
                formatter.stringToValue(getText());
            } catch (ParseException e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void setValue(Object value) {        
        boolean isValid = true;
        try {
            AbstractFormatter formatter = getFormatter();
            if (formatter != null)
                formatter.valueToString(value);
        } catch (Exception e) {
            isValid = false;
            this.updateBackground();
        }
        
        if (isValid) {
            super.setValue(value);
            this.setCaretPosition(Math.min(getCaretPosition(), getText().length()));
        }
    }

    @Override
    protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed) {
        if (checkValidContent())
            return super.processKeyBinding( ks, e, condition, pressed ) 
                    && ks != KeyStroke.getKeyStroke( KeyEvent.VK_ENTER, 0 );
        else 
            return super.processKeyBinding(ks, e, condition, pressed);
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
    
    private static class MousePositionCorrectorListener extends FocusAdapter {
        @Override
        public void focusGained(FocusEvent e) {
            final JTextField field = ( JTextField ) e.getSource();
            final int dot = field.getCaret().getDot();
            final int mark = field.getCaret().getMark();
            
            if ( field.isEnabled() && field.isEditable() )
                SwingUtilities.invokeLater(() -> {
                    if ( dot == mark ) field.getCaret().setDot( dot );
                });
        }
    }
}
