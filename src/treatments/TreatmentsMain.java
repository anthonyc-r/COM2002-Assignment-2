package treatments;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import forms.QueryHandler;
import interfaces.DPanel;

public class TreatmentsMain extends JPanel implements DPanel{

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
        qHand = new QueryHandler("team016", "eabb6f40");
        treatTable = new TreatmentsTable(parentF, this);
        pSelPane = new PatientSelectionPane(parentF, treatTable);
        submitButton = new JButton("Paid");

        costDueL = new JLabel("Cost due:");
        totalL = new JLabel("Total sum:");

        costDueF = new JTextField(4);
        totalF = new JTextField(4);
        //Make them uneditable
        costDueF.setEditable(false);
        totalF.setEditable(false);

        //Abs positioning.
        setLayout(null);

        add(pSelPane);
        add(treatTable);
        add(submitButton);
        add(totalL);
        add(totalF);
        add(costDueL);
        add(costDueF);

        //Set lsit
        setListeners();

        //setbounds
        pSelPane.setBounds(10, 10, 240, 40);
        treatTable.setBounds(10, 50, 240, 410); 
        submitButton.setBounds(10, 460, 80, 30);
        totalL.setBounds(100, 460, 80, 15);
        costDueL.setBounds(100, 475, 80, 15);
        totalF.setBounds(165, 460, 80, 15);
        costDueF.setBounds(165, 475, 80, 15);
    }

    public Dimension getPrefDims(){
        return TreatmentsMain.PREF_DIMS;
    }

    public void updateFields(String total, String owed){
        totalF.setText(total);
        costDueF.setText(owed);
    }

    private void updateCostsDue(){
        //Get vars
        String patID = pSelPane.getPatientID();
        //Set cost due in patient to 0.
        String updt = "UPDATE Patient "+
                        "SET amountOwed = 0 "+
                        "WHERE patientID = '"+patID+"';";
        int status = qHand.executeUpdate(updt);

        if(status >= 0){    
            JOptionPane.showMessageDialog(parentF, "Amount owed by patient"+
                    "set to 0.00");
        }else{
            throw new RuntimeException("Query handler indicates error in "+
                    "update.");
        }
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
    private JLabel totalL = null;
    private JLabel costDueL = null;
    private JTextField totalF = null;
    private JTextField costDueF = null;
    
    private QueryHandler qHand = null;
    
    public static final Dimension PREF_DIMS = new Dimension(280, 540);
}
