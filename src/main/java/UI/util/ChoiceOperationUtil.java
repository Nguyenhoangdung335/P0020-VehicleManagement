package UI.util;

import UI.MainFunctionPane;
import UI.MenuPanel;
import UI.VehicleInfoPanel;
import UI.VehicleListPanel;
import entity.Vehicle;
import entity_list.VehicleList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import util.ConstVars;
import view.Menu;

public class ChoiceOperationUtil {
    private static ConstVars consts = ConstVars.getInstance();
    
    public static void openVehicleInfoTab (MainFunctionPane mainP, Vehicle vehicleInfo) {
        VehicleInfoPanel newPanel = (vehicleInfo != null)? 
                new VehicleInfoPanel(mainP, vehicleInfo): 
                new VehicleInfoPanel(mainP);
        JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setSize(newPanel.getSize());
        scrollPane.setViewportView(newPanel);
        scrollPane.setBounds(0, 0, mainP.getWidth(), mainP.getHeight());
        newPanel.setParentScrollPane(scrollPane);
        
        mainP.updateOpenTab(scrollPane);
    }
    
    public static void openNewVehicleTab (MainFunctionPane mainP) {
        openVehicleInfoTab(mainP, null);
    }
    
    public static void removeVehicleInfo (MainFunctionPane mainP, Vehicle removeV) {
        int choice = JOptionPane.showConfirmDialog(
                mainP, 
                String.format("Do you want to delete Vehicle ID %s?",removeV.getVehicleID()), 
                "Continue?",
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE
        );
        if (choice == 0) {
            mainP.getVehicleList().deleting(removeV);
        }
    }
    
    public static void openSubMenu (MainFunctionPane mainP, int menuChoices) {
        MenuPanel newPanel;
        if (menuChoices == consts.SEARCH_MENU) {
            System.out.println("CREATING SEARCH MENU");
            newPanel = new MenuPanel(mainP, Menu.SEARCHOPTIONS);
        } else if (menuChoices == consts.DISPLAY_MENU) {
            System.out.println("CREATING DISPLAY MENU");
            newPanel = new MenuPanel(mainP, Menu.DISPLAYOPTIONS);
        } else {
            System.out.println("CREATING MAIN MENU");
            newPanel = new MenuPanel(mainP, Menu.MAINMENU);
        }
        
        newPanel.setBounds(0, 0, mainP.getWidth(), mainP.getHeight());
        mainP.updateOpenTab(newPanel);
    }
    
    public static void openVehicleListTab (MainFunctionPane mainP) {
        openVehicleListTab(mainP, mainP.getVehicleList());
    }
    
    public static void openVehicleListTab (MainFunctionPane mainP, VehicleList vList) {
        VehicleListPanel listPanel = new VehicleListPanel(mainP, vList);
        listPanel.setBounds(0, 0, mainP.getWidth(), mainP.getHeight());
        mainP.updateOpenTab(listPanel);
    }
}
