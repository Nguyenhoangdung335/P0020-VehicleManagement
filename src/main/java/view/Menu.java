package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*------------------------------------------------------------------------------
---------Menu class hold information for what to print for the menu and---------
---------------------methods for printing menu or statement---------------------
------------------------------------------------------------------------------*/

public class Menu {
    //<editor-fold desc="MENU INFORMATION" defaultstate="collapsed">
    static final public List<String> MAINMENU = new ArrayList(Arrays.asList(
            "Add new vehicle",
            "Check if vehicle exist",
            "Update vehicle information",
            "Remove vehicle",
            "Search for vehicle information",
            "Display all vehilcle information",
            "Save to file",
            "Exit"
    ));
    
    static final public List<String> SEARCHOPTIONS = new ArrayList(Arrays.asList(
            "Search by vehicle ID",
            "Search by vehicle name",
            "Search by vehicle type",
            "Return"
    ));
    
    static final public List<String> DISPLAYOPTIONS = new ArrayList(Arrays.asList(
            "Display all vehilce information",
            "Display all vehicle information descending by price",
            "Display all vehicle information grouped by type",
            "Return"
    ));
    //</editor-fold>
    
    //<editor-fold desc="STATEMENT INFORMATION" defaultstate="collapsed">
    static final public String CONTINUEMESSAGE = "DO YOU WISH TO CONTINUE? Y/N";
    static final public String SUCCESSFULSTATE = "THE OPERATION WAS A SUCCESS";
    static final public String FAILSTATE = "THE OPERATION WAS A FAILURE";
    static final public String SUCCESSFULREGISTER = "SUCCESSFULLY REGISTER, PLEASE LOG IN YOUR ACCOUNT";
    static final public String SUCCESSFULLOGIN = "SUCCESSFULLY LOGGING IN";
    static final public String INVALIDCHOICE = "INVALID USER CHOICE, PLEASE TRY AGAIN!";
    //</editor-fold>
    
    static public void printStatement (String choice) {
        System.out.println("------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------");
        System.out.println(choice);
    }
    
    static public void printMenu (List<String> menu) {
        System.out.println("------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------");
        int counter = 0;
        for (String choice: menu) {
            System.out.printf("%d. %s. \n", ++counter, choice);
        }
        System.out.println("------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------");
    }
}