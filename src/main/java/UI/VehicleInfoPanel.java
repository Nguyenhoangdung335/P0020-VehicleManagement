package UI;

import UI.UIEntity.ClickableImage;
import UI.UIEntity.InputFieldPanel;
import UI.UIEntity.InputPanel;
import UI.UIEntity.InputPanelFactory;
import UI.util.DocumentFilterFactory;
import UI.util.DocumentFilterFactory.IDFilter;
import entity.Vehicle;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import util.ColorUtil;
import util.ConstVars;

public final class VehicleInfoPanel extends JPanel implements Scrollable, MouseMotionListener, FocusListener{
    private MainFunctionPane mainP;
    private Vehicle vehicle;
    private ConstVars consts = ConstVars.getInstance();
    
    //<editor-fold desc="PANEL COMPONENTS" defaultstate="collapsed">
    private JPanel imageContainer;
    final private JFileChooser fileChooser = new JFileChooser();
    private Image vehicleImage;
    private JButton cancelButton;
    private JButton confirmButton;
    private JScrollPane parentScrollpane;
    //</editor-fold>
    String[] labelName = {"Vehicle ID", "Vehicle name", "Vehicle color", "Vehicle price", "Vehicle brand", "Vehicle type", "Product year"};
    private List<InputPanel> inputComponents = new LinkedList<>();
    
    private int currentPaneHeight = 0;
    private int marginPixel = 15;
    private int dividerXPoint = 150;
    private int gapBetweenFields = 25;
    private Dimension labelSize;
    private Dimension inputSize;
    private boolean isNewVehicle = false;
    
    private int maxUnitIncrement = 6;
    private boolean missingPicture;
    
    //<editor-fold desc="ADDING PANEL INITIALIZATION" defaultstate="collapsed">
    public VehicleInfoPanel (MainFunctionPane mainP) {
        this(mainP, new Vehicle());
        this.isNewVehicle = true;
    }
    
    public VehicleInfoPanel (MainFunctionPane mainP, Vehicle vehicle ) {
        this.mainP = mainP;
        this.vehicle = vehicle;
        this.missingPicture = (vehicleImage == null);
            
        //this.setLayout(new GridBagLayout());
        this.setLayout(null);
        this.setBounds(0, 0, mainP.getWidth(), mainP.getHeight());
        this.setBackground(Color.CYAN);
        this.setOpaque(true);
        this.setAutoscrolls(true);
        this.addMouseMotionListener(this);
        
        this.fileChooser.setFileFilter(new FileNameExtensionFilter("Image", "jpeg", "png", "jpg"));
        
        this.labelSize = new Dimension(dividerXPoint - 2*marginPixel, 40);
        this.inputSize = new Dimension(mainP.getWidth() - dividerXPoint - 3*marginPixel, labelSize.height);
        
        initPageComponents();
        this.repaint();
    }
    
    private void initPageComponents () {
        this.imageContainer = new ClickableImage(this);
        this.add(imageContainer);
        currentPaneHeight = imageContainer.getY() + imageContainer.getHeight() + gapBetweenFields;
        
        createInputField();
        
        this.cancelButton = createCancelButton();
        cancelButton.setLocation(mainP.getWidth() / 2 - cancelButton.getWidth() - consts.DEFAULT_MARGIN, currentPaneHeight);
        this.add(cancelButton);
        this.confirmButton = createConfirmButton();
        confirmButton.setLocation(mainP.getWidth() / 2 + confirmButton.getWidth() + consts.DEFAULT_MARGIN, currentPaneHeight);
        this.add(confirmButton);
        
        currentPaneHeight = cancelButton.getY() + cancelButton.getHeight() + gapBetweenFields;
        
        renewPreferredSize(new Dimension(this.getWidth(), currentPaneHeight));
        changeConfirmState();
    }
    
    private void renewPreferredSize (Dimension newD) {
        this.setPreferredSize(newD);
        this.setSize(newD);
    }
    
