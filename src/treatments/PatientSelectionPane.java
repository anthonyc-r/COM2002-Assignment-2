package treatments;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PatientSelectionPane extends JPanel{
    
    public PatientSelectionPane(JFrame parentF, TreatmentsTable treatTable){
        this.parentF = parentF;
        this.treatTable = treatTable;
        
        setLayout(new FlowLayout());
        initFields();
        addFields();
        addListeners();
    }

    private void initFields(){
        patientLabel = new JLabel("PatientID:");
        patientField = new JTextField(10);
        goButton = new JButton("Go!");
    }

    private void addFields(){
        add(patientLabel);
        add(patientField);
        add(goButton);
    }

    private void updateTable(){
        String patientID = patientField.getText();
        treatTable.update(patientID);
    }

    private void addListeners(){
        goButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                updateTable();                
            }
        });
    }


    //fields
    private JLabel patientLabel = null;
    private JTextField patientField = null;
    private JButton goButton = null;

    private JFrame parentF;
    private TreatmentsTable treatTable;

}
