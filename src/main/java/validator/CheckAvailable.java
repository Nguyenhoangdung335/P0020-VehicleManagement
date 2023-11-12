package validator;

import entity.Vehicle;
import exception.PropertyDoesNotExistException;
import java.util.List;

/*--------------------------------------------------------------------------------------------------
--CheckAvailable Class checks for the availability of a product using its code in the main product--
------------------------------------list or receipt product list------------------------------------
--------------------------------------------------------------------------------------------------*/

public class CheckAvailable{
    public static Vehicle doesVehicleIDExist (String ID, List<Vehicle> list){
        for (var vehicle: list) {
            if (vehicle.getVehicleID().equals(ID))
                return vehicle;
        }
        return null;
    }
}