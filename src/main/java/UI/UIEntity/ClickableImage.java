package UI.UIEntity;

import UI.VehicleInfoPanel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import util.ConstVars;

public class ClickableImage extends JPanel implements MouseListener, ActionListener{
    private Image image;
    private ConstVars consts = ConstVars.getInstance();
    private int arcSize = 50;
    private int borderSize = 10;
    RoundRectangle2D round;
    private boolean imageClicked = false;
    
    private VehicleInfoPanel mainP;
    private JLabel missingText;
    private JLabel imageLabel;
    private JButton addImage;
    final private JFileChooser fileChooser = new JFileChooser();
    
    private Cursor hoveredCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    private Cursor normalCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);

    public ClickableImage(VehicleInfoPanel mainP) {
        this.image = null;
        this.mainP = mainP;
        initPanel();
    }

    public ClickableImage(Image image, VehicleInfoPanel mainP) {
        this.image = image;
        this.mainP = mainP;
        if (image == null)
            initPanel();
    }
    
    private void initPanel () {
        this.setBackground(consts.COMP_BG_COLOR);
        this.setSize(new Dimension(300, 300));
        this.setLocation((mainP.getWidth() - this.getWidth()) / 2, consts.DEFAULT_MARGIN);
        this.setLayout(null);
        
        if (image == null) {
            this.setOpaque(false);
            this.setBorder(new RoundedCornerBorder(arcSize, borderSize));
            int offset = 20, newX, newY;
            //<editor-fold desc="MISSING IMAGE LABEL" defaultstate="collapsed">
            missingText = new JLabel();
            missingText.setForeground(Color.BLACK);
            missingText.setBackground(new Color(0, 0, 0, 1));
            missingText.setFont(consts.UI_FONT.deriveFont(25f));
            missingText.setText("No picture found");
            newX = (this.getPreferredSize().width - missingText.getPreferredSize().width) / 2;
            newY = (this.getPreferredSize().height - missingText.getPreferredSize().height) / 2 - offset;
            missingText.setSize(missingText.getPreferredSize());
            missingText.setBounds(newX, newY, 
                    missingText.getWidth(), missingText.getHeight());
            //</editor-fold>
            
            //<editor-fold desc="CREATE ADDING IMAGE BUTTON" defaultstate="collapsed">
            addImage = new JButton("Upload image file");
            newX = (this.getPreferredSize().width - addImage.getPreferredSize().width) / 2 - 5;
            newY = (this.getPreferredSize().height - addImage.getPreferredSize().height) / 2 + offset;
            addImage.setSize(addImage.getPreferredSize());
            addImage.setBounds(newX, newY,
                    addImage.getWidth() + 10, addImage.getHeight());
            addImage.setFocusPainted(false);
            addImage.addActionListener(this);
            //</editor-fold>
            
            this.add(missingText);
            this.add(addImage);
        }
    }
    
    private void updateAfterImage () {
        image = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        round = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arcSize, arcSize);
        this.setBackground(new Color(0, 0, 0, 0));
        this.removeAll();
        this.repaint();
    }
    
    //<editor-fold desc="GETTERS AND SETTERS" defaultstate="collapsed">
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    //</editor-fold>
    
    @Override 
    protected void paintComponent(Graphics g) {
        if (!isOpaque() && getBorder() instanceof RoundedCornerBorder && image == null) {
            int borderOffset = ((RoundedCornerBorder)getBorder()).getBorderOffset();
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(getBackground());
            g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(
                0, 0, getWidth() - borderOffset, getHeight() - borderOffset));
            g2.dispose();
        }
        super.paintComponent(g);
    }

    @Override
    public void paint(Graphics g) {
        if (image != null) {
            Graphics2D g2 = (Graphics2D)g;
            this.removeAll();
            int w = getWidth(), h = getHeight();
            
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setClip(round);
            g2.drawImage(image, 0, 0, null);
        } else 
            super.paint(g);
    }
    
    //<editor-fold desc="ADD CLICKING FUNCTION" defaultstate="collapsed">
    @Override
    public void mouseClicked(MouseEvent e) {
        int clickCount = e.getClickCount();
        System.out.println("Button clicked: " + clickCount);
        if (!imageClicked && clickCount == 2) {
            imageClicked = true;
            int returnVal = fileChooser.showOpenDialog(mainP);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    File opened = fileChooser.getSelectedFile();
                    image = ImageIO.read(opened);
                    updateAfterImage();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(mainP,
                            "Error while reading file, please try again!", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
            imageClicked = false;
        }
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
        this.setCursor(hoveredCursor);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setCursor(normalCursor);
    }
    //</editor-fold>
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addImage) {
            int returnVal = fileChooser.showOpenDialog(mainP);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    File opened = fileChooser.getSelectedFile();
                    image = ImageIO.read(opened);
                    this.addMouseListener(this);
                    updateAfterImage();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(mainP,
                            "Error while reading file, please try again!", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            }
        }
    }
    
    //<editor-fold desc="UNNECESSARY MOUSE ACTION" defaultstate="collapsed">
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}
    //</editor-fold>
}
