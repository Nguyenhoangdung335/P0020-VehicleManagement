package UI.UIEntity;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComboBox;

public class InputComboBoxPanel extends InputPanel{
    private JComboBox comboBox;
    private String[] defaultItems = {"Item 1", "Item 2", "Item 3", "Item 4"};
    private String[] boxItems;
    
    public InputComboBoxPanel(Component parentPanel, String labelName, String[] boxItems) {
        super(parentPanel, labelName);
        this.boxItems = boxItems;
        
        this.inputLabel = createFieldLabel(labelName);
        this.comboBox = createComboBoxInput();
        this.errorText = createErrorLabel();
        inputLabel.setLocation(consts.DEFAULT_MARGIN, 0);
        comboBox.setLocation(dividerXPoint + consts.DEFAULT_MARGIN, 0);
        errorText.setLocation(comboBox.getX() + consts.DEFAULT_MARGIN, comboBox.getHeight());
        
        this.add(inputLabel);
        this.add(comboBox);
        this.add(errorText);
    }
    
    //<editor-fold desc="GETTERS AND SETTERS" defaultstate="collapsed">
    @Override
    public Component getInputComponent() {
        return comboBox;
    }

    public String[] getBoxItems() {
        return boxItems;
    }

    public void setBoxItems(String[] boxItems) {
        this.boxItems = boxItems;
    }

    @Override
    public String getInputValue() {
        return comboBox.getSelectedItem().toString();
    }
    //</editor-fold>
    
    private JComboBox createComboBoxInput () {
        JComboBox box = new JComboBox( (boxItems != null)? 
                boxItems: 
                defaultItems)
        {
            //<editor-fold desc="OVERRIDED FOR ROUNDED CORNERRED-BORDER" defaultstate="collapsed">
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
            //</editor-fold>
        };        
        box.setSize(inputSize);
        box.setFont(consts.UI_FONT);
        box.setBackground(consts.COMP_BG_COLOR);
        box.setForeground(Color.BLACK);
        box.setFocusable(false);
        return box;
    }
}
