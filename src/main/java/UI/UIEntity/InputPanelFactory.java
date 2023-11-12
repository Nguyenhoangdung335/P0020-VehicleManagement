package UI.UIEntity;

import java.awt.Color;
import java.awt.Component;
import javax.swing.text.DocumentFilter;

public class InputPanelFactory {
    //<editor-fold desc="METHODS FOR CREATING TEXT FIELD PANEL" defaultstate="collapsed">
    public static InputFieldPanel createTextFieldPanel (Component parentPanel) {
        return new InputFieldPanel(parentPanel, "Default label", null);
    }
    
    public static InputFieldPanel createTextFieldPanel (Component parentPanel, String labelName) {
        return new InputFieldPanel(parentPanel, labelName, null);
    }
    
    public static InputFieldPanel createTextFieldPanel (Component parentPanel, String labelName, String fieldText) {
        return new InputFieldPanel(parentPanel, labelName, fieldText);
    }
    
    public static InputFieldPanel createTextFieldPanel (Component parentPanel, String labelName, String fieldText, DocumentFilter filter) {
        return new InputFieldPanel(parentPanel, labelName, fieldText, filter);
    }
    //</editor-fold>
    
    //<editor-fold desc="METHODS FOR CREATING COLOR INPUT PANEL" defaultstate="collapsed">
    public static InputColorPanel createColorPanel (Component parent) {
        return new InputColorPanel(parent, "Default label", null);
    }
    
    public static InputColorPanel createColorPanel (Component parent, String labelName) {
        return new InputColorPanel(parent, labelName, null);
    }
    
    public static InputColorPanel createColorPanel (Component parent, String labelName, Color defaultColor) {
        return new InputColorPanel(parent, labelName, defaultColor);
    }
    //</editor-fold>
    
    //<editor-fold desc="METHODS FOR CREATING COMBO BOX PANEL" defaultstate="collapsed">
    public static InputComboBoxPanel createComboBoxPanel (Component parent) {
        return new InputComboBoxPanel(parent, "Default label", null);
    }
    
    public static InputComboBoxPanel createComboBoxPanel (Component parent, String labelName) {
        return new InputComboBoxPanel(parent, labelName, null);
    }
    
    public static InputComboBoxPanel createComboBoxPanel (Component parent, String labelName, String[] boxItems) {
        return new InputComboBoxPanel(parent, labelName, boxItems);
    }
    //</editor-fold>
}
