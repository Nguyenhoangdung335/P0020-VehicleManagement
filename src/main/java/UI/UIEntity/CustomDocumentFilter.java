package UI.UIEntity;

import exception.InvalidInputException;
import java.awt.Color;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import util.ConstVars;

public abstract class CustomDocumentFilter extends DocumentFilter{
    private static ConstVars consts = ConstVars.getInstance();
    
    protected int maxCharacter = 50;
    protected String pattern;
    protected String wholeText = "";

    protected InputPanel inputPanel;
    protected CustomTextField textField;

    //<editor-fold desc="CONSTRUCTORS" defaultstate="collapsed">
    public CustomDocumentFilter (InputPanel inputPanel) {
        this.inputPanel = inputPanel;
        this.textField = (CustomTextField)inputPanel.getInputComponent();
    }

    public CustomDocumentFilter (CustomTextField textField) {
        this.textField = textField;
    }

    public CustomDocumentFilter (InputPanel inputPanel, String pattern) {
        this.inputPanel = inputPanel;
        this.textField = (CustomTextField)inputPanel.getInputComponent();
        this.pattern = pattern;
    }

    public CustomDocumentFilter (CustomTextField textField, String pattern) {
        this.textField = textField;
        this.pattern = pattern;
    }

    public CustomDocumentFilter (InputPanel inputPanel, String pattern, int maxChar) {
        this.inputPanel = inputPanel;
        this.textField = (CustomTextField)inputPanel.getInputComponent();
        this.maxCharacter = maxChar;
        this.pattern = pattern;
    }

    public CustomDocumentFilter (CustomTextField textField, String pattern, int maxChar) {
        this.textField = textField;
        this.maxCharacter = maxChar;
        this.pattern = pattern;
    }
    //</editor-fold>

    //<editor-fold desc="GETTERS AND SETTERS" defaultstate="collapsed">
    public int getMaxCharacter() {
        return maxCharacter;
    }

    public void setMaxCharacter(int maxChar) {
        this.maxCharacter = maxChar;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String regex) {
        this.pattern = regex;
    }
    //</editor-fold>

    //<editor-fold desc="DOCUMENT FILTER METHOD" defaultstate="collapsed">
    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        try {
            if (wholeText.length() + string.length() <= maxCharacter) {
                super.insertString(fb, offset, string, attr);
                wholeText = fb.getDocument().getText(0, fb.getDocument().getLength());
            } else
                throw new InvalidInputException(String.format("Input need to be less than %d characters", maxCharacter));
            checkValid();
        } catch (InvalidInputException e) {
            System.out.println("Insert: " + e.getMessage());
            updateOnError(e.getMessage());
        }
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text != null) {
            try {
                if (wholeText.length() + text.length() <= maxCharacter) {
                    super.replace(fb, offset, length, text, attrs);
                    wholeText = fb.getDocument().getText(0, fb.getDocument().getLength());
                } else
                    throw new InvalidInputException(String.format("Input need to be less than %d characters", maxCharacter));
                checkValid();
            } catch (InvalidInputException e) {
                System.out.println("Replace: " + e.getMessage());
                updateOnError(e.getMessage());
            }
        } else {
            resetError();
            super.replace(fb, offset, length, text, attrs);
        }
    }

    @Override
    public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
        super.remove(fb, offset, length);
        wholeText = fb.getDocument().getText(0, fb.getDocument().getLength());
        resetError();
        if (wholeText.length() > 0) {
            try {
                checkValid();
            } catch (InvalidInputException e) {
                System.out.println("Remove!");
                updateOnError(e.getMessage());
            }
        }
    }
    //</editor-fold>

    //<editor-fold desc="CHECK VALIDATION AND UPDATE ERROR" defaultstate="collapsed">
    protected abstract void checkValid() throws InvalidInputException;

    protected void updateOnError (String errorText) {
        if (inputPanel != null) 
            inputPanel.setErrorText(errorText);
        if (textField != null) {
            ((RoundedCornerBorder)textField.getBorder()).setBorderColor(consts.ERROR_BORDER_COLOR);
            textField.setForeground(Color.red);
        }
    }

    protected void resetError () {
        if (inputPanel != null)
            inputPanel.setErrorText(null);
        if (textField != null) {
            ((RoundedCornerBorder)textField.getBorder()).setBorderColor(consts.DEFAULT_BORDER_COLOR);
            textField.setForeground(Color.BLACK);
        }
    }
    //</editor-fold>
}
