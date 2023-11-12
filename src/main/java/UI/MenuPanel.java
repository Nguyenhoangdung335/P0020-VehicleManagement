package UI;

import UI.UIEntity.CustomTextField;
import UI.UIEntity.InputComboBoxPanel;
import UI.UIEntity.InputPanelFactory;
import UI.util.ChoiceOperationUtil;
import UI.util.DocumentFilterFactory;
import entity_list.VehicleList;
import exception.InvalidInputException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DocumentFilter;
import util.ConstVars;
import util.Event;
import validator.CheckAvailable;
import view.Menu;

public class MenuPanel extends JPanel{
    private Event onGetVehicleList = new Event();
    
    private static final ConstVars consts = ConstVars.getInstance();
    private MainFunctionPane funcPane;
    
    protected static int menuWidth = consts.S_WIDTH * 35 / 100;
    private int padding = 30;
    GridBagConstraints gbc;
    Map<String,Integer> buttonChoices = new HashMap<>();
    
    private List<String> menuChoices;
    private int numOfChoice;
    
    private Border border = new LineBorder(Color.BLACK, 1);
    
    public MenuPanel (MainFunctionPane funcPane) {
        this(funcPane, Menu.MAINMENU);
    }
    
    public MenuPanel (MainFunctionPane funcPane, List<String> menuChoices) {
        this.funcPane = funcPane;
        this.onGetVehicleList.addListener(new DocumentFilterFactory());
        this.onGetVehicleList.invoke(funcPane.getVehicleList());
        this.menuChoices = menuChoices;
        this.numOfChoice = menuChoices.size();
        
        this.setPreferredSize(new Dimension(menuWidth, consts.S_HEIGHT));
        
        this.setBackground(Color.red);
        this.setLayout(new GridBagLayout());
        this.setBorder(border);
        
        createChoiceBar();
    }
    
