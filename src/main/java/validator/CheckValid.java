package validator;

import exception.InvalidFileNameExcception;
import exception.InvalidInputException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import util.ConstVars;

/*----------------------------------------------------------------------------------------------
-------The CheckValid class check for the validity of data passed through the parameters--------
-----------------------------------------------------------------------------------------------*/

public class CheckValid{
    static private ConstVars consts = ConstVars.getInstance();    
    
    public static void isFileValid (String fileName) throws InvalidFileNameExcception{
        for (String name: consts.FILENAMES) {
            if (name.equalsIgnoreCase(fileName))
                return;
        }
        throw new InvalidFileNameExcception("There is no such file name!");
    }
    
    public static void isTimeValid (String date) throws InvalidInputException {
        try {
            LocalDate.parse(date, consts.TIMEPATTERN);
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Invalid time format, please try again!");
        }
    }
    
    public static void isYearValid (String year) throws InvalidInputException {
        try {
            int temp = Integer.parseInt(year);
            if (temp < 1000 || temp > 9999)
                throw new Exception();
        } catch (Exception e) {
            throw new InvalidInputException("Invalid year format, please try again!");
        }
    }
    
    public static <T extends Number> void isNumPositive (T num) throws InvalidInputException{
        if (num.toString().compareTo("0") < 0) 
            throw new InvalidInputException("Number should be positive");
    }
    
    public static void isVehicleIDValid (String ID) throws InvalidInputException {
        if (!ID.matches(consts.VEHICLEIDPATTERN)) 
            throw new InvalidInputException("Invalid vehicle ID, please try again!");
    }
    
    public static void isVehicleNameValid (String name) throws InvalidInputException {
        if (!name.matches(consts.NAMEPATTERN))
            throw new InvalidInputException("Invalid vehicle name, please try again!");
    }
    
    public static boolean isVehicleTypeValid (String type) throws InvalidInputException {
        for (var eachType: consts.VEHICLETYPES) {
            if (eachType.equalsIgnoreCase(type))
                return true;
        }
        throw new InvalidInputException("Invalid vehicle type, please try again");
    }
    
    public static void isVehicleColorValid (String hex) throws InvalidInputException {
        if (!hex.matches(consts.HEXCODEPATTERN))
            throw new InvalidInputException("Invalid vehicle color hex code, please try again");
    }
}