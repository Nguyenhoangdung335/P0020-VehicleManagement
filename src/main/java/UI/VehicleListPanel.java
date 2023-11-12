package UI;

import UI.util.ChoiceOperationUtil;
import entity.Vehicle;
import entity_list.VehicleList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import util.ConstVars;

public class VehicleListPanel extends JPanel implements ListSelectionListener{
    private MainFunctionPane mainP;
    private VehicleList vList;
    private ConstVars consts = ConstVars.getInstance();
    
    private JList jList;
    private DefaultListModel listModel;
    private JPanel navPanel;
    private JButton returnButton;
    private JButton editButton;
    
    private Dimension listPaneSize;
    private Dimension navPanelSize;
    
    public VehicleListPanel (MainFunctionPane mainP, VehicleList vList) {
        System.out.println("Creating List Panel");
        this.mainP = mainP;
        this.vList = vList;
        
        this.listPaneSize = new Dimension(mainP.getWidth(), 0);
        this.navPanelSize = new Dimension(mainP.getWidth() - 100, 50);
        
        this.setLayout(new BorderLayout());
        this.setBounds(0, 0, mainP.getWidth(), mainP.getHeight());
        this.setBackground(Color.CYAN);
        this.setOpaque(true);
        
        initJList();
        createNavPanel();
    }
    
    //<editor-fold desc="CREATE LIST PANEL" defaultstate="collapsed">
    private void createListModel () {
        this.listModel = new DefaultListModel();
        for (var vehicle: vList) {
            listModel.addElement(vehicle);
        }
    }
    
    private void initJList () {
        createListModel();
        this.jList = new JList(listModel);
        jList.setCellRenderer(new VehicleListCellRenderer());
        jList.setPreferredSize(listPaneSize);
        jList.setBounds(0, 0, listPaneSize.width, 200);
        jList.setBackground(consts.ALPHA_COLOR);
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList.addListSelectionListener(this);
        jList.setSelectionModel(new CustomListSelectionModel());
        
        this.add(jList, BorderLayout.CENTER);
    }
    //</editor-fold>
    
    //<editor-fold desc="CREATE NAVIGATION PANEL" defaultstate="collapsed">
    private void createNavPanel () {
        this.navPanel = new JPanel();
        navPanel.setPreferredSize(navPanelSize);
        
        this.returnButton = createButton("Return");
        returnButton.addActionListener((e) -> {
            System.out.println("Return to previous tab");
            mainP.returnATab(this);
        });
        this.editButton = createButton("Edit");
        editButton.addActionListener(new EditListener(editButton));
        
        navPanel.add(returnButton);
        navPanel.add(editButton);
        
        this.add(navPanel, BorderLayout.NORTH);
    }
    
    private JButton createButton (String defaultText) {
        JButton button = new JButton(defaultText);
        button.setFont(consts.UI_FONT);
        button.setFocusPainted(false);
        return button;
    }
    //</editor-fold>
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        this.repaint();
        if (e.getValueIsAdjusting() == false) {
            if (jList.getSelectedIndex() == -1) 
                editButton.setEnabled(false);
            else 
                editButton.setEnabled(true);
        }
    }
    
    //<editor-fold desc="FOR RENDERING LIST ITEM WITH IMAGE IF EXIST" defaultstate="collapsed">
    class VehicleListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            Vehicle cellObj = (Vehicle)value;
            label.setText(String.format("%s - %s", cellObj.getVehicleID(), cellObj.getName()));
            if (cellObj.getVehicleImage() != null) {
                label.setIcon(new ImageIcon(cellObj.getVehicleImage()));
            }
            label.setHorizontalTextPosition(JLabel.RIGHT);
            label.setFont(consts.UI_FONT.deriveFont(Font.BOLD,25f));
            return label;
        }
    }
    //</editor-fold>
    
    //<editor-fold desc="EDIT BUTTON LISTENER CLASS" defaultstate="collapsed">
    class EditListener implements ActionListener{
        private boolean alreadyEnabled = false;
        private JButton button;
        
        public EditListener (JButton editButton) {
            this.button = editButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Vehicle selected = (Vehicle)jList.getSelectedValue();
            ChoiceOperationUtil.openVehicleInfoTab(mainP, selected);
        }
    }
    //</editor-fold>
    
    //<editor-fold desc="CUSTOME LIST SELECTION MODEL CLASS" defaultstate="collapsed">
    class CustomListSelectionModel extends DefaultListSelectionModel {
        @Override
        public void setSelectionInterval(int index0, int index1) {
            if (index0 == index1) {
                if (isSelectedIndex(index0)) {
                    removeSelectionInterval(index0, index0);
                    return;
                }
            }
            super.setSelectionInterval(index0, index1);
        }

        @Override
        public void addSelectionInterval(int index0, int index1) {
            if (index0 == index1) {
                if (isSelectedIndex(index0)) {
                    removeSelectionInterval(index0, index0);
                    return;
                }
                super.addSelectionInterval(index0, index1);
            }
        }
    }
    //</editor-fold>
}
