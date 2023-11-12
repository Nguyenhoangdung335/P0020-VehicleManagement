package UI.util;

import UI.UIEntity.CustomDocumentFilter;
import UI.UIEntity.CustomTextField;
import UI.UIEntity.InputPanel;
import entity_list.VehicleList;
import exception.InvalidInputException;
import interfaces.IActionListener;
import javax.swing.JFrame;
import javax.swing.text.DocumentFilter;
import util.ConstVars;
import validator.CheckAvailable;
import validator.CheckValid;

public class DocumentFilterFactory implements IActionListener{
    private static VehicleList vList;
    private static JFrame mainFrame;
    private static ConstVars consts = ConstVars.getInstance();

    @Override
    public void update(Object o) {
        if (o instanceof VehicleList vehicleList)
            vList = vehicleList;
        if (o instanceof JFrame frame)
            mainFrame = frame;
    }
    
    //<editor-fold desc="CREATING ID DOCUMENT FILTER CLASS" defaultstate="collapsed">
    public static IDFilter getIDFilter (CustomTextField textField, String IDPattern) {
        return new IDFilter(textField, IDPattern);
    }
    
    public static IDFilter getIDFilter (CustomTextField textField, String IDPattern, boolean isCheckExist) {
        return new IDFilter(textField, IDPattern, isCheckExist);
    }
    
    public static IDFilter getIDFilter (InputPanel parent, String IDPattern) {
        return new IDFilter(parent, IDPattern);
    }
    
    public static IDFilter getIDFilter (InputPanel parent, String IDPattern, boolean isCheckExist) {
        return new IDFilter(parent, IDPattern, isCheckExist);
    }
    
    public static class IDFilter extends CustomDocumentFilter{
        private boolean isCheckExist = true;
        
        //<editor-fold desc="CONSTRUCTORS" defaultstate="collapsed">
        public IDFilter(InputPanel inputPanel) {
            super(inputPanel);
        }

        public IDFilter(CustomTextField textField) {
            super(textField);
        }

        public IDFilter(InputPanel inputPanel, String pattern) {
            super(inputPanel, pattern);
        }

        public IDFilter(CustomTextField textField, String pattern) {
            super(textField, pattern);
        }

        public IDFilter(InputPanel inputPanel, String pattern, boolean isCheckExist) {
            super(inputPanel, pattern);
            this.isCheckExist = isCheckExist;
        }

        public IDFilter(CustomTextField textField, String pattern, boolean isCheckExist) {
            super(textField, pattern);
            this.isCheckExist = isCheckExist;
        }
        //</editor-fold>
        
        @Override
        public void checkValid () throws InvalidInputException{
            if (wholeText != null) {
                if (!wholeText.matches(pattern))
                    throw new InvalidInputException("Invalid Vehicle ID, please try again");
                else if (!(CheckAvailable.doesVehicleIDExist(wholeText, vList) != null ^ isCheckExist)) {
                    if (isCheckExist)
                        throw new InvalidInputException("Vehicle ID already exist, please try another one");
                    else
                        throw new InvalidInputException("Vehicle ID does not exist, please try another one");
                }
            }
            resetError();
        }

        @Override
        protected void updateOnError(String errorText) {
            super.updateOnError(errorText);
            mainFrame.repaint();
        }

        @Override
        protected void resetError() {
            super.resetError();
            mainFrame.repaint();
        }
    }
    //</editor-fold>
    
    //<editor-fold desc="CREATING VEHICLE PRODUCT YEAR DOCUMENT FILTER CLASS" defaultstate="collapsed">
    public static YearFilter getYearFilter (InputPanel parent) {
        return new YearFilter(parent, null, 4);
    }
    
    public static class YearFilter extends CustomDocumentFilter{
        
        //<editor-fold desc="CONSTRUCTORS" defaultstate="collapsed">
        public YearFilter(InputPanel inputPanel) {
            super(inputPanel);
        }

        public YearFilter(CustomTextField textField) {
            super(textField);
        }

        public YearFilter(InputPanel inputPanel, String pattern) {
            super(inputPanel, pattern);
        }

        public YearFilter(CustomTextField textField, String pattern) {
            super(textField, pattern);
        }

        public YearFilter(InputPanel inputPanel, String pattern, int maxChar) {
            super(inputPanel, pattern, maxChar);
        }

        public YearFilter(CustomTextField textField, String pattern, int maxChar) {
            super(textField, pattern, maxChar);
        }
        //</editor-fold>
        
        @Override
        public void checkValid () throws InvalidInputException{
            if (wholeText != null) {
                CheckValid.isYearValid(wholeText);
            }
            resetError();
        }

        @Override
        protected void updateOnError(String errorText) {
            super.updateOnError(errorText);
            mainFrame.repaint();
        }

        @Override
        protected void resetError() {
            super.resetError();
            mainFrame.repaint();
        }
    }
    //</editor-fold>
    
    //<editor-fold desc="CREATING VEHICLE PRICE DOCUMENT FILTER CLASS" defaultstate="collapsed">
    public static PriceFilter getPriceFilter (InputPanel parent, String pricePattern) {
        return new PriceFilter(parent, pricePattern);
    }
    
    public static PriceFilter getPriceFilter (InputPanel parent, String pricePattern, int maxChar) {
        return new PriceFilter(parent, pricePattern, maxChar);
    }
    
    public static class PriceFilter extends CustomDocumentFilter{
        //<editor-fold desc="CONSTRUCTORS" defaultstate="collapsed">
        public PriceFilter(InputPanel inputPanel) {
            super(inputPanel);
        }

        public PriceFilter(CustomTextField textField) {
            super(textField);
        }

        public PriceFilter(InputPanel inputPanel, String pattern) {
            super(inputPanel, pattern);
        }

        public PriceFilter(CustomTextField textField, String pattern) {
            super(textField, pattern);
        }

        public PriceFilter(InputPanel inputPanel, String pattern, int maxChar) {
            super(inputPanel, pattern, maxChar);
        }

        public PriceFilter(CustomTextField textField, String pattern, int maxChar) {
            super(textField, pattern, maxChar);
        }
        //</editor-fold>
        
        @Override
        public void checkValid () throws InvalidInputException{
            if (wholeText != null) {
                if (!wholeText.matches(pattern))
                    throw new InvalidInputException("Invalid price format, please try again");
            }
            resetError();
        }

        @Override
        protected void updateOnError(String errorText) {
            super.updateOnError(errorText);
            mainFrame.repaint();
        }

        @Override
        protected void resetError() {
            super.resetError();
            mainFrame.repaint();
        }
    }
    //</editor-fold>
}
