package UI.UIEntity;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import util.ColorUtil;

public class InputColorPanel extends InputPanel implements ActionListener{
    private JPanel colorInput;
    private JLabel colorLabel;
    private JButton colorChooser;
        
    public InputColorPanel(Component parentPanel, String labelName, Color defaultColor) {
        super(parentPanel, labelName);
                
        this.inputLabel = createFieldLabel(labelName);
        this.colorInput = createColorInput(defaultColor);
        this.errorText = createErrorLabel();
        inputLabel.setLocation(consts.DEFAULT_MARGIN, 0);
        colorInput.setLocation(dividerXPoint + consts.DEFAULT_MARGIN, 0);
        errorText.setLocation(colorInput.getX() + consts.DEFAULT_MARGIN, colorInput.getHeight());
        
        this.add(inputLabel);
        this.add(colorInput);
        this.add(errorText);
    }
    
    //<editor-fold desc="GETTERS AND SETTERS" defaultstate="collapsed">
    @Override
    public Component getInputComponent() {
        return colorInput;
    }

    public Color getColor() {
        return colorLabel.getBackground();
    }
    
    public void setColor(Color newColor) {
        if (newColor != null) {
            this.colorLabel.setBackground(newColor);
            this.colorLabel.repaint();
        }
    }

    @Override
    public String getInputValue() {
        return ColorUtil.colorToHex(getColor());
    }
    //</editor-fold>
    
    private JPanel createColorInput (Color defaultColor) {
        int verticalPadd = 2;
        JPanel colorPanel = new JPanel();
        colorPanel.setSize(inputSize);
        colorPanel.setBackground(new Color(0, 0, 0, 0));
        colorPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, verticalPadd));
        
        this.colorLabel = new JLabel(){
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
                setBorder(new RoundedCornerBorder(15, 2));
            }
            //</editor-fold>
        };
        colorLabel.setPreferredSize(new Dimension(labelSize.height - 2*verticalPadd, labelSize.height - 2*verticalPadd));
        colorLabel.setBackground( (defaultColor != null)? 
                defaultColor: 
                Color.WHITE
        );

        
        this.colorChooser = new JButton("Show Color Chooser...");
        colorChooser.addActionListener(this);
        colorChooser.setFocusPainted(false);
        
        colorPanel.add(colorLabel);
        colorPanel.add(colorChooser);
        return colorPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == colorChooser) {
            Color newColor = JColorChooser.showDialog(parent,
                                    "Choose Background Color",
                                    colorLabel.getBackground());
             if (newColor != null) {
                 colorLabel.setBackground(newColor);
                 parent.repaint();
             }
        }
    }
}
