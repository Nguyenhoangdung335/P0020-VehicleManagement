package UI.UIEntity;

import UI.VehicleInfoPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import util.ConstVars;

public abstract class InputPanel extends JPanel{
    protected ConstVars consts = ConstVars.getInstance();
    protected Component parent;
    
    protected JLabel inputLabel;
    protected JLabel errorText;
    
    protected String labelName;
    
    protected int dividerXPoint = 150;
    protected int compHeight = 40;
    protected Dimension labelSize, 
            inputSize, 
            errorSize;
    
    //Because this is used in no layout manager, it use size methods 
    //instead of preferredSize methods.
    public InputPanel (Component parentPanel, String labelName) {
        this.parent = parentPanel;
        this.labelName = labelName;
        
        this.labelSize = new Dimension(dividerXPoint - 2*consts.DEFAULT_MARGIN, compHeight);
        this.inputSize = new Dimension(parent.getWidth() - dividerXPoint - 3*consts.DEFAULT_MARGIN, compHeight);
        this.errorSize = new Dimension((int)inputSize.getWidth() - consts.DEFAULT_MARGIN, compHeight/2);
        
        this.setPreferredSize(new Dimension(parent.getWidth(), compHeight*15/10));
        this.setSize(this.getPreferredSize());
        this.setLayout(null);
        this.setBackground(new Color(0, 0, 0, 0));   
    }
    
    //<editor-fold desc="GETTERS AND SETTERS" defaultstate="collapsed">
    public String getErrorText() {
        return errorText.getText();
    }
    
    public void setErrorText (String newText) {
        this.errorText.setText(newText);
    }
    
    public abstract String getInputValue();

    public Dimension getLabelSize() {
        return labelSize;
    }

    public Dimension getInputSize() {
        return inputSize;
    }

    public Dimension getErrorSize() {
        return errorSize;
    }
    
    public abstract Component getInputComponent();
    //</editor-fold>
    
    protected JLabel createFieldLabel (String text) {
        JLabel newLabel = new JLabel(text);
        newLabel.setFont(consts.UI_FONT.deriveFont(Font.BOLD, 20));
        newLabel.setForeground(Color.BLACK);
        //newLabel.setBackground(new Color(0, 0, 0, 0));
        newLabel.setBackground(Color.YELLOW);
        newLabel.setOpaque(true);
        newLabel.setSize(labelSize);
        newLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        return newLabel;
    }
    
    protected JLabel createErrorLabel () {
        JLabel newLabel = new JLabel();
        newLabel.setFont(consts.UI_FONT.deriveFont(Font.ITALIC, 15));
        newLabel.setForeground(Color.RED);
        newLabel.setBackground(new Color(0, 0, 0, 0));
        //newLabel.setBackground(Color.GREEN);
        newLabel.setOpaque(true);
        newLabel.setSize(errorSize);
        newLabel.setHorizontalAlignment(SwingConstants.LEFT);
        return newLabel;
    }
    
    public boolean isError () {
        boolean isError = false;
        if (errorText.getText() != null) {
            isError = !errorText.getText().isBlank() || !errorText.getText().isEmpty();
        }
        return isError;
    }
}
