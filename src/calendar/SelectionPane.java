package calendar;

import enums.Partner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SelectionPane extends JPanel{
    
    public SelectionPane(JFrame parentF, CalendarPane cPane){
        this.parentF = parentF;
        this.cPane = cPane;
        
        setLayout(new FlowLayout());

        initComponents();
        addComponents();
        initListeners();
    }

    public void initComponents(){
        dentButton = new JRadioButton("Dentist");
        hygButton = new JRadioButton("Hygienist");
        monthField = new JTextField(2);
        yearField = new JTextField(4);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(dentButton);
        buttonGroup.add(hygButton);

        //Default dent is selected
        dentButton.setSelected(true);

        dateLabel = new JLabel("Date(MM YYYY):");
        
        goButton = new JButton("Go!");


    }

    private void addComponents(){
        add(dentButton);
        add(hygButton);
        add(dateLabel);
        add(monthField);
        add(yearField);
        add(goButton);
    }

    private void initListeners(){
        goButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(validateInput()){
                    setVariables();
                    cPane.update(partner, month, year);
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
        year = yearField.getText();
        if(dentButton.isSelected()){
            partner = Partner.DENTIST;
        }
        else{
            partner = Partner.HYGIENIST;
        }
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


    private JFrame parentF = null;
    private JLabel dateLabel = null;
    private ButtonGroup buttonGroup = null;
    private JRadioButton dentButton = null;
    private JRadioButton hygButton = null;
    private JTextField monthField = null;
    private JTextField yearField = null;
    private JButton goButton = null;

    private CalendarPane cPane = null;
    
    private Partner partner = null;
    private String month = null;
    private String year = null;

    private boolean updateRequired = false;
}
