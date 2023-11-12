package util;

import java.awt.Color;

public class ColorUtil {
    public static Color hexToColor (String hexCode) {
        return Color.decode(hexCode);
    }
    
    public static String colorToHex (Color color) {
        return (color == null)? null: String.format("#%06x", (color.getRGB() & 0x00ffffff));
    }
    
    public static void main(String[] args) {
        Color test = hexToColor("#ff0000");
        System.out.println(test.getRed() + " " + test.getGreen() + " " + test.getBlue());
        System.out.println(colorToHex(Color.BLUE));
    }
}
