package treatments;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import forms.QueryHandler;

public class TreatmentsMain extends JPanel{

    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TreatmentsMain(frame));
        //NOTE: Adding layout=null here screws up the JPanel layout.
        frame.setSize(TreatmentsMain.PREF_DIMS);
        frame.setResizable(true); 
        frame.setVisible(true);    
    }

    public TreatmentsMain(JFrame parentF){
        this.parentF = parentF;
        treatTable = new TreatmentsTable(parentF);
        pSelPane = new PatientSelectionPane(parentF, treatTable);
        submitButton = new JButton("Submit");

        //Abs positioning.
        setLayout(null);

        add(pSelPane);
        add(treatTable);
        add(submitButton);

        //setbounds
        pSelPane.setBounds(10, 10, 240, 40);
        treatTable.setBounds(10, 50, 240, 410); 
        submitButton.setBounds(10, 460, 80, 30);
    }

    private void updateCostsDue(){
        //Set cost due in patient to 0.
        String updt = "";
        qHand.executeUpdate(updt);
    }

    private void setListeners(){
        submitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                updateCostsDue();
            }
        });
    }

    private PatientSelectionPane pSelPane = null;
    private TreatmentsTable treatTable = null;
    private JFrame parentF = null; 
    private JButton submitButton = null;
    
    private QueryHandler qHand = null;
    
    public static final Dimension PREF_DIMS = new Dimension(280, 540);
}