    private void createInputField () {
        for (int i = 0; i < labelName.length; i++) {
            InputPanel inputPane = null;
            switch (i) {
                case 0 -> {
                    //-------------------VEHICLE ID INPUT PANEL-------------------
                    inputPane = InputPanelFactory.createTextFieldPanel(this, labelName[i], vehicle.getVehicleID());
                    IDFilter idFilter = DocumentFilterFactory.getIDFilter(inputPane, consts.VEHICLEIDPATTERN);
                    idFilter.setMaxCharacter(5);
                    ((InputFieldPanel)inputPane).setDocumentFilter(idFilter);
                    //---------------------------------------------------------
                } case 1 -> {
                    //-------------------VEHICLE NAME INPUT PANEL-------------------
                    inputPane = InputPanelFactory.createTextFieldPanel(this, labelName[i], vehicle.getName());
                    //---------------------------------------------------------
                } case 2 -> {
                    //-------------------VEHICLE COLOR INPUT PANEL-------------------
                    inputPane = InputPanelFactory.createColorPanel(this, labelName[i], vehicle.getColor());
                    //---------------------------------------------------------
                } case 3 -> {
                    //-------------------VEHICLE PRICE INPUT PANEL-------------------
                    inputPane = InputPanelFactory.createTextFieldPanel(this, labelName[i], String.valueOf(vehicle.getPrice()));
                    ((InputFieldPanel)inputPane).setDocumentFilter(
                            DocumentFilterFactory.getPriceFilter(inputPane, consts.PRICE_PATTERN)
                    );
                    //---------------------------------------------------------
                } case 4 -> {
                    //-------------------VEHICLE BRAND INPUT PANEL-------------------
                    inputPane = InputPanelFactory.createTextFieldPanel(this, labelName[i], vehicle.getBrand());
                    //---------------------------------------------------------
                } case 5 -> {
                    //-------------------VEHICLE TYPE INPUT PANEL-------------------
                    inputPane = InputPanelFactory.createComboBoxPanel(this, labelName[i], consts.VEHICLETYPES);
                    //---------------------------------------------------------
                } case 6 -> {
                    //-------------------VEHICLE PRODUCT YEAR INPUT PANEL-------------------
                    inputPane = InputPanelFactory.createTextFieldPanel(this, labelName[i], vehicle.getProductYearString());
                    ((InputFieldPanel)inputPane).setDocumentFilter(
                            DocumentFilterFactory.getYearFilter(inputPane)
                    );
                    //---------------------------------------------------------
                } 
            }
            if (inputPane != null) {
                inputPane.setName(labelName[i]);
                inputPane.setLocation(0, currentPaneHeight);
                inputPane.getInputComponent().addFocusListener(this);
                
                currentPaneHeight = inputPane.getY() + inputPane.getHeight() + gapBetweenFields;
                this.add(inputPane);
                inputComponents.add(inputPane);
            }
        }
    }
    
    private JButton createConfirmButton () {
        JButton button = new JButton("Confirm");
        button.setFocusPainted(false);
        button.setFont(new Font(null, Font.BOLD, 14));
        button.setSize(button.getPreferredSize());
        
        button.addActionListener(confirm);
        
        return button;
    }
    
    private JButton createCancelButton () {
        JButton button = new JButton("Cancel");
        button.setFocusPainted(false);
        button.setFont(new Font(null, Font.BOLD, 14));
        button.setSize(button.getPreferredSize());
        
        button.addActionListener((e) -> {
            mainP.returnATab(parentScrollpane);
            button.setEnabled(false);
        });
        
        return button;
    }
    
    private boolean isAllValid () {
        for (var comp: inputComponents) {
            if (comp instanceof InputFieldPanel inputPanel) {
                if (inputPanel.isEmpty() || inputPanel.isError()){
                    System.out.println(inputPanel.isEmpty() + " " + inputPanel.isError());
                    return false;
                }
            }
        }
        return true;
    }
    
    private void changeConfirmState () {
        if (isAllValid()) 
            confirmButton.setEnabled(true);
        else 
            confirmButton.setEnabled(false);
    }
    
