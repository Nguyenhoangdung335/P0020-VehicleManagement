package UI;

import UI.util.ChoiceOperationUtil;
import entity_list.VehicleList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import util.ConstVars;
import util.FileManager;
import view.Menu;

public class MainFunctionPane extends JPanel{
    private static final ConstVars consts = ConstVars.getInstance();
    protected static final int listWidth = consts.S_WIDTH - MenuPanel.menuWidth;
    private Border border = new LineBorder(Color.BLACK, 1);
    private VehicleList vList;
    
    private int chosenOption;
    private int topLayerIndex;
    private List<Component> openedPanel;
    private JLayeredPane layeringPanel;
    
    public MainFunctionPane () {
        this.chosenOption = 0;
        this.topLayerIndex = 0;
        this.openedPanel = new LinkedList<>();
        this.vList = new VehicleList();
        
        this.setPreferredSize(new Dimension(listWidth, consts.S_HEIGHT));
        this.setSize(this.getPreferredSize());
        this.setBounds(0, 0, listWidth, consts.S_HEIGHT);
        this.setBorder(border);
        this.setLayout(new BorderLayout());
        this.setBackground(consts.PANEL_BG_COLOR);
        
        this.layeringPanel = new JLayeredPane();
        layeringPanel.setSize(this.getPreferredSize());
        layeringPanel.setPreferredSize(this.getPreferredSize());
        layeringPanel.setLocation(this.getX(), 0);
        
        this.add(layeringPanel);
    }
    
    //<editor-fold desc="GETTERS AND SETTERS" defaultstate="collapsed">
    public int getChosenOption() {
        return chosenOption;
    }

    public void setChosenOption(int chosenOption, int menuChoices) {
        if (menuChoices == consts.SEARCH_MENU)
            setSearchMenuChosen(chosenOption);
        else if (menuChoices == consts.DISPLAY_MENU)
            setDisplayMenuChosen(chosenOption);
        else 
            setMainMenuChosen(chosenOption);
    }
    
    public List<Component> getOpenedPanel () {
        return openedPanel;
    }
    
    public VehicleList getVehicleList () {
        return vList;
    }
    
    public JLayeredPane getLayeredPane () {
        return layeringPanel;
    }
    //</editor-fold>
    
    //<editor-fold desc="MENU CHOICES REDIRECT" defaultstate="collapsed">
    
        //<editor-fold desc="MAIN MENU CHOICES" defaultstate="collapsed">
        private void setMainMenuChosen (int chosenOption) {
            resetAllTabs();
            this.chosenOption = chosenOption;
            mainChoiceRedirect();
        }

        private void mainChoiceRedirect () {
            System.out.println("Main choice: " + chosenOption);
            switch (chosenOption) {
                case 0 -> {
                    System.out.println("Open new");
                    ChoiceOperationUtil.openNewVehicleTab(this);
                } case 4 -> {
                    ChoiceOperationUtil.openSubMenu(this, consts.SEARCH_MENU);
                    printAllTab();
                } case 5 -> {
                    ChoiceOperationUtil.openSubMenu(this, consts.DISPLAY_MENU);
                    printAllTab();
                } case 6 -> {
                    FileManager.saveFile("vehicle", vList);
                    JOptionPane.showMessageDialog(
                            this, 
                            "Successfully save file", 
                            "Save file", 
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } case 7 -> {
                    ExitPane.exitOption();
                } default -> {
                    System.out.println("Choice have yet to be implemented");
                }
            }
        }
        //</editor-fold>
    
        //<editor-fold desc="SEARCH MENU CHOICES" defaultstate="collapsed">
        private void setSearchMenuChosen (int chosenOption) {
            if (chosenOption == 3) this.returnATab(null);
            this.chosenOption = chosenOption;
            searchChoiceRedirect();
        }

        private void searchChoiceRedirect () {
            System.out.println("Search choice: " + chosenOption);
            switch (chosenOption) {
                case 3 -> {
                    this.returnATab(null);
                } default -> {
                    System.out.println("Choice have yet to be implemented");
                }
            }
        }
        //</editor-fold>
    
        //<editor-fold desc="DISPLAY MENU CHOICES" defaultstate="collapsed">
        private void setDisplayMenuChosen (int chosenOption) {
            if (chosenOption == 3) this.returnATab(null);
            this.chosenOption = chosenOption;
            displayChoiceRedirect();
        }

        private void displayChoiceRedirect () {
            System.out.println("Display choice: " + chosenOption);
            switch (chosenOption) {
                case 0 -> {
                    ChoiceOperationUtil.openVehicleListTab(this);
                } case 1 -> {
                    ChoiceOperationUtil.openVehicleListTab(this, vList.sortByPrice(false));
                } case 2 -> {
                    ChoiceOperationUtil.openVehicleListTab(this, vList.sortByType());
                } case 3 -> {
                    this.returnATab(null);
                } default -> {
                    System.out.println("Choice have yet to be implemented");
                }
            }
        }
        //</editor-fold>
    //</editor-fold>
    
    
    public void resetAllTabs () {
        layeringPanel.removeAll();
        openedPanel.clear();
        topLayerIndex = 0;
        this.repaint();
    }
    
    public void returnATab (Component currentTab) {
        if (topLayerIndex > 1 && currentTab != null) {
            layeringPanel.remove(currentTab);
            openedPanel.remove(currentTab);
            topLayerIndex--;
            this.repaint();
            printAllTab();
        } else 
            resetAllTabs();
    }
    
    public void updateOpenTab (Component newTab) {
        openedPanel.add(newTab);
        layeringPanel.add(newTab, Integer.valueOf(topLayerIndex++));
        System.out.println("\t\tCURRENT TAB OPENED");
        System.out.println(layeringPanel.getComponentsInLayer(topLayerIndex - 1));
    }
    
    private void printAllTab () {
        for (var comp: layeringPanel.getComponents()) {
            System.out.println(comp);
        }
    }
}