    private void createChoiceBar () {
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(padding / 2, 5, padding / 2, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        for (int i = 0; i < numOfChoice; i++) {
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridy = i;
            gbc.gridx = 0;
            gbc.ipady = 5;
            gbc.gridwidth = 2;
            
            menuBarRedirect(i);
        }
    }
    
    private void menuBarRedirect (int choice) {
        if (menuChoices == Menu.MAINMENU) {
            mainMenuBar(choice);
        } else if (menuChoices == Menu.SEARCHOPTIONS) {
            searchMenuBar(choice);
        } else if (menuChoices == Menu.DISPLAYOPTIONS) {
            displayMenuBar(choice);
        }
    }
    
    private void mainMenuBar (int choice) {
        buttonChoices.put(Menu.MAINMENU.get(choice), choice);
        JButton createdButton = createMenuChoice(menuChoices.get(choice));
        switch (choice) {
            case 1 -> {
                //<editor-fold desc="CREATE CHOICE FOR CHECK EXISTENCE" defaultstate="collapsed">
                CustomTextField idTextField = createTextField(null);
                idTextField.getDocument().addDocumentListener(new DocumentListener(){
                    //<editor-fold desc="DOCUMENT LISTENER FOR CHECK EMPTY" defaultstate="collapsed">
                    boolean alreadyEnabled = false;
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        enableButton();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        handleEmptyTextField(e);
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        if (!handleEmptyTextField(e))
                            enableButton();
                    }
                    
                    private void enableButton() {
                        if (!alreadyEnabled) {
                            createdButton.setEnabled(true);
                        }
                    }

                    private boolean handleEmptyTextField(DocumentEvent e) {
                        if (e.getDocument().getLength() <= 0) {
                            createdButton.setEnabled(false);
                            alreadyEnabled = false;
                            return true;
                        }
                        return false;
                    }
                    //</editor-fold>
                });
                
                createdButton.addActionListener(new AbstractAction(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            checkIDInputted(idTextField.getText());
                        } catch (InvalidInputException ex) {
                            JOptionPane.showMessageDialog(funcPane, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(funcPane, ex.getMessage(), "Successfully find vehicle ID", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });
                
                createdButton.setEnabled(false);
                gbc.gridwidth = 1;
                gbc.gridx = 1;
                this.add(idTextField, gbc);
                gbc.gridx = 0;
                //</editor-fold>
            } case 2 -> {
                //<editor-fold desc="CREATE CHOICE FOR UPDATE VEHICLE INFO" defaultstate="collapsed">
                CustomTextField idTextField = createTextField(null);
                idTextField.setDocumentFilter(DocumentFilterFactory.getIDFilter(idTextField, consts.VEHICLEIDPATTERN, false));
                idTextField.addPropertyChangeListener("foreground", (PropertyChangeEvent evt) -> {
                    Object source = evt.getSource();
                    if (source == idTextField) {
                        if (((CustomTextField)source).getForeground().equals(Color.RED))
                            createdButton.setEnabled(false);
                        else {
                            System.out.println(((CustomTextField)source).getText().length());
                            if (((CustomTextField)source).getText().isBlank())
                                createdButton.setEnabled(false);
                            else
                                createdButton.setEnabled(true);
                        }
                    }
                });
                createdButton.addActionListener(new AbstractAction(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == createdButton) {
                            if (idTextField.getText() == null) return;
                            ChoiceOperationUtil.openVehicleInfoTab(funcPane,
                                    CheckAvailable.doesVehicleIDExist(idTextField.getText(),
                                            funcPane.getVehicleList()
                                    )
                            );
                            idTextField.setText(null);
                            createdButton.setEnabled(false);
                        }
                    }
                });
                createdButton.setEnabled(false);
                gbc.gridwidth = 1;
                gbc.gridx = 1;
                this.add(idTextField, gbc);
                gbc.gridx = 0;
                //</editor-fold>
            } case 3 -> {
                //<editor-fold desc="CREATE CHOICE FOR REMOVE VEHICLE" defaultstate="collapsed">
                CustomTextField idTextField = createTextField(null);
                idTextField.setDocumentFilter(DocumentFilterFactory.getIDFilter(idTextField, consts.VEHICLEIDPATTERN, false));
                idTextField.addPropertyChangeListener("foreground", (PropertyChangeEvent evt) -> {
                    Object source = evt.getSource();
                    if (source == idTextField) {
                        if (((CustomTextField)source).getForeground().equals(Color.RED))
                            createdButton.setEnabled(false);
                        else {
                            System.out.println(((CustomTextField)source).getText().length());
                            if (((CustomTextField)source).getText().isBlank())
                                createdButton.setEnabled(false);
                            else
                                createdButton.setEnabled(true);
                        }
                    }
                });
                createdButton.addActionListener(new AbstractAction(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == createdButton) {
                            if (idTextField.getText() == null) return;
                            ChoiceOperationUtil.removeVehicleInfo(funcPane,
                                    CheckAvailable.doesVehicleIDExist(idTextField.getText(),
                                            funcPane.getVehicleList()
                                    )
                            );
                            idTextField.setText(null);
                            createdButton.setEnabled(false);
                        }
                    }
                });
                createdButton.setEnabled(false);
                gbc.gridwidth = 1;
                gbc.gridx = 1;
                this.add(idTextField, gbc);
                gbc.gridx = 0;
                //</editor-fold>
            } default -> {
                createdButton.addActionListener(new AbstractAction(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        funcPane.setChosenOption(buttonChoices.get(createdButton.getText()), consts.MAIN_MENU);
                        funcPane.repaint();
                        onGetVehicleList.invoke(funcPane.getVehicleList());
                    }
                });
            }
        }
        this.add(createdButton, gbc);
    }
    
    private void searchMenuBar (int choice) {
        buttonChoices.put(Menu.DISPLAYOPTIONS.get(choice), choice);
        JButton createdButton = createMenuChoice(menuChoices.get(choice));
        
        switch (choice) {
            case 0 -> {
                //<editor-fold desc="CREATE INPUT FIELD FOR ID CHECK" defaulstate="collapsed">
                CustomTextField idTextField = createTextField(null);
                idTextField.setDocumentFilter(DocumentFilterFactory.getIDFilter(idTextField, consts.VEHICLEIDPATTERN, false));
                idTextField.addPropertyChangeListener("foreground", (PropertyChangeEvent evt) -> {
                    Object source = evt.getSource();
                    if (source == idTextField) {
                        if (((CustomTextField)source).getForeground().equals(Color.RED))
                            createdButton.setEnabled(false);
                        else {
                            System.out.println(((CustomTextField)source).getText().length());
                            if (((CustomTextField)source).getText().isBlank())
                                createdButton.setEnabled(false);
                            else
                                createdButton.setEnabled(true);
                        }
                    }
                });
                
                createdButton.addActionListener(new AbstractAction(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == createdButton) {
                            if (idTextField.getText() == null) return;
                            ChoiceOperationUtil.openVehicleListTab(funcPane, 
                                    new VehicleList(funcPane.getVehicleList().searchByID(idTextField.getText()))
                            );
                            idTextField.setText(null);
                            createdButton.setEnabled(false);
                        }
                    }
                });
                createdButton.setEnabled(false);
                gbc.gridwidth = 1;
                gbc.gridx = 1;
                this.add(idTextField, gbc);
                gbc.gridx = 0;
                //</editor-fold> 
            } case 1 -> {
                //<editor-fold desc="CREATE INPUT FIELD FOR NAME" defaulstate="collapsed">
                CustomTextField nameTextField = createTextField(null);
                nameTextField.getDocument().addDocumentListener(new DocumentListener(){
                    //<editor-fold desc="DOCUMENT LISTENER FOR CHECK EMPTY" defaultstate="collapsed">
                    boolean alreadyEnabled = false;
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        enableButton();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        handleEmptyTextField(e);
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        if (!handleEmptyTextField(e))
                            enableButton();
                    }
                    
                    private void enableButton() {
                        if (!alreadyEnabled) {
                            createdButton.setEnabled(true);
                        }
                    }

                    private boolean handleEmptyTextField(DocumentEvent e) {
                        if (e.getDocument().getLength() <= 0) {
                            createdButton.setEnabled(false);
                            alreadyEnabled = false;
                            return true;
                        }
                        return false;
                    }
                    //</editor-fold>
                });
                
                createdButton.addActionListener(new AbstractAction(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == createdButton) {
                            if (nameTextField.getText() == null) return;
                            ChoiceOperationUtil.openVehicleListTab(funcPane, 
                                    new VehicleList(funcPane.getVehicleList().searchByName(nameTextField.getText()))
                            );
                            nameTextField.setText(null);
                            createdButton.setEnabled(false);
                        }
                    }
                });
                
                createdButton.setEnabled(false);
                gbc.gridwidth = 1;
                gbc.gridx = 1;
                this.add(nameTextField, gbc);
                gbc.gridx = 0;
                //</editor-fold>
            } case 2 -> {
                //<editor-fold desc="CREATE COMBO BOX FOR SELECTING VEHICLE TYPE" defaulstate="collapsed">
                InputComboBoxPanel inputPane = InputPanelFactory.createComboBoxPanel(this, null, consts.VEHICLETYPES);
                JComboBox typeBox = (JComboBox)inputPane.getInputComponent();
                
                createdButton.addActionListener(new AbstractAction(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == createdButton) {
                            String selectedType = typeBox.getSelectedItem().toString();
                            ChoiceOperationUtil.openVehicleListTab(funcPane, 
                                    new VehicleList(funcPane.getVehicleList().searchByType(selectedType))
                            );
                        }
                    }
                });
                
                gbc.gridwidth = 1;
                gbc.gridx = 1;
                this.add(typeBox, gbc);
                gbc.gridx = 0;
                //</editor-fold>
            } default -> {
                createdButton.addActionListener((e) -> {
                    if (e.getSource() == createdButton) {
                        funcPane.setChosenOption(buttonChoices.get(createdButton.getText()), consts.DISPLAY_MENU);
                        funcPane.repaint();
                        onGetVehicleList.invoke(funcPane.getVehicleList());
                    }
                });
            }
        }
        
        this.add(createdButton, gbc);
    }
    
    private void displayMenuBar (int choice) {
        buttonChoices.put(Menu.DISPLAYOPTIONS.get(choice), choice);
        JButton createdButton = createMenuChoice(menuChoices.get(choice));
        createdButton.addActionListener((e) -> {
            if (e.getSource() == createdButton) {
                funcPane.setChosenOption(buttonChoices.get(createdButton.getText()), consts.DISPLAY_MENU);
                funcPane.repaint();
                onGetVehicleList.invoke(funcPane.getVehicleList());
            }
        });
        
        this.add(createdButton, gbc);
    }
    
    private JButton createMenuChoice (String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(consts.UI_FONT);
        return button;
    }
    
    private CustomTextField createTextField (String defaultText) {
        CustomTextField newField = new CustomTextField((DocumentFilter)null);
        newField.setText(defaultText);
        newField.setBackgroundText("Enter vehicle ID");
        newField.setFont(consts.UI_FONT);
        newField.setBackground(consts.COMP_BG_COLOR);
        newField.setColumns(5);
        
        return newField;
    }
    
    private void checkIDInputted (String text) throws InvalidInputException, Exception{
        if (text.isBlank())
            throw new InvalidInputException("Input field is empty, please try again!");
        else if (!text.matches(consts.VEHICLEIDPATTERN))
            throw new InvalidInputException("Invalid Vehicle ID, please try again");
        else if (CheckAvailable.doesVehicleIDExist(text, funcPane.getVehicleList()) == null)
            throw new InvalidInputException(String.format("Vehicle ID %s does not exist, please try another one", text));
        else 
            throw new Exception(String.format("Vehicle with ID %s exist", text));
    }
}
