package util;

import java.util.Scanner;

/*------------------------------------------------------------------------------------------------------
--DataOperation Class read user input, check for blank/empty input, and convert input to wanted format--
------------------------------------------------------------------------------------------------------*/

public class DataOperation{
    private static final Scanner sc = new Scanner(System.in);
    
    //Read string input until there is data input by the user, then format it
    public static String readString (String message) {
        String input;
        System.out.printf("%s: ", message);
        do {
            input = sc.nextLine();
        } while (input.isBlank() || input.isEmpty());
        return input.trim();
    }
    
    //Read int input until there is data input by the user and is of type integer
    public static int readInt (String message) {
        int input = 0;
        System.out.printf("%s: ", message);        
        while (true) {
            try {
                input = Integer.parseInt(sc.nextLine());
                if (Integer.toString(input).isBlank())
                    throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {}
        } 
        return input;
    }
    
    public static long readLong (String message) {
        long input = 0;
        System.out.printf("%s: ", message);        
        while (true) {
            try {
                input = Long.parseLong(sc.nextLine());
                if (Long.toString(input).isBlank())
                    throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {}
        }
        return input;
    }
    
    public static double readDouble (String message) {
        double input = 0;
        System.out.printf("%s: ", message);        
        while (true) {
            try {
                input = Double.parseDouble(sc.nextLine());
                if (Double.toString(input).isBlank())
                    throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {}
        }
        return input;
    }
    
    //Read user choice, only return true when user input "yes", "y", "t", "true".
    public static Boolean readChoice () {
        String input;
        do {
            input = sc.nextLine();
        } while (input.isBlank());
        
        return (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y") || 
                input.equalsIgnoreCase("t") || input.equalsIgnoreCase("true"));
    }
    
    //Format user string input with uppercase for each letter in a sentence
    public static String firstLetterUpper (String str) {
        if (str.isBlank()) return null;
        String[] strArr = str.trim().split("\\s+");
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = strArr[i].substring(0, 1).toUpperCase() +
                    strArr[i].substring(1).toLowerCase();
        }
        return String.join(" ", strArr);
    }
}