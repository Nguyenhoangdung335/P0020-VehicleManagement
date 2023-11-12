package main;

import entity_list.VehicleList;
import java.util.Arrays;
import manager.VehicleManager;
import util.ConstVars;
import util.DataOperation;
import util.FileManager;
import util.input.VehicleInputUtil;
import view.Menu;

public class ConsoleMainController {
    private static final VehicleManager vehicleM = new VehicleManager();
    private static final ConstVars consts = ConstVars.getInstance();
    
    public static void run () {
        while (true) {
            mainMenu();
        }
    }
    
    private static void mainMenu () {
        Menu.printMenu(Menu.MAINMENU);
        int choice = DataOperation.readInt("Enter your choice");
        switch (choice) {
            case 1 -> {
                if (vehicleM.addVehicle()) 
                    Menu.printStatement(Menu.SUCCESSFULSTATE);
                else 
                    Menu.printStatement(Menu.FAILSTATE);
            } case 2 -> {
                if (vehicleM.checkExist())
                    Menu.printStatement(Menu.SUCCESSFULSTATE);
                else 
                    Menu.printStatement(Menu.FAILSTATE);
            } case 3 -> {
                if (vehicleM.updateVehicle())
                    Menu.printStatement(Menu.SUCCESSFULSTATE);
                else 
                    Menu.printStatement(Menu.FAILSTATE);
            } case 4 -> {
                if (vehicleM.removeVehicle()) 
                    Menu.printStatement(Menu.SUCCESSFULSTATE);
                else 
                    Menu.printStatement(Menu.FAILSTATE);
            } case 5 -> {
                searchMenu();
            } case 6 -> {
                displayMenu();
            } case 7 -> {
                FileManager.saveFile("vehicle", vehicleM.getList());
            } case 8 -> {
                Menu.printStatement(Menu.CONTINUEMESSAGE);
                if (DataOperation.readChoice()) {
                    Menu.printStatement(Menu.SUCCESSFULSTATE);
                    System.exit(0);
                } else 
                    return;
            }
        }
    }
    
    private static void searchMenu () {
        Menu.printMenu(Menu.SEARCHOPTIONS);
        int choice = DataOperation.readInt("Enter your choice");
        VehicleList list = vehicleM.getList();
        switch (choice) {
            case 1 -> {
                String ID = VehicleInputUtil.enterVehicleID("Enter searching vehilce ID");
                if (vehicleM.printSearchResult(list.searchByID(ID)))
                    Menu.printStatement(Menu.SUCCESSFULSTATE);
                else 
                    Menu.printStatement(Menu.FAILSTATE);
            } case 2 -> {
                String name = VehicleInputUtil.enterVehicleName("Enter searching vehicle name");
                if (vehicleM.printSearchResult(list.searchByName(name)))
                    Menu.printStatement(Menu.SUCCESSFULSTATE);
                else 
                    Menu.printStatement(Menu.FAILSTATE);
            } case 3 -> {
                Menu.printMenu(Arrays.asList(consts.VEHICLETYPES));
                int typeChosen;
                do {
                    typeChosen = DataOperation.readInt("Enter your choice");
                    if (typeChosen < 1 || typeChosen > consts.VEHICLETYPES.length)
                        Menu.printStatement(Menu.INVALIDCHOICE);
                } while (typeChosen < 1 || typeChosen > consts.VEHICLETYPES.length);
                
                if (vehicleM.printSearchResult(list.searchByType(consts.VEHICLETYPES[typeChosen - 1])))
                    Menu.printStatement(Menu.SUCCESSFULSTATE);
                else 
                    Menu.printStatement(Menu.FAILSTATE);
            } case 4 -> {
                return;
            } default -> {
                Menu.printStatement(Menu.INVALIDCHOICE);
                searchMenu();
            }
        }
    }
    
    private static void displayMenu () {
        Menu.printMenu(Menu.DISPLAYOPTIONS);
        int choice = DataOperation.readInt("Enter your choice");
        VehicleList list = vehicleM.getList();
        switch (choice) {
            case 1 -> {
                list.showAll();
            } case 2 -> {
                list = list.sortByPrice(false);
                list.showAll();
            } case 3 -> {
                list = list.sortByType();
                list.showAll();
            } case 4 -> {
                return;
            } default -> {
                Menu.printStatement(Menu.INVALIDCHOICE);
                displayMenu();
            }
        }
    }
}