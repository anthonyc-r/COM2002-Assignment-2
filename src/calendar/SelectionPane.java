package calendar;

import enums.Partner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SelectionPane extends JPanel{
    
    public SelectionPane(JFrame parentF){
        this.parentF = parentF;
    }

    public void initComponents(){
        dentButton = new JRadioButton("Dentist");
        hygButton = new JRadioButton("Hygienist");
        monthField = new JTextField();
        yearField = new JTextField();

        //Default dent is selected
        dentButton.setSelected(true);

        dateLabel = new JLabel("Date(MM YYYY):");
        
        goButton = new JButton("Go!");


    }

    private void initListeners(){
        goButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(validateInput()){
                    setVariables();
                }
                else{
                    JOptionPane.showMessageDialog(parentF, "Not all inputs"+
                            " are valid!");
                }
            }
        });
    }

    private boolean validateInput(){
        return true;
    }

    private void setVariables(){
        //Set all instance variables appropriately
        //Indicate an update is needed.
        month = monthField.getText();
        year = monthField.getText();
        if(dentButton.isSelected()){
            partner = Partner.DENTIST;
        }
        else{
            partner = Partner.HYGIENIST;
        }
        updateRequired = true;
    }

    public void resetUpdateRequired(){
        updateRequired = false;
    }

    public String getSelectedMonth(){
        return month;
    }

    public String getSelectedYear(){
        return year;
    }
    
    public Partner getSelectedPartner(){
        return partner;
    }

    public boolean updateRequired(){
        return updateRequired;
    }

    private JFrame parentF = null;
    private JLabel dateLabel = null;
    private ButtonGroup buttonGroup = null;
    private JRadioButton dentButton = null;
    private JRadioButton hygButton = null;
    private JTextField monthField = null;
    private JTextField yearField = null;
    private JButton goButton = null;
    
    private Partner partner = null;
    private String month = null;
    private String year = null;

    private boolean updateRequired = false;
}