    Action confirm = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
            for (int i = 0; i < inputComponents.size(); i++) {
                switch (i) {
                    case 0 -> {
                        //-------------------VEHICLE ID INPUT PANEL-------------------
                        vehicle.setVehicleID(inputComponents.get(i).getInputValue());
                        //---------------------------------------------------------
                    } case 1 -> {
                        //-------------------VEHICLE NAME INPUT PANEL-------------------
                        vehicle.setName(inputComponents.get(i).getInputValue());
                        //---------------------------------------------------------
                    } case 2 -> {
                        //-------------------VEHICLE COLOR INPUT PANEL-------------------
                        vehicle.setColor(ColorUtil.hexToColor(inputComponents.get(i).getInputValue()));
                        //---------------------------------------------------------
                    } case 3 -> {
                        //-------------------VEHICLE PRICE INPUT PANEL-------------------
                        vehicle.setPrice(inputComponents.get(i).getInputValue());
                        //---------------------------------------------------------
                    } case 4 -> {
                        //-------------------VEHICLE BRAND INPUT PANEL-------------------
                        vehicle.setBrand(inputComponents.get(i).getInputValue());
                        //---------------------------------------------------------
                    } case 5 -> {
                        //-------------------VEHICLE TYPE INPUT PANEL-------------------
                        vehicle.setType(inputComponents.get(i).getInputValue());
                        //---------------------------------------------------------
                    } case 6 -> {
                        //-------------------VEHICLE PRODUCT YEAR INPUT PANEL-------------------
                        vehicle.setProductYear(inputComponents.get(i).getInputValue());
                        //---------------------------------------------------------
                    } 
                }
            }
            if (isNewVehicle)
                mainP.getVehicleList().addNew(vehicle);
            mainP.returnATab(parentScrollpane);
        } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        mainP, 
                        ex.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE
                );
        }
        }
    };
    //</editor-fold>
    
    //<editor-fold desc="GETTERS AND SETTERS" defaultstate="collapsed">
    public void setMaxUnitIncrement (int pixels) {
        this.maxUnitIncrement = pixels;
    }
    
    public void setParentScrollPane (JScrollPane parentScroll) {
        this.parentScrollpane = parentScroll;
    }
    
    public JScrollPane getParentScrollPane () {
        return parentScrollpane;
    }
    //</editor-fold>
    
    //<editor-fold desc="UNIT AND BLOCK SCROLLING METHODS" defaultstate="collapsed">
    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        //Get the current position.
        int currentPosition = (orientation == SwingConstants.HORIZONTAL)? 
                visibleRect.x: visibleRect.y;

        //Return the number of pixels between currentPosition
        //and the nearest tick mark in the indicated direction.
        if (direction < 0) {
            int newDistance = currentPosition -
                             (currentPosition / maxUnitIncrement)
                              * maxUnitIncrement;
            return (newDistance == 0) ? maxUnitIncrement : newDistance;
        } else {
            return ((currentPosition / maxUnitIncrement) + 1)
                     * maxUnitIncrement
                     - currentPosition;
        }
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return (orientation == SwingConstants.HORIZONTAL)? 
                visibleRect.width - maxUnitIncrement: 
                visibleRect.height - maxUnitIncrement;
    }
    //</editor-fold>
    
    //<editor-fold desc="MOUSE MOTION METHODS" defaultstate="collapsed">
    @Override
    public void mouseDragged(MouseEvent e) {
        Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
        scrollRectToVisible(r);
    }

    @Override
    public void mouseMoved(MouseEvent e) { }
    //</editor-fold>
    
    //<editor-fold desc="FOCUS EVENT" defaultstate="collapsed">
    @Override
    public void focusGained(FocusEvent e) {}

    @Override
    public void focusLost(FocusEvent e) {
        System.out.println("Focus lost: " + e.getCause());
        changeConfirmState();
    }
    //</editor-fold>
    
    
    //<editor-fold desc="UNNECESSARY SCROLLABLE METHODS" defaultstate="collapsed">
    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return super.getPreferredSize();
    }
    
    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
    //</editor-fold>
}
