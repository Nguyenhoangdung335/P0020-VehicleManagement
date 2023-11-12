package UI.UIEntity;

import java.awt.Color;
import java.awt.Component;
import javax.swing.text.DocumentFilter;

public class InputFieldPanel extends InputPanel{
    private CustomTextField inputField;
    
    public InputFieldPanel (Component parentPanel, String labelName, String fieldText) {
        super(parentPanel, labelName);
        
        this.inputLabel = createFieldLabel(labelName);
        this.inputField = createTextField(fieldText, null);
        this.errorText = createErrorLabel();
        inputLabel.setLocation(consts.DEFAULT_MARGIN, 0);
        inputField.setLocation(dividerXPoint + consts.DEFAULT_MARGIN, 0);
        errorText.setLocation(inputField.getX() + consts.DEFAULT_MARGIN, inputField.getHeight());
        
        this.add(inputLabel);
        this.add(inputField);
        this.add(errorText);
    }
    
    public InputFieldPanel (Component parentPanel, String labelName, String fieldText, DocumentFilter filter) {
        //this(parentPanel, labelName, fieldText);
        super(parentPanel, labelName);
        this.inputLabel = createFieldLabel(labelName);
        this.inputField = createTextField(fieldText, filter);
        this.errorText = createErrorLabel();
        
        inputLabel.setLocation(consts.DEFAULT_MARGIN, 0);
        inputField.setLocation(dividerXPoint + consts.DEFAULT_MARGIN, 0);
        errorText.setLocation(inputField.getX() + consts.DEFAULT_MARGIN, inputField.getHeight());
        
        this.add(inputLabel);
        this.add(inputField);
        this.add(errorText);
    }
    
    //<editor-fold desc="GETTERS AND SETTERS" defaultstate="collapsed">
    public String getFieldText () {
        return inputField.getText();
    }
    
    public void setFieldText (String newText) {
        this.inputField.setText(newText);
    }

    @Override
    public Component getInputComponent() {
        return inputField;
    }
    
    public void setDocumentFilter (DocumentFilter filter) {
        inputField.setDocumentFilter(filter);
    }

    @Override
    public String getInputValue() {
        return inputField.getText();
    }
    //</editor-fold>
    
    private CustomTextField createTextField (String defaultText, DocumentFilter filter) {
        CustomTextField newField;
        if (filter != null)
            newField = new CustomTextField(this, filter);
        else 
            newField = new CustomTextField(this);
        
        if (defaultText != null) newField.setText(defaultText);
        newField.setSize(inputSize);
        newField.setFont(consts.UI_FONT);
        newField.setBackground(consts.COMP_BG_COLOR);
        newField.setForeground(Color.BLACK);
        return newField;
    }
    
    public boolean isEmpty () {
        boolean isEmpty = false;
        if (inputField.getText() != null) {
            isEmpty = inputField.getText().isBlank() || inputField.getText().isEmpty();
        }
        return isEmpty;
    }
}
