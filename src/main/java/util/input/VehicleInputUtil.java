package util.input;

import entity.Vehicle;
import exception.InvalidInputException;
import java.util.List;
import util.DataOperation;
import validator.CheckAvailable;
import validator.CheckValid;

public class VehicleInputUtil {
    public static String enterVehicleID (String message, Boolean existCheckException, List<Vehicle> list) {
        String input = null;
        try {
            input = DataOperation.readString(message).toUpperCase();
            CheckValid.isVehicleIDValid(input);
            if (existCheckException == null) {}
            else if ((CheckAvailable.doesVehicleIDExist(input, list) != null) ^ existCheckException) {
                if (existCheckException)
                    throw new Exception("Vehicle ID already exist");
                else 
                    throw new Exception("Vehicle ID does not exist");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            enterVehicleID(message, existCheckException, list);
        }
        return input;
    }
    
    public static String enterVehicleID (String message) {
        String input = null;
        try {
            input = DataOperation.readString(message).toUpperCase();
            CheckValid.isVehicleIDValid(input);
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
            enterVehicleID(message);
        }
        return input;
    }
    
    public static String enterVehicleName (String message) {
        String input = null;
        try {
            input = DataOperation.readString(message);
            CheckValid.isVehicleNameValid(input);
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
            enterVehicleName(message);
        }
        return DataOperation.firstLetterUpper(input);
    }
    
    public static String enterVehicleHexColor (String message) {
        String input = null;
        try {
            input = DataOperation.readString(message);
            CheckValid.isVehicleColorValid(input);
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
            enterVehicleHexColor(message);
        }
        return input.toUpperCase();
    }
    
    public static double enterVehiclePrice (String message) {
        double input = 0;
        try {
            input = DataOperation.readDouble(message);
            CheckValid.isNumPositive(input);
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
            enterVehiclePrice(message);
        }
        return input;
    }
    
    public static String enterVehicleBrand (String message) {
        String input = DataOperation.readString(message);
        return input;
    }
    
    public static String enterVehicleType (String message) {
        String input = null;
        try {
            input = DataOperation.readString(message);
            CheckValid.isVehicleTypeValid(input);
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
            enterVehicleType(message);
        }
        return DataOperation.firstLetterUpper(input);
    }
    
    public static String enterVehicleProductYear (String message) {
        String input = null;
        try {
            input = DataOperation.readString(message);
            CheckValid.isYearValid(input);
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
            enterVehicleProductYear(message);
        }
        return input;
    }
}
