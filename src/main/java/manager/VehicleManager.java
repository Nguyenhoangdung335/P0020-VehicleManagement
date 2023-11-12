package manager;

import entity.Vehicle;
import entity_list.VehicleList;
import exception.PropertyDoesNotExistException;
import java.util.List;
import util.DataOperation;
import util.input.VehicleInputUtil;
import validator.CheckAvailable;
import view.Menu;

public class VehicleManager {
    VehicleList list;

    public VehicleManager() {
        this.list = new VehicleList();
    }

    public VehicleList getList() {
        return list;
    }
    
    public boolean addVehicle () {
        Vehicle newVehicle = new Vehicle();
        changeIDIfExist(newVehicle);
        newVehicle.input();
        list.addNew(newVehicle);
        return true;
    }
    
    private void changeIDIfExist (Vehicle newElement) {
        boolean isIDAvailable = false;
        do {
            if (CheckAvailable.doesVehicleIDExist(newElement.getVehicleID(), list) != null)
                isIDAvailable = true;
            if (isIDAvailable)
                newElement.setIDNumber(newElement.getIDNumber() + 1);
        } while (isIDAvailable);
    }
    
    public boolean checkExist () {
        String ID = VehicleInputUtil.enterVehicleID("Enter the vehicle ID");
        Vehicle enteredVehicle = list.get(ID);
        System.out.printf("Vehicle ID %s%s exist\n", ID, (list.isExist(enteredVehicle))? "": " does not");
        return true;
    }
    
    public boolean updateVehicle () {
        String ID = VehicleInputUtil.enterVehicleID("Enter updating vehicle ID", true, list);
        Vehicle updatingV = list.get(ID);
        list.updating(updatingV);
        return true;
    }
    
    public boolean removeVehicle () {
        String ID = VehicleInputUtil.enterVehicleID("Enter removing vehicle ID", true, list);
        Vehicle removingV = list.get(ID);
        
        Menu.printStatement(Menu.CONTINUEMESSAGE);
        if (DataOperation.readChoice()) {
            list.deleting(removingV);
            return true;
        }
        return false;
    }
    
    public boolean printSearchResult (List<Vehicle> list) {
        if (list == null || list.isEmpty())
            System.out.println("\tTHERE IS NO ENTRY");
        else {
            int counter = 0;
            for (var vehicle: list) {
                System.out.printf("%d. %s\n", ++counter, vehicle.toString());
            }
        }
        return true;
    }
}
