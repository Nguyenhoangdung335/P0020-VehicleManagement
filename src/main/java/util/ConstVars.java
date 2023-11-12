package util;

import java.awt.Color;
import java.awt.Font;
import java.time.format.DateTimeFormatter;

public final class ConstVars {
    public final String[] FILENAMES = {
        "VEHICLE"
    };
    public final String[] VEHICLETYPES = {
        "SEDAN", "COUPE", "SPORTS CAR", "STATION WAGON", "HATCHBACK", "CONVERTIBLE", "SUV", "MINIVAN", "PICKUP TRUCK"
    };
    public final int MAIN_MENU = 0,
            SEARCH_MENU = 1,
            DISPLAY_MENU = 2;
    
    public final DateTimeFormatter TIMEPATTERN = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    public final DateTimeFormatter DATEPATTERN = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public final DateTimeFormatter YEARPATTERN = DateTimeFormatter.ofPattern("yyyy");
    
    public final String VEHICLEIDPATTERN = "V\\d{4}";
    public final String NAMEPATTERN = "^[a-zA-Z0-9[ ]]*$";
    public final String CONTACTNUMPATTERN = "^0\\d{9}";
    public final String HEXCODEPATTERN = "^#[0-9a-fA-F]{6}$";
    public final String PRICE_PATTERN = "^[0-9[.]]*$";
    
    public final int S_WIDTH = 1000;
    public final int S_HEIGHT = 600;
    public final Font UI_FONT = new Font("Serif", Font.PLAIN, 17);
    public final Color COMP_BG_COLOR = new Color(190, 190, 190);
    public final Color PANEL_BG_COLOR = new Color(210, 210, 210);
    public final Color ERROR_BORDER_COLOR = new Color(225, 0, 0);
    public final Color DEFAULT_BORDER_COLOR = Color.GRAY;
    public final Color ALPHA_COLOR = new Color (0, 0, 0, 0);
    public final int DEFAULT_MARGIN = 15;
    
    
    private static class SingletonHolder {
        public static final ConstVars instance = new ConstVars();
    }
    
    public static ConstVars getInstance () {
        return SingletonHolder.instance;
    }
}